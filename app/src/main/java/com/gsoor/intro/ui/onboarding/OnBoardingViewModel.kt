package com.gsoor.intro.ui.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager

class OnBoardingViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


}