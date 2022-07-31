package com.ddev.myapplication.model

import android.graphics.drawable.Drawable

data class FavoriteModel(
    var productId:String? = null,
    var productName:String? = null,
    var productImage: String? = null,
    var productPrice:String? = null,
    var isFavorite:Boolean? = null
)