package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.ProductImageViewpagerBinding
import com.ddev.myapplication.model.product.ProductViewPagerModel

class ProductViewPagerAdapter: BaseAdapter<ProductViewPagerModel, ProductImageViewpagerBinding>() {

    override fun getLayout() = R.layout.product_image_viewpager

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ProductImageViewpagerBinding>,
        position: Int
    ) {
        holder.binding.image = itemList[position]
    }

}