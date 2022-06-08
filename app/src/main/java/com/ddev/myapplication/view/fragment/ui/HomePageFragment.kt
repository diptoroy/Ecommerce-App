package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentHomePageBinding
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.delay


class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as? NavHostFragment

        val navController = nestedNavHostFragment?.navController

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        if (navController != null) {
            bottomNavigationView.setupWithNavController(navController)
        } else {
            throw RuntimeException("Controller not found")
        }

        var badgeNo = "3"

        lifecycleScope.launchWhenCreated {
            delay(3000)
            badgeNo = "3"
        }

        fragmentBinding.toolbar.notificationContainer.text = badgeNo
        if (fragmentBinding.toolbar.notificationContainer.text == "0"){
            fragmentBinding.toolbar.notificationContainer.visibility = View.GONE
        }else{
            fragmentBinding.toolbar.notificationContainer.visibility = View.VISIBLE
            fragmentBinding.toolbar.notificationContainer.text = badgeNo
        }
    }

}