package com.ddev.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressModel(
    var name:String? = null,
    var phone:String? = null,
    var streetHouseNo:String? = null,
    var cityName:String? = null,
    var addressDetail:String? = null
):Parcelable
