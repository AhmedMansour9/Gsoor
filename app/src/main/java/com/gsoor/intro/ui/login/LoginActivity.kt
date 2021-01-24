package com.gsoor.intro.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.gsoor.R
import com.gsoor.base.BaseActivity
import com.gsoor.databinding.ActivityLoginBinding
import com.gsoor.intro.ui.chooseacount.ChooseAccount
import com.gsoor.intro.ui.loginallusers.LoginAllUsersActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    var type:String?= String()
    override var idLayoutRes: Int = R.layout.activity_login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle()
    }

    private fun setTitle(){
         type = intent.getStringExtra("type")
        if(type.equals("admin")){
            mViewDataBinding.Title.text=resources.getString(R.string.loginadmin)
            mViewDataBinding.buttonRegister.isVisible=false
        }else if(type.equals("user")){
            mViewDataBinding.Title.text=resources.getString(R.string.loginuser)
        }
        else if(type.equals("userprovider")){
            mViewDataBinding.Title.text=resources.getString(R.string.loginprovider)
        }
    }
     fun onClickLogin(view:View){
        var intent= Intent(this, LoginAllUsersActivity::class.java)
        intent.putExtra("type",type)
        startActivity(intent)
        finish()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this, ChooseAccount::class.java)
        intent.putExtra("type",type)
        startActivity(intent)
        finish()
    }
}