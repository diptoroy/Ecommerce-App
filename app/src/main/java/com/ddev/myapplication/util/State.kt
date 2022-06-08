package com.ddev.myapplication.util

sealed class State(){
    object Success:State()
    object Error:State()
    object Loading:State()
}
