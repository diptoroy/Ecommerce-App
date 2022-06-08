package com.ddev.myapplication.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.RawValue


abstract class BaseAdapter<I : Any, B : ViewDataBinding>:
    RecyclerView.Adapter<BaseAdapter.Companion.BaseViewHolder<B>>() {

    abstract fun getLayout():Int

    var itemList = mutableListOf<I>()

    fun addItems(itemList:List<I>){
        this.itemList = itemList as MutableList<I>
        notifyDataSetChanged()
    }

    var listener:((item:I,position:Int)->Unit)? = null

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<B>(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayout(),
            parent,
            false
        )
    )

    companion object{
        class BaseViewHolder<B:ViewDataBinding>( val binding:B):
            RecyclerView.ViewHolder(binding.root)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}