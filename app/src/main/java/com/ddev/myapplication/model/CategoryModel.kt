package com.ddev.myapplication.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CategoryModel(
    var categoryImage:@RawValue Drawable? = null,
    var categoryName:String? = null
):Parcelable
