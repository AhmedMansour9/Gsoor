package com.gsoor.admin.ui.privacy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.gsoor.R
import com.gsoor.base.BaseFragment
import com.gsoor.databinding.FragmentPrivacyBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Privacy.newInstance] factory method to
 * create an instance of this fragment.
 */
class Privacy : BaseFragment<FragmentPrivacyBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_privacy

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickCardMembers()
        onClickCardProvider()
    }

    fun onClickCardMembers(){
        mViewDataBinding.CardMember.setOnClickListener(){
            var bundle= Bundle()
            bundle.putString("data","MemberPolicy")
            Navigation.findNavController(requireActivity(), R.id.fragment)
                .navigate(R.id.action_privacy_to_privacyMembersFragment,bundle)

        }
    }

    fun onClickCardProvider(){
        mViewDataBinding.CardService.setOnClickListener(){
            var bundle= Bundle()
            bundle.putString("data","ServiceProviderPolicy")
            Navigation.findNavController(requireActivity(), R.id.fragment)
                .navigate(R.id.action_privacy_to_privacyMembersFragment,bundle)

        }
    }
}