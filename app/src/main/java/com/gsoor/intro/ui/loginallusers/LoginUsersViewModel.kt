package com.gsoor.intro.ui.loginallusers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AccountResponse
import com.gsoor.data.remote.model.RequestLoginModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginUsersViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<LoginNavigator>(dataCenterManager) {

    var loginRequest: RequestLoginModel = RequestLoginModel()

    private val _accountResponse = MutableLiveData<Resource<AccountResponse>>()
    val accountResponse: LiveData<Resource<AccountResponse>>
        get() = _accountResponse

    fun login() {
        if (!loginRequest.empty()) {
//            viewModelScope.launch {
            _accountResponse.postValue(Resource.loading(null))
            dataCenterManager.loginAccount(loginRequest).enqueue(object : Callback,retrofit2.Callback<AccountResponse> {
                override fun onResponse(
                    call: Call<AccountResponse>,
                    response: Response<AccountResponse>
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


                override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                    _accountResponse.postValue(Resource.error(t.message.toString(), null))

                }
            })


        }
    }
}