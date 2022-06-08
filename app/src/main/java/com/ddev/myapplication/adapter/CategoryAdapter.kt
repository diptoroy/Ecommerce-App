package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.CategoryRowBinding
import com.ddev.myapplication.databinding.OnboardingItemBinding
import com.ddev.myapplication.model.CategoryModel
import com.ddev.myapplication.model.OnBoardingItemModel

class CategoryAdapter: BaseAdapter<CategoryModel, CategoryRowBinding>() {

    override fun getLayout() = R.layout.category_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<CategoryRowBinding>,
        position: Int
    ) {
        holder.binding.category = itemList[position]
    }

}