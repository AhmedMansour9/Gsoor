package com.gsoor.admin.ui.registeradmin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AccountResponse
import com.gsoor.data.remote.model.AdminRegisterResponse
import com.gsoor.data.remote.model.RequestAdminRegisterModel
import com.gsoor.data.remote.model.RequestLoginModel
import com.gsoor.intro.ui.loginallusers.LoginNavigator
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RegisterAdminViewModel  @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {

    var itemPositionLanguage = MutableLiveData<Int>()


    private val _accountResponse = MutableLiveData<Resource<AdminRegisterResponse>>()
    val accountResponse: LiveData<Resource<AdminRegisterResponse>>
        get() = _accountResponse

    fun register(registerRequest: RequestAdminRegisterModel) {
//            viewModelScope.launch {
            _accountResponse.postValue(Resource.loading(null))
            dataCenterManager.registerAdmin(registerRequest).enqueue(object : Callback,retrofit2.Callback<AdminRegisterResponse> {
                override fun onResponse(
                    call: Call<AdminRegisterResponse>,
                    response: Response<AdminRegisterResponse>
                ) {
                    if(response.isSuccessful){
                        if (response.body()?.code == 200) {
                            _accountResponse.postValue(Resource.success(response.body()))

                        } else {
                            _accountResponse.postValue(Resource.error(response.body()?.message.toString(), null))
                        }
                    }else
                        _accountResponse.postValue(Resource.error(response.message(), null))


                }


                override fun onFailure(call: Call<AdminRegisterResponse>, t: Throwable) {
                    _accountResponse.postValue(Resource.error(t.message.toString(), null))

                }
            })
    }
}