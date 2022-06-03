package com.grocery_app.citymart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.AdapterCategoryHomeBinding
import com.grocery_app.citymart.model.homeCategory.MainCategory
import com.grocery_app.citymart.ui.fragment.restaurant_mart.RestautantMartFragment


class adapter_home_category : RecyclerView.Adapter<adapter_home_category.MainViewHolder>() {
    var homeCategoryList = mutableListOf<MainCategory>()
    lateinit var context: Context

    fun setCategoty(mainCategory: List<MainCategory>,context: Context) {
        this.context=context
        this.homeCategoryList = mainCategory as MutableList<MainCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCategoryHomeBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val category = homeCategoryList[position]
//        if (ValidationUtil.validateMovie(movie)) {
        holder.binding.tvTitleCategoryhome.text = category.name
        Glide.with(holder.itemView.context).load(category.image)
            .into(holder.binding.ivAdapterCategoryhome)
//        }

        holder.itemView.setOnClickListener {
            var bundle = bundleOf(
                "category" to category.name, "mainCategory" to category.categoryType
            )
            (context as FragmentActivity).supportFragmentManager?.beginTransaction()?.replace(R.id.mainActivityDefaultFragment,
                com.grocery_app.citymart.ui.fragment.main_category.MainCategory::class.java,bundle)?.addToBackStack(null)?.commit()
//            (context as FragmentActivity).fragmentManager.beginTransaction()
//                .replace(R.id.mainActivityDefaultFragment, numNumbersFrag())
//                .commit()
        }
    }


    override fun getItemCount(): Int {
        return homeCategoryList.size
    }

    class MainViewHolder(val binding: AdapterCategoryHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}