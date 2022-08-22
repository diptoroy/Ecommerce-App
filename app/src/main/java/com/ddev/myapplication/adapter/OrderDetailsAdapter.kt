package com.ddev.myapplication.adapter

import android.util.Log
import android.view.View
import android.widget.Toast
import com.ddev.myapplication.Application.EcommerceApp
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.OrderItemRowBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.OrderModel
import com.ddev.myapplication.model.UserProductRatingModel
import com.ddev.myapplication.model.product.ProductsRatingModel
import com.ddev.myapplication.util.dialog.ReviewDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.rating_dialog.*
import kotlinx.coroutines.coroutineScope


class OrderDetailsAdapter  () : BaseAdapter<AddToCartModel, OrderItemRowBinding>() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var currentUserId: String
    var currentUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    private lateinit var ratingDialog: ReviewDialog
    var ratingData = mutableListOf<UserProductRatingModel>()

    override fun getLayout() = R.layout.order_item_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<OrderItemRowBinding>,
        position: Int
    ) {
        currentUserId = currentUser.uid

        if (itemList[position].isReview == false){
            holder.binding.reviewBtn.visibility = View.VISIBLE
        }
        holder.binding.orderItemModel = itemList[position]

        ratingDialog = ReviewDialog(holder.binding.root.context, holder.binding.mainRootView)

        holder.binding.reviewBtn.setOnClickListener {
            ratingDialog.show()
            ratingDialog.setOnRootClickListener {
                var userRating: String = ratingDialog.ratingBar.rating.toString()
                var ratingNote: String = ratingDialog.ratingNote.text.toString()
                if (!userRating.isNullOrEmpty()){
                    var ratingModel = UserProductRatingModel(currentUserId,itemList[position].productId,userRating,ratingNote)
                    db.collection("Users").document(currentUserId).collection("ProductRating").add(ratingModel)
                    ratingData.add(ratingModel)
                    var productsRating = ProductsRatingModel(ratingData)
                    db.collection("ProductRating").document(itemList[position]!!.productId!!)
//                    db.collection("ProductRating").document(itemList[position]!!.productId!!).get().addOnSuccessListener {document->
//                        var data = document.toObject(ProductsRatingModel::class.java)
//                    }
                }
                Toast.makeText(EcommerceApp.getApp()!!.applicationContext, "rating$userRating", Toast.LENGTH_LONG).show()
                ratingDialog.dismiss()
            }
        }
    }

}

