package com.ddev.myapplication.adapter

import android.util.Log
import android.view.View
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.OrderItemRowBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.OrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class OrderDetailsAdapter  () : BaseAdapter<AddToCartModel, OrderItemRowBinding>() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var currentUserId: String
    var currentUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    var status = ""

    override fun getLayout() = R.layout.order_item_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<OrderItemRowBinding>,
        position: Int
    ) {
        holder.binding.orderItemModel = itemList[position]
        var productId = itemList[position].productId
    }

}