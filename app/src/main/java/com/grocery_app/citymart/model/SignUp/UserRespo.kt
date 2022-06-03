package com.grocery_app.citymart.model.SignUp

import com.google.gson.annotations.SerializedName

data class UserRespo(
    @SerializedName("user" ) var user : User? = User()
)
