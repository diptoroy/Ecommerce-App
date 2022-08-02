package com.ddev.myapplication.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.ddev.myapplication.Application.EcommerceApp
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.ProductColorRowBinding
import com.ddev.myapplication.model.product.ColorModel
import com.ddev.myapplication.listener.ClickListener

class ColorAdapter(var clickListener: ClickListener<ColorModel>) : BaseAdapter<ColorModel, ProductColorRowBinding>() {

    override fun getLayout() = R.layout.product_color_row

    var selectedItem = -1

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(
        holder: BaseAdapter.Companion.BaseViewHolder<ProductColorRowBinding>,
        position: Int
    ) {
        var color = itemList[position]
        holder.binding.color = itemList[position]
//        holder.itemView.background = color.color
        holder.itemView.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color.color))

        if (selectedItem == position){
            holder.itemView.background = ResourcesCompat.getDrawable(
                EcommerceApp.getApp()!!.resources,
                R.drawable.color_select_drawable,
                null
            )
        }else{
            holder.itemView.background = ResourcesCompat.getDrawable(
                EcommerceApp.getApp()!!.resources,
                R.drawable.color_backgorund,
                null
            )
        }

        holder.itemView.setOnClickListener {
            clickListener.onClick(itemList[position], position)
            setSingleSelection(holder.adapterPosition)
        }
    }

    private fun setSingleSelection(adapterPosition: Int) {
        if (adapterPosition == RecyclerView.NO_POSITION) return
        notifyItemChanged(selectedItem)
        selectedItem = adapterPosition
        notifyItemChanged(selectedItem)
    }
}