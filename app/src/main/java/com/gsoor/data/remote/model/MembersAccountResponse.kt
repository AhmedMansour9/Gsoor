package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class MembersAccountResponse(
    @SerializedName("Code")
    var code: Int?,
    @SerializedName("Data")
    var `data`: MutableList<Data>?,
    @SerializedName("Message")
    var message: String?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("Email")
        var email: String?,
        @SerializedName("Id")
        var id: String?,
        @SerializedName("MemberCertified")
        var memberCertified: Boolean,
        @SerializedName("MemberVerification")
        var memberVerification: Boolean,
        @SerializedName("StatusType")
        var statusType: Boolean,
        @SerializedName("UserName")
        var userName: String,
        @SerializedName("FullName")
        var FullName: String,

        @SerializedName("UserType")
        var userType: Int?
    ) : Parcelable
}