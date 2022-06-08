package com.ddev.myapplication.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddev.myapplication.model.UserInfoModel
import com.ddev.myapplication.util.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var ref: DocumentReference
    private lateinit var userId: String

    private val _signUpState: MutableStateFlow<State?> = MutableStateFlow(null)
    val signUpState: StateFlow<State?> = _signUpState

    private val _signInState: MutableStateFlow<State?> = MutableStateFlow(null)
    val signInState: StateFlow<State?> = _signInState

    private val _userInfoState: MutableStateFlow<State?> = MutableStateFlow(null)
    val userInfoState: StateFlow<State?> = _userInfoState

    private val _forgotPasswordState: MutableStateFlow<State?> = MutableStateFlow(null)
    val forgotPasswordState: StateFlow<State?> = _forgotPasswordState

    fun getSignUp(email: String, password: String) = viewModelScope.launch {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _signUpState.value = State.Loading
            if (task.isSuccessful) {
                _signUpState.value = State.Success
            }
        }.addOnFailureListener { exception ->
            _signUpState.value = State.Error
        }
    }

    fun getSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _signInState.value = State.Loading
            if (task.isSuccessful) {
                _signInState.value = State.Success
            }
        }.addOnFailureListener {
            _signInState.value = State.Error
        }
    }

    fun getUserInfo(userInfo: UserInfoModel) {
        userId = auth.currentUser!!.uid
        ref = db.collection("Users").document(userId)
        ref.set(userInfo).addOnSuccessListener {
            _userInfoState.value = State.Loading
            if (userInfo != null) {
                _userInfoState.value = State.Success
            }
        }.addOnFailureListener {
            _userInfoState.value = State.Error
        }
    }

    fun getForgotPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            _forgotPasswordState.value = State.Loading
            if (it.isSuccessful) {
                _forgotPasswordState.value = State.Success
            }
        }.addOnFailureListener {
            _forgotPasswordState.value = State.Error
        }
    }

    fun getSignOut() = auth.signOut()
}

