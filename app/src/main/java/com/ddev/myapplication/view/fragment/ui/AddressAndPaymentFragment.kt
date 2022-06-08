package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
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
import com.ddev.myapplication.util.PriceClickListener
import com.ddev.myapplication.view.fragment.BaseFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = findNavController()

        list.add(PaymentMethodModel(ResourcesCompat.getDrawable(resources, R.drawable.cod, null)!!,"Cash On Delivery"))
        list.add(PaymentMethodModel(ResourcesCompat.getDrawable(resources, R.drawable.wallet, null)!!,"Online Payment"))

        //setUpRecyclerView()
        adapter.addItems(list)

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
            var orderId:String = randomID() + orderTime()
            var paymentId = fragmentBinding.paymentGroup.checkedRadioButtonId
            selectedRadioButton = view.findViewById(paymentId)
            val paymentMethod: String = selectedRadioButton.text.toString()

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            var oTime =  sdf.format(Date())
            var order = OrderModel("",orderId,list.toList(),addressData,paymentMethod,oTime,amount)
            Log.i("orderId", "onViewCreated: $order")
            var action = AddressAndPaymentFragmentDirections.actionAddressAndPaymentFragmentToCheckOutSuccessFragment()
            navController.navigate(action)
        }

    }

    private fun randomID(): String = List(12) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")

    private fun orderTime():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
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

//    private fun setUpRecyclerView() {
//        fragmentBinding.paymentMethodRecyclerView.layoutManager = LinearLayoutManager(
//            activity,
//            LinearLayoutManager.VERTICAL, false
//        )
//        fragmentBinding.paymentMethodRecyclerView.setHasFixedSize(true)
//        fragmentBinding.paymentMethodRecyclerView.adapter = adapter
//    }
}