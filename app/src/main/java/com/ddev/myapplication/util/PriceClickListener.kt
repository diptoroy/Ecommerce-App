package com.ddev.myapplication.util

import com.ddev.myapplication.model.AddToCartModel

interface PriceClickListener {
    fun priceClick(plusMinus:Int,position:Int)
}