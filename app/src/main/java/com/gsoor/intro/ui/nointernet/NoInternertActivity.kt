package com.gsoor.intro.ui.nointernet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.gsoor.R
import com.gsoor.base.BaseActivity
import com.gsoor.data.remote.model.MessageEvent
import com.gsoor.databinding.ActivityNoInternertBinding
import com.gsoor.utils.isConnected
import org.greenrobot.eventbus.EventBus

class NoInternertActivity : BaseActivity<ActivityNoInternertBinding>() {

    override var idLayoutRes: Int = R.layout.activity_no_internert

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        mViewDataBinding.BtnTryAgain.setOnClickListener() {
            if (this.isConnected) {
                EventBus.getDefault().postSticky(MessageEvent("network"))
                finish()
            }
        }
    }
}