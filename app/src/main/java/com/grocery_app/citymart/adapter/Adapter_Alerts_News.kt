package com.grocery_app.citymart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grocery_app.citymart.R
import com.grocery_app.citymart.model.homeCategory.MainCategory

class Adapter_Alerts_News() :
    RecyclerView.Adapter<Adapter_Alerts_News.ViewHolder>() {

//    val incomeTrackerList: ArrayList<String> = ArrayList()
    lateinit var data:List<MainCategory>
    lateinit var context:Context
    fun setList(DataList: List<MainCategory>, context: Context) {
        this.data = DataList
        this.context=context
        Toast.makeText(context, "data$data", Toast.LENGTH_SHORT).show()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_category_home, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNewstitle.setText(data[position].name)
//        holder.tvTime.setText(data[position].price)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNewstitle: TextView = itemView.findViewById(R.id.tv_Title_categoryhome)
//        var tvTime: TextView = itemView.findViewById(R.id.tv_price_adapter_cart)
    }
}