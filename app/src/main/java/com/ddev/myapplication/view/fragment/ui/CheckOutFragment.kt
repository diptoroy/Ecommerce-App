package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentCheckOutBinding
import com.ddev.myapplication.view.fragment.BaseFragment


class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(FragmentCheckOutBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBinding.homeBtn.setOnClickListener {
            var action = CheckOutFragmentDirections.actionCheckOutFragmentToHomePageFragment()
            findNavController().navigate(action)
        }
    }

}