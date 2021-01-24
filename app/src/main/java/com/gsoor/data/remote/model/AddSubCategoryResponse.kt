package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AddSubCategoryResponse(
    @SerializedName("Code")
    var code: Int?,
    @SerializedName("Data")
    var `data`: Data?,
    @SerializedName("Message")
    var message: String?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("CategoryId")
        var categoryId: Int?,
        @SerializedName("Id")
        var id: Int?,
        @SerializedName("ServiceCondition")
        var serviceCondition: Int?,
        @SerializedName("SubCategoryName")
        var subCategoryName: String?
    ) : Parcelable
}