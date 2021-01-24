package com.gsoor.intro.ui.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.cairocartt.utils.SharedData
import com.gsoor.ChangeLanguage
import com.gsoor.R
import com.gsoor.intro.adapter.IntroSliderAdapter
import com.gsoor.base.BaseActivity
import com.gsoor.data.remote.model.IntroSlide
import com.gsoor.databinding.ActivityOnBoardingBinding
import com.gsoor.intro.ui.chooseacount.ChooseAccount
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    override var idLayoutRes: Int= R.layout.activity_on_boarding

    private var data: SharedData? = null

    private lateinit var introSliderAdapter :IntroSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(0xff0180AD.toInt())
        }
        Lingver.getInstance().setLocale(this, ChangeLanguage.getLanguage(this), "")
        super.onCreate(savedInstanceState)

        data = SharedData(this)
        initAdapter()
        skip()
        mViewDataBinding.viewPager.adapter = introSliderAdapter
        mViewDataBinding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == introSliderAdapter.itemCount - 1) {
                        val animation = AnimationUtils.loadAnimation(
                            this@OnBoardingActivity,
                            R.anim.app_name_animation
                        )
                        mViewDataBinding.buttonNext.animation = animation
                        mViewDataBinding.buttonNext.text = resources.getString(R.string.finish)
                        mViewDataBinding.buttonNext.setOnClickListener {
                            data?.putValue("slider", "true")
                            startActivity(
                                Intent(
                                    this@OnBoardingActivity,
                                    ChooseAccount::class.java
                                )
                            )
                            finish()
                        }
                    } else {
                        mViewDataBinding.buttonNext.text = resources.getString(R.string.next)
                        mViewDataBinding.buttonNext.setOnClickListener {
                            mViewDataBinding.viewPager.currentItem.let {
                                mViewDataBinding.viewPager.setCurrentItem(it + 1, false)
                            }
                        }
                    }
                }
            })


    }

    private fun initAdapter() {
        introSliderAdapter = IntroSliderAdapter(
            listOf(
                IntroSlide(
                    resources.getString(R.string.f_text),
                    ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_firstdot)
                ),
                IntroSlide(
                    resources.getString(R.string.second_txt)
                        ,ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_seconddot)
                ),
                IntroSlide(
                    resources.getString(R.string.third_txt),ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_thirddot)
                )
            )
        )
    }

    fun skip(){
        mViewDataBinding.buttonlogin.setOnClickListener(){
            data?.putValue("slider", "true")
            startActivity(
                Intent(
                    this@OnBoardingActivity,
                    ChooseAccount::class.java
                )
            )
            finish()
        }
    }
}