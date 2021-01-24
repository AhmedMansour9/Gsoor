package com.gsoor.data.remote.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DeleteCountryResponse(
    @SerializedName("Code")
    var code: Int?,
    @SerializedName("Message")
    var message: String?
) : Parcelable