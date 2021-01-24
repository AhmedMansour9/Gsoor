package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AdminRegisterResponse(
    @SerializedName("Code")
    var code: Int?,
    @SerializedName("Message")
    var message: String?
) : Parcelable