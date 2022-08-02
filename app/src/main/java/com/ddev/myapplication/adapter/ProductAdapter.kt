package com.ddev.myapplication.adapter

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
        holder.itemView.setOnClickListener {
            clickListener.onClick(itemList[position],position)
            notifyDataSetChanged()
        }
    }

}