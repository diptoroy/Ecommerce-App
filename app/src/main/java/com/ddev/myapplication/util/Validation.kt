package com.ddev.myapplication.util

import android.util.Patterns

object Validation {
    fun validateName(firstName: String, lastName: String):Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty()
    }

    fun checkCap(password:String):Boolean{
        return password.matches(".*[A-Z].*".toRegex())
    }

    fun checkSymbol(password:String):Boolean{
        return password.matches(".*[@#\\\$%^&+=~()?<>!^].*".toRegex())
    }

    fun checkEmail(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String,rePassword:String,firstName:String,lastName:String):Boolean{
        return checkCap(password) && checkSymbol(password) && password.length >= 8 && password == rePassword && password != firstName && password != lastName
    }

    fun validatePasswordField(password: String,firstName:String,lastName:String):Boolean{
        return checkCap(password) && checkSymbol(password) && password != firstName && password != lastName
    }

    fun reValidatePasswordField(password:String,rePassword:String):Boolean{
        return password == rePassword
    }

    fun validatePasswordForNewPasswordFiled(password: String):Boolean{
        return checkCap(password) && checkSymbol(password) && password.length >= 8
    }

    fun validatePasswordForNewPassword(password: String,rePassword: String):Boolean{
        return checkCap(password) && checkSymbol(password) && password.length >= 8 && password == rePassword
    }
}