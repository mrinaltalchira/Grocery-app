package com.grocery_app.citymart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.ActivityCheckoutBinding
import com.grocery_app.citymart.databinding.AdapterCategoryHomeBinding
import com.grocery_app.citymart.databinding.AdapterOrderDetailsBinding
import com.grocery_app.citymart.model.homeCategory.MainCategory
import com.grocery_app.citymart.model.pastOrder.OrderX
import com.grocery_app.citymart.model.pastOrder.Product

class Adapter_Order_Detail:RecyclerView.Adapter<MainViewHolder>(){

    var orderList = mutableListOf<Product>()


    fun setCategoty(orderList: List<Product>) {

        this.orderList = orderList as MutableList<Product>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterOrderDetailsBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val order = orderList[position]
        holder.binding.tvTitleOrderDetail.text=order.name
        holder.binding.tvPriceOrderDetil.text="Rs. "+order.price

    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}

class MainViewHolder(val binding:AdapterOrderDetailsBinding)
    :RecyclerView.ViewHolder(binding.root){}