package com.grocery_app.citymart.model.cart

data class Cart(
    val _id: String,
    val products: Products,
    val quantity: String,
    val userId: String
)