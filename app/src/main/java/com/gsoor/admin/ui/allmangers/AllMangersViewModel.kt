package com.gsoor.admin.ui.allmangers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AllMangersResponse
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AllMangersViewModel  @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _mangersResponse = MutableLiveData<Resource<AllMangersResponse>>()
    val rolesResponse: LiveData<Resource<AllMangersResponse>>
        get() = _mangersResponse

    private val _deletmangersResponse = MutableLiveData<Resource<DeleteCountryResponse>>()
    val deleteResponse: LiveData<Resource<DeleteCountryResponse>>
        get() = _deletmangersResponse

    fun getMangers() {

        _mangersResponse.postValue(Resource.loading(null))
        dataCenterManager.mangers()
            .enqueue(object :
                Callback, retrofit2.Callback<AllMangersResponse> {
                override fun onResponse(
                    call: Call<AllMangersResponse>,
                    response: Response<AllMangersResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _mangersResponse.postValue(Resource.success(response.body()))
                        } else {
                            _mangersResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _mangersResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<AllMangersResponse>, t: Throwable) {
                    _mangersResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun deleteCountry(id: String) {

        _deletmangersResponse.postValue(Resource.loading(null))
        var hashMap = HashMap<String, String>()
        hashMap.put("id", id)
        dataCenterManager.deletemanger(hashMap)
            .enqueue(object :
                Callback, retrofit2.Callback<DeleteCountryResponse> {
                override fun onResponse(
                    call: Call<DeleteCountryResponse>,
                    response: Response<DeleteCountryResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _deletmangersResponse.postValue(Resource.success(response.body()))
                        } else {
                            _deletmangersResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _deletmangersResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<DeleteCountryResponse>, t: Throwable) {
                    _deletmangersResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}