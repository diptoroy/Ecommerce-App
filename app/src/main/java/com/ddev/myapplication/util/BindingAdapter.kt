package com.ddev.myapplication.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("urlImage")
fun bindUrlImage(view: AppCompatImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .into(view)
    } else {
        view.setImageBitmap(null)
    }
}