package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestSavePackadges(
    @SerializedName("CountOfServices")
    var countOfServices: Int?=null,
    @SerializedName("CountOfStars")
    var countOfStars: Int?=null,
    @SerializedName("Id")
    var id: Int?=null,
    @SerializedName("IsFree")
    var isFree: Boolean?=null,
    @SerializedName("PackageName")
    var packageName: String?=null
) : Parcelable