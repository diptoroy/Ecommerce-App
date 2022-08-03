package com.ddev.myapplication.adapter

import android.graphics.Color
import android.view.View
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.ShipmentItemBinding
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.model.ShipmentProcessModel
import com.ddev.myapplication.util.alphaAnimation

class ShipmentProcessAdapter(): BaseAdapter<ShipmentProcessModel, ShipmentItemBinding>() {

    override fun getLayout() = R.layout.shipment_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ShipmentItemBinding>,
        position: Int
    ) {
        holder.binding.shipmentProcess = itemList[position]

        if (position == itemList.lastIndex){
            holder.binding.shipmentLower.visibility = View.GONE
        }
        if (position == 0){
            holder.binding.shipmentUpper.visibility = View.GONE
        }

        if (position == itemList.lastIndex){
            if (itemList[position].isProcessComplete == true){
                holder.binding.processCompletedImage.setImageResource(R.drawable.ic_baseline_check_24)
                holder.binding.shipmentProcessName.setTextColor(Color.parseColor("#673AB7"))
            }else{
                holder.binding.processCompletedImage.visibility = View.GONE
            }
        }

        if (itemList[position].processName == "shipping"){
            holder.binding.processCompletedImage.setImageResource(R.drawable.ic_baseline_local_shipping_24)
        }

        holder.binding.shipmentProcessBack1State.alphaAnimation(700)
        holder.binding.shipmentProcessBack2State.alphaAnimation(800)

//        if (itemList[position].isProcessComplete == false){
//            holder.binding.shipmentProcessName.visibility = View.GONE
//        }
//        holder.itemView.setOnClickListener {
//            clickListener.onClick(itemList[position],position)
//            notifyDataSetChanged()
//        }
    }

}