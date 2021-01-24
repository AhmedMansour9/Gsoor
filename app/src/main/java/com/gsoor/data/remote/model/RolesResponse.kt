package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RolesResponse(
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
        @SerializedName("Id")
        var id: String?,
        @SerializedName("Name")
        var name: String?,
        @SerializedName("Permissions")
        var permissions: List<Int>?,
        @SerializedName("UserType")
        var userType: Int?
    ) : Parcelable {
        override fun toString(): String {
            return "$name"
        }
    }
}