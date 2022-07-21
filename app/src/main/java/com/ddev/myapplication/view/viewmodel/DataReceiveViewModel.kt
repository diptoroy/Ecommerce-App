package com.ddev.myapplication.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.util.State
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataReceiveViewModel: ViewModel() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _productViewState: MutableStateFlow<List<ProductModel>?> = MutableStateFlow(null)
    val productViewState: StateFlow<List<ProductModel>?> = _productViewState
    private var productList = ArrayList<ProductModel>()

    fun getAllProduct() {
        db.collection("Products").addSnapshotListener { value, error ->
            productList.clear()
            for (doc: DocumentChange in value!!.documentChanges){
                productList.add(doc.document.toObject(ProductModel::class.java))

            }
            _productViewState.value = productList
        }
    }

}