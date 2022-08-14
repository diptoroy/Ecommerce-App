package com.ddev.myapplication.util.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.DynamicViewBinding
import com.ddev.myapplication.databinding.SortingProductRowBinding
import kotlinx.android.synthetic.main.sorting_product_row.*

class ProductSortDialog: Dialog {
    private var sortingDialogLayoutBinding: SortingProductRowBinding? = null
//    private var rootView: ViewGroup? = null
//    private var activity: Activity? = null

    private var setLatestProductBtn: View.OnClickListener? = null
    private var setHighPriceProductBtn: View.OnClickListener? = null
    private var setLowPriceProductBtn: View.OnClickListener? = null
    private var setTopRatedProductBtn: View.OnClickListener? = null

//    constructor (activity: Activity, rootView: ViewGroup?) : super(activity) {
//        this.activity = activity
//        this.rootView = rootView
//        doConfig()
//    }
//
//    private fun doConfig() {
//        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        //this.window!!.setGravity(Gravity.TOP)
//        //this.window!!.attributes.windowAnimations = R.style.SortingDialogAnimation
//
//        sortingDialogLayoutBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            R.layout.sorting_product_row,
//            null,
//            false
//        )
//
//        setContentView(sortingDialogLayoutBinding!!.root)
//
//        sortingDialogLayoutBinding!!.sortingDateBtn.setOnClickListener(setLatestProductBtn)
//        sortingDialogLayoutBinding!!.sortingHighPriceBtn.setOnClickListener(setHighPriceProductBtn)
//        sortingDialogLayoutBinding!!.sortingLowPriceBtn.setOnClickListener(setLowPriceProductBtn)
//        sortingDialogLayoutBinding!!.sortingTopRatedBtn.setOnClickListener(setTopRatedProductBtn)
//    }

//    fun showDialog(){
//        show()
//    }

    constructor(context: Context):super(context)
    constructor(context: Context, themeResId:Int):super(context,themeResId)
    constructor(context: Context, cancelable:Boolean, cancelListener: DialogInterface.OnCancelListener):super(context,cancelable,cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //window!!.setGravity(Gravity.TOP)
        sortingDialogLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.sorting_product_row,
            null,
            false
        )

        setContentView(sortingDialogLayoutBinding!!.root)

        sortingDialogLayoutBinding!!.sortingDateBtn.setOnClickListener(setLatestProductBtn)
        sortingDialogLayoutBinding!!.sortingHighPriceBtn.setOnClickListener(setHighPriceProductBtn)
        sortingDialogLayoutBinding!!.sortingLowPriceBtn.setOnClickListener(setLowPriceProductBtn)
        sortingDialogLayoutBinding!!.sortingTopRatedBtn.setOnClickListener(setTopRatedProductBtn)

    }

    fun setLatestBtn( onClickListener: View.OnClickListener){
        dismiss()
        this.setLatestProductBtn = onClickListener
    }

    fun setHighPriceBtn( onClickListener: View.OnClickListener){
        dismiss()
        this.setHighPriceProductBtn = onClickListener
    }

    fun setLowPriceBtn( onClickListener: View.OnClickListener){
        dismiss()
        this.setLowPriceProductBtn = onClickListener
    }

    fun setTopRatedBtn( onClickListener: View.OnClickListener){
        dismiss()
        this.setTopRatedProductBtn = onClickListener
    }
}