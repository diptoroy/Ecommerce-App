package com.ddev.myapplication.util.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.ConnectivityDialogBinding

class ConnectivityDialog: Dialog {

    private var dialogLayoutBinding: ConnectivityDialogBinding? = null
    private var rootView: ViewGroup? = null
    private var activity: Activity? = null
    //private var displayMetrics: DisplayMetrics? = null
    private var settingClickListener: View.OnClickListener? = null

    constructor (activity: Activity, rootView: ViewGroup?) : super(activity) {
        this.activity = activity
        this.rootView = rootView
        doConfig()
    }

    private fun doConfig() {
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.connectivity_dialog,
            null,
            false
        )

        dialogLayoutBinding!!.btnSetting.setOnClickListener { view ->
            settingClickListener?.onClick(dialogLayoutBinding!!.btnSetting)
        }

        setContentView(dialogLayoutBinding!!.root)
        setCancelable(false)
    }

    fun setOnSettingBtnClickListener(clickListener: View.OnClickListener?) {
        settingClickListener = clickListener
    }

    override fun show() {
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }

    fun showSuccess() {
        setOnSettingBtnClickListener { view: View? ->
            dismiss()
            context.startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
        }
        show()
    }

}