package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestSavePackageDetails(
    @SerializedName("id")
    var id: String?=null,
    @SerializedName("packagesId")
    var packagesId: String?=null,
    @SerializedName("price")
    var price: String?=null,
    @SerializedName("timeType")
    var timeType: String?=null,
    @SerializedName("timeValue")
    var timeValue: String?=null
) : Parcelable