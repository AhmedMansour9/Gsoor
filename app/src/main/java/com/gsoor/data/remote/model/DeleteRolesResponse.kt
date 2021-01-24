package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class DeleteRolesResponse(
    @SerializedName("Code")
    var code: Int?,
    @SerializedName("Data")
    var `data`: String?,
    @SerializedName("Message")
    var message: String?
) : Parcelable