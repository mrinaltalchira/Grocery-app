package com.grocery_app.citymart.data

import com.grocery_app.citymart.Retrofit.ApiParam
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.model.order.OrderProduct
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import retrofit2.http.Path

class Repository constructor(private val retrofitService: RetrofitService) {
    val lang: String = ApiParam.lang

    suspend fun getFaq()=retrofitService.getFAQ()

    suspend fun uploadProfile(userId:String,image:String)=retrofitService.postImage(userId,image)

    //Get Otp
    fun getOtp(api_key: String, users_phone_no: String) =
        retrofitService.getOTP(api_key, users_phone_no)

    //Match Otp
    fun matchOtp(api_key: String, session_id: String, otp_entered_by_user: String) =
        retrofitService.checkOTP(api_key, session_id, otp_entered_by_user)

    //Get Home category
    suspend fun getSlider() = retrofitService.sliderHome()


    suspend fun updateProfile(
        userId: String,
        fullname: String,
        mobile: String
    ) = retrofitService.UpdateProfile(userId, fullname, mobile)

    //search product
    suspend fun searchProduct(name: String, max: Int,min: Int) = retrofitService.searchProduct(name, max,min)

    // update Address
    suspend fun updateAdd(
        id: String,
        houseNumber: String,
        city: String,
        postal: String,
        country: String,
        instruction: String,
        firstName: String,
        lastName: String,
        mobile: String
    ) = retrofitService.updateAddress(
        id,
        houseNumber,
        city,
        postal,
        country,
        instruction,
        firstName,
        lastName,
        mobile
    )


    //get Address
    suspend fun getAllAddress(userId: String) = retrofitService.getAllAddress(userId)


    //set Address
    suspend fun setAddress(
        userId: String,
        houseNumber: String,
        city: String,
        postal: String,
        country: String,
        instruction: String,
        firstName: String,
        lastName: String,
        mobile: String,
    ) = retrofitService.setNewAddress(
        userId,
        houseNumber,
        city,
        country,
        postal,
        instruction,
        firstName,
        lastName,
        mobile
    )

    fun signUp_User(
        firstname: String,
        lastname: String,
        password: String,
        country: String,
        mobile: String
    ) =
        retrofitService.UserRegist(firstname, lastname, password, country, mobile)

    //Get Home category
    suspend fun getHomeCategory() = retrofitService.getHomeCategory(lang)

    //Get sub Category
    suspend fun getSubCategory(categoyName: String) =
        retrofitService.getSubCategory(categoyName, lang)

    //Get All product by category
    suspend fun getAllProduct(categoyName: String) =
        retrofitService.getAllProduct(categoyName, lang)

    //Product add to cart
    fun addtoCart(userId: String, productId: String, qty: String) = retrofitService.addToCart(
        userId, productId, qty
    )

    //Get Cart value
    fun getCartValue(userId: String) = retrofitService.cartValue(userId)

    //Get Cart Items
    fun getCartItems(userId: String) = retrofitService.getCart(userId)

    //Clear Cart
    suspend fun clearCart(userId: String) = retrofitService.clearCart(userId)

    //Update Cart item qty
    suspend fun changeCartItemQty(qty: String, productId: String, cartId: String) =
        retrofitService.updateCartProductQuntity(cartId, productId, qty)

    // Delete Cart item
    suspend fun deleteCartItem(cartId: String) = retrofitService.deleteCartProduct(cartId)

    //SignIn user
    fun signin_User(mobileNo: String) =
        retrofitService.signin(mobileNo)

    //GoogleSignIn user
    fun googleSignin_User(email: String) =
        retrofitService.googleSignin(email)

    //Get profile Details
    suspend fun getProfileDetails(userID: String) = retrofitService.userDetails(userID)

    //Get Coupon Code
    suspend fun getCouponCode() = retrofitService.getCouponCode()

    //Apply Coupon Code
    suspend fun applyCouponCode(userId: String, couponId: String) =
        retrofitService.applyCouponCode(userId, couponId)


    //Order Checkout
    fun orderCheckout(
        userId: String,
        orderProduct: OrderProduct
    ) = retrofitService.orderCheckout(userId, orderProduct)

    //Get orderList
    suspend fun orderList(userId: String) =
        retrofitService.getOrderList(userId)

    //Get orderList
    suspend fun getPastOrderList(orderType:String,userId: String) =
        retrofitService.getPastOrderList(orderType,userId)

    //Get order Detail
    suspend fun getOrderDetail(orderId: String) =
        retrofitService.getOrderDetail(orderId)

    //Get order Detail
    suspend fun getUserInfo(userId: String) =
        retrofitService.getUserProfile(userId)
}