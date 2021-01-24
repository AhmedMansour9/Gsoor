package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestEditManger(
    @SerializedName("email")
    var email: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("phoneNumber")
    var phoneNumber: String?,
    @SerializedName("roles")
    var roles: String?
) : Parcelable