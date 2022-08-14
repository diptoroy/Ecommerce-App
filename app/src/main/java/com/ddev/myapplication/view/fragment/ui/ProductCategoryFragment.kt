package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.ProductAdapter
import com.ddev.myapplication.databinding.FragmentProductCategoryBinding
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.util.dialog.ProductSortDialog
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.simple_toolbar.view.*

class ProductCategoryFragment :
    BaseFragment<FragmentProductCategoryBinding>(FragmentProductCategoryBinding::inflate),
    ClickListener<ProductModel> {

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
    private lateinit var sortingDialog: ProductSortDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Confirmation")
            return
        }

        db = FirebaseFirestore.getInstance()

        sortingDialog = ProductSortDialog(requireActivity())


        buildUi(bundle)

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
        fragmentBinding.soringBtn.setOnClickListener {
            sortingDialog.setHighPriceBtn(View.OnClickListener {
                categoryProductList.sortByDescending {
                    it.productPrice?.toInt()
                }
                trendingAdapter.notifyDataSetChanged()
                sortingDialog.dismiss()
            })

            sortingDialog.setLowPriceBtn(View.OnClickListener {
                categoryProductList.sortBy {
                    it.productPrice?.toInt()
                }
                trendingAdapter.notifyDataSetChanged()
                sortingDialog.dismiss()
            })

            sortingDialog.setLatestBtn(View.OnClickListener {
                categoryProductList.sortBy {
                    it.productRating
                }
                trendingAdapter.notifyDataSetChanged()
                sortingDialog.dismiss()
            })

            sortingDialog.setTopRatedBtn(View.OnClickListener {
                categoryProductList.sortBy {
                    it.productRating?.toFloat()?.toInt()
                }
                trendingAdapter.notifyDataSetChanged()
                sortingDialog.dismiss()
            })
            sortingDialog.show()
        }

    }

    override fun onClick(item: ProductModel, position: Int) {
        var navController =
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        var action = ProductCategoryFragmentDirections.actionProductCategoryFragmentToProductDetailsFragment2(item)
        navController.navigate(action)
    }
}