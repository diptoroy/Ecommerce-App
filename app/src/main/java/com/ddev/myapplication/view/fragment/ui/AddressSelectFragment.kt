package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.adapter.AddressAdapter
import com.ddev.myapplication.databinding.FragmentAddressSelectBinding
import com.ddev.myapplication.model.AddressModel
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore


class AddressSelectFragment : BaseFragment<FragmentAddressSelectBinding>(FragmentAddressSelectBinding::inflate),
    ClickListener<AddressModel> {

    private val adapter by lazy {
        AddressAdapter(this)
    }

    var list = ArrayList<AddressModel>()
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
        val args = AddressSelectFragmentArgs.fromBundle(bundle)
        var cart = args.addToCart
        var amount = args.totalAmount

        list.clear()
        db.collection("Users").document(currentUserId).collection("Address").addSnapshotListener { value, error ->
            for (doc: DocumentChange in value!!.documentChanges){
                list.add(doc.document.toObject(AddressModel::class.java))
                Log.i("addressList", "showTrendingProducts: ${doc.document}")
                adapter.addItems(list)
                adapter.notifyDataSetChanged()
            }
        }

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