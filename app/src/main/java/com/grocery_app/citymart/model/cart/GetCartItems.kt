package com.grocery_app.citymart.model.cart

data class GetCartItems(
    val cartData: List<CartData>,
    val totalPrice: String,
    val totalQuantity: String
)