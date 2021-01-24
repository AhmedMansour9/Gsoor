package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class PackagesResponse(
    @SerializedName("Code")
    var code: Int?,
    @SerializedName("Data")
    var `data`: List<Data>?,
    @SerializedName("Message")
    var message: String?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("CountOfServices")
        var countOfServices: String?,
        @SerializedName("CountOfStars")
        var countOfStars: Float,
        @SerializedName("Id")
        var id: Int?,
        @SerializedName("IsFree")
        var isFree: Boolean,
        @SerializedName("PackageName")
        var packageName: String?
    ) : Parcelable
}