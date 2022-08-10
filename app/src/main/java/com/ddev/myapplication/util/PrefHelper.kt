package com.ddev.myapplication.util

import android.content.Context
import android.content.SharedPreferences

object PrefHelper {
    private lateinit var prefs: SharedPreferences
    private const val PREF_NAME = "PrefHelper"

    fun init(context: Context){
        prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    }

    //For String
    fun getValue(key:String,value:String):String?{
        return prefs.getString(key,value)
    }

    fun setValue(key:String,value:String){
        val prefEditor: SharedPreferences.Editor = prefs.edit()
        with(prefEditor){
            putString(key,value)
            commit()
        }
    }

    //For Integer
    fun getValue(key:String,value:Int):Int?{
        return prefs.getInt(key,value)
    }

    fun setValue(key:String,value:Int){
        val prefEditor: SharedPreferences.Editor = prefs.edit()
        with(prefEditor){
            putInt(key,value)
            commit()
        }
    }

    //For Boolean
    fun getValue(key:String,value:Boolean):Boolean?{
        return prefs.getBoolean(key,value)
    }

    fun setValue(key:String,value:Boolean){
        val prefEditor: SharedPreferences.Editor = prefs.edit()
        with(prefEditor){
            putBoolean(key,value)
            commit()
            apply()
        }
    }
}