package com.ddev.myapplication.Application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class EcommerceApp:Application() {

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
    }
}