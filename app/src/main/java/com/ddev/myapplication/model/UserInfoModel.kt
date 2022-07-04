package com.ddev.myapplication.model

data class UserInfoModel(
    var fullName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var accountCreatedTime: String? = null,
    var userToken: String? = null
)