package com.ddev.myapplication.adapter

import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.AddressRowBinding
import com.ddev.myapplication.model.AddressModel
import com.ddev.myapplication.listener.ClickListener

class AddressAdapter(var clickListener: ClickListener<AddressModel>) : BaseAdapter<AddressModel, AddressRowBinding>() {

    override fun getLayout() = R.layout.address_row

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<AddressRowBinding>,
        position: Int
    ) {
        holder.binding.address = itemList[position]
        holder.itemView.setOnClickListener {
            clickListener.onClick(itemList[position],position)
        }
    }

}