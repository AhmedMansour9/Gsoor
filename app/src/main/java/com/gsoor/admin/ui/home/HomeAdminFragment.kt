package com.gsoor.admin.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.cairocartt.utils.SharedData
import com.google.gson.Gson
import com.gsoor.R
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.AccountResponse
import com.gsoor.databinding.FragmentHomeAdminBinding
import com.gsoor.intro.ui.chooseacount.ChooseAccount
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeAdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class HomeAdminFragment : BaseFragment<FragmentHomeAdminBinding>() ,HomeAdminNavigator{

    override var idLayoutRes: Int = R.layout.fragment_home_admin
    private var data: SharedData? = null
    var account: AccountResponse? = AccountResponse()
    var listPermisson: List<Int> = mutableListOf()
    val mViewModel: HomeAdminViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = SharedData(requireContext())
        mViewModel.navigator=this
        mViewDataBinding.viewModel=mViewModel

        getData()
        onClickLogout()


    }

    private fun onClickLogout() {
        mViewDataBinding.ImgLogout.setOnClickListener(){
            data?.putValue("json",null)
            val intent=Intent(requireContext(), ChooseAccount::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }


    private fun getData() {
        var gsonData = Gson()
        var json: String? = data?.getValue(SharedData.ReturnValue.STRING, "json")
        account = gsonData.fromJson(json, AccountResponse::class.java)
        listPermisson = account?.data?.permissions as List<Int>
        listPermisson.forEachIndexed { index, i ->
            setVisablity(i)
        }
    }

    private fun setVisablity(index: Int) {
        when (index) {
            1 -> mViewDataBinding.Rela1.isVisible = true
            2 -> mViewDataBinding.Rela2.isVisible = true
            3 -> mViewDataBinding.Rela3.isVisible = true
            4 -> mViewDataBinding.Rela4.isVisible = true
//            5 -> mViewDataBinding.Rela5.isVisible = true
            6 -> mViewDataBinding.Rela6.isVisible = true
            7 -> mViewDataBinding.Rela7.isVisible = true
            8 -> mViewDataBinding.Rela8.isVisible = true
            9 -> mViewDataBinding.Rela9.isVisible = true
//            10 -> mViewDataBinding.Rela10.isVisible = true
            11 -> mViewDataBinding.Rela11.isVisible = true
        }

    }

    override fun onClickRela1() {
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_countriesFragment)
    }

    override fun onClickRela2() {
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_registerAdminFragment)
    }

    override fun onClickRela3() {
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_rolesFragment)
    }

    override fun onClickRela4() {
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_categoriesFragment)

    }

    override fun onClickRela5() {
        TODO("Not yet implemented")
    }

    override fun onClickRela6() {
        var bundle= Bundle()
        bundle.putString("id","2")
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_membersAccountFragment,bundle)
    }

    override fun onClickRela7() {
        var bundle= Bundle()
        bundle.putString("id","3")
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_membersAccountFragment,bundle)
    }

    override fun onClickRela8() {
    }

    override fun onClickRela9() {
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_privacy)

    }

    override fun onClickRela10() {
    }

    override fun onClickRela11() {
        Navigation.findNavController(requireActivity(), R.id.fragment)
            .navigate(R.id.action_homeFragment_to_packagesFragment)
    }


}