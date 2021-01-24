package com.gsoor.admin.ui.packadges

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
import com.gsoor.admin.adapter.PackagesAdapter
import com.gsoor.admin.ui.addcountry.AddCountryFragment
import com.gsoor.admin.ui.addpackage.AddPackageFragment
import com.gsoor.admin.ui.countries.CountriesViewModel
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import com.gsoor.data.remote.model.MessageEvent
import com.gsoor.data.remote.model.PackagesResponse
import com.gsoor.databinding.FragmentPackagesBinding
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
 * Use the [PackagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class PackagesFragment : BaseFragment<FragmentPackagesBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_packages
    val mViewModel: PackagesViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    lateinit var packagesAdapter :PackagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecycle()
        getPackages()
        subscribeRoles()
        subscribeDeleteCounties()
        onClickAdd()
        onClickLogo()

    }

    private fun initAdapter() {
        packagesAdapter = PackagesAdapter(requireContext(),object : PackagesAdapter.CountiresListner {
            override fun onClickDelete(postion: PackagesResponse.Data) {
                mViewModel.delete(postion.id.toString())
            }

            override fun onClickEditCountry(postion: PackagesResponse.Data) {
                val newDialogFragment = AddPackageFragment()
                val bundle = Bundle()
                bundle.putString("type","edit")
                bundle.putParcelable("item", postion)
                newDialogFragment.arguments = bundle
                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                newDialogFragment.show(transaction, "permisson")
            }

            override fun onClickDetailsCountry(postion: PackagesResponse.Data) {
                val bundle = Bundle()
                bundle.putParcelable("item", postion)

                Navigation.findNavController(requireActivity(), R.id.fragment)
                    .navigate(R.id.action_packagesFragment_to_packageDetailsFragment,bundle)

            }


        })
    }

    private fun onClickLogo(){
        mViewDataBinding.ImgLogo.setOnClickListener(){
            startActivity(Intent(requireContext(), NavigationAdmin::class.java))
            activity?.finish()
        }

    }
    private fun onClickAdd() {
        mViewDataBinding.buttonaddCountry.setOnClickListener(){
            val newDialogFragment = AddPackageFragment()
            val bundle=Bundle()
            bundle.putString("type","add")
            newDialogFragment.arguments=bundle
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "permisson")

        }
    }

    private fun subscribeDeleteCounties() {
        mViewModel.deletePackagesResponse.observe(viewLifecycleOwner, Observer {
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
            resources.getString(R.string.deletepackage_sucess),
            Toast.LENGTH_SHORT
        ).show()
        getPackages()

    }


    fun getPackages() {
        mViewDataBinding.recyclerPakages.visibility=View.GONE
        mViewModel.getPackages()
    }

    private fun initRecycle() {
        mViewDataBinding.recyclerPakages.setHasFixedSize(true)
        mViewDataBinding.recyclerPakages.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerPakages.adapter = packagesAdapter
    }

    private fun subscribeRoles() {
        mViewModel.packagesResponse.observe(viewLifecycleOwner, Observer {
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

    private fun setData(it1: PackagesResponse) {
        mViewDataBinding.recyclerPakages.visibility=View.VISIBLE
        packagesAdapter.setData(it1.data as MutableList<PackagesResponse.Data>)

    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
        if(event.Message.equals("addpackage")){
            getPackages()
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }
}