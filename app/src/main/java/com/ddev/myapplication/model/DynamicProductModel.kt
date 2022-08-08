package com.ddev.myapplication.model

data class DynamicProductModel(
    var dynamicViewName:String? = null,//this should be "FullImage" or "Product"
    var dynamicProductId:String? =null,
    var dynamicProductName:String? =null,
    var dynamicProductImage:String? =null,
    var dynamicProductPrice:String? =null,
    var dynamicProductFullImage:String? =null
)
