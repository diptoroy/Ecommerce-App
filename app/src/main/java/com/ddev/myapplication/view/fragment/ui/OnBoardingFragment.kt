package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.OnBoardingAdapter
import com.ddev.myapplication.databinding.FragmentOnBoardingBinding
import com.ddev.myapplication.model.OnBoardingItemModel
import com.ddev.myapplication.util.ViewPager2PageTransformation
import com.ddev.myapplication.view.fragment.BaseFragment


class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    private val adapter by lazy {
        OnBoardingAdapter()
    }

    private lateinit var indicatorContainer: LinearLayout
    var list = ArrayList<OnBoardingItemModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildUi()
    }

    private fun buildUi() {
        list.add(
            OnBoardingItemModel(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_pageone, null)!!,
                getString(R.string.onBoardingTitle),
                getString(R.string.onBoardingDesc)
            )
        )
        list.add(
            OnBoardingItemModel(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_pagetwo, null)!!,
                getString(R.string.onBoardingTitle),
                getString(R.string.onBoardingDesc)
            )
        )
        list.add(
            OnBoardingItemModel(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_pagethree, null)!!,
                getString(R.string.onBoardingTitle),
                getString(R.string.onBoardingDesc)
            )
        )


        //OnBoarding
        adapter.addItems(list)
        fragmentBinding.onBoardingViewPager.adapter = adapter
        with(fragmentBinding.onBoardingViewPager){
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        fragmentBinding.onBoardingViewPager.setPageTransformer(ViewPager2PageTransformation())
        fragmentBinding.onBoardingViewPager
        fragmentBinding.onBoardingViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                setCurrentIndicator(position)
//                fragmentBinding.startBtn.visibility = View.GONE
//                fragmentBinding.skipBtn.visibility = View.VISIBLE
//                if (position == 3){
//                    fragmentBinding.skipBtn.visibility = View.GONE
//                    fragmentBinding.startBtn.visibility = View.VISIBLE
//                }
            }
        })
        (fragmentBinding.onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        fragmentBinding.indicator.setViewPager2(fragmentBinding.onBoardingViewPager)
    }
}