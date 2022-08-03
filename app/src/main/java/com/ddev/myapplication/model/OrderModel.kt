package com.ddev.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderModel(
    var userId:String? = null,
    var orderId:String? = null,
    var orderItem:List<AddToCartModel>? = null,
    var address:AddressModel? = null,
    var paymentType:String? = null,
    var orderDate:String? = null,
    var totalPrice:Int? = null,
    var orderStatus:String? = null,
    var orderShipmentProcessModel: List<ShipmentProcessModel>? = null
):Parcelable
