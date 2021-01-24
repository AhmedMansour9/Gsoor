package com.gsoor.admin.ui.privacymembers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.ui.registeradmin.RegisterAdminViewModel
import com.gsoor.admin.ui.roles.RolesViewModel
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.AdminRegisterResponse
import com.gsoor.data.remote.model.PolicyResponse
import com.gsoor.data.remote.model.RequestAdminRegisterModel
import com.gsoor.data.remote.model.RolesResponse
import com.gsoor.databinding.ActivityRegisterAdminBinding
import com.gsoor.databinding.FragmentPrivacyMembersBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrivacyMembersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class PrivacyMembersFragment : BaseFragment<FragmentPrivacyMembersBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_privacy_members
    private val mViewModel: PrivacyMembersViewModel by viewModels()
   lateinit var link:String
    lateinit var bundle:Bundle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        onClickRegisterAdmin()
        setupObserver()

    }

    private fun getData() {
        bundle=requireArguments()
        link=bundle.getString("data")!!
        if(link.equals("MemberPolicy")){
         mViewDataBinding.Title.text=context?.resources?.getString(R.string.policymembers)
        }else {
            mViewDataBinding.Title.text=context?.resources?.getString(R.string.policyprovider)
        }
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

    private fun setData(data: PolicyResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_addprivacy),
            Toast.LENGTH_SHORT
        ).show()
        mViewDataBinding.EPrivacy.setText(null)
    }

    private fun onClickRegisterAdmin() {
        mViewDataBinding.buttonRegister.setOnClickListener() {
            if (validateInputs()) {
                mViewModel.savePolicy(link,mViewDataBinding.EPrivacy.text.toString())
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (validateUserName()) {
            return true
        }
        return false
    }

    private fun validateUserName(): Boolean {
        var userName = mViewDataBinding.EPrivacy.text
        if (userName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.validate_firstname, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }



}