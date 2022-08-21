package com.ddev.myapplication.view.fragment.bottomMenu

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.CategoryAdapter
import com.ddev.myapplication.adapter.ProductAdapter
import com.ddev.myapplication.databinding.FragmentHomeBinding
import com.ddev.myapplication.listener.CategoryListener
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.model.*
import com.ddev.myapplication.model.product.ColorModel
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.model.product.ProductViewPagerModel
import com.ddev.myapplication.util.PrefHelper
import com.ddev.myapplication.util.dialog.DynamicViewDialog
import com.ddev.myapplication.util.dialog.LoadingDialog
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.fragment.ui.HomePageFragmentDirections
import com.ddev.myapplication.view.viewmodel.DataReceiveViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import java.util.*


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ClickListener<ProductModel>,CategoryListener<CategoryModel> {
    private val productViewModel: DataReceiveViewModel by navGraphViewModels(R.id.bottom_nav) {
        SavedStateViewModelFactory(
            requireActivity().application,
            requireParentFragment()
        )
    }
    private val categoryAdapter by lazy {
        CategoryAdapter(this)
    }
    private val trendingAdapter by lazy {
        ProductAdapter(this)
    }
    private lateinit var productList: ArrayList<ProductModel>;

    private var timer: CountDownTimer? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var dbRef: DocumentReference
    lateinit var loadingDialog: LoadingDialog
    private lateinit var dynamicViewDialog:DynamicViewDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PrefHelper.init(requireContext())

        db = FirebaseFirestore.getInstance()
        loadingDialog = LoadingDialog(requireContext())
        dynamicViewDialog = DynamicViewDialog(requireActivity(), fragmentBinding.mainRootView)

        categoryAdapter.addItems(category())
         //showTrendingProducts()
        var total = 0F

        db.collection("ProductRating").addSnapshotListener { value, error ->
            for (doc: DocumentChange in value!!.documentChanges){
                var data = doc.document.toObject(UserProductRatingModel::class.java)
                var rating = data.productRating
                Log.i("RatingData", "buildUi: $data")
            }

        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            uiBuild()
        }

        //dynamicView()

        fragmentBinding.searchProduct.setOnClickListener {
            var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            var action = HomePageFragmentDirections.actionHomePageFragmentToProductSearchFragment()
            navController.navigate(action)
        }

        fragmentBinding.refreshLayout.setOnRefreshListener {
            trendingAdapter.notifyDataSetChanged()
            fragmentBinding.refreshLayout.isRefreshing = false
        }

        lifecycle.addObserver(object: DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                PrefHelper.setValue("background", false)
            }
            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                PrefHelper.setValue("background", true)
                dynamicView()
            }
        })
    }

    private fun dynamicView() {
        //        var dynamicData = DynamicProductModel("Product","Cwzz3zqB1mQD6EBgdz7B","Iphone 12 pro max","https://m.media-amazon.com/images/I/71MHTD3uL4L._FMwebp__.jpg","1200","https://m.media-amazon.com/images/I/71MHTD3uL4L._FMwebp__.jpg")
//        db.collection("DynamicView").add(dynamicData)

        db.collection("DynamicView").document("vffybaITuQCD9NzRjeRP").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task != null) {
                    var data = task.result.toObject(DynamicProductModel::class.java)
                    var name = data!!.dynamicProductName
                    var price = data!!.dynamicProductPrice
                    var image = data!!.dynamicProductImage
                    var id = data!!.dynamicProductId
                    if (data?.dynamicViewName.equals("Product")) {
                        dynamicViewDialog.showDynamicProduct(name, price!!, image!!)
                    } else if (data?.dynamicViewName.equals("FullImage")) {
                        db.collection("Products").document(id!!).get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    var data = task.result.toObject(ProductModel::class.java)
                                    dynamicViewDialog.showDynamicImage(image!!)
                                    dynamicViewDialog.setOnRootClickListener{
                                        dynamicViewDialog.dismiss()
                                        var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                                        var action = HomePageFragmentDirections.actionHomePageFragmentToProductDetailsFragment2(data!!)
                                        navController.navigate(action)
                                    }
                                }
                            }

                    } else if (data?.dynamicViewName.equals("view")) {
                        fragmentBinding.demoDynamic.visibility = View.VISIBLE
                        Picasso.get().load(image).into(fragmentBinding!!.demoDynamic)
                        fragmentBinding.demoDynamic.setOnClickListener {
                            db.collection("Products").document(id!!).get()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        var data = task.result.toObject(ProductModel::class.java)
                                        var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                                        var action = HomePageFragmentDirections.actionHomePageFragmentToProductDetailsFragment2(data!!)
                                        navController.navigate(action)
                                    }
                                }
                        }
                    }else{

                    }
                }
            }
    }

    private suspend fun uiBuild() {
        fragmentBinding.categoryRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL, false
        )
        fragmentBinding.categoryRecyclerView.setHasFixedSize(true)
        fragmentBinding.categoryRecyclerView.adapter = categoryAdapter

        productList = ArrayList<ProductModel>()
        productList.clear()
        fragmentBinding.trendingRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        fragmentBinding.trendingRecyclerView.setHasFixedSize(true)
        fragmentBinding.trendingRecyclerView.adapter = trendingAdapter
        trendingAdapter.notifyDataSetChanged()


        productList.clear()

        loadingDialog.show()
        productViewModel.getAllProduct()
        productViewModel.productViewState.collect {productsList ->
            if (productsList != null) {
                trendingAdapter.addItems(productsList)
                loadingDialog.dismiss()
                trendingAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun category():List<CategoryModel>{
        var categoryList = ArrayList<CategoryModel>()
        categoryList.clear()
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.smartphoneicon, null)!!, getString(R.string.category_1)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.laptopicon, null)!!, getString(R.string.category_2)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.smartwatchicon, null)!!, getString(R.string.category_3)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.headphonesicon, null)!!, getString(R.string.category_4)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources,R.drawable.cameraicon,null)!!, getString(R.string.category_5)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.videogameicon, null)!!, getString(R.string.category_6)))
        categoryList.add(CategoryModel(ResourcesCompat.getDrawable(resources, R.drawable.accessoriesicon, null)!!, getString(R.string.category_7)))
        return categoryList
    }

    private fun showTrendingProducts() {
        var document = db.collection("Products").document()
        var productId = document.id
        var productImage = ArrayList<ProductViewPagerModel>()
        productImage.add(ProductViewPagerModel("https://static.gadgetandgear.com/tmp/product/20220423_1650687083_966562.png"))
        productImage.add(ProductViewPagerModel("https://i5.wal.co/asr/098a8946-5d0a-4c9a-b419-cc8f23a271e2_1.221e5f2761a4e6cb5ffeb80e13963c5b.png?odnBg=ffffff&odnHeight=612&odnWidth=612"))
        var productColor = ArrayList<ColorModel>()
        productColor.add(ColorModel("Black", "#F90000"))
        productColor.add(ColorModel("Green", "#CFA06A"))
        var productSpec = ArrayList<SpecModel>()
        productSpec.add(SpecModel("Display", "None"))
        productSpec.add(SpecModel("Ram", "None"))


        var data = ProductModel(productId, "Smartphone", productImage, "Samsung s22+", "1199", "5.0", false, "Nothing in desc", productSpec, productColor)
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
        var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        var action = HomePageFragmentDirections.actionHomePageFragmentToProductDetailsFragment2(item)
        navController.navigate(action)
    }

    override fun onCategoryClick(item: CategoryModel, position: Int) {
        var navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        var action = HomePageFragmentDirections.actionHomePageFragmentToProductCategoryFragment(item)
        navController.navigate(action)
    }

}