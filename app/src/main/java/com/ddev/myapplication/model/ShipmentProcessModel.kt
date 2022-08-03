package com.ddev.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShipmentProcessModel(
    var processName:String? = null,
    var isProcessComplete: Boolean? = null,
    var productDate:String? = null
):Parcelable
