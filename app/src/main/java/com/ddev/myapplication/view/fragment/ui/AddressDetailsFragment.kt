package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentAddressDetailsBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.AddressModel
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class AddressDetailsFragment : BaseFragment<FragmentAddressDetailsBinding>(FragmentAddressDetailsBinding::inflate) {

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }
        val args = AddressDetailsFragmentArgs.fromBundle(bundle)
        var cart = args.addToCart
        var amount = args.totalAmount

        fragmentBinding.addBtn.setOnClickListener {
            var name = fragmentBinding.nameText.text.toString().trim()
            var phone = fragmentBinding.phoneText.text.toString().trim()
            var streetNo = fragmentBinding.houseText.text.toString().trim()
            var cityName = fragmentBinding.cityText.text.toString().trim()
            var addressDetails = fragmentBinding.addressText.text.toString().trim()
            var address = AddressModel(name,phone,streetNo,cityName,addressDetails)
            db.collection("Users").document(currentUserId).collection("Address").add(address).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.i("address", "buildUi: address added")
                }
            }.addOnFailureListener { it->
                Log.i("address", "buildUi: address not added")
            }
            //var cart = AddToCartModel("","","",0,0,0)
            var action = AddressDetailsFragmentDirections.actionAddressDetailsFragmentToAddressSelectFragment(
                cart,amount,address)
            navController.navigate(action)
        }

    }

}