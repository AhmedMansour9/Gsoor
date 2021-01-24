package com.gsoor.admin.ui.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.adapter.CountriesAdapter
import com.gsoor.admin.adapter.RolesAdapter
import com.gsoor.admin.ui.addcountry.AddCountryFragment
import com.gsoor.admin.ui.addpermission.AddPermissionFragment
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.admin.ui.roles.RolesViewModel
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentCountriesBinding
import com.gsoor.databinding.FragmentRolesBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CountriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class CountriesFragment : BaseFragment<FragmentCountriesBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_countries
    val mViewModel: CountriesViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    var countriesadapter = CountriesAdapter(object : CountriesAdapter.CountiresListner {
        override fun onClickDelete(postion: CountriesResponse.Data) {
            mViewModel.deleteCountry(postion.id.toString())
        }

        override fun onClickEditCountry(postion: CountriesResponse.Data) {
            val newDialogFragment = AddCountryFragment()
            val bundle = Bundle()
            bundle.putString("type","edit")
            bundle.putParcelable("item", postion)
            newDialogFragment.arguments = bundle
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "permisson")
        }

        override fun onClickDetailsCountry(postion: CountriesResponse.Data) {
            val bundle = Bundle()
            bundle.putParcelable("item", postion)

            Navigation.findNavController(requireActivity(), R.id.fragment)
                .navigate(R.id.action_countriesFragment_to_citiesFragment,bundle)

        }


    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycle()
        getCountry()
        subscribeRoles()
        subscribeDeleteCounties()
        onClickAdd()
        onClickLogo()

    }
    private fun onClickLogo(){
        mViewDataBinding.ImgLogo.setOnClickListener(){
            startActivity(Intent(requireContext(), NavigationAdmin::class.java))
            activity?.finish()
        }

    }
    private fun onClickAdd() {
        mViewDataBinding.buttonaddCountry.setOnClickListener(){
            val newDialogFragment = AddCountryFragment()
            val bundle=Bundle()
            bundle.putString("type","add")
            newDialogFragment.arguments=bundle
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "permisson")

        }
    }

    private fun subscribeDeleteCounties() {
        mViewModel.deleteResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> successDeleteRoles(it1) }

                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()

                }
            }
        })
    }

    private fun successDeleteRoles(it1: DeleteCountryResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.deletecountry_sucess),
            Toast.LENGTH_SHORT
        ).show()
        getCountry()

    }


    fun getCountry() {
        mViewDataBinding.recyclerRoles.visibility=View.GONE
        mViewModel.getCountries()
    }

    private fun initRecycle() {
        mViewDataBinding.recyclerRoles.setHasFixedSize(true)
        mViewDataBinding.recyclerRoles.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerRoles.adapter = countriesadapter
    }

    private fun subscribeRoles() {
        mViewModel.rolesResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> setData(it1) }
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                }
            }
        })
    }

    private fun setData(it1: CountriesResponse) {
        mViewDataBinding.recyclerRoles.visibility=View.VISIBLE
        countriesadapter.setData(it1.data as MutableList<CountriesResponse.Data>)

    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
        if(event.Message.equals("addcountry")){
            getCountry()
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }
}