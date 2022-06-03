package com.grocery_app.citymart.model.address.getAddress

import com.google.gson.annotations.SerializedName

data class Addres(

    @SerializedName("_id") var Id: String? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("houseNumber") var houseNumber: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("postal") var postal: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("instruction") var instruction: String? = null,
    @SerializedName("userId") var userId: String? = null,
    @SerializedName("__v") var _v: Int? = null

)