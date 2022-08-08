package com.ddev.myapplication.adapter

import com.ddev.myapplication.databinding.AddressPaymentProductCartRowBinding


import com.ddev.myapplication.R
import com.ddev.myapplication.model.AddToCartModel


class AddressPaymentCartAdapter() : BaseAdapter<AddToCartModel, AddressPaymentProductCartRowBinding>() {
    override fun getLayout() = R.layout.address_payment_product_cart_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<AddressPaymentProductCartRowBinding>,
        position: Int
    ) {
        holder.binding.cart = itemList[position]
    }

}