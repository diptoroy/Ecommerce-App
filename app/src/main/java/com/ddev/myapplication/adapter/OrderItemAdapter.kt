package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.OrderGroupRowBinding
import com.ddev.myapplication.databinding.OrderItemRowBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.OrderModel
import com.ddev.myapplication.util.ClickListener

class OrderItemAdapter () : BaseAdapter<AddToCartModel, OrderItemRowBinding>() {

    override fun getLayout() = R.layout.order_item_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<OrderItemRowBinding>,
        position: Int
    ) {
        holder.binding.orderItemModel = itemList[position]
    }

}