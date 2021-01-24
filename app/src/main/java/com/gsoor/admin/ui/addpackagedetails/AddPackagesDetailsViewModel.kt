package com.gsoor.admin.ui.addpackagedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AddPackadgesResponse
import com.gsoor.data.remote.model.AddPackageDetailsResponse
import com.gsoor.data.remote.model.RequestSavePackadges
import com.gsoor.data.remote.model.RequestSavePackageDetails
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddPackagesDetailsViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _addPackagedetailsResponse = MutableLiveData<Resource<AddPackageDetailsResponse>>()
    val addPackagesResponse: LiveData<Resource<AddPackageDetailsResponse>>
        get() = _addPackagedetailsResponse

    private val _editPackageResponse = MutableLiveData<Resource<AddPackageDetailsResponse>>()
    val editrolesResponse: LiveData<Resource<AddPackageDetailsResponse>>
        get() = _editPackageResponse

    fun createPackageDetails(request: RequestSavePackageDetails) {

        _addPackagedetailsResponse.postValue(Resource.loading(null))
        dataCenterManager.createPackageDetails(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddPackageDetailsResponse> {
                override fun onResponse(
                    call: Call<AddPackageDetailsResponse>,
                    response: Response<AddPackageDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _addPackagedetailsResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addPackagedetailsResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _addPackagedetailsResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AddPackageDetailsResponse>, t: Throwable) {
                    _addPackagedetailsResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editPackagesDetails(request: RequestSavePackageDetails) {

        _editPackageResponse.postValue(Resource.loading(null))
        dataCenterManager.editPackageDetails(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddPackageDetailsResponse> {
                override fun onResponse(
                    call: Call<AddPackageDetailsResponse>,
                    response: Response<AddPackageDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _editPackageResponse.postValue(Resource.success(response.body()))
                        } else {
                            _editPackageResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _editPackageResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AddPackageDetailsResponse>, t: Throwable) {
                    _editPackageResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }



}
