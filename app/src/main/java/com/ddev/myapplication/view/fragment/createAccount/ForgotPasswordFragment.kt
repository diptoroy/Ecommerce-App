package com.ddev.myapplication.view.fragment.createAccount

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentForgotPasswordBinding
import com.ddev.myapplication.util.LoadingDialog
import com.ddev.myapplication.util.State
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collect

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {
    lateinit var loadingDialog: LoadingDialog

    private val forgotPasswordViewModel: AuthViewModel by navGraphViewModels(R.id.nav_graph) {
        SavedStateViewModelFactory(
            requireActivity().application,
            requireParentFragment()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()
        loadingDialog = LoadingDialog(requireContext())

        forgotPassword(navController)
    }

    private fun forgotPassword(navController: NavController) {
        fragmentBinding.submitBtn.setOnClickListener {
            var email = fragmentBinding.forgotEmailText.text.toString().trim()
            forgotPasswordViewModel.getForgotPassword(email)
            lifecycleScope.launchWhenCreated {
                forgotPasswordViewModel.forgotPasswordState.collect {
                    when(it){
                        is State.Loading -> {
                            loadingDialog.show()
                        }
                        is State.Success -> {
                            loadingDialog.dismiss()
                            var action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToSignInFragment()
                            navController.navigate(action)
                            Log.i("forgot", "onCreate: .....Success")
                        }
                        is State.Error -> {
                            loadingDialog.dismiss()
                            Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }

    }
}