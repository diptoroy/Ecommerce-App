package com.ddev.myapplication.view.fragment.bottomMenu

import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import com.ddev.myapplication.databinding.FragmentAccountBinding
import com.ddev.myapplication.view.fragment.BaseFragment


class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val display: Display = requireActivity().windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//        val center_x: Int = size.x
//        val center_y: Int = size.y
//
//        fragmentBinding.textView17.text = "$center_x $center_y"


    }
}