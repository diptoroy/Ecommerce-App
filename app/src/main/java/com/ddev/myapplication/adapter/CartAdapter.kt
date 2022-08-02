package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.AddToCartRowBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.listener.PriceClickListener
import kotlinx.android.synthetic.main.add_to_cart_row.view.*


class CartAdapter(var priceClickListener: PriceClickListener, var clickListener: ClickListener<AddToCartModel>) : BaseAdapter<AddToCartModel, AddToCartRowBinding>() {
    override fun getLayout() = R.layout.add_to_cart_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<AddToCartRowBinding>,
        position: Int
    ) {
        holder.binding.cart = itemList[position]
        holder.binding.productAddBtn.setOnClickListener {
            priceClickListener.priceClick(1,position)
        }
        holder.binding.productMinusBtn.setOnClickListener {
            priceClickListener.priceClick(0,position)
        }

        holder.itemView.deleteBtnContainer.setOnClickListener {
            clickListener.onClick(itemList[position],position)
        }
    }


}