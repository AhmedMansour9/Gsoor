package com.gsoor.admin.ui.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.CategoriesResponse
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CategoriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _countriesResponse = MutableLiveData<Resource<CategoriesResponse>>()
    val rolesResponse: LiveData<Resource<CategoriesResponse>>
        get() = _countriesResponse

    private val _deletcountriesResponse = MutableLiveData<Resource<DeleteCountryResponse>>()
    val deleteResponse: LiveData<Resource<DeleteCountryResponse>>
        get() = _deletcountriesResponse

    fun getCategories() {

        _countriesResponse.postValue(Resource.loading(null))
        dataCenterManager.categories()
            .enqueue(object :
                Callback, retrofit2.Callback<CategoriesResponse> {
                override fun onResponse(
                    call: Call<CategoriesResponse>,
                    response: Response<CategoriesResponse>
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

                override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                    _countriesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun deleteCategory(id: String) {

        _deletcountriesResponse.postValue(Resource.loading(null))
        var hashMap = HashMap<String, String>()
        hashMap.put("id", id)
        dataCenterManager.deletecategory(hashMap)
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