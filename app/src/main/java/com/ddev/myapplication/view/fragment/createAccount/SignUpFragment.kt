package com.ddev.myapplication.view.fragment.createAccount

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentSignUpBinding
import com.ddev.myapplication.model.UserInfoModel
import com.ddev.myapplication.util.LoadingDialog
import com.ddev.myapplication.util.State
import com.ddev.myapplication.util.Validation
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var isAllCheck: Boolean = false
    lateinit var loadingDialog: LoadingDialog

    private val signUpViewModel: AuthViewModel by navGraphViewModels(R.id.nav_graph) {
        SavedStateViewModelFactory(
            requireActivity().application,
            requireParentFragment()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        loadingDialog = LoadingDialog(requireContext())

        fragmentBinding.signInBtn.setOnClickListener {
            var action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            navController.navigate(action)
        }

        storeData(navController)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun storeData(navController:NavController) {
        fragmentBinding.signUpBtn.setOnClickListener {
            isAllCheck = allCheck()
            if (isAllCheck) {
                loadingDialog.show()
                var email = fragmentBinding.signUpEmailText.text.toString().trim()
                var password = fragmentBinding.signUpPasswordText.text.toString().trim()
                var fullName = fragmentBinding.signUpNameText.text.toString().trim()
                var phone = fragmentBinding.signUpPhoneText.text.toString().trim()

                signUpViewModel.getSignUp(email, password)
                lifecycleScope.launchWhenCreated {
                    signUpViewModel.signUpState.collect { it ->
                        when (it) {
                            is State.Loading -> {
                                loadingDialog.show()
                            }
                            is State.Success -> {
                                Log.i("Auth", "onCreate: .....success")
                                loadingDialog.dismiss()
                                val accountCreated = LocalDateTime.now()
                                val formatter =
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                                val accountCreatedTime = accountCreated.format(formatter)
                                var userMap =
                                    UserInfoModel(fullName, email, phone, accountCreatedTime)
                                signUpViewModel.getUserInfo(userMap)
                                signUpViewModel.userInfoState.collect {
                                    when (it) {
                                        is State.Loading -> Log.i(
                                            "UserInfo",
                                            "onCreate: .....Loading"
                                        )
                                        is State.Success -> Log.i(
                                            "UserInfo",
                                            "onCreate: .....Success"
                                        )
                                        is State.Error -> Log.i("UserInfo", "onCreate: .....Error")
                                    }
                                }
                                var action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                                navController.navigate(action)
                            }
                            is State.Error -> {
                                loadingDialog.dismiss()
                                Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                                Log.i("Auth", "onCreate: .....error")
                            }
                        }
                    }
                }
            } else {

            }
        }
    }

    private fun allCheck(): Boolean {
        var email = fragmentBinding.signUpEmailText.text.toString()
        var name = fragmentBinding.signUpNameText.text.toString()
        var phone = fragmentBinding.signUpPhoneText.text.toString()
        var password = fragmentBinding.signUpPasswordText.text.toString()
        if (email.isEmpty()) {
            fragmentBinding.signUpEmailText.error = "Please enter your email email!";
            return false
        }

        if (!Validation.checkEmail(email)) {
            fragmentBinding.signUpEmailText.error = "Please enter the correct email!";
            return false
        }

        if (name.isEmpty()) {
            fragmentBinding.signUpNameText.error = "Please Enter your name!"
            return false
        }

        if (phone.length < 11) {
            fragmentBinding.signUpPhoneText.error = "Please enter your number correctly!";
            return false
        }
        if (phone.isEmpty()) {
            fragmentBinding.signUpPhoneText.error = "Please enter your number!";
            return false
        }

        if (password.isEmpty()) {
            fragmentBinding.signUpPasswordText.error = "Please enter your password!";
            return false
        }

        if (password.length < 6) {
            fragmentBinding.signUpPasswordText.error = "Please enter 6 digit password!";
            return false
        }
        return true
    }

}

