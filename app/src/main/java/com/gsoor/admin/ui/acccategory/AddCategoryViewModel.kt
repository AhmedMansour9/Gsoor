package com.gsoor.admin.ui.acccategory

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AddCategoryResponse
import com.gsoor.data.remote.model.AddCountryResponse
import com.gsoor.data.remote.model.RequestAddCountry
import com.gsoor.data.remote.model.RequestSaveCategory
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddCategoryViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _addcountryResponse = MutableLiveData<Resource<AddCategoryResponse>>()
    val rolesResponse: LiveData<Resource<AddCategoryResponse>>
        get() = _addcountryResponse

    private val _editcountryResponse = MutableLiveData<Resource<AddCategoryResponse>>()
    val editrolesResponse: LiveData<Resource<AddCategoryResponse>>
        get() = _editcountryResponse

    fun createCategory(request: RequestSaveCategory) {

        _addcountryResponse.postValue(Resource.loading(null))
        dataCenterManager.createCategory(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddCategoryResponse> {
                override fun onResponse(
                    call: Call<AddCategoryResponse>,
                    response: Response<AddCategoryResponse>
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
                override fun onFailure(call: Call<AddCategoryResponse>, t: Throwable) {
                    _addcountryResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editPermission(request: RequestSaveCategory) {

        _editcountryResponse.postValue(Resource.loading(null))
        dataCenterManager.editCategory(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddCategoryResponse> {
                override fun onResponse(
                    call: Call<AddCategoryResponse>,
                    response: Response<AddCategoryResponse>
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
                override fun onFailure(call: Call<AddCategoryResponse>, t: Throwable) {
                    _editcountryResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}