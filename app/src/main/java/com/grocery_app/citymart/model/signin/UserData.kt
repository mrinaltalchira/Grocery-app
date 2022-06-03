package com.grocery_app.citymart.model.signin

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("userId"    ) var userId    : String? = null,
    @SerializedName("firstName" ) var firstName : String? = null,
    @SerializedName("lastName"  ) var lastName  : String? = null,
    @SerializedName("email"     ) var email     : String? = null,
    @SerializedName("password"  ) var password  : String? = null,
    @SerializedName("country"   ) var country   : String? = null,
    @SerializedName("mobile"    ) var mobile    : String? = null,
    @SerializedName("token"     ) var token     : String? = null
)
