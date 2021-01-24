package com.gsoor.admin.ui.addpackage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AddCategoryResponse
import com.gsoor.data.remote.model.AddPackadgesResponse
import com.gsoor.data.remote.model.RequestSaveCategory
import com.gsoor.data.remote.model.RequestSavePackadges
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddPackagesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _addPackageResponse = MutableLiveData<Resource<AddPackadgesResponse>>()
    val addPackagesResponse: LiveData<Resource<AddPackadgesResponse>>
        get() = _addPackageResponse

    private val _editPackageResponse = MutableLiveData<Resource<AddPackadgesResponse>>()
    val editrolesResponse: LiveData<Resource<AddPackadgesResponse>>
        get() = _editPackageResponse

    fun createPackage(request: RequestSavePackadges) {

        _addPackageResponse.postValue(Resource.loading(null))
        dataCenterManager.createPackadge(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddPackadgesResponse> {
                override fun onResponse(
                    call: Call<AddPackadgesResponse>,
                    response: Response<AddPackadgesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _addPackageResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addPackageResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _addPackageResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AddPackadgesResponse>, t: Throwable) {
                    _addPackageResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editPackages(request: RequestSavePackadges) {

        _editPackageResponse.postValue(Resource.loading(null))
        dataCenterManager.editPackadges(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddPackadgesResponse> {
                override fun onResponse(
                    call: Call<AddPackadgesResponse>,
                    response: Response<AddPackadgesResponse>
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
                override fun onFailure(call: Call<AddPackadgesResponse>, t: Throwable) {
                    _editPackageResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}