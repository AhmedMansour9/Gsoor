package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestSaveSubCategory(
    @SerializedName("categoryId")
    var categoryId: String?=null,
    @SerializedName("id")
    var id: String?=null,
    @SerializedName("serviceCondition")
    var serviceCondition: String?=null,
    @SerializedName("subCategoryName")
    var subCategoryName: String?=null
) : Parcelable