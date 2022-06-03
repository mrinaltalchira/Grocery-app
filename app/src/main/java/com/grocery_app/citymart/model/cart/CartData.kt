package com.grocery_app.citymart.model.cart

data class CartData(
    val _id: String,
    val category: String,
    val image: String,
    val mrp: String,
    val name: String,
    val price: String,
    val productType: String,
    var quantity: String,
    val cartId: String

)