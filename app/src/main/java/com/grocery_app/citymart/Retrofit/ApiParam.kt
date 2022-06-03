package com.grocery_app.citymart.Retrofit

import android.text.style.UpdateAppearance

object ApiParam {
     var lang="en"

    //Get Subcategory using MainCategory
    const val subCategory:String="get/all/categories"

    //Get Product using subCategory
    const val productItem:String="get/all/products"

    //Get Product using subCategory
    const val  ADD_TO_CART_PRODUCT:String="add/cart"

    //Get Cart value
    const val CART_VALUE:String="cart/length"

    //Get Cart items
    const val CART_GET:String="cart/items"

    //Clear Cart
    const val CLEAR_CART:String="cart/clear"

    //Update cart Product quantity
    const val UPDATE_QUNTITY_CART_PRODUCT:String="cart/update/"

    //Delete cart Product
    const val DELETE_CART_PRODUCT:String="item/"

    //Get coupon code
    const val GET_COUPON_CODE_LIST:String="get/coupon-code"

    //Apply coupon code
    const val APPLY_COUPON_CODE:String="apply/coupon"

    //Apply coupon code
    const val FINAL_ORDER:String="create/order"


    //Get Orders
    const val GET_ORDER:String="get/user/orders"

    //Get past order
    const val GET_PAST_ORDER:String="filter/orders/"


    //Get past order
    const val GET_ORDER_DETAIL:String="user/order/details"


    //Get user profile
    const val GET_USER_PROFILE:String="profile/me"

















}