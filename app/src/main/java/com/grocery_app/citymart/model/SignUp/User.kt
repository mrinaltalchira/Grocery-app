package com.grocery_app.citymart.model.SignUp

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("firstName" ) var firstName : String? = null,
    @SerializedName("lastName"  ) var lastName  : String? = null,
    @SerializedName("email"     ) var email     : String? = null,
    @SerializedName("password"  ) var password  : String? = null,
    @SerializedName("country"   ) var country   : String? = null,
    @SerializedName("mobile"    ) var mobile    : String? = null,
    @SerializedName("_id"       ) var Id        : String? = null,
    @SerializedName("__v"       ) var _v        : Int?    = null
)
