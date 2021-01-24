package com.gsoor.admin.ui.addpackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.ui.acccategory.AddCategoryViewModel
import com.gsoor.base.BaseDialogFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentAddCategoryBinding
import com.gsoor.databinding.FragmentAddPackageBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPackage.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AddPackageFragment : BaseDialogFragment<FragmentAddPackageBinding>() {
    lateinit var postion: PackagesResponse.Data
    override var idLayoutRes: Int = R.layout.fragment_add_package
    lateinit var bundle: Bundle
    var listChecked: MutableList<String>? = mutableListOf()
    var createPermissio: RequestSavePackadges? = RequestSavePackadges()
    var type: String? = String()
    val mViewModel: AddPackagesViewModel by viewModels()
    var statusPaid: Boolean=true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        onClickAddPermission()
        subscribeAddRoles()
        subscribeEditRoles()
        onClickRadioButton()

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

    private fun successEditRoles(it1: AddPackadgesResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_editpackage),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addpackage"))
        dismiss()
    }

    fun onClickAddPermission() {
        mViewDataBinding.buttonSave.setOnClickListener() {

            if (mViewDataBinding.EPackageName.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.validate_package),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (mViewDataBinding.ECountofServic.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.validate_noservices),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if(type.equals("add")){
                createPermissio?.packageName = mViewDataBinding.EPackageName.text.toString()
                createPermissio?.countOfServices = mViewDataBinding.ECountofServic.text.toString().toInt()
                createPermissio?.isFree = statusPaid
                createPermissio?.countOfStars = mViewDataBinding.RatingBar.rating.toInt()
                mViewModel.createPackage(createPermissio!!)
            }else {
                createPermissio?.id=postion.id
                createPermissio?.packageName = mViewDataBinding.EPackageName.text.toString()
                createPermissio?.countOfServices = mViewDataBinding.ECountofServic.text.toString().toInt()
                createPermissio?.isFree = statusPaid
                createPermissio?.countOfStars = mViewDataBinding.RatingBar.rating.toInt()
                mViewModel.editPackages(createPermissio!!)
            }

        }
    }


    fun onClickRadioButton(){

        mViewDataBinding.radios.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = mViewDataBinding.radios.findViewById(checkedId)
                if (radio.text .equals("Free") || radio.text .equals("مجاني")) {
                  statusPaid=true
                } else {
                    statusPaid=false
                }
            })
    }
    private fun subscribeAddRoles() {
        mViewModel.addPackagesResponse.observe(this, Observer {
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

    private fun successAddRoles(it1: AddPackadgesResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_addpackage),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addpackage"))
        dismiss()

    }

    private fun getData() {
        bundle = requireArguments()
        type=bundle.getString("type")
        if (type.equals("edit")) {
            postion = bundle.getParcelable("item")!!
            mViewDataBinding.EPackageName.setText(postion.packageName.toString())
            mViewDataBinding.ECountofServic.setText(postion.countOfServices.toString())
                mViewDataBinding.RatingBar.rating=postion.countOfStars
            if(postion.isFree){
                mViewDataBinding.radiofree.isChecked=true
            }else {
                mViewDataBinding.radiopaid.isChecked=true
            }
        }


    }

}