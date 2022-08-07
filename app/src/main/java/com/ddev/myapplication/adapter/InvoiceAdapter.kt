package com.ddev.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ddev.myapplication.R
import com.ddev.myapplication.model.AddToCartModel

class InvoiceAdapter (private val cartItemList: List<AddToCartModel>) :
    RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {
    class InvoiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cartItemName: TextView = view.findViewById(R.id.productName)
        val cartItemQuantity: TextView = view.findViewById(R.id.productQuantity)
        val cartItemPrice: TextView = view.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.invoice_item_row, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        holder.cartItemName.text = cartItemList[position].productName
        holder.cartItemQuantity.text = cartItemList[position].productQuantity.toString()
        holder.cartItemPrice.text = cartItemList[position].productPrice.toString()
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }
}