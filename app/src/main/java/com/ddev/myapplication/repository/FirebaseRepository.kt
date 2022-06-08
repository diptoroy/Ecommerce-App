package com.ddev.myapplication.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseRepository {
    private var auth: FirebaseAuth = Firebase.auth

    suspend fun getRegister(fullName:String,email:String,phone:String,password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {

        }
    }
}