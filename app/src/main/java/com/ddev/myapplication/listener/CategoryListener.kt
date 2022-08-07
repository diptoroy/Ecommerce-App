package com.ddev.myapplication.listener

interface CategoryListener<T> {
    fun onCategoryClick(item:T, position:Int)
}