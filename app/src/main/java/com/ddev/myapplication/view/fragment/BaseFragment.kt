package com.ddev.myapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>
    (private val bindingInflater:(inflater: LayoutInflater) -> T): Fragment() {

    private lateinit var _fragmentBinding:T
    val fragmentBinding:T
        get() = _fragmentBinding as T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBinding = bindingInflater.invoke(inflater)
        if(_fragmentBinding == null)
            throw IllegalArgumentException("Binding cannot be null")
        return fragmentBinding.root
    }



}