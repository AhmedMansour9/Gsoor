package com.gsoor.admin.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import javax.inject.Singleton


@Singleton
class HomeAdminViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<HomeAdminNavigator>(dataCenterManager) {

    

}