package com.ddev.myapplication.model.product

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductViewPagerModel(
    var image:String? = null
):Parcelable
