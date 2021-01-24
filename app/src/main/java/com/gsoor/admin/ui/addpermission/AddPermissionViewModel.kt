package com.gsoor.admin.ui.addpermission

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AdminRegisterResponse
import com.gsoor.data.remote.model.DeleteRolesResponse
import com.gsoor.data.remote.model.RequestCreatePermission
import com.gsoor.data.remote.model.RolesResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddPermissionViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _addroleResponse = MutableLiveData<Resource<AdminRegisterResponse>>()
    val rolesResponse: LiveData<Resource<AdminRegisterResponse>>
        get() = _addroleResponse

    private val _editroleResponse = MutableLiveData<Resource<AdminRegisterResponse>>()
    val editrolesResponse: LiveData<Resource<AdminRegisterResponse>>
        get() = _editroleResponse

    fun createPermission(request:RequestCreatePermission) {

        _addroleResponse.postValue(Resource.loading(null))
        dataCenterManager.createPerimssion(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AdminRegisterResponse> {
                override fun onResponse(
                    call: Call<AdminRegisterResponse>,
                    response: Response<AdminRegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _addroleResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addroleResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _addroleResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AdminRegisterResponse>, t: Throwable) {
                    _addroleResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editPermission(request:RequestCreatePermission) {

        _editroleResponse.postValue(Resource.loading(null))
        dataCenterManager.editPerimssion(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AdminRegisterResponse> {
                override fun onResponse(
                    call: Call<AdminRegisterResponse>,
                    response: Response<AdminRegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _editroleResponse.postValue(Resource.success(response.body()))
                        } else {
                            _editroleResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _editroleResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AdminRegisterResponse>, t: Throwable) {
                    _editroleResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}