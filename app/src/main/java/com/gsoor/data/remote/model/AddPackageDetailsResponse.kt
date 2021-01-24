package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AddPackageDetailsResponse(
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
        @SerializedName("Id")
        var id: Int?,
        @SerializedName("PackagesId")
        var packagesId: Int?,
        @SerializedName("Price")
        var price: Double?,
        @SerializedName("TimeType")
        var timeType: Int?,
        @SerializedName("TimeValue")
        var timeValue: Int?
    ) : Parcelable
}