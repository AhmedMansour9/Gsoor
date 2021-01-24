package com.gsoor.admin.ui.navigationadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.gsoor.R
import com.gsoor.base.BaseActivity
import com.gsoor.databinding.ActivityNavigationAdminBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NavigationAdmin : BaseActivity<ActivityNavigationAdminBinding>()
{
    override var idLayoutRes: Int = R.layout.activity_navigation_admin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}