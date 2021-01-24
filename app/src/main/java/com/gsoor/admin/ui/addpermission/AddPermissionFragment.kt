package com.gsoor.admin.ui.addpermission

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.ui.roles.RolesViewModel
import com.gsoor.base.BaseDialogFragment
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.AdminRegisterResponse
import com.gsoor.data.remote.model.MessageEvent
import com.gsoor.data.remote.model.RequestCreatePermission
import com.gsoor.data.remote.model.RolesResponse
import com.gsoor.databinding.FragmentAddPermissionBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPermissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AddPermissionFragment : BaseDialogFragment<FragmentAddPermissionBinding>() {
    lateinit var postion: RolesResponse.Data
    override var idLayoutRes: Int = R.layout.fragment_add_permission
    lateinit var bundle: Bundle
    var listChecked: MutableList<String>? = mutableListOf()
    var createPermissio: RequestCreatePermission? = RequestCreatePermission()
    var type: String? = String()
    val mViewModel: AddPermissionViewModel by viewModels()

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

    private fun successEditRoles(it1: AdminRegisterResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_editrole),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addrole"))
        dismiss()
    }

    fun onClickAddPermission() {
        mViewDataBinding.buttonSave.setOnClickListener() {
            listChecked?.clear()
            getCheckBoxsData()
            if (listChecked.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.validate_perimssion),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (mViewDataBinding.ERole.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.validate_role),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if(type.equals("add")){
                createPermissio?.permissions = listChecked
                createPermissio?.roleName = mViewDataBinding.ERole.text.toString()
                mViewModel.createPermission(createPermissio!!)
            }else {
                createPermissio?.id=postion.id
                createPermissio?.permissions = listChecked
                createPermissio?.roleName = mViewDataBinding.ERole.text.toString()
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

    private fun successAddRoles(it1: AdminRegisterResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_addrole),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addrole"))
        dismiss()

    }

    private fun getData() {
        bundle = requireArguments()
        type=bundle.getString("type")
        if (type.equals("edit")) {
            postion = bundle.getParcelable("item")!!
            mViewDataBinding.ERole.setText(postion.name.toString())
            mViewDataBinding.TPermission.text=resources.getString(R.string.edit_role)
            if (!postion.permissions.isNullOrEmpty()) {
                postion.permissions!!.forEachIndexed { index, i ->
                    setVisablity(i)
                }
            }
        }


    }

    fun getCheckBoxsData() {
        if (mViewDataBinding.CheckBox1.isChecked) {
            listChecked?.add("1")
        }
        if (mViewDataBinding.CheckBox2.isChecked) {
            listChecked?.add("2")
        }
        if (mViewDataBinding.CheckBox3.isChecked) {
            listChecked?.add("3")
        }
        if (mViewDataBinding.CheckBox4.isChecked) {
            listChecked?.add("4")
        }
        if (mViewDataBinding.CheckBox5.isChecked) {
            listChecked?.add("5")
        }
        if (mViewDataBinding.CheckBox6.isChecked) {
            listChecked?.add("6")
        }
        if (mViewDataBinding.CheckBox7.isChecked) {
            listChecked?.add("7")
        }
        if (mViewDataBinding.CheckBox8.isChecked) {
            listChecked?.add("8")
        }
        if (mViewDataBinding.CheckBox9.isChecked) {
            listChecked?.add("9")
        }
        if (mViewDataBinding.CheckBox10.isChecked) {
            listChecked?.add("10")
        }
        if (mViewDataBinding.CheckBox11.isChecked) {
            listChecked?.add("11")
        }
    }


    private fun setVisablity(index: Int) {
        when (index) {
            1 -> mViewDataBinding.CheckBox1.isChecked = true
            2 -> mViewDataBinding.CheckBox2.isChecked = true
            3 -> mViewDataBinding.CheckBox3.isChecked = true
            4 -> mViewDataBinding.CheckBox4.isChecked = true
            5 -> mViewDataBinding.CheckBox5.isChecked = true
            6 -> mViewDataBinding.CheckBox6.isChecked = true
            7 -> mViewDataBinding.CheckBox7.isChecked = true
            8 -> mViewDataBinding.CheckBox8.isChecked = true
            9 -> mViewDataBinding.CheckBox9.isChecked = true
            10 -> mViewDataBinding.CheckBox10.isChecked = true
            11 -> mViewDataBinding.CheckBox11.isChecked = true
        }

    }

}