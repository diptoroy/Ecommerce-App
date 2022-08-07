package com.ddev.myapplication.view.fragment.createAccount

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentSignInBinding
import com.ddev.myapplication.util.dialog.LoadingDialog
import com.ddev.myapplication.util.State
import com.ddev.myapplication.util.Validation
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.collect

class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    var isAllCheck: Boolean = false
    lateinit var loadingDialog: LoadingDialog

    private val signInViewModel: AuthViewModel by navGraphViewModels(R.id.nav_graph) {
        SavedStateViewModelFactory(
            requireActivity().application,
            requireParentFragment()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()
        auth = FirebaseAuth.getInstance()
        loadingDialog = LoadingDialog(requireContext())

        setClick(navController)
    }

    private fun setClick(navController: NavController) {
        fragmentBinding.createAccountBtn.setOnClickListener {
            var action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            navController.navigate(action)
        }

        fragmentBinding.forgotPasswordBtn.setOnClickListener {
            var action = SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment()
            navController.navigate(action)
        }

        fragmentBinding.signInBtn.setOnClickListener {
            isAllCheck = allCheck()
            if (isAllCheck) {
                loadingDialog.show()
                var email = fragmentBinding.loginEmailText.text.toString().trim()
                var password = fragmentBinding.loginPasswordText.text.toString().trim()
                signInViewModel.getSignIn(email, password)
                lifecycleScope.launchWhenCreated {
                    signInViewModel.signInState.collect { it ->
                        when (it) {
                            is State.Loading -> {
                                loadingDialog.show()
                                Log.i("Auth", "onCreate: .....Loading$it")
                            }
                            is State.Success -> {
                                Log.i("Auth", "onCreate: .....success")
                                loadingDialog.dismiss()
                                //Token
                                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                                    if (task.isSuccessful){
                                        var newToken = task.result
                                        Log.i("Gadget Gang Token", "setClick: $newToken")
                                        signInViewModel.getToken(newToken)
                                    }else{

                                    }
                                }
                                var action =
                                    SignInFragmentDirections.actionSignInFragmentToHomePageFragment()
                                navController.navigate(action)
                            }
                            is State.Error -> {
                                loadingDialog.dismiss()
                                fragmentBinding.loginEmailText.setText("")
                                fragmentBinding.loginPasswordText.setText("")
//                                val snack = Snackbar.make(requireView(),"Email or Password incorrect,Please try again!",Snackbar.LENGTH_LONG)
//                                snack.show()
                                Log.i("Auth", "onCreate: .....error")
                            }
                            else -> {}
                        }
                    }
                }
            }else{

            }
        }
    }

    private fun allCheck():Boolean{
        var email = fragmentBinding.loginEmailText.text.toString()
        var password = fragmentBinding.loginPasswordText.text.toString()
        if (email.isEmpty()){
            fragmentBinding.loginEmailText.error = "Please enter your email!";
            return false
        }

        if (!Validation.checkEmail(email)){
            fragmentBinding.loginEmailText.error = "Please enter the correct email!";
            return false
        }

        if (password.isEmpty()){
            fragmentBinding.loginPasswordText.error = "Please enter your password!";
            return false
        }

        if (password.length < 6){
            fragmentBinding.loginPasswordText.error = "Please enter 6 digit password!";
            return false
        }
        return true
    }


}