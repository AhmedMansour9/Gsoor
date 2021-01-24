package com.gsoor.intro.ui.loginallusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.cairocartt.utils.SharedData
import com.cairocartt.utils.Status
import com.google.gson.Gson
import com.gsoor.R
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.base.BaseActivity
import com.gsoor.data.remote.model.AccountResponse
import com.gsoor.databinding.ActivityLoginAllUsersBinding
import com.gsoor.intro.ui.nointernet.NoInternertActivity
import com.gsoor.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginAllUsersActivity : BaseActivity<ActivityLoginAllUsersBinding>() ,LoginNavigator{
    var type:String?= String()
    override var idLayoutRes: Int = R.layout.activity_login_all_users
    private var data: SharedData? = null
    private val mViewModel: LoginUsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.navigator=this
        mViewDataBinding.loginViewModel = mViewModel
        setVisablity()
        data = SharedData(this)
        setupObserver()
    }
    private fun setupObserver() {
        mViewModel.accountResponse.observe(this, Observer {
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

    private fun setData(account: AccountResponse) {
        val gson=Gson()
        val datajson:String=gson.toJson(account)
        data?.putValue("json", datajson)

        val intent=Intent(this,NavigationAdmin::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }


    private fun setVisablity(){
        type = intent.getStringExtra("type")
        if(type.equals("admin")){
            mViewDataBinding.RelaForget.isVisible=false
            mViewDataBinding.RelaNewaccount.isVisible=false
        }
    }

    override fun loginClick() {
        if(!this.isConnected){
            startActivity(Intent(this, NoInternertActivity::class.java))
        }
        mViewModel.login()
    }

    override fun createAccoutClick() {
//        startActivity(Intent(this,RegisterActivity::class.java))
//        finish()
    }

    override fun forgetPasswordClick() {
    }

}