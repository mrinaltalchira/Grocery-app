package com.grocery_app.citymart.model.order

data class Order(
    val finalPrice: String,
    val items: List<Item>,
    val orderId: String,
    val orderedAt: String,
    val paymentMethod: String
)