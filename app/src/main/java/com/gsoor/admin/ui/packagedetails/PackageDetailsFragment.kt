package com.gsoor.admin.ui.packagedetails

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.adapter.CitiesAdapter
import com.gsoor.admin.adapter.PackageDetailsAdapter
import com.gsoor.admin.ui.addcity.AddCityFragment
import com.gsoor.admin.ui.addpackagedetails.AddPackageDetailsFragment
import com.gsoor.admin.ui.cities.CitiesViewModel
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentCitiesBinding
import com.gsoor.databinding.FragmentPackageDetailsBinding
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
 * Use the [PackageDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class PackageDetailsFragment : BaseFragment<FragmentPackageDetailsBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_package_details
    val mViewModel: PackageDetailsViewModel by viewModels (  )
    lateinit var postion: PackagesResponse.Data
    lateinit var bundle: Bundle

    lateinit var packagesadapter :PackageDetailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecycle()
        getData()
        getCities()
        subscribeCities()
        subscribeDeleteCounties()
        onClickAdd()
        onClickLogo()

    }

    private fun initAdapter() {
        packagesadapter  = PackageDetailsAdapter(requireContext(),object : PackageDetailsAdapter.CountiresListner {
            override fun onClickDelete(postion: PackageDetailsResponse.Data) {
                mViewModel.deletePackageDetails(postion.id.toString())
            }

            override fun onClickEditCountry(posti: PackageDetailsResponse.Data) {
                val newDialogFragment = AddPackageDetailsFragment()
                val bundle = Bundle()
                bundle.putString("type","edit")
                bundle.putParcelable("item", postion)
                bundle.putParcelable("itemPackage", posti)
                newDialogFragment.arguments = bundle
                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                newDialogFragment.show(transaction, "permisson")
            }


        })
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
            val newDialogFragment = AddPackageDetailsFragment()
            val bundle=Bundle()
            bundle.putString("type","add")
            bundle.putParcelable("item", postion)
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
            resources.getString(R.string.deletepackagedetails_sucess),
            Toast.LENGTH_SHORT
        ).show()
        getCities()

    }


    fun getCities() {
        mViewModel.getPeckageDetails(postion.id.toString())
    }

    private fun initRecycle() {
        mViewDataBinding.recyclerAckageDetails.setHasFixedSize(true)
        mViewDataBinding.recyclerAckageDetails.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerAckageDetails.adapter = packagesadapter
    }

    private fun subscribeCities() {
        mViewModel.packagedetailsResponse.observe(viewLifecycleOwner, Observer {
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

    private fun setData(it1: PackageDetailsResponse) {
        if(it1.data!=null) {
            packagesadapter.setData(it1.data as MutableList<PackageDetailsResponse.Data>)
        }
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
        if(event.Message.equals("addpackage")){
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