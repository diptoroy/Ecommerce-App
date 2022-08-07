package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.CategoryRowBinding
import com.ddev.myapplication.listener.CategoryListener
import com.ddev.myapplication.model.CategoryModel

class CategoryAdapter(var categoryListener: CategoryListener<CategoryModel>): BaseAdapter<CategoryModel, CategoryRowBinding>() {

    override fun getLayout() = R.layout.category_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<CategoryRowBinding>,
        position: Int
    ) {
        holder.binding.category = itemList[position]
        holder.itemView.setOnClickListener {
            categoryListener.onCategoryClick(itemList[position],position)
        }
    }

}