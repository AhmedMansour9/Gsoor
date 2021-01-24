package com.gsoor.admin.ui.cities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.CitiesResponse
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CitiesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _citiesResponse = MutableLiveData<Resource<CitiesResponse>>()
    val rolesResponse: LiveData<Resource<CitiesResponse>>
        get() = _citiesResponse

    private val _deletcountriesResponse = MutableLiveData<Resource<DeleteCountryResponse>>()
    val deleteResponse: LiveData<Resource<DeleteCountryResponse>>
        get() = _deletcountriesResponse

    fun getCountries(id:String) {
        var map=HashMap<String,String>()
        map.put("CountryId",id)
        _citiesResponse.postValue(Resource.loading(null))
        dataCenterManager.cities(map)
            .enqueue(object :
                Callback, retrofit2.Callback<CitiesResponse> {
                override fun onResponse(
                    call: Call<CitiesResponse>,
                    response: Response<CitiesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _citiesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _citiesResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _citiesResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {
                    _citiesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun deleteCountry(id: String) {

        _deletcountriesResponse.postValue(Resource.loading(null))
        var hashMap = HashMap<String, String>()
        hashMap.put("model", id)
        dataCenterManager.deleteCountries(hashMap)
            .enqueue(object :
                Callback, retrofit2.Callback<DeleteCountryResponse> {
                override fun onResponse(
                    call: Call<DeleteCountryResponse>,
                    response: Response<DeleteCountryResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _deletcountriesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _deletcountriesResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _deletcountriesResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<DeleteCountryResponse>, t: Throwable) {
                    _deletcountriesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}