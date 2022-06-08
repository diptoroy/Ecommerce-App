package com.ddev.myapplication.view.fragment.bottomMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.FavoriteAdapter
import com.ddev.myapplication.databinding.FragmentFavoriteBinding
import com.ddev.myapplication.model.FavoriteModel
import com.ddev.myapplication.view.fragment.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {
    private val adapter by lazy {
        FavoriteAdapter()
    }
    var list = ArrayList<FavoriteModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.add(
            FavoriteModel(
                "0011",
                "Beats Solo 3",
                ResourcesCompat.getDrawable(resources, R.drawable.headphone, null)!!,
                "$220",
                true
            )
        )
        list.add(
            FavoriteModel(
                "0011",
                "Beats Solo 3",
                ResourcesCompat.getDrawable(resources, R.drawable.headphone, null)!!,
                "$220",
                true
            )
        )
        list.add(
            FavoriteModel(
                "0011",
                "Beats Solo 3",
                ResourcesCompat.getDrawable(resources, R.drawable.headphone, null)!!,
                "$220",
                true
            )
        )

        setUpRecyclerView()
        adapter.addItems(list)
    }

    private fun setUpRecyclerView() {
        fragmentBinding.favoriteRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.favoriteRecyclerView.setHasFixedSize(true)
        fragmentBinding.favoriteRecyclerView.adapter = adapter
    }
}