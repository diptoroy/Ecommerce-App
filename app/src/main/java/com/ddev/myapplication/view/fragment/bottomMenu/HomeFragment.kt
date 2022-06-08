package com.ddev.myapplication.view.fragment.bottomMenu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.CategoryAdapter
import com.ddev.myapplication.adapter.ProductAdapter
import com.ddev.myapplication.databinding.FragmentHomeBinding
import com.ddev.myapplication.model.*
import com.ddev.myapplication.model.product.ColorModel
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.model.product.ProductSpecModel
import com.ddev.myapplication.model.product.ProductViewPagerModel
import com.ddev.myapplication.util.ClickListener
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.fragment.ui.HomePageFragmentDirections
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),ClickListener<ProductModel> {
    private val categoryAdapter by lazy {
        CategoryAdapter()
    }
    private val trendingAdapter by lazy {
        ProductAdapter(this)
    }
    var categoryList = ArrayList<CategoryModel>()

    private lateinit var db: FirebaseFirestore
    private lateinit var dbRef: DocumentReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        //category
        categoryList.clear()
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.smartphoneicon, null)!!,getString(R.string.category_1)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.laptopicon, null)!!,getString(R.string.category_2)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.smartwatchicon, null)!!,getString(R.string.category_3)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.headphonesicon, null)!!,getString(R.string.category_4)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.cameraicon, null)!!,getString(R.string.category_5)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.videogameicon, null)!!,getString(R.string.category_6)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.accessoriesicon, null)!!,getString(R.string.category_7)))

        uiBuild()
        categoryAdapter.addItems(categoryList)
       // showTrendingProducts()

    }


    private fun uiBuild() {
        fragmentBinding.categoryRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL, false
        )
        fragmentBinding.categoryRecyclerView.setHasFixedSize(true)
        fragmentBinding.categoryRecyclerView.adapter = categoryAdapter

        var productList = ArrayList<ProductModel>()
        productList.clear()
        fragmentBinding.trendingRecyclerView.layoutManager = GridLayoutManager(activity,2)
        fragmentBinding.trendingRecyclerView.setHasFixedSize(true)
        fragmentBinding.trendingRecyclerView.adapter = trendingAdapter
        trendingAdapter.notifyDataSetChanged()


        productList.clear()
        db.collection("Products").addSnapshotListener { value, error ->
            for (doc: DocumentChange in value!!.documentChanges){
                productList.add(doc.document.toObject(ProductModel::class.java))
                Log.i("productList", "showTrendingProducts: ${doc.document}")
                trendingAdapter.addItems(productList)
                trendingAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showTrendingProducts() {
        var document = db.collection("Products").document()
        var productId = document.id
        var productImage = ArrayList<ProductViewPagerModel>()
        productImage.add(ProductViewPagerModel("https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MX472?wid=1144&hei=1144&fmt=jpeg&qlt=95&.v=1570119347612"))
        productImage.add(ProductViewPagerModel("https://i5.wal.co/asr/098a8946-5d0a-4c9a-b419-cc8f23a271e2_1.221e5f2761a4e6cb5ffeb80e13963c5b.png?odnBg=ffffff&odnHeight=612&odnWidth=612"))
        var productColor = ArrayList<ColorModel>()
        productColor.add(ColorModel("Red","#F90000"))
        productColor.add(ColorModel("Green","#CFA06A"))
        var productSpec = ArrayList<SpecModel>()
        productSpec.add(SpecModel("Display","None"))
        productSpec.add(SpecModel("Ram","None"))


        var data = ProductModel(productId,"Headphone",productImage,"Beats 2022","300","2.5",false,"Nothing in desc",productSpec,productColor)
        Log.i("data", "showTrendingProducts: $data")
        db.collection("Products").document(productId).set(data).addOnSuccessListener { result ->
            Log.i("Products", "onViewCreated: $result")
        }.addOnFailureListener { it ->
            Log.i("Products", "onViewCreated: $it")
        }
    }

    private fun randomProductID(): String = List(8) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")

    override fun onClick(item: ProductModel, position: Int) {
        var navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
        var action = HomePageFragmentDirections.actionHomePageFragmentToProductDetailsFragment2(item)
        navController.navigate(action)
    }
}