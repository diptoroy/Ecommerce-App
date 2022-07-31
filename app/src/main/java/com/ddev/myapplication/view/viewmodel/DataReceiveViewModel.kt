package com.ddev.myapplication.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.FavoriteModel
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.util.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataReceiveViewModel : ViewModel() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = FirebaseAuth.getInstance().currentUser!!
    var currentUserId = currentUser.uid

    private val _productViewState: MutableStateFlow<List<ProductModel>?> = MutableStateFlow(null)
    val productViewState: StateFlow<List<ProductModel>?> = _productViewState
    private var productList = ArrayList<ProductModel>()

    private val _cartViewState: MutableStateFlow<List<AddToCartModel>?> = MutableStateFlow(null)
    val cartViewState: StateFlow<List<AddToCartModel>?> = _cartViewState
    private var cartList = ArrayList<AddToCartModel>()

    private val _favoriteViewState: MutableStateFlow<List<FavoriteModel>?> = MutableStateFlow(null)
    val favoriteViewState: StateFlow<List<FavoriteModel>?> = _favoriteViewState
    private var favoriteList = ArrayList<FavoriteModel>()

    suspend fun getAllProduct() {
        db.collection("Products").addSnapshotListener { value, error ->
            productList.clear()
            for (doc: DocumentChange in value!!.documentChanges) {
                productList.add(doc.document.toObject(ProductModel::class.java))
            }
            viewModelScope.launch {
                _productViewState.emit(productList)
            }

        }
    }

    suspend fun getCartProducts(){
        db.collection("Users").document(currentUserId).collection("AddToCart")
            .addSnapshotListener { value, error ->
                cartList.clear()
                for (doc: DocumentChange in value!!.documentChanges) {
                    cartList.add(doc.document.toObject(AddToCartModel::class.java))
                }
                viewModelScope.launch {
                    _cartViewState.emit(cartList)
                }
            }
    }

    suspend fun getFavoriteProducts(){
        db.collection("Users").document(currentUserId).collection("Favorite")
            .addSnapshotListener { value, error ->
                favoriteList.clear()
                for (doc: DocumentChange in value!!.documentChanges) {
                    favoriteList.add(doc.document.toObject(FavoriteModel::class.java))
                }
                viewModelScope.launch {
                    _favoriteViewState.emit(favoriteList)
                }
            }
    }


}