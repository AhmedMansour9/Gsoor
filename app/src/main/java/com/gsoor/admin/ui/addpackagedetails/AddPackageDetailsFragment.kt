package com.gsoor.admin.ui.addpackagedetails

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
import com.gsoor.admin.ui.addpackage.AddPackagesViewModel
import com.gsoor.base.BaseDialogFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentAddPackageBinding
import com.gsoor.databinding.FragmentAddPackageDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPackageDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AddPackageDetailsFragment : BaseDialogFragment<FragmentAddPackageDetailsBinding>() {
    lateinit var postion: PackagesResponse.Data
    lateinit var postionDetails: PackageDetailsResponse.Data

    override var idLayoutRes: Int = R.layout.fragment_add_package_details
    lateinit var bundle: Bundle
    var listChecked: MutableList<String>? = mutableListOf()
    var createPermissio: RequestSavePackageDetails? = RequestSavePackageDetails()
    var type: String? = String()
    val mViewModel: AddPackagesDetailsViewModel by viewModels()
    var statusType: String="1"

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

    private fun successEditRoles(it1: AddPackageDetailsResponse) {
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

            if (mViewDataBinding.EPackagePrice.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.validate_packageprice),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            if(type.equals("add")){
                createPermissio?.packagesId=postion.id.toString()
                createPermissio?.price = mViewDataBinding.EPackagePrice.text.toString()
                createPermissio?.timeValue = mViewDataBinding.EPackageCounts.text.toString()
                createPermissio?.timeType = statusType
                mViewModel.createPackageDetails(createPermissio!!)
            }else {
                createPermissio?.id=postionDetails.id.toString()
                createPermissio?.packagesId=postion.id.toString()
                createPermissio?.price = mViewDataBinding.EPackagePrice.text.toString()
                createPermissio?.timeValue = mViewDataBinding.EPackageCounts.text.toString()
                createPermissio?.timeType = statusType
                mViewModel.editPackagesDetails(createPermissio!!)
            }

        }
    }


    fun onClickRadioButton(){

        mViewDataBinding.radios.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = mViewDataBinding.radios.findViewById(checkedId)
                if (radio.text .equals("Day") || radio.text .equals("يوم")) {
                    statusType="1"
                } else  if (radio.text .equals("Month") || radio.text .equals("شهر")) {
                    statusType="2"
                }else {
                    statusType="3"
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

    private fun successAddRoles(it1: AddPackageDetailsResponse) {
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
        postion = bundle.getParcelable("item")!!
        if (type.equals("edit")) {
            postionDetails = bundle.getParcelable("itemPackage")!!
            mViewDataBinding.EPackagePrice.setText(postionDetails.price.toString())
            mViewDataBinding.EPackageCounts.setText(postionDetails.timeValue.toString())
            if(postionDetails.timeType.equals("Day")){
                mViewDataBinding.radioday.isChecked=true
            }else if(postionDetails.timeType.equals("Month")) {
                mViewDataBinding.radiomonth.isChecked=true
            }else if(postionDetails.timeType.equals("Year")) {
                mViewDataBinding.radioyear.isChecked=true
            }
        }
    }

}