package com.gsoor.intro.ui.chooseacount

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager

class ChooseAccountViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {

    var itemPositionLanguage = MutableLiveData<Int>()


}