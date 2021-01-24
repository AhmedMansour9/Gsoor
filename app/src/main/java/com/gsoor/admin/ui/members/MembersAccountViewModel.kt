package com.gsoor.admin.ui.members

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MembersAccountViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _memberResponse = MutableLiveData<Resource<MembersAccountResponse>>()
    val membersResponse: LiveData<Resource<MembersAccountResponse>>
        get() = _memberResponse

    private val _editmembersResponse = MutableLiveData<Resource<UpDateStatusResponse>>()
    val editmemberResponse: LiveData<Resource<UpDateStatusResponse>>
        get() = _editmembersResponse

    fun getSubMembers(user_Id: String) {
        var map = HashMap<String, String>()
        map.put("UserType", user_Id)
        _memberResponse.postValue(Resource.loading(null))
        dataCenterManager.getMembersAccounts(map)
            .enqueue(object :
                Callback, retrofit2.Callback<MembersAccountResponse> {
                override fun onResponse(
                    call: Call<MembersAccountResponse>,
                    response: Response<MembersAccountResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _memberResponse.postValue(Resource.success(response.body()))
                        } else {
                            _memberResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _memberResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<MembersAccountResponse>, t: Throwable) {
                    _memberResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun updateStatus(link: String, id: String, status: String) {

        _editmembersResponse.postValue(Resource.loading(null))
        var request = RequestUpdateStatus()
        request.id=id
        if (link.equals("MembersAccount/UpdateVerification")) {
           request.memberVerification=status
        } else if (link.equals("MembersAccount/UpdateCertified")) {
            request.memberCertified=status

        } else {
            request.statusType=status
        }


        dataCenterManager.editMemberStatus(link, request)
            .enqueue(object :
                Callback, retrofit2.Callback<UpDateStatusResponse> {
                override fun onResponse(
                    call: Call<UpDateStatusResponse>,
                    response: Response<UpDateStatusResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _editmembersResponse.postValue(Resource.success(response.body()))
                        } else {
                            _editmembersResponse.postValue(
                                Resource.error(
                                    response.body()?.message.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _editmembersResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<UpDateStatusResponse>, t: Throwable) {
                    _editmembersResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}