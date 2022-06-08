package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.CategoryRowBinding
import com.ddev.myapplication.databinding.PaymentMethodRowBinding
import com.ddev.myapplication.model.CategoryModel
import com.ddev.myapplication.model.PaymentMethodModel

class PaymentAdapter: BaseAdapter<PaymentMethodModel, PaymentMethodRowBinding>() {

    var lastPosition = -1

    override fun getLayout() = R.layout.payment_method_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<PaymentMethodRowBinding>,
        position: Int
    ) {
        holder.binding.payment = itemList[position]
        holder.binding.paymentSelection.isChecked = lastPosition == position

        holder.binding.paymentSelection.setOnClickListener {
            lastPosition = position
            notifyDataSetChanged()
        }
    }

}