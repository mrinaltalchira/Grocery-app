package com.grocery_app.citymart.model.faq

import com.google.gson.annotations.SerializedName

data class FAQ(
    @SerializedName("faq" ) var faq : ArrayList<FaqX>
)