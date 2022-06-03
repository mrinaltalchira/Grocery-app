package com.grocery_app.citymart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.ImageSliderBinding
import com.grocery_app.citymart.model.slider.SliderX
import com.smarteist.autoimageslider.SliderViewAdapter

class AdapterSlider():SliderViewAdapter<AdapterSlider.ViewHolder>() {

var imgList = mutableListOf<SliderX>()
    var context: Context? = null

    fun setImageList(imgList: List<SliderX>, context: Context){
        this.imgList = imgList as MutableList<SliderX>
        this.context = context
        notifyDataSetChanged()
    }


    class ViewHolder(var binding:ImageSliderBinding):SliderViewAdapter.ViewHolder(binding.root){

    }

    override fun getCount(): Int {
        return imgList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ImageSliderBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        var placeholder = imgList[position]

        try {
            Glide.with(context as Context)
                .load(placeholder.image)
                .error(placeholder.title)
                .into(viewHolder!!.binding.imageOfSlider)

        }catch(e:Exception){

        }

          }
}