package com.ddev.myapplication.model

data class OrderModel(
    var userId:String,
    var orderId:String,
    var orderItem:List<AddToCartModel>,
    var address:AddressModel,
    var paymentType:String,
    var orderDate:String,
    var totalPrice:Int
)
