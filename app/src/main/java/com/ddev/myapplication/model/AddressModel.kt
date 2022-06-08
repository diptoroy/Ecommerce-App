package com.ddev.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressModel(
    var name:String,
    var phone:String,
    var streetHouseNo:String,
    var cityName:String,
    var addressDetail:String
):Parcelable
