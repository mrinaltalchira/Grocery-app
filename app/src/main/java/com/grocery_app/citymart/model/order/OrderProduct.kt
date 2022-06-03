package com.grocery_app.citymart.model.order

import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.model.cart.Products

data class OrderProduct(
    var address: String,
    val deliveryCharge: String,
    val finalPrice: String,
    val paymentMethod: String,
    val products: List<products>,
    val subTotal: String,
    val discountAmout:String,
    val discountPercent: String

)