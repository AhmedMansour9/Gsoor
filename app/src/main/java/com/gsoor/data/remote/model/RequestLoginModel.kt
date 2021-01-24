package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import android.text.TextUtils

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestLoginModel(
    @SerializedName("email")
    var email: String?=null,
    @SerializedName("password")
    var password: String?=null
) : Parcelable{
    fun empty() = TextUtils.isEmpty(password) && TextUtils.isEmpty(email)

}

