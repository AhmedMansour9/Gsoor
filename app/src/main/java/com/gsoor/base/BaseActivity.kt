package com.gsoor.base

import android.annotation.TargetApi
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure
import com.gsoor.R
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract var idLayoutRes: Int

    open var colorStatusBar: Int = 0

    lateinit var mViewDataBinding: T

    private var dailog: Dialog? = null

    private  lateinit var hud: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (colorStatusBar != 0)
//                window.statusBarColor = colorStatusBar
//        }

        mViewDataBinding = setContentView(this, idLayoutRes)
        mViewDataBinding.executePendingBindings()
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun error(title:String,msg:String){
        AwesomeErrorDialog(this)
            .setTitle(title)
            .setMessage(msg)
            .setColoredCircle(R.color.dialogErrorBackgroundColor)
            .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
            .setCancelable(true).setButtonText(getString(R.string.dialog_ok_button))
            .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)
            .setButtonText(getString(R.string.dialog_ok_button))
                  .setErrorButtonClick( Closure() {
                fun exec() {
                    finish()
                }
            })
            .show();
    }

    fun showLoading() {
        hud = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show()

    }

    fun dismissLoading() {
        if( this::hud.isInitialized){
            if(hud.isShowing){
                hud.dismiss()
            }
        }    }

}