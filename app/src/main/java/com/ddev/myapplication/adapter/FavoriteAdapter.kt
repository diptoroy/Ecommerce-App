package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FavoriteRowBinding
import com.ddev.myapplication.model.FavoriteModel
import com.ddev.myapplication.listener.ClickListener

class FavoriteAdapter(var clickListener: ClickListener<FavoriteModel>) : BaseAdapter<FavoriteModel, FavoriteRowBinding>() {

    override fun getLayout() = R.layout.favorite_row

    override fun onBindViewHolder(
        holder: BaseAdapter.Companion.BaseViewHolder<FavoriteRowBinding>,
        position: Int
    ) {
        holder.binding.favorite = itemList[position]

        holder.binding.favoriteDeleteBtn.setOnClickListener {
            clickListener.onClick(itemList[position],position)
        }
    }

}