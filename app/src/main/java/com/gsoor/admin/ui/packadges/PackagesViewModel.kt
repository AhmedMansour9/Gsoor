package com.gsoor.admin.ui.packadges

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.CategoriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import com.gsoor.data.remote.model.PackagesResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PackagesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _packagesResponse = MutableLiveData<Resource<PackagesResponse>>()
    val packagesResponse: LiveData<Resource<PackagesResponse>>
        get() = _packagesResponse

    private val _deletePackagesResponse = MutableLiveData<Resource<DeleteCountryResponse>>()
    val deletePackagesResponse: LiveData<Resource<DeleteCountryResponse>>
        get() = _deletePackagesResponse

    fun getPackages() {
        _packagesResponse.postValue(Resource.loading(null))
        dataCenterManager.getPackadges()
            .enqueue(object :
                Callback, retrofit2.Callback<PackagesResponse> {
                override fun onResponse(
                    call: Call<PackagesResponse>,
                    response: Response<PackagesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _packagesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _packagesResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _packagesResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<PackagesResponse>, t: Throwable) {
                    _packagesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun delete(id: String) {

        _deletePackagesResponse.postValue(Resource.loading(null))
        var hashMap = HashMap<String, String>()
        hashMap.put("id", id)
        dataCenterManager.deletePackadge(hashMap)
            .enqueue(object :
                Callback, retrofit2.Callback<DeleteCountryResponse> {
                override fun onResponse(
                    call: Call<DeleteCountryResponse>,
                    response: Response<DeleteCountryResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _deletePackagesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _deletePackagesResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _deletePackagesResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<DeleteCountryResponse>, t: Throwable) {
                    _deletePackagesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}