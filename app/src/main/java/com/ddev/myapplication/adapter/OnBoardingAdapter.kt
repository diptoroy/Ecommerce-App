package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.BaseAdapter
import com.ddev.myapplication.databinding.OnboardingItemBinding
import com.ddev.myapplication.model.OnBoardingItemModel
import kotlinx.android.synthetic.main.onboarding_item.view.*


class OnBoardingAdapter(): BaseAdapter<OnBoardingItemModel, OnboardingItemBinding>() {

    override fun getLayout() = R.layout.onboarding_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<OnboardingItemBinding>,
        position: Int
    ) {
            holder.binding.onBoardingModel = itemList[position]
    }

}