package com.ddev.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpecModel(
    var specTitle:String? = null,
    var specDetails:String? = null
):Parcelable
