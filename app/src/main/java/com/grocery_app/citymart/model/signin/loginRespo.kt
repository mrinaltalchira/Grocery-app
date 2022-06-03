package com.grocery_app.citymart.model.signin

import com.google.gson.annotations.SerializedName

data class loginRespo(
    val message: String,
    val user: User
)

data class User(
    val __v: Int,
    val _id: String,
    val mobile: String,
    val email: String
)