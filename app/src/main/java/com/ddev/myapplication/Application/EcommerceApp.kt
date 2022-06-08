package com.ddev.myapplication.Application

import android.app.Application

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
    }
}