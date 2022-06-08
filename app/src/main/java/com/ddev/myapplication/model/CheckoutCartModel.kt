package com.ddev.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CheckoutCartModel: ArrayList<AddToCartModel>(), Parcelable