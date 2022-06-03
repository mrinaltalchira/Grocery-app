package com.grocery_app.citymart.ui.activity.PastOrder

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.grocery_app.citymart.R
import com.grocery_app.citymart.adapter.Adapter_OrderLIst
import kotlinx.android.synthetic.main.activity_restaurant_mart_past_order.*
import com.grocery_app.citymart.ui.activity.coupon_code.CouponCodeViewModel
import com.grocery_app.citymart.ui.fragment.restaurant_mart.RestautantMartViewModel

class RestaurantMart_PastOrderActivity : AppCompatActivity() {

    lateinit var restromartviewModel:ResaurantMart_PastOrderViewModel
    lateinit var sharep: SharedPreferences
    lateinit var userId:String
    lateinit var orderType:String
    lateinit var sweetAlertDialog: SweetAlertDialog


    private var adapterOrderlist= Adapter_OrderLIst()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_mart_past_order)
        sweetAlertDialog=SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        sweetAlertDialog.titleText =getString(R.string.loading)
        sweetAlertDialog.setCancelable(false)
        sharep = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId", "").toString()

        orderType= intent.getStringExtra("orderType").toString()
        when (orderType)
        {
            "rest"-> tv_titleRestaurantMartOrder.text="Restaurant Orders"
            "mart"-> tv_titleRestaurantMartOrder.text="Mart Order"
            else->tv_titleRestaurantMartOrder.text=""
        }
        back_btn.setOnClickListener {
            onBackPressed()
        }



        restromartviewModel = ViewModelProvider(this).get(ResaurantMart_PastOrderViewModel::class.java)

        rv_restromart_pastOrder.adapter=adapterOrderlist

        restromartviewModel.orderList.observe(this, Observer {
            adapterOrderlist.setCategoty(it,this)
        })

        restromartviewModel.getPastorder(orderType, userId)

        restromartviewModel.loading.observe(this, Observer {
            if (it) {
               sweetAlertDialog.show()
            } else {
              sweetAlertDialog.dismiss()
            }
        })

    }
}