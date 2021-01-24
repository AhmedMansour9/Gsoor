package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AllMangersResponse(
    @SerializedName("Code")
    var code: Int?,
    @SerializedName("Data")
    var `data`: List<Data?>?,
    @SerializedName("Message")
    var message: String?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("Email")
        var email: String?,
        @SerializedName("UserName")
        var firstName: String,
        @SerializedName("Id")
        var id: String?,
        @SerializedName("FullName")
        var lastName: String,
        @SerializedName("PhoneNumber")
        var phoneNumber: String,
        @SerializedName("RoleName")
        var roleName: String?,
        @SerializedName("Roles")
        var roles: String?
    ) : Parcelable
}