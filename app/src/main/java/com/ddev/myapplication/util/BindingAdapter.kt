package com.ddev.myapplication.util

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

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

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("transDate")
fun bindDate(view: TextView, historyDate:String) {
    val sdf = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        Locale.ENGLISH
    )
    val parsedDate = sdf.parse(historyDate)
    val showDate = SimpleDateFormat("MMM'.'d")
    val showTime = SimpleDateFormat("h:mm a")
    view.text = showDate.format(parsedDate) + "\n" + showTime.format(parsedDate)

}