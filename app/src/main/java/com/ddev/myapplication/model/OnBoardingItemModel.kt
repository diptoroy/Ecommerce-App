package com.ddev.myapplication.model

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

data class OnBoardingItemModel(
    val onBoardingImage:Drawable,
    val onBoardingTitle:String,
    val onBoardingDesc:String
)
