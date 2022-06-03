package com.grocery_app.citymart.model.pastOrder

data class Order(
    val finalPrice: String,
    val items: List<Item>,
    val orderId: String,
    val paymentMethod: String,
    val status: String,
    val orderedAt: String
)