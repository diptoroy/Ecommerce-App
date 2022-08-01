package com.ddev.myapplication.model.product

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ColorModel(
    var colorName:String? = null,
    var color:String? = null
):Parcelable
