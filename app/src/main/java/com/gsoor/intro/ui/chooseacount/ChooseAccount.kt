package com.gsoor.intro.ui.chooseacount

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cairocartt.utils.SharedData
import com.gsoor.R
import com.gsoor.base.BaseActivity
import com.gsoor.databinding.ActivityChooseAccountBinding
import com.gsoor.intro.ui.login.LoginActivity
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseAccount : BaseActivity<ActivityChooseAccountBinding>() {

    override var idLayoutRes: Int = R.layout.activity_choose_account
    var Language: String? = String()
    private var data: SharedData? = null

    val mViewModel: ChooseAccountViewModel by viewModels(){
        defaultViewModelProviderFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding.viewmodel=mViewModel
        data = SharedData(this)

        setData()

    }
    fun btnLogin(view:View){
        if(checkLang()){
            data?.putValue("lang",Language)
            var intent=Intent(this, LoginActivity::class.java)
            intent.putExtra("type","admin")
            startActivity(intent)
        }
    }
    fun btnLoginAdmin(view:View){
//        if(checkLang()){
//            data?.putValue("lang",Language)
//            Lingver.getInstance().setLocale(this, Language.toString(), "")

            var intent=Intent(this, LoginActivity::class.java)
            intent.putExtra("type","admin")
            startActivity(intent)
            finish()
//        }

    }

    private fun setData(){
        val adapter =
            ArrayAdapter(this, R.layout.spinner_item_selected, resources.getStringArray(R.array.language))
        mViewDataBinding.spinnerAdapter=adapter

        mViewModel.itemPositionLanguage.observe(this, Observer { postion ->
            setLang(postion)


        })
    }
    private fun setLang(postion:Int){
        if(postion==1){
            Language="en"
        }else if(postion==2) {
            Language="ar"
        }
    }
    private fun checkLang():Boolean{
        if(Language.isNullOrEmpty()){
            Toast.makeText(this, resources.getString(R.string.choose_lang), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}