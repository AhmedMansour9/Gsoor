package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestAddCity(
    @SerializedName("cityName")
    var cityName: String?=null,
    @SerializedName("countryId")
    var countryId: String?=null,
    @SerializedName("id")
    var id: String?=null
) : Parcelable