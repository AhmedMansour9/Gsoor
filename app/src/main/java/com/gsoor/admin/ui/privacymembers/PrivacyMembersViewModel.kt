package com.gsoor.admin.ui.privacymembers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AdminRegisterResponse
import com.gsoor.data.remote.model.PolicyResponse
import com.gsoor.data.remote.model.RequestAdminRegisterModel
import com.gsoor.data.remote.model.SavePolicyResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PrivacyMembersViewModel  @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {



    private val _policyResponse = MutableLiveData<Resource<PolicyResponse>>()
    val accountResponse: LiveData<Resource<PolicyResponse>>
        get() = _policyResponse

    fun savePolicy(link:String,policy:String) {
        _policyResponse.postValue(Resource.loading(null))
       var map=HashMap<String,String>()
        map.put("policyName",policy)

        dataCenterManager.getPolicyStatus(link,map).enqueue(object : Callback,retrofit2.Callback<PolicyResponse> {
            override fun onResponse(
                call: Call<PolicyResponse>,
                response: Response<PolicyResponse>
            ) {
                if(response.isSuccessful){
                    if (response.body()?.code == 200) {
                        _policyResponse.postValue(Resource.success(response.body()))

                    } else {
                        _policyResponse.postValue(Resource.error(response.body()?.message.toString(), null))
                    }
                }else
                    _policyResponse.postValue(Resource.error(response.message(), null))


            }


            override fun onFailure(call: Call<PolicyResponse>, t: Throwable) {
                _policyResponse.postValue(Resource.error(t.message.toString(), null))

            }
        })
    }
}