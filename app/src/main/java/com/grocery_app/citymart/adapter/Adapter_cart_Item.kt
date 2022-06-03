package com.grocery_app.citymart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grocery_app.citymart.databinding.AdapterCartProductBinding
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.model.cart.CartData

class Adapter_cart_Item(public var onChangeQty: CartItemClick) :
    RecyclerView.Adapter<Adapter_cart_Item.MainViewHolder>() {
    var itemList = mutableListOf<products>()

    fun setCategoty(item: List<products>) {
        this.itemList = item as MutableList<products>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCartProductBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val category = itemList[position]
        holder.binding.tvTitleAdapterCart.text = category.name
        holder.binding.tvQtyAdapterCart.text = category.qty
        holder.binding.tvPriceAdapterCart.text = "Rs. " + category.price

        holder.binding.btnIncrementAdapterCart.setOnClickListener {
            var count: Int = holder.binding.tvQtyAdapterCart.text.toString().toInt()
            count++
//            category.quantity=count.toString()
//            itemList[position].quantity=count.toString()
            onChangeQty.changeQty(count.toString(), category.idcrop)
            holder.binding.tvQtyAdapterCart.text = count.toString()
        }

        holder.binding.btnDecrementAdapterCart.setOnClickListener {
            var count: Int = holder.binding.tvQtyAdapterCart.text.toString().toInt()
            if (count > 1) {
                --count
//                category.quantity=count.toString()
//                itemList[position].quantity=count.toString()
                onChangeQty.changeQty(count.toString(), category.idcrop)
                holder.binding.tvQtyAdapterCart.text = count.toString()
            }
        }
        holder.binding.btnDeleteAdapterCart.setOnClickListener {
            itemList.removeAt(position)
            notifyDataSetChanged()
            onChangeQty.deleteCartItem(category._id, category.idcrop)
        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class MainViewHolder(val binding: AdapterCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    interface CartItemClick {
        fun changeQty(qty: String, productId: Int)
        fun deleteCartItem(cartId: String, position: Int)
    }



}