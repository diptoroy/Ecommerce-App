package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.CategoryRowBinding
import com.ddev.myapplication.databinding.SpecificationsRowBinding
import com.ddev.myapplication.model.CategoryModel
import com.ddev.myapplication.model.SpecModel
import com.ddev.myapplication.model.product.ProductSpecModel

class ProductSpecAdapter : BaseAdapter<SpecModel, SpecificationsRowBinding>() {

    override fun getLayout() = R.layout.specifications_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<SpecificationsRowBinding>,
        position: Int
    ) {
        holder.binding.spec = itemList[position]
    }

}