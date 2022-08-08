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
import com.ddev.myapplication.databinding.CustomDialogLayoutBinding
import com.ddev.myapplication.databinding.DynamicViewBinding
import com.squareup.picasso.Picasso

class DynamicViewDialog:Dialog {

    private var dynamicDialogLayoutBinding: DynamicViewBinding? = null
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

//        displayMetrics = DisplayMetrics()
//        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(
//            displayMetrics
//        )

        dynamicDialogLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dynamic_view,
            null,
            false
        )

        dynamicDialogLayoutBinding!!.dynamicViewRoot.minimumWidth =
            context.resources.displayMetrics.widthPixels

        dynamicDialogLayoutBinding!!.dynamicProductImage.visibility = View.GONE
        dynamicDialogLayoutBinding!!.dynamicProductName.visibility = View.GONE
        dynamicDialogLayoutBinding!!.dynamicProductPrice.visibility = View.GONE
        dynamicDialogLayoutBinding!!.dynamicFullImageView.visibility = View.GONE

        dynamicDialogLayoutBinding!!.dynamicViewRoot.setOnClickListener { view ->
            onRootClickListener?.onClick(dynamicDialogLayoutBinding!!.dynamicViewRoot)
        }

        dynamicDialogLayoutBinding!!.dynamicCloseBtnContainer.setOnClickListener { view ->
            closeClickListener?.onClick(dynamicDialogLayoutBinding!!.dynamicCloseBtnContainer)
        }

        setContentView(dynamicDialogLayoutBinding!!.root)
    }

    private fun setDynamicProduct(name: String?, price: String, image: String) {
        dynamicDialogLayoutBinding!!.dynamicProductName.text = name
        dynamicDialogLayoutBinding!!.dynamicProductPrice.text = price
        Picasso.get().load(image).into(dynamicDialogLayoutBinding!!.dynamicProductImage);
        dynamicDialogLayoutBinding!!.dynamicProductImage.visibility = View.VISIBLE
        dynamicDialogLayoutBinding!!.dynamicProductName.visibility = View.VISIBLE
        dynamicDialogLayoutBinding!!.dynamicProductPrice.visibility = View.VISIBLE
    }

    private fun setDynamicImage(image: String) {
        Picasso.get().load(image).into(dynamicDialogLayoutBinding!!.dynamicFullImageView);
        dynamicDialogLayoutBinding!!.dynamicFullImageView.visibility = View.VISIBLE
    }

    fun setOnRootClickListener(clickListener: View.OnClickListener?) {
        onRootClickListener = clickListener
    }

    private fun setOnCloseBtnClickListener(clickListener: View.OnClickListener?) {
        closeClickListener = clickListener
    }

    override fun show() {
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }

    fun showDynamicProduct(name: String?, price: String, image: String){
        setDynamicProduct(name,price,image)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        setOnCloseBtnClickListener{ view: View? -> dismiss() }
        show()
    }

    fun showDynamicImage(image: String){
        setDynamicImage(image)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        setOnCloseBtnClickListener{ view: View? -> dismiss() }
        show()
    }
}