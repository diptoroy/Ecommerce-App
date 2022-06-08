package com.ddev.myapplication.util

interface ClickListener<T> {
    fun onClick(item:T,position:Int)
}