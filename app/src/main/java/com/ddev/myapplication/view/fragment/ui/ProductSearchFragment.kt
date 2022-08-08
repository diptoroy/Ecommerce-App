package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.ProductAdapter
import com.ddev.myapplication.databinding.FragmentProductSearchBinding
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.util.CustomTextWatcher
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList


class ProductSearchFragment : BaseFragment<FragmentProductSearchBinding>(FragmentProductSearchBinding::inflate),ClickListener<ProductModel> {

    private val searchAdapter by lazy {
        ProductAdapter(this)
    }

    private lateinit var searchList: ArrayList<ProductModel>;

    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        searchList = ArrayList<ProductModel>()

        setRecyclerView()

        var searchTextWatcher = object: CustomTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        }
        fragmentBinding.searchProduct.addTextChangedListener(searchTextWatcher)

    }

    private fun setRecyclerView() {
        fragmentBinding.searchRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        fragmentBinding.searchRecyclerView.setHasFixedSize(true)
        fragmentBinding.searchRecyclerView.adapter = searchAdapter
        searchAdapter.notifyDataSetChanged()
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<ProductModel>()
        db.collection("Products").addSnapshotListener { value, error ->
            searchList.clear()
            for (doc: DocumentChange in value!!.documentChanges) {
                searchList.add(doc.document.toObject(ProductModel::class.java))
            }
            // || item.receivedFrom!!.lowercase().contains(text.lowercase(Locale.getDefault()))
            for (item in searchList) {
                if (item.productName!!.lowercase().contains(text.lowercase(Locale.getDefault())) || item.productCategory!!.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                    filteredList.add(item)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(requireActivity(), "No Data Found..", Toast.LENGTH_SHORT).show()
            } else {
                searchAdapter.filterList(filteredList)
                searchAdapter.notifyDataSetChanged()
                searchList.clear()
            }
        }

    }

    override fun onClick(item: ProductModel, position: Int) {
        var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        var action = ProductSearchFragmentDirections.actionProductSearchFragmentToProductDetailsFragment2(item)
        navController.navigate(action)
    }
}