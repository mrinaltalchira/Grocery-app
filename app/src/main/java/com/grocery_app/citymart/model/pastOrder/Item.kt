package com.grocery_app.citymart.model.pastOrder

data class Item(
    val _id: String,
    val category: String,
    val desc: String,
    val idcrop: Int,
    val image: String,
    val mrp: String,
    val name: String,
    val price: String,
    val productType: String,
    val qty: String,
    val quantity: String
)