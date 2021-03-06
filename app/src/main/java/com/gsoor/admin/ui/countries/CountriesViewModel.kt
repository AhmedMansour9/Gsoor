package com.gsoor.admin.ui.countries

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import com.gsoor.data.remote.model.DeleteRolesResponse
import com.gsoor.data.remote.model.RolesResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CountriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _countriesResponse = MutableLiveData<Resource<CountriesResponse>>()
    val rolesResponse: LiveData<Resource<CountriesResponse>>
        get() = _countriesResponse

    private val _deletcountriesResponse = MutableLiveData<Resource<DeleteCountryResponse>>()
    val deleteResponse: LiveData<Resource<DeleteCountryResponse>>
        get() = _deletcountriesResponse

    fun getCountries() {

        _countriesResponse.postValue(Resource.loading(null))
        dataCenterManager.countries()
            .enqueue(object :
                Callback, retrofit2.Callback<CountriesResponse> {
                override fun onResponse(
                    call: Call<CountriesResponse>,
                    response: Response<CountriesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _countriesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _countriesResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _countriesResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<CountriesResponse>, t: Throwable) {
                    _countriesResponse.postValue(Resource.error(t.message.toString(), null))
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