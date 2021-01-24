package com.gsoor.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CitiesResponse(
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
        @SerializedName("CityName")
        var cityName: String?,
        @SerializedName("CountryId")
        var countryId: Int?,
        @SerializedName("CountryName")
        var countryName: String?,
        @SerializedName("Id")
        var id: Int?
    ) : Parcelable
}