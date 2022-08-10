package com.ddev.myapplication.view.fragment.bottomMenu

import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentAccountBinding
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.fragment.ui.HomePageFragmentDirections


class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildUi()
    }

    private fun buildUi() {
        fragmentBinding.OrderBtn.setOnClickListener {
            var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            var action = HomePageFragmentDirections.actionHomePageFragmentToOrderUiFragment()
            navController.navigate(action)
        }
    }
}