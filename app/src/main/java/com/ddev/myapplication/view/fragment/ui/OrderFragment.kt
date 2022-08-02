package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.adapter.OrderGroupAdapter
import com.ddev.myapplication.databinding.FragmentOrderBinding
import com.ddev.myapplication.model.OrderModel
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate),
    ClickListener<OrderModel> {

    private val adapter by lazy {
        OrderGroupAdapter(this)
    }

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String

    private var orderList = ArrayList<OrderModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        db.collection("Users").document(currentUserId).collection("Order").addSnapshotListener { value, error ->
            for (doc: DocumentChange in value!!.documentChanges) {
                orderList.add(doc.document.toObject(OrderModel::class.java))
                adapter.addItems(orderList)
                Log.i("allOrderByDipto", "onViewCreated: ${doc.document}")
            }
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        fragmentBinding.orderRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.orderRecyclerView.setHasFixedSize(true)
        fragmentBinding.orderRecyclerView.adapter = adapter
    }

    override fun onClick(item: OrderModel, position: Int) {

    }
}