package com.ddev.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddToCartModel(
    var productId:String? = null,
    var productImage:String? = null,
    var productName:String? = null,
    var productColor:String? = null,
    var productColorName:String? = null,
    var productQuantity:Int? = null,
    var productPrice:Int? = null,
    var productSinglePrice:Int? = null
):Parcelable