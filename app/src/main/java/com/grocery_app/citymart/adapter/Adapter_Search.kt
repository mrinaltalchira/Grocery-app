package com.grocery_app.citymart.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.databinding.SearchItemRecyclerviewLayoutBinding
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.model.Search.Product
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.ui.activity.product_details.DetailProductActivity
import kotlinx.android.synthetic.main.customedialoug.view.*
import kotlinx.android.synthetic.main.search_item_recyclerview_layout.*

class Adapter_Search(var onselectAddtoCart: ItemAddtocart): RecyclerView.Adapter<Adapter_Search.ViewHolder>() {

    var products = mutableListOf<Product>()
    var context:Context? = null

    companion object MyDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem._id == newItem._id
        }
    }

    fun adapterFunction(product: List<Product>, context:Context){
        this.context = context
        this.products = product as MutableList<Product>
        notifyDataSetChanged()
    }


    class ViewHolder(var binding:SearchItemRecyclerviewLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val infleter = LayoutInflater.from(parent.context)
        val binding = SearchItemRecyclerviewLayoutBinding.inflate(infleter, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var place = products[position]
        holder.binding.tvProductNameSearchRv.setText(place.name)
        holder.binding.tvCategoryNameSearchRv.setText(place.category)
        holder.binding.tvQuantitySearchRv.setText(place.quantity)
        holder.binding.tvPriceSearchRv.setText("/ ₹ ${place.price}")
        holder.binding.tvMrpSearchRv.setText("₹ ${place.mrp}")
        holder.binding.tvMrpSearchRv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        try {

            Glide.with(context as Context)
                .load(place.image)
                .into(holder.binding.ivSearchRv)
        }catch (e:Exception){}

        holder.binding.cardView.setOnClickListener {
            var bundle = bundleOf(
                "category" to place.category,
                "id" to place._id,
                "title" to place.name,
                "productimage" to place.image,
                "price" to place.price,
                "mrp" to place.mrp,
                "size" to place.quantity,
                "description" to place.desc,
            )
            var intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtras(bundle)
            startActivity(context as Context,intent,bundle)
        }

        holder.binding.btnAddToCart.setOnClickListener {
           var shareMartRestroTrueFalse = context?.getSharedPreferences("shareMartRestroTrueFalse",
                Context.MODE_PRIVATE)

            if (place.productType == "Mart") {
                var a = shareMartRestroTrueFalse?.getString("isMart", "")

                if (a == "Mart" || a == "") {
                    onselectAddtoCart.itemAddtoCart(place)
                    var editor = shareMartRestroTrueFalse?.edit()
                    editor?.putString("isMart", "Mart")
                    editor?.apply()

                } else {
                    val view = View.inflate(context, R.layout.custome_dialoug_box_delete_previour_cart, null)
                    val builder = AlertDialog.Builder(context)
                    builder.setView(view)
                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                    view.yes.setOnClickListener {
                        dialog.dismiss()
                        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(context as Context)
                        shareMartRestroTrueFalse!!.edit().clear().apply()

                        appDatabaseObj.getAppDao().deleteAllCart()
                        appDatabaseObj.getAppDao().deleteCoupon()


                    }
                    view.no.setOnClickListener {
                        dialog.dismiss()
                        Toast.makeText(context, "cancel request", Toast.LENGTH_SHORT).show()
                    }
                }


            }

            if (place.productType == "Restaurant") {

                var a = shareMartRestroTrueFalse?.getString("isMart", "")

                if (a == "Restaurant" || a == "") {
                    onselectAddtoCart.itemAddtoCart(place)
                    var editor = shareMartRestroTrueFalse?.edit()
                    editor?.putString("isMart", "Restaurant")
                    editor?.apply()
                } else {
                    val view = View.inflate(context, R.layout.custome_dialoug_box_delete_previour_cart, null)
                    val builder = AlertDialog.Builder(context)
                    builder.setView(view)
                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                    view.yes.setOnClickListener {
                        dialog.dismiss()
                        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(context as Context)
                        shareMartRestroTrueFalse!!.edit().clear().apply()

                        appDatabaseObj.getAppDao().deleteAllCart()
                        appDatabaseObj.getAppDao().deleteCoupon()


                    }
                    view.no.setOnClickListener {
                        dialog.dismiss()
                        Toast.makeText(context, "cancel request", Toast.LENGTH_SHORT).show()
                    }

                }
            }


            }
    }

    override fun getItemCount(): Int {
        return products.size    }


    public interface ItemAddtocart{
        fun itemAddtoCart(productId: Product)

    }
}