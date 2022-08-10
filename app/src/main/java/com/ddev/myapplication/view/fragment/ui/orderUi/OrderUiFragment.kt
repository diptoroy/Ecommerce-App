package com.ddev.myapplication.view.fragment.ui.orderUi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.ddev.myapplication.adapter.TabAdapter
import com.ddev.myapplication.databinding.FragmentOrderUiBinding
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.fragment.ui.AddressDetailsFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_order_ui.*


class OrderUiFragment : BaseFragment<FragmentOrderUiBinding>(FragmentOrderUiBinding::inflate) {
    val tabArray = arrayOf(
        "Order",
        "Delivery"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fList = arrayListOf<Fragment>(
            OrderFragment(),
            OrderDeliveryFragment()
        )

        var adapter = TabAdapter(fList,requireActivity().supportFragmentManager,lifecycle)
        fragmentBinding.viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager){tab,pos ->
            tab.text = tabArray[pos]
        }.attach()
    }
}