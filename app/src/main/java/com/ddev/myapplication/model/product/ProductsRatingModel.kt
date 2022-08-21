package com.ddev.myapplication.model.product

import android.os.Parcelable
import com.ddev.myapplication.model.UserProductRatingModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ProductsRatingModel(
    var productsRating:@RawValue List<UserProductRatingModel>? = null,
):Parcelable
