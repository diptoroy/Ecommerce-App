package com.ddev.myapplication.view.fragment.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.ProductAdapter
import com.ddev.myapplication.databinding.FragmentProductCategoryBinding
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.simple_toolbar.view.*

class ProductCategoryFragment : BaseFragment<FragmentProductCategoryBinding>(FragmentProductCategoryBinding::inflate), ClickListener<ProductModel> {

//    private val categoryProductViewModel: DataReceiveViewModel by navGraphViewModels(R.id.bottom_nav) {
//        SavedStateViewModelFactory(
//            requireActivity().application,
//            requireParentFragment()
//        )
//    }

    //sort data by date,high price,low price and rating

    private val trendingAdapter by lazy {
        ProductAdapter(this)
    }
    private var categoryProductList = ArrayList<ProductModel>()
    private lateinit var db: FirebaseFirestore
    private lateinit var animatorSet: AnimatorSet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }

        db = FirebaseFirestore.getInstance()
        animatorSet = AnimatorSet()

        fragmentBinding.soringBtn.setOnClickListener {
            ObjectAnimator.ofFloat( fragmentBinding.sortRootView,View.TRANSLATION_X,180f,0f ).apply {
                duration = 500
                start()
            }
            fragmentBinding.sortRootView.visibility = View.VISIBLE
        }

        buildUi(bundle)

        sortViewGone()
    }

    private fun buildUi(bundle: Bundle) {
        val context = context ?: return
        val args = ProductCategoryFragmentArgs.fromBundle(bundle)

        var categoryName = args.categoryModel!!.categoryName

        fragmentBinding.toolbar.toolbarTitle.text = categoryName

        db.collection("Products").whereEqualTo("productCategory", categoryName)
            .addSnapshotListener { value, error ->
                categoryProductList.clear()
                for (doc: DocumentChange in value!!.documentChanges) {
                    categoryProductList.add(doc.document.toObject(ProductModel::class.java))
                    trendingAdapter.addItems(categoryProductList)
                    setSorting(categoryProductList)
                }
            }

        fragmentBinding.categoryProductRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        fragmentBinding.categoryProductRecyclerView.setHasFixedSize(true)
        fragmentBinding.categoryProductRecyclerView.adapter = trendingAdapter
        trendingAdapter.notifyDataSetChanged()
    }

    private fun setSorting(categoryProductList: ArrayList<ProductModel>) {
        fragmentBinding.sortingDateBtn.setOnClickListener {
            categoryProductList.sortBy {
                it.productRating
            }
            trendingAdapter.notifyDataSetChanged()

            fragmentBinding.sortRootView.visibility = View.GONE
        }

        fragmentBinding.sortingHighPriceBtn.setOnClickListener {
            categoryProductList.sortByDescending {
                it.productPrice?.toInt()
            }
            trendingAdapter.notifyDataSetChanged()
            fragmentBinding.sortRootView.visibility = View.GONE
        }

        fragmentBinding.sortingLowPriceBtn.setOnClickListener {
            categoryProductList.sortBy {
                it.productPrice?.toInt()
            }
            trendingAdapter.notifyDataSetChanged()
            fragmentBinding.sortRootView.visibility = View.GONE
        }

        fragmentBinding.sortingTopRatedBtn.setOnClickListener {
            categoryProductList.sortByDescending {
                it.productRating?.toFloat()?.toInt()
            }
            trendingAdapter.notifyDataSetChanged()
            fragmentBinding.sortRootView.visibility = View.GONE
        }
    }

    override fun onClick(item: ProductModel, position: Int) {
        var navController =
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        var action = ProductCategoryFragmentDirections.actionProductCategoryFragmentToProductDetailsFragment2(item)
        navController.navigate(action)
    }

    private fun sortViewGone() {
        fragmentBinding.mainRootView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_UP -> fragmentBinding.sortRootView.visibility = View.GONE
                    MotionEvent.ACTION_DOWN -> fragmentBinding.sortRootView.visibility = View.GONE
                    MotionEvent.ACTION_MOVE -> fragmentBinding.sortRootView.visibility = View.GONE
                }
                return true
            }
        })

        fragmentBinding.toolbar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_UP -> fragmentBinding.sortRootView.visibility = View.GONE
                    MotionEvent.ACTION_DOWN -> fragmentBinding.sortRootView.visibility = View.GONE
                    MotionEvent.ACTION_MOVE -> fragmentBinding.sortRootView.visibility = View.GONE
                }
                return true
            }
        })

        fragmentBinding.categoryProductRecyclerView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_UP -> fragmentBinding.sortRootView.visibility = View.GONE
                    MotionEvent.ACTION_DOWN -> fragmentBinding.sortRootView.visibility = View.GONE
                    MotionEvent.ACTION_MOVE -> fragmentBinding.sortRootView.visibility = View.GONE
                }
                return true
            }
        })
    }

}