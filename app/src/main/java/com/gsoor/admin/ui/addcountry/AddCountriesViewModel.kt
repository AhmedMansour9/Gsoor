package com.gsoor.admin.ui.addcountry

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AddCountryResponse
import com.gsoor.data.remote.model.AdminRegisterResponse
import com.gsoor.data.remote.model.RequestAddCountry
import com.gsoor.data.remote.model.RequestCreatePermission
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddCountriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
BaseViewModel<Any>(dataCenterManager) {


    private val _addcountryResponse = MutableLiveData<Resource<AddCountryResponse>>()
    val rolesResponse: LiveData<Resource<AddCountryResponse>>
    get() = _addcountryResponse

    private val _editcountryResponse = MutableLiveData<Resource<AddCountryResponse>>()
    val editrolesResponse: LiveData<Resource<AddCountryResponse>>
    get() = _editcountryResponse

    fun createPermission(request: RequestAddCountry) {

        _addcountryResponse.postValue(Resource.loading(null))
        dataCenterManager.createCountry(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddCountryResponse> {
                override fun onResponse(
                    call: Call<AddCountryResponse>,
                    response: Response<AddCountryResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _addcountryResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addcountryResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _addcountryResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<AddCountryResponse>, t: Throwable) {
                    _addcountryResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editPermission(request: RequestAddCountry) {

        _editcountryResponse.postValue(Resource.loading(null))
        dataCenterManager.editCountry(request)
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