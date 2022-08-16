package com.ddev.myapplication.util.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.RatingDialogBinding

class ReviewDialog: Dialog {
    private var ratingDialogBinding: RatingDialogBinding? = null
    private var rootView: ViewGroup? = null
    private var activity: Activity? = null

    //private var displayMetrics: DisplayMetrics? = null
    private var onRootClickListener: View.OnClickListener? = null
    private var closeClickListener: View.OnClickListener? = null

    constructor (activity: Activity, rootView: ViewGroup?) : super(activity) {
        this.activity = activity
        this.rootView = rootView
        doConfig()
    }

    private fun doConfig() {
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        ratingDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.rating_dialog,
            null,
            false
        )
        ratingDialogBinding!!.okBtn.setOnClickListener { view ->
            onRootClickListener?.onClick(ratingDialogBinding!!.okBtn)
        }
        setContentView(ratingDialogBinding!!.root)
    }

    fun setOnRootClickListener(clickListener: View.OnClickListener?) {
        onRootClickListener = clickListener
    }

    fun showDialog(){
        show()
    }
}