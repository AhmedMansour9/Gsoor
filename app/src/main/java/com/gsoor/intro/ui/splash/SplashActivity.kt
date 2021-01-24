package com.gsoor.intro.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.cairocartt.utils.SharedData
import com.google.gson.Gson
import com.gsoor.R
import com.gsoor.admin.ui.navigationadmin.NavigationAdmin
import com.gsoor.data.remote.model.AccountResponse
import com.gsoor.intro.ui.chooseacount.ChooseAccount
import com.gsoor.intro.ui.login.LoginActivity
import com.gsoor.intro.ui.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    private var cdt: CountDownTimer? = null
    private var checkIntro:String?= String()
    private var token:String?= String()
    private var data: SharedData? = null
    var account:AccountResponse?=AccountResponse()
    private var role:String?= String()
    private var slider:String?= String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        data = SharedData(this)

        getData()
        navigateToLogin()

    }
    private fun getData(){
        slider=data?.getValue(SharedData.ReturnValue.STRING, "slider")
        var gsonData=Gson()
        var json:String?=data?.getValue(SharedData.ReturnValue.STRING, "json")
        account=gsonData.fromJson(json,AccountResponse::class.java)
        role=account?.data?.userType
    }

    private fun navigateToLogin() {
        setupCounterDown {
            if(role.isNullOrEmpty()){
                if(slider.isNullOrEmpty()){
                    startActivity(Intent(this, OnBoardingActivity::class.java))
                    finish()
                }else {
                    startActivity(Intent(this, ChooseAccount::class.java))
                    finish()
                }

            }else if(role.equals("Admin")){
                startActivity(Intent(this, NavigationAdmin::class.java))
                finish()
            }
        }

    }
    private fun navigateToSlider() {
        setupCounterDown {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }

    }

    private fun setupCounterDown(action: () -> Unit) {
        cdt = object : CountDownTimer(1000, 3000) {
            override fun onFinish() {
                action()
            }

            override fun onTick(p0: Long) {

            }
        }
        cdt?.start()
    }


    override fun onStop() {
        cdt?.cancel()
        super.onStop()
    }
}