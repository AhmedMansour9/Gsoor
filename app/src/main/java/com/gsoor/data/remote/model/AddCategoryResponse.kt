package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AddCategoryResponse(
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
        @SerializedName("CategoryName")
        var categoryName: String?,
        @SerializedName("Id")
        var id: Int?
    ) : Parcelable
}