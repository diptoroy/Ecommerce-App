package com.ddev.myapplication.model.product

import android.os.Parcelable
import com.ddev.myapplication.model.SpecModel
import com.ddev.myapplication.model.product.ColorModel
import com.ddev.myapplication.model.product.ProductSpecModel
import com.ddev.myapplication.model.product.ProductViewPagerModel
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
@Parcelize
data class ProductModel(
    var productId:String? = null,
    var productCategory:String? = null,
    var productImage:@RawValue List<ProductViewPagerModel>? = null,
    var productName:String? = null,
    var productPrice:String? = null,
    var productRating:String? = null,
    var isRating:Boolean? = null,
    var productDesc:String? = null,
    var productSpecification:@RawValue List<SpecModel>? = null,
    var productColor:@RawValue List<ColorModel>? = null,
    var isStock:Boolean? = null
):Parcelable
