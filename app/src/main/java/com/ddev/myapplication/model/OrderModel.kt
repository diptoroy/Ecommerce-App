package com.ddev.myapplication.model

data class OrderModel(
    var userId:String? = null,
    var orderId:String? = null,
    var orderItem:List<AddToCartModel>? = null,
    var address:AddressModel? = null,
    var paymentType:String? = null,
    var orderDate:String? = null,
    var totalPrice:Int? = null,
    var orderStatus:String? = null
)
