package com.grocery_app.citymart.model.order

data class Item(
    val cartId: String,
    val id: String,
    val name: String,
    val price: Int,
    val quantity: Int,
    val size: String
)