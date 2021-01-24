package com.gsoor.admin.ui.addsubcategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.ui.acccategory.AddCategoryViewModel
import com.gsoor.admin.ui.addcity.AddCityViewModel
import com.gsoor.base.BaseDialogFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentAddCityBinding
import com.gsoor.databinding.FragmentAddSubCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddSubCategories.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AddSubCategories : BaseDialogFragment<FragmentAddSubCategoriesBinding>() {
    lateinit var postion: SubCategoriesResponse.Data
    lateinit var postionConutry: CategoriesResponse.Data
    override var idLayoutRes: Int = R.layout.fragment_add_sub_categories
    lateinit var bundle: Bundle
    var createPermissio: RequestSaveSubCategory? = RequestSaveSubCategory()
    var type: String? = String()
    val mViewModel: AddSubCategoriesViewModel by viewModels()
    var serviceCondition="1"
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        onClickAddPermission()
        subscribeAddRoles()
        subscribeEditRoles()

    }
    private fun checkRadioButton(){
        var id: Int = mViewDataBinding.radios.getCheckedRadioButtonId()
        val radioButton: View = mViewDataBinding.radios.findViewById(id)
        val radioId = mViewDataBinding.radios.indexOfChild(radioButton)
        val btn = mViewDataBinding.radios.getChildAt(radioId) as RadioButton
        val selection = btn.text as String
        if (selection == "Free"  || selection == "مجاني") {
            serviceCondition="1"
        }else {
            serviceCondition="2"
        }

    }

    private fun subscribeEditRoles() {
        mViewModel.editrolesResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> successEditRoles(it1) }
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

    private fun successEditRoles(it1: AddSubCategoryResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_editcity),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addcity"))
        dismiss()
    }

    fun onClickAddPermission() {
        mViewDataBinding.buttonSave.setOnClickListener() {
          checkRadioButton()
            if (mViewDataBinding.ERole.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.validate_service),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            createPermissio?.categoryId=postionConutry.id.toString()
            if(type.equals("add")){
                createPermissio?.subCategoryName = mViewDataBinding.ERole.text.toString()
                createPermissio?.serviceCondition=serviceCondition
                mViewModel.createCity(createPermissio!!)
            }else {
                createPermissio?.id=postion.id.toString()
                createPermissio?.serviceCondition=serviceCondition
                createPermissio?.subCategoryName = mViewDataBinding.ERole.text.toString()
                mViewModel.editCity(createPermissio!!)
            }

        }
    }

    private fun subscribeAddRoles() {
        mViewModel.rolesResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> successAddRoles(it1) }

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

    private fun successAddRoles(it1: AddSubCategoryResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_addservice),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addcity"))
        dismiss()

    }

    private fun getData() {
        bundle = requireArguments()
        type=bundle.getString("type")
        postionConutry = bundle.getParcelable("itemCountry")!!
        mViewDataBinding.TSubName.text=resources.getString(R.string.category)+": "+postionConutry.categoryName
        if (type.equals("edit")) {
            postion= bundle.getParcelable("item")!!
            mViewDataBinding.ERole.setText(postion.subCategoryName.toString())
            mViewDataBinding.TPermission.text=resources.getString(R.string.edit_sub)
        }


    }

}