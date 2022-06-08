package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.AddressAdapter
import com.ddev.myapplication.databinding.FragmentAddressSelectBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.AddressModel
import com.ddev.myapplication.util.ClickListener
import com.ddev.myapplication.view.fragment.BaseFragment


class AddressSelectFragment : BaseFragment<FragmentAddressSelectBinding>(FragmentAddressSelectBinding::inflate),ClickListener<AddressModel> {

    private val adapter by lazy {
        AddressAdapter(this)
    }

    var list = ArrayList<AddressModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()

        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }
        val args = AddressSelectFragmentArgs.fromBundle(bundle)
        var cart = args.addToCart
        var amount = args.totalAmount

        list.clear()
        list.add(AddressModel("Dipto","01755823377","House no 51,Road no 19","Dhaka","House no 51,Road no 19,Nikunja 2,Dhaka"))
        list.add(AddressModel("Dipto","01755823377","Gaibandha Road","Gaibandha","Hat Lakshmipur,Gaibandha,Rangpur"))


        fragmentBinding.addAddressBtn.setOnClickListener {

            var action = AddressSelectFragmentDirections.actionAddressSelectFragmentToAddressDetailsFragment(cart,amount)
            navController.navigate(action)
        }

        setUpRecyclerView()
        adapter.addItems(list)
    }

    private fun setUpRecyclerView() {
        fragmentBinding.addressRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.addressRecyclerView.setHasFixedSize(true)
        fragmentBinding.addressRecyclerView.adapter = adapter
    }

    override fun onClick(item: AddressModel, position: Int) {
        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }
        val args = AddressSelectFragmentArgs.fromBundle(bundle)
        var cart = args.addToCart
        var amount = args.totalAmount


        var navController = findNavController()
        var action = AddressSelectFragmentDirections.actionAddressSelectFragmentToAddressAndPaymentFragment(
            cart,amount,item
        )
        navController.navigate(action)
    }

}