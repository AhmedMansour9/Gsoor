package com.gsoor.admin.ui.subcategories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.CitiesResponse
import com.gsoor.data.remote.model.DeleteCountryResponse
import com.gsoor.data.remote.model.SubCategoriesResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SubCategoriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _subcategoriesResponse = MutableLiveData<Resource<SubCategoriesResponse>>()
    val rolesResponse: LiveData<Resource<SubCategoriesResponse>>
        get() = _subcategoriesResponse

    private val _deletcountriesResponse = MutableLiveData<Resource<DeleteCountryResponse>>()
    val deleteResponse: LiveData<Resource<DeleteCountryResponse>>
        get() = _deletcountriesResponse

    fun getSubCategories(id:String) {
        var map=HashMap<String,String>()
        map.put("CategoryId",id)
        _subcategoriesResponse.postValue(Resource.loading(null))
        dataCenterManager.subcategories(map)
            .enqueue(object :
                Callback, retrofit2.Callback<SubCategoriesResponse> {
                override fun onResponse(
                    call: Call<SubCategoriesResponse>,
                    response: Response<SubCategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            _subcategoriesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _subcategoriesResponse.postValue(
                                Resource.error(
                                    response.body()?.code.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _subcategoriesResponse.postValue(Resource.error(response.message(), null))
                }

                override fun onFailure(call: Call<SubCategoriesResponse>, t: Throwable) {
                    _subcategoriesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun deleteCountry(id: String) {

        _deletcountriesResponse.postValue(Resource.loading(null))
        var hashMap = HashMap<String, String>()
        hashMap.put("id", id)
        dataCenterManager.deletesubcategory(hashMap)
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