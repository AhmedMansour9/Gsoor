package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestAdminRegisterModel(
    @SerializedName("confirmPassword")
    var confirmPassword: String?=null,
    @SerializedName("email")
    var email: String?=null,
    @SerializedName("password")
    var password: String?=null,
    @SerializedName("phoneNumber")
    var phoneNumber: String?=null,
    @SerializedName("roles")
    var roles: String?=null,
    @SerializedName("userName")
    var firstName: String?=null,
    @SerializedName("fullName")
    var lastName: String?=null
) : Parcelable