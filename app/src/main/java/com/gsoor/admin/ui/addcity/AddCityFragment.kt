package com.gsoor.admin.ui.addcity

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
import com.gsoor.admin.ui.addcountry.AddCountriesViewModel
import com.gsoor.base.BaseDialogFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentAddCityBinding
import com.gsoor.databinding.FragmentAddCountryBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddCityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AddCityFragment : BaseDialogFragment<FragmentAddCityBinding>() {
    lateinit var postion: CitiesResponse.Data
    lateinit var postionConutry: CountriesResponse.Data

    override var idLayoutRes: Int = R.layout.fragment_add_city
    lateinit var bundle: Bundle
    var listChecked: MutableList<String>? = mutableListOf()
    var createPermissio: RequestAddCity? = RequestAddCity()
    var type: String? = String()
    val mViewModel: AddCityViewModel by viewModels()

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
            resources.getString(R.string.success_editcity),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addcity"))
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
            createPermissio?.countryId=postionConutry.id.toString()
            if(type.equals("add")){
                createPermissio?.cityName = mViewDataBinding.ERole.text.toString()
                mViewModel.createCity(createPermissio!!)
            }else {
                createPermissio?.id=postion.id.toString()
                createPermissio?.cityName = mViewDataBinding.ERole.text.toString()
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

    private fun successAddRoles(it1: AddCountryResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_addcity),
            Toast.LENGTH_SHORT
        ).show()
        EventBus.getDefault().postSticky(MessageEvent("addcity"))
        dismiss()

    }

    private fun getData() {
        bundle = requireArguments()
        type=bundle.getString("type")
        postionConutry = bundle.getParcelable("itemCountry")!!
        if (type.equals("edit")) {
            postion= bundle.getParcelable("item")!!
            mViewDataBinding.ERole.setText(postion.cityName.toString())
            mViewDataBinding.TPermission.text=resources.getString(R.string.edit_country)
        }


    }

}