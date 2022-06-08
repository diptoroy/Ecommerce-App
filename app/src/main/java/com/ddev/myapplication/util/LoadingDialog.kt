package com.ddev.myapplication.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.ddev.myapplication.R

class LoadingDialog : Dialog {
    constructor(context: Context):super(context)
    constructor(context: Context, themeResId:Int):super(context,themeResId)
    constructor(context: Context, cancelable:Boolean, cancelListener: DialogInterface.OnCancelListener):super(context,cancelable,cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(R.drawable.round_dialog)
        setContentView(R.layout.loading_dialog)
        this.setCancelable(false)
    }
}