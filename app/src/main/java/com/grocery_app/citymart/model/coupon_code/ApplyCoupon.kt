package com.grocery_app.citymart.model.coupon_code

import android.os.Message

data class ApplyCoupon(
    val discountValue: Double,
    val newPrice: Double,
    val message: String,
    val discount:String,
    val fixedAmount:String,

)