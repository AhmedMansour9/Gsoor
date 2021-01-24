package com.gsoor.admin.ui.subcategories

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
import com.gsoor.admin.adapter.SubCategoriesAdapter
import com.gsoor.admin.ui.addcity.AddCityFragment
import com.gsoor.admin.ui.addsubcategories.AddSubCategories
import com.gsoor.admin.ui.cities.CitiesViewModel
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentCitiesBinding
import com.gsoor.databinding.FragmentSubCategoriesBinding
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
 * Use the [SubCategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class SubCategoriesFragment : BaseFragment<FragmentSubCategoriesBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_sub_categories
    val mViewModel: SubCategoriesViewModel by viewModels (  )
    lateinit var postion: CategoriesResponse.Data
    lateinit var bundle: Bundle

    var countriesadapter = SubCategoriesAdapter(object : SubCategoriesAdapter.CitiesListner {
        override fun onClickDelete(postion: SubCategoriesResponse.Data) {
            mViewModel.deleteCountry(postion.id.toString())
        }

        override fun onClickEditCountry(posti: SubCategoriesResponse.Data) {
            val newDialogFragment = AddSubCategories()
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
        mViewDataBinding.TSubName.text=resources.getString(R.string.category)+": "+postion.categoryName
    }

    private fun onClickAdd() {
        mViewDataBinding.buttonaddCity.setOnClickListener(){
            val newDialogFragment = AddSubCategories()
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
        mViewDataBinding.recyclerRoles.visibility=View.VISIBLE
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.deleterole_sucess),
            Toast.LENGTH_SHORT
        ).show()
        getCities()

    }


    fun getCities() {
        mViewDataBinding.recyclerRoles.visibility=View.VISIBLE
        mViewModel.getSubCategories(postion.id.toString())
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

    private fun setData(it1: SubCategoriesResponse) {
        if(it1.data!=null) {
            countriesadapter.setData(it1.data as MutableList<SubCategoriesResponse.Data>)
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