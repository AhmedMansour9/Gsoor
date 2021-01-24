package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestAddSubCategory(
    @SerializedName("categoryId")
    var categoryId: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("serviceCondition")
    var serviceCondition: String?,
    @SerializedName("subCategoryName")
    var subCategoryName: String?
) : Parcelable