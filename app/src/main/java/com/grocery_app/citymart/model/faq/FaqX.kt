package com.grocery_app.citymart.model.faq

import com.google.gson.annotations.SerializedName

data class FaqX(
    @SerializedName("_id"      ) var Id       : String? = null,
    @SerializedName("Question" ) var Question : String? = null,
    @SerializedName("Answer"   ) var Answer   : String? = null,
    @SerializedName("__v"      ) var _v       : Int?    = null

)