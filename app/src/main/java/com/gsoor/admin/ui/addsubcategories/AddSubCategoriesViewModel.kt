package com.gsoor.admin.ui.addsubcategories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cairocartt.utils.Resource
import com.gsoor.base.BaseViewModel
import com.gsoor.data.DataCenterManager
import com.gsoor.data.remote.model.AddSubCategoryResponse
import com.gsoor.data.remote.model.RequestAddCity
import com.gsoor.data.remote.model.RequestAddSubCategory
import com.gsoor.data.remote.model.RequestSaveSubCategory
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddSubCategoriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


    private val _addcityResponse = MutableLiveData<Resource<AddSubCategoryResponse>>()
    val rolesResponse: LiveData<Resource<AddSubCategoryResponse>>
        get() = _addcityResponse

    private val _editcountryResponse = MutableLiveData<Resource<AddSubCategoryResponse>>()
    val editrolesResponse: LiveData<Resource<AddSubCategoryResponse>>
        get() = _editcountryResponse

    fun createCity(request: RequestSaveSubCategory) {
        _addcityResponse.postValue(Resource.loading(null))
        dataCenterManager.saveSubCategory(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddSubCategoryResponse> {
                override fun onResponse(
                    call: Call<AddSubCategoryResponse>,
                    response: Response<AddSubCategoryResponse>
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
                override fun onFailure(call: Call<AddSubCategoryResponse>, t: Throwable) {
                    _addcityResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editCity(request: RequestSaveSubCategory) {

        _editcountryResponse.postValue(Resource.loading(null))
        dataCenterManager.editSubCategory(request)
            .enqueue(object :
                Callback, retrofit2.Callback<AddSubCategoryResponse> {
                override fun onResponse(
                    call: Call<AddSubCategoryResponse>,
                    response: Response<AddSubCategoryResponse>
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
                override fun onFailure(call: Call<AddSubCategoryResponse>, t: Throwable) {
                    _editcountryResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }
}