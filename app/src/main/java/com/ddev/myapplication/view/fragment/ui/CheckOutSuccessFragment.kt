package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentCheckOutSuccessBinding
import com.ddev.myapplication.view.fragment.BaseFragment


class CheckOutSuccessFragment : BaseFragment<FragmentCheckOutSuccessBinding>(FragmentCheckOutSuccessBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()

        fragmentBinding.homeBtn.setOnClickListener {
            var action = CheckOutSuccessFragmentDirections.actionCheckOutSuccessFragmentToHomePageFragment()
            navController.navigate(action)
        }
    }
}