package com.grocery_app.citymart.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.AdapterSubCategroryBinding
import com.grocery_app.citymart.model.subCategory.MatchedCategory

class Adapter_subCategory(private var onselectitem: SelectItem): RecyclerView.Adapter<Adapter_subCategory.MainViewHolder>() {

    var subCategoryList = mutableListOf<MatchedCategory>()
    var mainCategoryName=" "

    fun setCategoty(mainCategory: List<MatchedCategory>, mainCategoryName:String) {
        this.subCategoryList = mainCategory as MutableList<MatchedCategory>
        this.mainCategoryName = mainCategoryName

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterSubCategroryBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {


        val category = subCategoryList[position]
//        if (ValidationUtil.validateMovie(movie)) {
        holder.binding.tvTitleSubCategory.text = category.name
        Glide.with(holder.itemView.context).load(category.image)
            .into(holder.binding.ivAdapterSubcategory)
        if (holder.binding.tvTitleSubCategory.text==mainCategoryName)
        {
            holder.binding.cardViewCategory.setBackgroundResource(R.drawable.add_cart_button_bg)
            holder.binding.tvTitleSubCategory.setTextColor(Color.parseColor("#44AC48"))

        }
        else
        {
            holder.binding.cardViewCategory.setBackgroundResource(R.drawable.select_bg)
            holder.binding.tvTitleSubCategory.setTextColor(Color.parseColor("#000000"))

        }

//        }

        holder.itemView.setOnClickListener {
            onselectitem.onIemSelect(category.defaultName)
            mainCategoryName= holder.binding.tvTitleSubCategory.text as kotlin.String
            holder.binding.cardViewCategory.setBackgroundResource(R.drawable.add_cart_button_bg)
            holder.binding.tvTitleSubCategory.setTextColor(Color.parseColor("#44AC48"))
            notifyDataSetChanged()

//            var bundle = bundleOf(
//                "category" to category.name
//            )
//            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_restaurantMartFragment2,bundle).onClick(holder.itemView)
        }
    }


    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    class MainViewHolder(val binding: AdapterSubCategroryBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface SelectItem{
        fun onIemSelect(subCategoryName: String)

    }


}