package com.gsoor.data.remote.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class RequestUpdateStatus(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("memberVerification")
    var memberVerification: String? = null,
    @SerializedName("memberCertified")
    var memberCertified: String? = null,
    @SerializedName("statusType")
    var statusType: String? = null,

    ) : Parcelable
