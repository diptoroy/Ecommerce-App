package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.view.View
import com.ddev.myapplication.databinding.FragmentProductDescBinding
import com.ddev.myapplication.view.fragment.BaseFragment


class ProductDescFragment : BaseFragment<FragmentProductDescBinding>(FragmentProductDescBinding::inflate) {


    private var text: String? = null

    /*companion object {
        private const val ARG_TEXT = "argText"
        fun newInstance(text: String?): ProductDescFragment? {
            val fragment = ProductDescFragment()
            val args = Bundle()
            args.putString(ARG_TEXT, text)
            fragment.arguments = args
            return fragment
        }
    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            text = requireArguments().getString("TEST");
        }
        fragmentBinding.productDescText.text = text

    }

}