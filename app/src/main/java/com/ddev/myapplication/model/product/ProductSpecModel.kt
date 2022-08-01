package com.ddev.myapplication.model.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductSpecModel(
    var productDisplay:String? = null,
    var productRam:String? = null,
    var productRom:String? = null,
    var productCamera:String? = null,
    var productBattery:String? = null
):Parcelable
