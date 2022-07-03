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

    fun getAllProduct():MutableStateFlow<List<ProductModel>?> {
        db.collection("Products").addSnapshotListener { value, error ->
            var productList = ArrayList<ProductModel>()
            for (doc: DocumentChange in value!!.documentChanges){
                if (value != null) {
                    productList.add(doc.document.toObject(ProductModel::class.java))
                    _productViewState.value = productList
                }
            }
        }
        return _productViewState
    }

}