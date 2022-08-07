package com.ddev.myapplication.view.activiry

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.ddev.myapplication.Application.EcommerceApp
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.ActivityMainBinding
import com.ddev.myapplication.listener.LogoutListener
import com.ddev.myapplication.util.ConnectivityMonitor
import com.ddev.myapplication.util.dialog.ConnectivityDialog
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var connectivityMonitor: ConnectivityMonitor
    private lateinit var showDialog: ConnectivityDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        connectivityMonitor = ConnectivityMonitor(application)
        showDialog = ConnectivityDialog(this,mainActivityBinding.root.root)
    }

    override fun onResume() {
        super.onResume()
        connectivityMonitor.observe(this, Observer {isNetwork ->
            if (isNetwork){
                showDialog.dismiss()
            }else{
                showDialog.showSuccess()
            }
        })
 //       EcommerceApp.getApp()!!.registerSessionListener(this)
    }

//    override fun onUserInteraction() {
//        super.onUserInteraction()
//        EcommerceApp.getApp()!!.resetSession()
//    }
//    override fun onSessionLogout() {
//
//    }

}