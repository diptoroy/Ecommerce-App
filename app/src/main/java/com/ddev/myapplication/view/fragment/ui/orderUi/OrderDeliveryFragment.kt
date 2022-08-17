package com.ddev.myapplication.view.fragment.ui.orderUi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.OrderGroupAdapter
import com.ddev.myapplication.databinding.FragmentOrderDeliveryBinding
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.model.OrderModel
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class OrderDeliveryFragment : BaseFragment<FragmentOrderDeliveryBinding>(FragmentOrderDeliveryBinding::inflate), ClickListener<OrderModel> {

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String
    private var orderList = ArrayList<OrderModel>()

    private val adapter by lazy {
        OrderGroupAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        orderList.clear()
        db.collection("Users").document(currentUserId).collection("Order").whereEqualTo("orderStatus","Received").addSnapshotListener { value, error ->
            for (doc: DocumentChange in value!!.documentChanges) {
                orderList.add(doc.document.toObject(OrderModel::class.java))
                adapter.addItems(orderList)
                Log.i("allOrderByDipto", "onViewCreated: ${doc.document}")
            }
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        fragmentBinding.deliveryRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.deliveryRecyclerView.setHasFixedSize(true)
        fragmentBinding.deliveryRecyclerView.adapter = adapter
    }

    override fun onClick(item: OrderModel, position: Int) {
        var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        var action = OrderUiFragmentDirections.actionOrderUiFragmentToOrderDetailsFragment(2,item)
        navController.navigate(action)
    }
}