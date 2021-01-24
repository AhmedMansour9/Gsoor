package com.gsoor.admin.ui.addcity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AddCountryResponse
import com.gsoor.data.remote.model.RequestAddCity
import com.gsoor.data.remote.model.RequestAddCountry
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddCityViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _addcityResponse = MutableLiveData<Resource<AddCountryResponse>>()
    val rolesResponse: LiveData<Resource<AddCountryResponse>>
        get() = _addcityResponse

    private val _editcountryResponse = MutableLiveData<Resource<AddCountryResponse>>()
    val editrolesResponse: LiveData<Resource<AddCountryResponse>>
        get() = _editcountryResponse

    fun createCity(request: RequestAddCity) {
        _addcityResponse.postValue(Resource.loading(null))
        dataCenterManager.createCity(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddCountryResponse> {
                override fun onResponse(
                    call: Call<AddCountryResponse>,
                    response: Response<AddCountryResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _addcityResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addcityResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _addcityResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AddCountryResponse>, t: Throwable) {
                    _addcityResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editCity(request: RequestAddCity) {

        _editcountryResponse.postValue(Resource.loading(null))
        dataCenterManager.editCity(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddCountryResponse> {
                override fun onResponse(
                    call: Call<AddCountryResponse>,
                    response: Response<AddCountryResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _editcountryResponse.postValue(Resource.success(response.body()))
                        } else {
                            _editcountryResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _editcountryResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AddCountryResponse>, t: Throwable) {
                    _editcountryResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}