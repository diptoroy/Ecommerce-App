package com.ddev.myapplication.view.fragment.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ddev.myapplication.adapter.ColorAdapter
import com.ddev.myapplication.adapter.ProductViewPagerAdapter
import com.ddev.myapplication.adapter.ViewPagerAdapter
import com.ddev.myapplication.databinding.FragmentProductDetailsBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.SpecModel
import com.ddev.myapplication.model.product.ColorModel
import com.ddev.myapplication.model.product.ProductViewPagerModel
import com.ddev.myapplication.util.ClickListener
import com.ddev.myapplication.util.ColorClickListener
import com.ddev.myapplication.util.ViewPager2PageTransformation
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate),
    ClickListener<ColorModel> {

    private val adapter by lazy {
        ProductViewPagerAdapter()
    }

    private val colorAdapter by lazy {
        ColorAdapter(this)
    }
//    private lateinit var colorClick:ColorClickListener
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        colorClick = parentFragment as ProductDetailsFragment
//    }

    var list = ArrayList<ColorModel>()
    val dArray = arrayOf(
        "Description",
        "Specifications"
    )

    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String

    private lateinit var productId:String
    private lateinit var imageList: ArrayList<ProductViewPagerModel>
    private lateinit var productName: String
    private lateinit var colorList: ArrayList<ColorModel>
    private lateinit var productDesc: String
    private lateinit var productSpec: ArrayList<SpecModel>
    private lateinit var productPrice: String
    private lateinit var productRating: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }

        buildUi(bundle)
    }

    private fun buildUi(bundle: Bundle) {
        val args = ProductDetailsFragmentArgs.fromBundle(bundle)
        productId = args.productDetails.productId!!
        imageList = (args.productDetails.productImage as ArrayList<ProductViewPagerModel>?)!!
        productName = args.productDetails.productName!!
        colorList = args.productDetails.productColor as ArrayList<ColorModel>
        var israte = args.productDetails.isRating
        productDesc = args.productDetails.productDesc!!
        productSpec = args.productDetails.productSpecification as ArrayList<SpecModel>
        productPrice = args.productDetails.productPrice!!
        productRating = args.productDetails.productRating!!

        fragmentBinding.productNameText.text = productName
        fragmentBinding.productNameText2.text = productName
        fragmentBinding.productPrice.text = "$$productPrice"
        fragmentBinding.productRating.text = productRating


        //image viewpager
        if (imageList != null) {
            adapter.addItems(imageList)
        }
        fragmentBinding.productImageViewPager.adapter = adapter
        with(fragmentBinding.productImageViewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        fragmentBinding.productImageViewPager.setPageTransformer(ViewPager2PageTransformation())
        fragmentBinding.productImageViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        (fragmentBinding.productImageViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        fragmentBinding.indicator.setViewPager2(fragmentBinding.productImageViewPager)

        if (colorList != null) {
            colorAdapter.addItems(colorList)
        }
        fragmentBinding.colorRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL, false
        )
        fragmentBinding.colorRecyclerView.setHasFixedSize(true)
        fragmentBinding.colorRecyclerView.adapter = colorAdapter

        //product detials
        val pagerAdapter =
            productSpec?.let { ViewPagerAdapter(childFragmentManager, lifecycle,productDesc, it) }
        fragmentBinding.specViewPager.adapter = pagerAdapter
        TabLayoutMediator(fragmentBinding.tabLayout, fragmentBinding.specViewPager) { tab, position ->
            tab.text = dArray[position]
        }.attach()



    }

    override fun onClick(item: ColorModel, position: Int) {
        fragmentBinding.addToCartBtn.setOnClickListener {
            var addToCartModelData = AddToCartModel(productId!!,imageList[0].image,productName!!, item.color.toString(),item.colorName.toString(),1,productPrice!!.toInt(),productPrice!!.toInt())
            var addToCartDb = db.collection("Users").document(currentUserId).collection("AddToCart").document(productId)
            addToCartDb.set(addToCartModelData).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Log.i("addToCart", "buildUi: item is add to cart")
                }
            }.addOnFailureListener {
                Log.i("addToCart", "buildUi: Failed to add to cart")
            }
        }
    }
}