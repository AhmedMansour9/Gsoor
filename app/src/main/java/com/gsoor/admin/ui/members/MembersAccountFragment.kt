package com.gsoor.admin.ui.members

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.gsoor.admin.adapter.CountriesAdapter
import com.gsoor.admin.adapter.MembersAccountAdapter
import com.gsoor.admin.ui.addcountry.AddCountryFragment
import com.gsoor.admin.ui.countries.CountriesViewModel
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.*
import com.gsoor.databinding.FragmentCountriesBinding
import com.gsoor.databinding.FragmentMembersAccountBinding
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
 * Use the [MembersAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MembersAccountFragment : BaseFragment<FragmentMembersAccountBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_members_account
    val mViewModel: MembersAccountViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory  }

    lateinit var typeUser:String
    lateinit var bundle:Bundle
    lateinit var membersadapter : MembersAccountAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initAdapter()
        initRecycle()
        getMembers()
        subscribeRoles()
        Filtertion()
        subscribeEditStatus()

    }
    private fun Filtertion() {
        mViewDataBinding.ESearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                membersadapter.getFilter().filter(s)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })


    }
    private fun getData() {
        bundle=requireArguments()
        typeUser=bundle.getString("id")!!
        if(typeUser.equals("2")){
            mViewDataBinding.TTitle.text=resources.getString(R.string.login_service)
        }else {
            mViewDataBinding.TTitle.text=resources.getString(R.string.members)
        }
    }

    private fun initAdapter() {
       membersadapter= MembersAccountAdapter(requireContext(),object : MembersAccountAdapter.MembersListner {
            override fun onClickCertification(postion: MembersAccountResponse.Data) {
                if(postion.statusType==true){
                    mViewModel.updateStatus("MembersAccount/UpdateCertified",postion.id.toString(),"false")
                }else {
                    mViewModel.updateStatus("MembersAccount/UpdateCertified",postion.id.toString(),"true")
                }

            }

            override fun onClickVerfication(postion: MembersAccountResponse.Data) {
                if(postion.statusType==true){
                    mViewModel.updateStatus("MembersAccount/UpdateVerification",postion.id.toString(),"false")
                }else {
                    mViewModel.updateStatus("MembersAccount/UpdateVerification",postion.id.toString(),"true")
                }

            }

            override fun onClickStatus(postion: MembersAccountResponse.Data) {
                if(postion.statusType==true) {
                    mViewModel.updateStatus("MembersAccount/UpdateStatus",postion.id.toString(),"false")
                }else {
                    mViewModel.updateStatus("MembersAccount/UpdateStatus",postion.id.toString(),"true")
                }


            }


        })

    }


    private fun successEditsStatus(it1: UpDateStatusResponse) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.updatestatus),
            Toast.LENGTH_SHORT
        ).show()

      getMembers()
    }


    fun getMembers() {
        mViewModel.getSubMembers(typeUser)
    }

    private fun initRecycle() {
        mViewDataBinding.recyclerMembers.setHasFixedSize(true)
        mViewDataBinding.recyclerMembers.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerMembers.adapter = membersadapter
    }

    private fun subscribeRoles() {
        mViewModel.membersResponse.observe(viewLifecycleOwner, Observer {
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

    private fun subscribeEditStatus() {
        mViewModel.editmemberResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> successEditsStatus(it1) }
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

    private fun setData(it1: MembersAccountResponse) {
        it1.data?.reverse()
        mViewDataBinding.recyclerMembers.visibility=View.VISIBLE
        mViewDataBinding.TTotalUsers.text=it1.data?.size.toString() + " "+resources.getString(R.string.users)
        membersadapter.setData(it1.data as MutableList<MembersAccountResponse.Data>)
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
//        if(event.Message.equals("addcountry")){
//            getMembers()
//        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }
}