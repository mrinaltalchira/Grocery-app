package com.grocery_app.citymart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grocery_app.citymart.R
import com.grocery_app.citymart.model.faq.FaqX

class Adapter_FAQ: RecyclerView.Adapter<Adapter_FAQ.MyFarmsViewHolder>() {

    lateinit var data: MutableList<FaqX>
    lateinit var context: Context
    @SuppressLint("NotifyDataSetChanged")
    fun setList(DataList: List<FaqX>, context: Context) {
        this.data = DataList as MutableList<FaqX>
        this.context=context
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFarmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_parent, parent, false)
        return MyFarmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyFarmsViewHolder, position: Int) {
    holder.que.setText("Q.  " + data[position].Question)
    holder.ans.setText("\t\t\t"+data[position].Answer)
        holder.que.setOnClickListener { if (holder.rV.visibility == View.GONE){
            holder.rV.visibility = View.VISIBLE
        }else{
            holder.rV.visibility = View.GONE
        } }
     }

    override fun getItemCount(): Int {
       return data.size
    }
    class MyFarmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var que: TextView = itemView.findViewById(R.id.tv_Question)
    var ans: TextView = itemView.findViewById(R.id.tv_answer)
    var rV: RelativeLayout = itemView.findViewById(R.id.expanded_view)

    }
}
