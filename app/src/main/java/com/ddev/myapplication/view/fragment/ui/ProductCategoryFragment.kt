package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.ProductAdapter
import com.ddev.myapplication.databinding.FragmentProductCategoryBinding
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.viewmodel.DataReceiveViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class ProductCategoryFragment :
    BaseFragment<FragmentProductCategoryBinding>(FragmentProductCategoryBinding::inflate),
    ClickListener<ProductModel> {

//    private val categoryProductViewModel: DataReceiveViewModel by navGraphViewModels(R.id.bottom_nav) {
//        SavedStateViewModelFactory(
//            requireActivity().application,
//            requireParentFragment()
//        )
//    }

    private val trendingAdapter by lazy {
        ProductAdapter(this)
    }
    private var categoryProductList = ArrayList<ProductModel>()
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }

        db = FirebaseFirestore.getInstance()

        buildUi(bundle)
    }

    private fun buildUi(bundle: Bundle) {
        val context = context ?: return
        val args = ProductCategoryFragmentArgs.fromBundle(bundle)

        var categoryName = args.categoryModel!!.categoryName


        db.collection("Products").whereEqualTo("productCategory", categoryName)
            .addSnapshotListener { value, error ->
                categoryProductList.clear()
                for (doc: DocumentChange in value!!.documentChanges) {
                    categoryProductList.add(doc.document.toObject(ProductModel::class.java))
                    trendingAdapter.addItems(categoryProductList)
                }
            }


        fragmentBinding.categoryProductRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        fragmentBinding.categoryProductRecyclerView.setHasFixedSize(true)
        fragmentBinding.categoryProductRecyclerView.adapter = trendingAdapter
        trendingAdapter.notifyDataSetChanged()
    }

    override fun onClick(item: ProductModel, position: Int) {

    }
}