package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AddPackadgesResponse(
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
        @SerializedName("CountOfServices")
        var countOfServices: Int?,
        @SerializedName("CountOfStars")
        var countOfStars: Int?,
        @SerializedName("Id")
        var id: Int?,
        @SerializedName("IsFree")
        var isFree: Boolean?,
        @SerializedName("PackageName")
        var packageName: String?
    ) : Parcelable
}