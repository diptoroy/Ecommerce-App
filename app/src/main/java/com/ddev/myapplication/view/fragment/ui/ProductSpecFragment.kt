package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.ProductAdapter
import com.ddev.myapplication.adapter.ProductSpecAdapter
import com.ddev.myapplication.databinding.FragmentProductSpecBinding
import com.ddev.myapplication.model.SpecModel
import com.ddev.myapplication.view.fragment.BaseFragment


class ProductSpecFragment : BaseFragment<FragmentProductSpecBinding>(FragmentProductSpecBinding::inflate) {

    private val specAdapter by lazy {
        ProductSpecAdapter()
    }
    private var list = ArrayList<SpecModel>()
    var myList = ArrayList<SpecModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            myList = requireArguments().getParcelableArrayList<SpecModel>("Spec") as ArrayList<SpecModel>
        }

        list.addAll(myList)
        uiBuild()
        specAdapter.addItems(list)
    }

    private fun uiBuild() {
        fragmentBinding.specRecyclerview.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.specRecyclerview.setHasFixedSize(true)
        fragmentBinding.specRecyclerview.adapter = specAdapter
    }
}