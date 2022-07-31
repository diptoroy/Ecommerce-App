package com.ddev.myapplication.view.fragment.bottomMenu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.CartAdapter
import com.ddev.myapplication.databinding.FragmentCartBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.AddressModel
import com.ddev.myapplication.util.ClickListener
import com.ddev.myapplication.util.LoadingDialog
import com.ddev.myapplication.util.PriceClickListener
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.fragment.ui.HomePageFragmentDirections
import com.ddev.myapplication.view.viewmodel.DataReceiveViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate),PriceClickListener,ClickListener<AddToCartModel> {
    private val adapter by lazy {
        CartAdapter(this,this)
    }
    var list = ArrayList<AddToCartModel>()
    private val cartViewModel: DataReceiveViewModel by navGraphViewModels(R.id.bottom_nav) {
        SavedStateViewModelFactory(
            requireActivity().application,
            requireParentFragment()
        )
    }

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String
    private lateinit var dbRef: DocumentReference

    lateinit var loadingDialog: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        loadingDialog = LoadingDialog(requireContext())

        setUpRecyclerView()
        //adapter.addItems(list)
        list.clear()
        db.collection("Users").document(currentUserId).collection("AddToCart")
            .addSnapshotListener { value, error ->
                for (doc: DocumentChange in value!!.documentChanges) {
                    list.add(doc.document.toObject(AddToCartModel::class.java))
                    adapter.addItems(list)
                    addressAndPayment(list)
                    totalPrice()
                }
            }

//        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//            cartViewModel.getCartProducts()
//            cartViewModel.cartViewState.collect {cartList ->
//                if (cartList != null) {
//                    adapter.addItems(cartList)
//                    adapter.notifyDataSetChanged()
//                    totalPrice()
//                }
//
//            }
//        }


    }

    private fun addressAndPayment(list: ArrayList<AddToCartModel>) {
        if (list.size <= 0){
            fragmentBinding.buyNowBtn.isEnabled = false
        }else{
            fragmentBinding.buyNowBtn.isEnabled = true
            fragmentBinding.buyNowBtn.setOnClickListener {
                var navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                var address = AddressModel("","","","","")
                var action = HomePageFragmentDirections.actionHomePageFragmentToAddressAndPaymentFragment(
                    this.list.toTypedArray(),totalPrice(),address)
                navController.navigate(action)
            }
        }

    }

    private fun totalPrice():Int {
        var count = list.size
        var totalPrice = 0
        for (i in 0 until count) {
            var p = list[i].productPrice
            if (p != null) {
                totalPrice += p
            }
        }
        Log.i("totalPrice", "onBindViewHolder: $totalPrice")
        fragmentBinding.totalPriceText.text = "$${totalPrice}"
        return totalPrice

    }

    private fun setUpRecyclerView() {
        fragmentBinding.cartRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.cartRecyclerView.setHasFixedSize(true)
        fragmentBinding.cartRecyclerView.adapter = adapter
    }

    override fun onClick(item: AddToCartModel, position: Int) {
        list.clear()
        loadingDialog.show()
        db.collection("Users").document(currentUserId).collection("AddToCart").document(item.productId!!)
            .delete()
            .addOnCompleteListener {
                loadingDialog.dismiss()
                adapter.notifyItemRemoved(position)
                totalPrice()
                adapter.notifyDataSetChanged()
                Log.d("delete success", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e ->
                loadingDialog.dismiss()
                Log.w("delete failed", "Error deleting document", e) }

    }

    override fun priceClick(plusMinus: Int,position:Int) {
        when(plusMinus){
            1->{
                list[position].productQuantity = list[position].productQuantity?.plus(1)
                list[position].productPrice = list[position].productSinglePrice?.times(list[position].productQuantity!!)
            }
            0->{
                if (list[position].productQuantity!! > 1) {
                    list[position].productQuantity = list[position].productQuantity?.minus(1)
                    list[position].productPrice = list[position].productQuantity?.let {
                        list[position].productSinglePrice?.times(
                            it
                        )
                    }
                }
            }
        }
        adapter.notifyDataSetChanged()
        totalPrice()
    }
}