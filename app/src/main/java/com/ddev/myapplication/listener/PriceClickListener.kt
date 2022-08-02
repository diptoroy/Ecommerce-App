package com.ddev.myapplication.listener

import com.ddev.myapplication.model.AddToCartModel

interface PriceClickListener {
    fun priceClick(plusMinus:Int,position:Int)
}