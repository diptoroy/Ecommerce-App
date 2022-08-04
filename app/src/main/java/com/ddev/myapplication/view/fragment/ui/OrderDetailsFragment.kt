package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.adapter.OrderDetailsAdapter
import com.ddev.myapplication.adapter.ShipmentProcessAdapter
import com.ddev.myapplication.databinding.FragmentOrderDetailsBinding
import com.ddev.myapplication.view.fragment.BaseFragment


class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>(FragmentOrderDetailsBinding::inflate) {
    private val adapter by lazy {
        OrderDetailsAdapter()
    }

    private val shipmentAdapter by lazy {
        ShipmentProcessAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }

        buildUi(bundle)
    }

    private fun buildUi(bundle: Bundle) {
        val args = OrderDetailsFragmentArgs.fromBundle(bundle)

        var orderId = args.orderDetails!!.orderId
        var orderPlaceDate = args.orderDetails!!.orderDate
        var orderItem = args.orderDetails!!.orderItem
        var orderProcess = args.orderDetails!!.orderStatus
        var shipmentName = args.orderDetails!!.address!!.name
        var shipmentPhone = args.orderDetails!!.address!!.phone
        var shipmentAddress = args.orderDetails!!.address!!.addressDetail + ", " +args.orderDetails!!.address!!.cityName
        var orderTotalPrice = args.orderDetails!!.totalPrice
        var shipmentProcess = args.orderDetails!!.orderShipmentProcessModel

        fragmentBinding.orderIdTv.text = "Order #$orderId"
        fragmentBinding.orderDateTv.text = "Placed on $orderPlaceDate"
        fragmentBinding.shippingNameTv.text = shipmentName
        fragmentBinding.shippingPhoneTv.text = shipmentPhone
        fragmentBinding.shippingAddressTv.text = shipmentAddress
        fragmentBinding.totalBalanceAmountTv.text = "$$orderTotalPrice"
        fragmentBinding.processTv.text = orderProcess

        adapter.addItems(orderItem!!)
        shipmentAdapter.addItems(shipmentProcess!!)
//
//        fragmentBinding.button.setOnClickListener {
//            var cartItem = AddToCartModel("eee","","3333","","ee",2,222,222,"")
//            val pdfConverter = PdfConverter()
//            pdfConverter.createPdf(requireContext(), cartItem, requireActivity())
//        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        fragmentBinding.orderListRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.orderListRecyclerView.setHasFixedSize(true)
        fragmentBinding.orderListRecyclerView.adapter = adapter

        fragmentBinding.shipmentProcessRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.shipmentProcessRecyclerView.setHasFixedSize(true)
        fragmentBinding.shipmentProcessRecyclerView.adapter = shipmentAdapter
    }
}