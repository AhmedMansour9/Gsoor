package com.gsoor.admin.ui.categories

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
import com.gsoor.admin.adapter.CategoriesAdapter
import com.gsoor.admin.adapter.CountriesAdapter
import com.gsoor.admin.ui.acccategory.AddCategoryFragment
import com.gsoor.admin.ui.addcountry.AddCountryFragment
import com.gsoor.admin.ui.countries.CountriesViewModel
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.CategoriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import com.gsoor.data.remote.model.MessageEvent
import com.gsoor.databinding.FragmentCategoriesBinding
import com.gsoor.databinding.FragmentCountriesBinding
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
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_categories
    val mViewModel: CategoriesViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    var countriesadapter = CategoriesAdapter(object : CategoriesAdapter.CountiresListner {
        override fun onClickDelete(postion: CategoriesResponse.Data) {
            mViewModel.deleteCategory(postion.id.toString())
        }

        override fun onClickEditCountry(postion: CategoriesResponse.Data) {
            val newDialogFragment = AddCategoryFragment()
            val bundle = Bundle()
            bundle.putString("type", "edit")
            bundle.putParcelable("item", postion)
            newDialogFragment.arguments = bundle
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "permisson")
        }

        override fun onClickDetailsCountry(postion: CategoriesResponse.Data) {
            val bundle = Bundle()
            bundle.putParcelable("item", postion)

            Navigation.findNavController(requireActivity(), R.id.fragment)
                .navigate(R.id.action_categoriesFragment_to_subCategoriesFragment, bundle)

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
        mViewDataBinding.buttonaddCountry.setOnClickListener() {
            val newDialogFragment = AddCategoryFragment()
            val bundle = Bundle()
            bundle.putString("type", "add")
            newDialogFragment.arguments = bundle
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
            resources.getString(R.string.deletecategory_sucess),
            Toast.LENGTH_SHORT
        ).show()
        getCountry()

    }


    fun getCountry() {
        mViewDataBinding.recyclerRoles.visibility=View.GONE
        mViewModel.getCategories()
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

    private fun setData(it1: CategoriesResponse) {
        mViewDataBinding.recyclerRoles.visibility=View.VISIBLE
        countriesadapter.setData(it1.data as MutableList<CategoriesResponse.Data>)

    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
        if (event.Message.equals("addcountry")) {
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