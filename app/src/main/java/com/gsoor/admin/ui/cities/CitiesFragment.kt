package com.gsoor.admin.ui.cities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.adapter.CitiesAdapter
import com.gsoor.admin.adapter.CountriesAdapter
import com.gsoor.admin.ui.addcity.AddCityFragment
import com.gsoor.admin.ui.addcountry.AddCountryFragment
import com.gsoor.admin.ui.countries.CountriesViewModel
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.CitiesResponse
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import com.gsoor.data.remote.model.MessageEvent
import com.gsoor.databinding.FragmentCitiesBinding
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
 * Use the [CitiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CitiesFragment : BaseFragment<FragmentCitiesBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_cities
    val mViewModel: CitiesViewModel by viewModels (  )
    lateinit var postion: CountriesResponse.Data
    lateinit var bundle: Bundle

    var countriesadapter = CitiesAdapter(object : CitiesAdapter.CitiesListner {
        override fun onClickDelete(postion: CitiesResponse.Data) {
            mViewModel.deleteCountry(postion.id.toString())
        }

        override fun onClickEditCountry(posti: CitiesResponse.Data) {
            val newDialogFragment = AddCityFragment()
            val bundle = Bundle()
            bundle.putString("type","edit")
            bundle.putParcelable("itemCountry", postion)
            bundle.putParcelable("item", posti)
            newDialogFragment.arguments = bundle
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "permisson")
        }



    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycle()
        getData()
        getCities()
        subscribeCities()
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
    private fun getData() {
        bundle = requireArguments()
        postion = bundle.getParcelable("item")!!
        }

    private fun onClickAdd() {
        mViewDataBinding.buttonaddCity.setOnClickListener(){
            val newDialogFragment = AddCityFragment()
            val bundle=Bundle()
            bundle.putString("type","add")
            bundle.putParcelable("itemCountry", postion)
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
            resources.getString(R.string.deletecity_sucess),
            Toast.LENGTH_SHORT
        ).show()
        getCities()

    }


    fun getCities() {
        mViewModel.getCountries(postion.id.toString())
    }

    private fun initRecycle() {
        mViewDataBinding.recyclerRoles.setHasFixedSize(true)
        mViewDataBinding.recyclerRoles.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerRoles.adapter = countriesadapter
    }

    private fun subscribeCities() {
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

    private fun setData(it1: CitiesResponse) {
        if(it1.data!=null) {
            countriesadapter.setData(it1.data as MutableList<CitiesResponse.Data>)
        }
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
        if(event.Message.equals("addcity")){
            getCities()
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }
}