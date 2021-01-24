package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestCreatePermission(
    @SerializedName("id")
    var id: String?=null,
    @SerializedName("permissions")
    var permissions: MutableList<String>?=null,
    @SerializedName("roleName")
    var roleName: String?=null
) : Parcelable