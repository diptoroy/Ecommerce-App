package com.ddev.myapplication.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ddev.myapplication.model.SpecModel
import com.ddev.myapplication.view.fragment.ui.ProductDescFragment
import com.ddev.myapplication.view.fragment.ui.ProductSpecFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    desc: String?,
    spec: List<SpecModel>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var descFragment = ProductDescFragment()
    init {
        val args = Bundle()
        args.putString("TEST", desc)
        descFragment.arguments = args
    }

    var specFragment = ProductSpecFragment()
    init {
        val args = Bundle()
        args.putParcelableArrayList("Spec",ArrayList(spec))
        specFragment.arguments = args
    }
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return descFragment
            1 -> return specFragment
        }
        return ProductSpecFragment()
    }
}

