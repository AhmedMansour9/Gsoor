package com.gsoor.admin.ui.roles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.cairocartt.utils.SharedData
import com.cairocartt.utils.Status
import com.gsoor.R
import com.gsoor.admin.adapter.RolesAdapter
import com.gsoor.admin.ui.addpermission.AddPermissionFragment
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseFragment
import com.gsoor.data.remote.model.DeleteRolesResponse
import com.gsoor.data.remote.model.MessageEvent
import com.gsoor.data.remote.model.RolesResponse
import com.gsoor.databinding.FragmentRolesBinding
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
 * Use the [RolesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class RolesFragment : BaseFragment<FragmentRolesBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_roles
    val mViewModel: RolesViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
     var rolesadapter=RolesAdapter(object : RolesAdapter.Rolsitemlistner{
         override fun onClickDelete(postion: RolesResponse.Data) {
          mViewModel.deleteRoles(postion.id.toString())
         }

         override fun onClickPermission(postion: RolesResponse.Data) {
             val newDialogFragment = AddPermissionFragment()
             val bundle=Bundle()
             bundle.putString("type","edit")
             bundle.putParcelable("item",postion)
             newDialogFragment.arguments=bundle
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        newDialogFragment.show(transaction, "permisson")
         }

     })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycle()
        getRoles()
        subscribeRoles()
        subscribeDeleteRoles()
        onClickAdd()
        onClickLogo()

    }
    private fun onClickLogo(){
        mViewDataBinding.ImgLogo.setOnClickListener(){
            startActivity(Intent(requireContext(), NavigationAdmin::class.java))
            activity?.finish()
        }

    }
    private fun onClickAdd() {
    mViewDataBinding.buttonaddPermisson.setOnClickListener(){
        val newDialogFragment = AddPermissionFragment()
        val bundle=Bundle()
        bundle.putString("type","add")
        newDialogFragment.arguments=bundle
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        newDialogFragment.show(transaction, "permisson")

    }
    }

    private fun subscribeDeleteRoles() {
        mViewModel.deleteResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> successDeleteRoles(it1) }

                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                    Toast.makeText(requireContext(),it.message.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    private fun successDeleteRoles(it1: DeleteRolesResponse) {
        Toast.makeText(requireContext(),resources.getString(R.string.deleterole_sucess), Toast.LENGTH_SHORT).show()
        getRoles()

    }


    fun getRoles() {
        mViewModel.getRoles()


    }

    private fun initRecycle() {
        mViewDataBinding.recyclerRoles.setHasFixedSize(true)
        mViewDataBinding.recyclerRoles.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerRoles.adapter = rolesadapter
    }

    private fun subscribeRoles() {
        mViewModel.rolesResponse.observe(viewLifecycleOwner, Observer {
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

    private fun setData(it1: RolesResponse) {
        rolesadapter.setData(it1.data as MutableList<RolesResponse.Data>)

    }



    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
      if(event.Message.equals("addrole")){
          getRoles()
      }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }
}