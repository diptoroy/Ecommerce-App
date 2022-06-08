package com.ddev.myapplication.model

import android.graphics.drawable.Drawable

data class FavoriteModel(
    var productId:String,
    var productName:String,
    var productImage: Drawable,
    var productPrice:String,
    var isFavorite:Boolean
)