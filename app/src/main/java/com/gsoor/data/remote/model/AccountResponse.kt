package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AccountResponse(
        @SerializedName("Code")
        var code: Int?=0,
        @SerializedName("Data")
        var `data`: Data?=null,
        @SerializedName("Message")
        var message: String?=null
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
            @SerializedName("Code")
            var code: Int?,
            @SerializedName("Message")
            var message: String?,
            @SerializedName("Permissions")
            var permissions: List<Int>?,
            @SerializedName("Role")
            var role: String?,
            @SerializedName("Token")
            var token: String?,
            @SerializedName("UserType")
            var userType: String?
    ) : Parcelable
}