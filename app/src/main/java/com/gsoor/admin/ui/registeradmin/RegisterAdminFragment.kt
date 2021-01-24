package com.gsoor.admin.ui.registeradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.ui.addcountry.AddCountryFragment
import com.gsoor.admin.ui.allmangers.AllMangersFragment
import com.gsoor.admin.ui.roles.RolesViewModel
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.AdminRegisterResponse
import com.gsoor.data.remote.model.RequestAdminRegisterModel
import com.gsoor.data.remote.model.RolesResponse
import com.gsoor.databinding.ActivityRegisterAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterAdminFragment : BaseFragment<ActivityRegisterAdminBinding>() {

    override var idLayoutRes: Int = R.layout.activity_register_admin
    private val mViewModel: RegisterAdminViewModel by viewModels()
    var registerRequest: RequestAdminRegisterModel = RequestAdminRegisterModel()
    var role_id:String?= String()
    val mViewModel2: RolesViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.loginViewModel=mViewModel
        onClickRegisterAdmin()
        onClickShowAll()
        setupObserver()
        getRoles()
        subscribeRoles()
    }

    private fun onClickShowAll() {
      mViewDataBinding.CardShow.setOnClickListener(){
          Navigation.findNavController(requireActivity(), R.id.fragment)
              .navigate(R.id.action_registerAdminFragment_to_allMangersFragment)

      }
    }


    fun getRoles() {
        mViewModel2.getRoles()
    }
    private fun subscribeRoles() {
        mViewModel2.rolesResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> setDataRoles(it1) }
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

    private fun setDataRoles(it1: RolesResponse) {
        val adapter =
            it1.data?.let { ArrayAdapter(requireContext(), R.layout.spinner_item_selected, it) }
        mViewDataBinding.spinnerAdapter=adapter

        mViewModel.itemPositionLanguage.observe(viewLifecycleOwner, Observer { postion ->
            role_id=adapter?.getItem(postion)?.id
        })

    }
    private fun setupObserver() {
        mViewModel.accountResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    setData(it.data!!)
                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    dismissLoading()
                    error(resources.getString(R.string.error), it.message.toString())
                }
            }
        })
    }

    private fun setData(data: AdminRegisterResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_addadmin),
            Toast.LENGTH_SHORT
        ).show()
         mViewDataBinding.EFirstName.text=null
        mViewDataBinding.ELastName.text=null
        mViewDataBinding.EEmail.text=null
         mViewDataBinding.ERole.text=null
         mViewDataBinding.EPhone.text=null
         mViewDataBinding.EPassword.text=null
         mViewDataBinding.EConfirmpassword.text=null

    }

    private fun onClickRegisterAdmin() {
        mViewDataBinding.buttonRegister.setOnClickListener() {
            if (validateInputs()) {
                registerRequest.firstName = mViewDataBinding.EFirstName.text.toString()
                registerRequest.lastName = mViewDataBinding.ELastName.text.toString()
                registerRequest.email = mViewDataBinding.EEmail.text.toString()
                registerRequest.roles = role_id
                registerRequest.phoneNumber = mViewDataBinding.EPhone.text.toString()
                registerRequest.password = mViewDataBinding.EPassword.text.toString()
                registerRequest.confirmPassword = mViewDataBinding.EConfirmpassword.text.toString()
                mViewModel.register(registerRequest)
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (validateUserName() && validateLastName() && validatePhone() && validateEmail() && validatePassword() && validateConfirmpassword() && validateMatchpassword()) {
            return true
        }
        return false
    }

    private fun validateUserName(): Boolean {
        var userName = mViewDataBinding.EFirstName.text
        if (userName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.validate_firstname, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun validateLastName(): Boolean {
        var userName = mViewDataBinding.ELastName.text
        if (userName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.validate_lastname, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun validateEmail(): Boolean {
        var email = mViewDataBinding.EEmail.text
        if (email.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.validate_email, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validatePhone(): Boolean {
        var phone = mViewDataBinding.EPhone.text
        if (phone.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.validate_phone, Toast.LENGTH_SHORT).show()

            return false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        var password = mViewDataBinding.EPassword.text
        if (password.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.validate_password, Toast.LENGTH_SHORT).show()

            return false
        }
        return true
    }

    private fun validateConfirmpassword(): Boolean {
        var confirmPassword = mViewDataBinding.EConfirmpassword.text
        if (confirmPassword.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.validate_confirmpassword, Toast.LENGTH_SHORT)
                .show()

            return false
        }
        return true
    }

    private fun validateMatchpassword(): Boolean {
        val password = mViewDataBinding.EPassword.text.toString().toInt()
        val confirmPassword = mViewDataBinding.EConfirmpassword.text.toString().toInt()

        if (password!=confirmPassword) {
            Toast.makeText(requireContext(), R.string.validate_matchpassword, Toast.LENGTH_SHORT)
                .show()

            return false
        }
        return true
    }
}