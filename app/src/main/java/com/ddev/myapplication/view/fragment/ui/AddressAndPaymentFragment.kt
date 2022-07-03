package com.ddev.myapplication.view.fragment.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.CartAdapter
import com.ddev.myapplication.adapter.PaymentAdapter
import com.ddev.myapplication.databinding.FragmentAddressAndPaymentBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.AddressModel
import com.ddev.myapplication.model.OrderModel
import com.ddev.myapplication.model.PaymentMethodModel
import com.ddev.myapplication.util.ClickListener
import com.ddev.myapplication.util.Notification
import com.ddev.myapplication.util.PriceClickListener
import com.ddev.myapplication.view.activiry.MainActivity
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.InternalTokenProvider
import java.text.SimpleDateFormat
import java.util.*


class AddressAndPaymentFragment : BaseFragment<FragmentAddressAndPaymentBinding>(FragmentAddressAndPaymentBinding::inflate),PriceClickListener,ClickListener<AddToCartModel> {

    private val adapter by lazy {
        PaymentAdapter()
    }

    private val cartAdapter by lazy {
        CartAdapter(this,this)
    }
    var list = ArrayList<PaymentMethodModel>()
    private lateinit var selectedRadioButton: RadioButton

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()
        Notification.createNotificationChannel(requireContext())

        list.add(PaymentMethodModel(ResourcesCompat.getDrawable(resources, R.drawable.cod, null)!!,"Cash On Delivery"))
        list.add(PaymentMethodModel(ResourcesCompat.getDrawable(resources, R.drawable.wallet, null)!!,"Online Payment"))

        //setUpRecyclerView()
        adapter.addItems(list)

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }
        val args = AddressAndPaymentFragmentArgs.fromBundle(bundle)
        var list = args.addToCart
        var amount = args.totalAmount
        var addressData = args.addressDetails
        Log.i("checkout", "onViewCreated: ${list[0]},$amount")
        fragmentBinding.totalPriceText.text = "$"+amount.toString()
        fragmentBinding.addressDetailsText.text = addressData.addressDetail
        fragmentBinding.cityNameText.text = addressData.cityName
        fragmentBinding.phoneText.text = "Phone: " + addressData.phone

        setUpRecyclerView()
        cartAdapter.addItems(list.toList())

        var address = AddressModel("Dipto","01755823377","House no 51,Road no 19","Dhaka","House no 51,Road no 19,Nikunja 2,Dhaka")
        fragmentBinding.addressLayout.setOnClickListener {
            var action = AddressAndPaymentFragmentDirections.actionAddressAndPaymentFragmentToAddressSelectFragment(list,amount,address)
            navController.navigate(action)
        }


        fragmentBinding.checkoutBtn.setOnClickListener {
            if (fragmentBinding.addressDetailsText.text.toString().isNotBlank() &&
                fragmentBinding.cityNameText.text.toString().isNotEmpty() &&
                fragmentBinding.phoneText.text.toString().isNotEmpty() ) {
                val randomString = UUID.randomUUID().toString().substring(0,5)
                var orderId: String = randomString + orderTime()
                var paymentId = fragmentBinding.paymentGroup.checkedRadioButtonId
                selectedRadioButton = view.findViewById(paymentId)
                val paymentMethod: String = selectedRadioButton.text.toString()

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                var oTime = sdf.format(Date())
                var order = OrderModel(
                    currentUserId,
                    orderId,
                    list.toList(),
                    addressData,
                    paymentMethod,
                    oTime,
                    amount,
                    "Processing"
                )
                Log.i("orderId", "onViewCreated: $order")
                var orderDb = db.collection("Users").document(currentUserId).collection("Order").document(orderId)
                orderDb.set(order).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        db.collection("Users").document(currentUserId).collection("AddToCart").addSnapshotListener { value, error ->
                            for (doc: DocumentChange in value!!.documentChanges) {
                                var cartId = doc.document.id
                                Notification.buildNotification(requireContext(),"Your order has been placed","Your order id $orderId.You have to pay $$amount.Thank you.")
                                db.collection("Users").document(currentUserId).collection("AddToCart").document(cartId).delete()
                            }
                        }
                        Log.i("order", "buildUi: item is added in order")
                        var action = AddressAndPaymentFragmentDirections.actionAddressAndPaymentFragmentToCheckOutFragment()
                        navController.navigate(action)
                    }
                }.addOnFailureListener {
                    Log.i("order", "buildUi: Failed to order")
                }

            }
        }

    }

    private fun orderTime():String{
        val sdf = SimpleDateFormat("SSS")
        return sdf.format(Date())
    }

    private fun setUpRecyclerView() {
        fragmentBinding.chcekoutCartRecyclerview.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.chcekoutCartRecyclerview.setHasFixedSize(true)
        fragmentBinding.chcekoutCartRecyclerview.adapter = cartAdapter
    }

    override fun priceClick(plusMinus: Int, position: Int) {

    }

    override fun onClick(item: AddToCartModel, position: Int) {

    }
}