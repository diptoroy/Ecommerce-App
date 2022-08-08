package com.ddev.myapplication.adapter

import android.view.View
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.TrendingRowBinding
import com.ddev.myapplication.model.product.ProductModel
import com.ddev.myapplication.listener.ClickListener

class ProductAdapter(var clickListener: ClickListener<ProductModel>): BaseAdapter<ProductModel, TrendingRowBinding>() {

    override fun getLayout() = R.layout.trending_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<TrendingRowBinding>,
        position: Int
    ) {
        holder.binding.product = itemList[position]


        if(itemList[position].isStock != false){
            holder.itemView.setOnClickListener {
                clickListener.onClick(itemList[position],position)
                notifyDataSetChanged()
            }
        }else{
            holder.binding.apply {
                productName.visibility = View.GONE
                productPrice.visibility = View.GONE
                productRatingText.visibility = View.GONE
                productRating.visibility = View.GONE
                stockOutText.visibility = View.VISIBLE
                productImage.alpha = 0.3f
            }
        }
    }

    fun filterList(filterList: ArrayList<ProductModel>) {
        itemList = filterList
        notifyDataSetChanged()
    }

}