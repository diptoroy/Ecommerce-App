package com.ddev.myapplication.adapter

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.Application.EcommerceApp
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.OrderGroupRowBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.OrderModel
import com.ddev.myapplication.listener.ClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class OrderGroupAdapter(var clickListener: ClickListener<OrderModel>) :
    BaseAdapter<OrderModel, OrderGroupRowBinding>() {

    private var orderGroup = ArrayList<OrderModel>()
    private var orderItem = ArrayList<AddToCartModel>()
    private val adapter by lazy {
        OrderItemAdapter()
    }

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String

    override fun getLayout() = R.layout.order_group_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<OrderGroupRowBinding>,
        position: Int
    ) {
        holder.binding.orderGroupModel = itemList[position]
        holder.itemView.setOnClickListener {
            clickListener.onClick(itemList[position], position)
        }

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid


//        db.collection("Users").document(currentUserId).collection("Order")
//            .addSnapshotListener { value, error ->
//                for (doc: DocumentChange in value!!.documentChanges) {
//                    orderGroup.add(doc.document.toObject(OrderModel::class.java))
//                    Log.i("modelData", "onBindViewHolder: $orderGroup")
//                    orderGroup.forEach { modelData ->
//                        orderItem = modelData.orderItem as ArrayList<AddToCartModel>
//                        adapter.addItems(orderItem)
//                        setUpRecyclerView(holder)
//                    }
//
//                }
//            }

        db.collection("Users").document(currentUserId).collection("Order").get().addOnCompleteListener { task ->
            if (task.isSuccessful){
                for (documentSnapshot in task.result) {
                    var id = documentSnapshot.id
                    db.collection("Users").document(currentUserId).collection("Order").document(id).get().addOnCompleteListener {data ->
                        if (data.isSuccessful){
                            val document = data.result
                            if (document != null){
                                val doc = document.toObject(OrderModel::class.java)
                                var i = doc!!.orderItem!!
                                Log.i("oI", "onBindViewHolder: $i")
                                adapter.addItems(i)
                                setUpRecyclerView(holder)
//                                for (ii in i){
//                                    adapter.addItems(ii)
//                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private fun setUpRecyclerView(holder: BaseAdapter.Companion.BaseViewHolder<OrderGroupRowBinding>) {
        holder.binding.orderItemRecyclerView.layoutManager = LinearLayoutManager(
            EcommerceApp.getApp()!!.applicationContext,
            LinearLayoutManager.HORIZONTAL, false
        )
        holder.binding.orderItemRecyclerView.setHasFixedSize(true)
        holder.binding.orderItemRecyclerView.adapter = adapter
    }


}