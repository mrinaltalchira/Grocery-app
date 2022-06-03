package com.grocery_app.citymart.ui.activity.orderDetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.grocery_app.citymart.R
import com.grocery_app.citymart.adapter.Adapter_Order_Detail
import kotlinx.android.synthetic.main.activity_order_detail.*

class Order_Detail_Activity : AppCompatActivity() {
    val adapterOrderDetail = Adapter_Order_Detail()
    lateinit var sweetAlertDialog: SweetAlertDialog

    lateinit var orderDetailViewModel: OrderDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        sweetAlertDialog.titleText = getString(R.string.loading)
        back_btn.setOnClickListener {
            onBackPressed()
        }

        rv_orderDetail.adapter = adapterOrderDetail
        val orderId = intent.getStringExtra("orderId").toString()


        orderDetailViewModel = ViewModelProvider(this).get(OrderDetailViewModel::class.java)

        orderDetailViewModel.orderList.observe(this, Observer {
            adapterOrderDetail.setCategoty(it.products)
            tv_orderNo_orderDetail.text = "Order No. #" + it._id
            tv_item_total_.text = "Rs. " + it.subTotal
            tv_deliveryFee.text = "Rs. " + it.deliveryCharge
            tv_discount_price.text = "Rs. " + it.discountAmout
            tv_grandTotal.text = "Rs. " + it.finalPrice
            if (it.discountPercent.toInt() > 0) {
                tv_discountOffer.text = it.discountPercent + "% OFF applied"
            }
        })

        orderDetailViewModel.getOrderId(orderId)


        orderDetailViewModel.loading.observe(this, Observer {
            if (it) {
                sweetAlertDialog.show()
            } else {
                sweetAlertDialog.dismiss()
            }
        })


    }
}