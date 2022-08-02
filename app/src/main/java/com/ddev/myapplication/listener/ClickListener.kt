package com.ddev.myapplication.listener

interface ClickListener<T> {
    fun onClick(item:T,position:Int)
}