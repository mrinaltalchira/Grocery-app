package com.grocery_app.citymart.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.AdapterProductBycategoryBinding
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.ui.activity.product_details.DetailProductActivity

class Adapter_productBycategory (public var onselectAddtoCart: ItemAddtocart): RecyclerView.Adapter<Adapter_productBycategory.MainViewHolder>() {
//    var count=1
    var subCategoryList = mutableListOf<products>()
    lateinit var context: Context

    fun setCategoty(mainCategory: List<products>, context: Context) {
        this.context=context
        this.subCategoryList = mainCategory as MutableList<products>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = com.grocery_app.citymart.databinding.AdapterProductBycategoryBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var count=1

        val category = subCategoryList[position]

        holder.binding.tvTitleProduct.text = category.name
        holder.binding.tvProductSize.text=category.quantity
        holder.binding.tvPriceProduct.text="₹"+category.price
        holder.binding.tvMrpProduct.text="₹"+category.mrp

        holder.binding.tvMrpProduct.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
        Glide.with(holder.itemView.context).load(category.image)
            .into(holder.binding.ivAdapterSubcategory)

//        holder.binding.tvAddcart.setOnClickListener {
////            holder.binding.ll1BtnProductAdapter.visibility=View.VISIBLE
////            holder.binding.ll2BtnProductAdapter.visibility=View.VISIBLE
//
//        }
        holder.binding.tvAddcart.setOnClickListener {

            onselectAddtoCart.itemAddtoCart(category )
//            holder.binding.tvAddcart.text="Add"

        }


        holder.itemView.setOnClickListener {
            var bundle = bundleOf(
                "id" to category._id,
                "title" to category.name,
                "productimage" to category.image,
                "price" to category.price,
                "mrp" to category.mrp,
                "size" to category.quantity,
                "category" to category.desc,
                "description" to category.desc,
                "productType" to category.productType,
            )
            var intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtras(bundle)
            startActivity(context as Context,intent,bundle)
        }
    }


    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    class MainViewHolder(val binding: AdapterProductBycategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    public interface ItemAddtocart{
        fun itemAddtoCart(productId: products)

    }


}