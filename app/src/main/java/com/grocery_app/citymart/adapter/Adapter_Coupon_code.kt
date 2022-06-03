package com.grocery_app.citymart.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.AdapterCouponCodeBinding
import com.grocery_app.citymart.model.coupon_code.ApplyCoupon
import com.grocery_app.citymart.model.coupon_code.Code
import kotlinx.coroutines.*

class Adapter_Coupon_code(private val clickCouponCode: ClickCouponCode) :
    RecyclerView.Adapter<Adapter_Coupon_code.MyViewHolder>() {
    var itemList = mutableListOf<Code>()
    val errorMessage = MutableLiveData<String>()
    val couponCode = MutableLiveData<ApplyCoupon>()
    var job: Job? = null
    val successMsg = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    var userId = ""
    lateinit var context: Context


    fun setList(coupon: List<Code>, userId: String?, requireActivity: FragmentActivity) {
        this.itemList = coupon as MutableList<Code>
        this.context = requireActivity
        if (userId != null) {
            this.userId = userId
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val binding = AdapterCouponCodeBinding.inflate(inflter, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val couponPostion = itemList[position]
        holder.binding.tvTitleCouponCode.text = couponPostion.title
        holder.binding.tvDecsCouponCode.text = couponPostion.desc
        holder.binding.tvCouponCode.text = couponPostion.code

        holder.binding.btnTvApply.setOnClickListener {

//            holder.binding.progressBarCoupon.visibility = View.VISIBLE

            clickCouponCode.applyCoupon(couponPostion)
//          applyCoupon(userId,couponPostion._id )

        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false

    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(val binding: AdapterCouponCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    interface ClickCouponCode {
        fun applyCoupon(id: Code)
        fun applyMessage(message: String)

    }

}

