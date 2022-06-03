package com.grocery_app.citymart.model.pastOrder

data class OrderX(
    val __v: Int,
    val _id: String,
    val address: String,
    val deliveryCharge: String,
    val email: String,
    val finalPrice: String,
    val orderedAt: String,
    val paymentMethod: String,
    val products: List<Product>,
    val status: String,
    val subTotal: String,
    val userId: String,
    val userName: String,
    val discountAmout:String,
    val discountPercent: String
)