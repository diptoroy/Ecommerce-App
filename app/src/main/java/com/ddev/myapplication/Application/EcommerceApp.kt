package com.ddev.myapplication.Application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import com.ddev.myapplication.listener.LifecycleListener
import com.ddev.myapplication.listener.LogoutListener
import java.util.*

class EcommerceApp:Application() {

    private var logoutListener: LogoutListener? = null
    private var timer: Timer? = null

    private val lifecycleListener: LifecycleListener by lazy {
        LifecycleListener()
    }

    companion object {
        private var app: EcommerceApp? = null
        fun getApp(): EcommerceApp? {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupLifecycleListener()
    }
    private fun setupLifecycleListener() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleListener)
    }

//
//    private fun userSessionStart() {
//        timer?.cancel()
//        timer = Timer()
//        timer!!.schedule(object : TimerTask() {
//            override fun run() {
//                logoutListener?.onSessionLogout()
//            }
//        }, 10000)
//        //1000 * 60 * 2
//    }
//
//    fun resetSession() {
//        userSessionStart()
//    }
//
//    fun registerSessionListener(listener: LogoutListener) {
//        logoutListener = listener
//    }

}