package com.gsoor.admin.ui.roles

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.DeleteRolesResponse
import com.gsoor.data.remote.model.RolesResponse
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

class RolesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _rolesResponse = MutableLiveData<Resource<RolesResponse>>()
    val rolesResponse: LiveData<Resource<RolesResponse>>
        get() = _rolesResponse

    private val _deleteResponse = MutableLiveData<Resource<DeleteRolesResponse>>()
    val deleteResponse: LiveData<Resource<DeleteRolesResponse>>
        get() = _deleteResponse

    fun getRoles() {

        _rolesResponse.postValue(Resource.loading(null))
        dataCenterManager.roles()
            .enqueue(object :
                Callback, retrofit2.Callback<RolesResponse> {
                override fun onResponse(
                    call: Call<RolesResponse>,
                    response: Response<RolesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _rolesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _rolesResponse.postValue(
                                Resource.error(
                                    response.body()?.message.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _rolesResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<RolesResponse>, t: Throwable) {
                    _rolesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun deleteRoles(id:String) {

        _deleteResponse.postValue(Resource.loading(null))
        var hashMap=HashMap<String,String>()
        hashMap.put("model",id)
        dataCenterManager.deleteRoles(hashMap)
            .enqueue(object :
                Callback, retrofit2.Callback<DeleteRolesResponse> {
                override fun onResponse(
                    call: Call<DeleteRolesResponse>,
                    response: Response<DeleteRolesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _deleteResponse.postValue(Resource.success(response.body()))
                        } else {
                            _deleteResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _deleteResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<DeleteRolesResponse>, t: Throwable) {
                    _deleteResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }

}