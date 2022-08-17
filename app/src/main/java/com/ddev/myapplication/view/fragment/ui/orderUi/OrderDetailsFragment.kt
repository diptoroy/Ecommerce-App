package com.ddev.myapplication.view.fragment.ui.orderUi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.adapter.OrderDetailsAdapter
import com.ddev.myapplication.adapter.ShipmentProcessAdapter
import com.ddev.myapplication.databinding.FragmentOrderDetailsBinding
import com.ddev.myapplication.model.OrderModel
import com.ddev.myapplication.model.ProductRatingModel
import com.ddev.myapplication.util.PdfConverter
import com.ddev.myapplication.util.dialog.ReviewDialog
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.invoice_item_row.*
import kotlinx.android.synthetic.main.rating_dialog.*


class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>(FragmentOrderDetailsBinding::inflate) {
    private val adapter by lazy {
        OrderDetailsAdapter()
    }

    private val shipmentAdapter by lazy {
        ShipmentProcessAdapter()
    }

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String
    private lateinit var userRating:String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        buildUi(bundle)
    }

    private fun buildUi(bundle: Bundle) {
        val args = OrderDetailsFragmentArgs.fromBundle(bundle)

        var userId = args.orderDetails!!.userId
        var orderId = args.orderDetails!!.orderId
        var orderPlaceDate = args.orderDetails!!.orderDate
        var orderItem = args.orderDetails!!.orderItem
        var orderProcess = args.orderDetails!!.orderStatus
        var fullAddress = args.orderDetails!!.address
        var paymentType = args.orderDetails!!.paymentType
        var shipmentName = args.orderDetails!!.address!!.name
        var shipmentPhone = args.orderDetails!!.address!!.phone
        var shipmentAddress = args.orderDetails!!.address!!.addressDetail + ", " +args.orderDetails!!.address!!.cityName
        var orderTotalPrice = args.orderDetails!!.totalPrice
        var shipmentProcess = args.orderDetails!!.orderShipmentProcessModel
        var isVisible = args.isVisible

        fragmentBinding.orderIdTv.text = "Order #$orderId"
        fragmentBinding.orderDateTv.text = "Placed on $orderPlaceDate"
        fragmentBinding.shippingNameTv.text = shipmentName
        fragmentBinding.shippingPhoneTv.text = shipmentPhone
        fragmentBinding.shippingAddressTv.text = shipmentAddress
        fragmentBinding.totalBalanceAmountTv.text = "$$orderTotalPrice"
        fragmentBinding.processTv.text = orderProcess

        if (orderItem != null) {
            for (item in orderItem){
                if (isVisible == 1){
                    item.isReview = true
                }else if (isVisible == 2){
                    item.isReview = false
                }
            }
        }
        adapter.addItems(orderItem!!)
        shipmentAdapter.addItems(shipmentProcess!!)

        fragmentBinding.downloadInvoiceBtn.setOnClickListener {
            var invoiceItem = OrderModel(userId,orderId,orderItem,fullAddress,paymentType,orderPlaceDate,orderTotalPrice,orderProcess,shipmentProcess)
            val pdfConverter = PdfConverter()
            pdfConverter.createPdf(requireContext(),invoiceItem , requireActivity(),orderId!!)
        }
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