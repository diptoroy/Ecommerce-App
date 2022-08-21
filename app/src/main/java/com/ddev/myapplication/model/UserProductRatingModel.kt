package com.ddev.myapplication.model

data class UserProductRatingModel(
    var userId: String? = null,
    var productId: String? = null,
    var productRating: String? = null,
    var note: String? = null,
)
