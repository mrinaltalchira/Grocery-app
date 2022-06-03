package com.grocery_app.citymart.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grocery_app.citymart.databinding.RvProfilefragmentBinding
import com.grocery_app.citymart.ui.activity.orderDetail.Order_Detail_Activity

class Adapter_OrderLIst (): RecyclerView.Adapter<Adapter_OrderLIst.MainViewHolder>() {
//    var count=1
    var orderList = mutableListOf<com.grocery_app.citymart.model.pastOrder.Order>()
    lateinit var context:Context


    fun setCategoty(mainCategory: List<com.grocery_app.citymart.model.pastOrder.Order>,context: Context) {
        this.context=context
        this.orderList = mainCategory as MutableList<com.grocery_app.citymart.model.pastOrder.Order>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvProfilefragmentBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var count=1
        var orderString=""

        val order = orderList[position]
        try {
        for (add in order.items)
        {

            orderString+=", "+add.name+" x "+add.qty


        }
        }
        catch (e:Exception)
        {
            Log.d("pappu", e.toString())

        }
        orderString=orderString.drop(1)

        holder.binding.tvOrderTime.text=order.orderedAt
        holder.binding.tvPriceList.text="Rs."+order.finalPrice
        holder.binding.tvOrderName.text=orderString
        holder.binding.tvDelivery.text=order.status

        holder.itemView.setOnClickListener {
            val intent=Intent(context, Order_Detail_Activity::class.java)
            intent.putExtra("orderId", order.orderId)
            context.startActivity(intent)

        }
        if (order.status=="Delivered")
        {
            holder.binding.checkboxDeliveryStatusPending.visibility= View.GONE
            holder.binding.checkboxDeliveryStatusPlaced.visibility=View.GONE
            holder.binding.checkboxDeliveryStatusDelivered.visibility= View.VISIBLE
        } else if(order.status=="Placed")

        {
            holder.binding.checkboxDeliveryStatusPlaced.visibility=View.VISIBLE
            holder.binding.checkboxDeliveryStatusDelivered.visibility=View.GONE
            holder.binding.checkboxDeliveryStatusPending.visibility=View.GONE
        }else{
            holder.binding.checkboxDeliveryStatusPlaced.visibility=View.GONE
            holder.binding.checkboxDeliveryStatusPending.visibility=View.VISIBLE
            holder.binding.checkboxDeliveryStatusDelivered.visibility=View.GONE
        }



    }


    override fun getItemCount(): Int {
        return orderList.size
    }

    class MainViewHolder(val binding: RvProfilefragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}