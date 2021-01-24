package com.gsoor.admin.ui.addcountry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.ui.addpermission.AddPermissionViewModel
import com.gsoor.base.BaseDialogFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentAddCountryBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddCountryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AddCountryFragment : BaseDialogFragment<FragmentAddCountryBinding>() {
    lateinit var postion: CountriesResponse.Data
    override var idLayoutRes: Int = R.layout.fragment_add_country
    lateinit var bundle: Bundle
    var listChecked: MutableList<String>? = mutableListOf()
    var createPermissio: RequestAddCountry? = RequestAddCountry()
    var type: String? = String()
    val mViewModel: AddCountriesViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        onClickAddPermission()
        subscribeAddRoles()
        subscribeEditRoles()

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

    private fun successEditRoles(it1: AddCountryResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_editrole),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addcountry"))
        dismiss()
    }

    fun onClickAddPermission() {
        mViewDataBinding.buttonSave.setOnClickListener() {

            if (mViewDataBinding.ERole.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.validate_role),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if(type.equals("add")){
                createPermissio?.countryName = mViewDataBinding.ERole.text.toString()
                mViewModel.createPermission(createPermissio!!)
            }else {
                createPermissio?.id=postion.id.toString()
                createPermissio?.countryName = mViewDataBinding.ERole.text.toString()
                mViewModel.editPermission(createPermissio!!)
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

    private fun successAddRoles(it1: AddCountryResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_addrole),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addcountry"))
        dismiss()

    }

    private fun getData() {
        bundle = requireArguments()
        type=bundle.getString("type")
        if (type.equals("edit")) {
            postion = bundle.getParcelable("item")!!
            mViewDataBinding.ERole.setText(postion.countryName.toString())
            mViewDataBinding.TPermission.text=resources.getString(R.string.edit_country)
        }


    }

}