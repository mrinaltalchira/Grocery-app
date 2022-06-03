package com.grocery_app.citymart.model.cart

data class Order(
    val __v: Int,
    val _id: String,
    val address: String,
    val deliveryCharge: String,
    val finalPrice: String,
    val items: List<Item>,
    val mobile: String,
    val orderedAt: String,
    val paymentMethod: String,
    val subTotal: String,
    val userId: String,
    val userName: String
)