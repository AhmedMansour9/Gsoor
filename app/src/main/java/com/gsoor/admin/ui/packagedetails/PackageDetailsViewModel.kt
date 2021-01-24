package com.gsoor.admin.ui.packagedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.CitiesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import com.gsoor.data.remote.model.PackageDetailsResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PackageDetailsViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _packagedetailsResponse = MutableLiveData<Resource<PackageDetailsResponse>>()
    val packagedetailsResponse: LiveData<Resource<PackageDetailsResponse>>
        get() = _packagedetailsResponse

    private val _deletpackagedetailsResponse = MutableLiveData<Resource<DeleteCountryResponse>>()
    val deleteResponse: LiveData<Resource<DeleteCountryResponse>>
        get() = _deletpackagedetailsResponse

    fun getPeckageDetails(id:String) {
        var map=HashMap<String,String>()
        map.put("PackagId",id)
        _packagedetailsResponse.postValue(Resource.loading(null))
        dataCenterManager.packagedetails(map)
            .enqueue(object :
                Callback, retrofit2.Callback<PackageDetailsResponse> {
                override fun onResponse(
                    call: Call<PackageDetailsResponse>,
                    response: Response<PackageDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _packagedetailsResponse.postValue(Resource.success(response.body()))
                        } else {
                            _packagedetailsResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _packagedetailsResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<PackageDetailsResponse>, t: Throwable) {
                    _packagedetailsResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun deletePackageDetails(id: String) {

        _deletpackagedetailsResponse.postValue(Resource.loading(null))
        var hashMap = HashMap<String, String>()
        hashMap.put("id", id)
        dataCenterManager.deletepackagedetails(hashMap)
            .enqueue(object :
                Callback, retrofit2.Callback<DeleteCountryResponse> {
                override fun onResponse(
                    call: Call<DeleteCountryResponse>,
                    response: Response<DeleteCountryResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _deletpackagedetailsResponse.postValue(Resource.success(response.body()))
                        } else {
                            _deletpackagedetailsResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _deletpackagedetailsResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<DeleteCountryResponse>, t: Throwable) {
                    _deletpackagedetailsResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}