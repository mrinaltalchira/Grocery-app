package com.grocery_app.citymart.Retrofit

import com.grocery_app.citymart.model.Otp.GetOtp
import com.grocery_app.citymart.model.Product.AllProductByCategoryList
import com.grocery_app.citymart.model.Search.Search
import com.grocery_app.citymart.model.SignUp.SignUpRespo
import com.grocery_app.citymart.model.address.EditAddress
import com.grocery_app.citymart.model.address.getAddress.DeleteAdd
import com.grocery_app.citymart.model.address.getAddress.GetAddress
import com.grocery_app.citymart.model.address.setAddress.SetAddressX
import com.grocery_app.citymart.model.cart.*
import com.grocery_app.citymart.model.cart.updateCart.UpdateCartQty
import com.grocery_app.citymart.model.coupon_code.ApplyCoupon
import com.grocery_app.citymart.model.coupon_code.CouponCodeList
import com.grocery_app.citymart.model.faq.FAQ
import com.grocery_app.citymart.model.homeCategory.HomeCategoryList
import com.grocery_app.citymart.model.myProfile.GetProfileData
import com.grocery_app.citymart.model.myProfile.UpdateProfile
import com.grocery_app.citymart.model.order.OrderProduct
import com.grocery_app.citymart.model.pastOrder.GetOrderDetail
import com.grocery_app.citymart.model.pastOrder.GetPastOrder
import com.grocery_app.citymart.model.signin.loginRespo
import com.grocery_app.citymart.model.slider.Slider
import com.grocery_app.citymart.model.subCategory.SubCategoryList
import com.grocery_app.citymart.model.uploadImage.UserImage
import com.grocery_app.citymart.model.userInfo.GetProfileInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

import retrofit2.http.POST


interface RetrofitService {

    // get FAQ

@GET("get/faq")
suspend fun getFAQ():Response<FAQ>

    //Get user Info
    @GET(ApiParam.GET_USER_PROFILE)
    suspend fun getUserProfile(
        @Query("userId") userId: String
    ): Response<GetProfileInfo>


    @FormUrlEncoded
    @PUT("upload/pic/{id}")
   suspend fun postImage(
        @Path("id") userId: String,
        @Field("image") image: String
    ): Response<UserImage>

    // OTP get
    @GET("{api_key}/SMS/{users_phone_no}/AUTOGEN")
    fun getOTP(
        @Path("api_key") api_key: String,
        @Path("users_phone_no") users_phone_no: String
    ): Call<GetOtp>

    //CHECK OTP
    @POST("{api_key}/SMS/VERIFY/{session_id}/{otp_entered_by_user}")
    fun checkOTP(
        @Path("api_key") api_key: String,
        @Path("session_id") session_id: String,
        @Path("otp_entered_by_user") otp_entered_by_user: String
    ): Call<GetOtp>

    //Get home category
    @GET("get/slider")
    suspend fun sliderHome(): Response<Slider>

    // get address
    @GET("product/search")
    suspend fun searchProduct(
        @Query("name") name: String, @Query("max") max: Int, @Query("min") min: Int
    ): Response<Search>

    //update address
    @FormUrlEncoded
    @PUT("update/address/{id}")
    suspend fun updateAddress(
        @Path("id") id: String,
        @Field("houseNumber") houseNumber: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("mobile") mobile: String,
        @Field("city") city: String,
        @Field("postal") postal: String,
        @Field("country") country: String,
        @Field("instruction") instruction: String
    ): Response<EditAddress>

    // delete address
    @DELETE("delete/address/{id}")
    fun deleteAddress(@Path("id") id: String): Call<DeleteAdd>

    // get address
    @GET("get/address")
    suspend fun getAllAddress(@Query("userId") userId: String): Response<GetAddress>

    // set Address
    @FormUrlEncoded
    @POST("add/address")
    suspend fun setNewAddress(
        @Query("userId") userId: String,
        @Field("houseNumber") houseNumber: String,
        @Field("city") city: String,
        @Field("postal") postal: String,
        @Field("country") country: String,
        @Field("instruction") instruction: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("mobile") mobile: String,
    ): Response<SetAddressX>

    //Update Profile

    @FormUrlEncoded
    @PUT("me/update/{id}")
    suspend fun UpdateProfile(
        @Path("id") UserId: String,
        @Field("fullName") fullName: String,
        @Field("mobile") mobile: String
    ): Response<UpdateProfile>


    //User SignUp
    @FormUrlEncoded
    @POST("register")
    fun UserRegist(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("country") country: String,
        @Field("mobile") mobile: String,
    ): Call<SignUpRespo>

    // Login
    @FormUrlEncoded
    @POST("login")
    fun signin(
        @Field("mobile") mobile: String
    ): Call<loginRespo>

    //google signin
    @FormUrlEncoded
    @POST("login/google")
    fun googleSignin(
        @Field("email") email: String
    ): Call<loginRespo>

    //Get home category
    val lang: String
        get() = ApiParam.lang

    @GET("get/home/categories")
    suspend fun getHomeCategory(
        @Query("lang") lang: String
    ): Response<HomeCategoryList>

    //Get sub Category
    @GET(ApiParam.subCategory)
    suspend fun getSubCategory(
        @Query("name") categoryName: String,
        @Query("lang") lang: String
    ): Response<SubCategoryList>

    //Get All product By category
    @GET(ApiParam.productItem)
    suspend fun getAllProduct(
        @Query("category") subCategoryname: String,
        @Query("lang") lang: String
    ): Response<AllProductByCategoryList>

    // Product Add to cart
    @POST(ApiParam.ADD_TO_CART_PRODUCT)
    fun addToCart(
        @Query("userId") userId: String,
        @Query("id") productId: String,
        @Query("quantity") qty: String,

        ): Call<AddtoCartProduct>

    //Get Cart length
    @GET(ApiParam.CART_VALUE)
    fun cartValue(
        @Query("userId") userId: String
    ): Call<CartValue>

//    //Get Cart length
//    @GET(ApiParam.CART_VALUE)
//    suspend fun cartValue(
//        @Query("userId")userId: String
//    ):Response<CartValue>

    //Get Cart items
    @GET(ApiParam.CART_GET)
    fun getCart(
        @Query("userId") userId: String
    ): Call<GetCartItems>

    //Clear Cart
    @DELETE(ApiParam.CLEAR_CART)
    suspend fun clearCart(
        @Query("userId") UserId: String
    ): Response<ClearCartResponse>

    //Update Cart Quntity
    @PUT(ApiParam.UPDATE_QUNTITY_CART_PRODUCT + "{cartId}")
    suspend fun updateCartProductQuntity(
        @Path("cartId") cartId: String,
        @Query("productId") productId: String,
        @Query("quantity") qty: String
    ): Response<UpdateCartQty>

    //Delete Cart Item
    @DELETE(ApiParam.DELETE_CART_PRODUCT + "{cartId}")
    suspend fun deleteCartProduct(
        @Path("cartId") cartId: String
    ): Response<String>

    //Get coupon code list
    @GET(ApiParam.GET_COUPON_CODE_LIST)
    suspend fun getCouponCode(): Response<CouponCodeList>

    //Apply coupon code
    @GET(ApiParam.APPLY_COUPON_CODE)
    suspend fun applyCouponCode(
        @Query("userId") userId: String,
        @Query("id") couponId: String
    ): Response<ApplyCoupon>


    //Order final


    @POST(ApiParam.FINAL_ORDER)
    fun orderCheckout(
        @Query("userId") userId: String,
        @Body products: OrderProduct
    ): Call<OrderCheckout>


    //Get order List
    @GET(ApiParam.GET_ORDER)
    suspend fun getOrderList(
        @Query("userId") userId: String
    ): Response<GetPastOrder>

    //Get past order List
    @GET(ApiParam.GET_PAST_ORDER + "{orderType}")
    suspend fun getPastOrderList(
        @Path("orderType") orderType: String,
        @Query("userId") userId: String
    ): Response<GetPastOrder>

    //    /Get Order Details
    @GET(ApiParam.GET_ORDER_DETAIL)
    suspend fun getOrderDetail(
        @Query("orderId") orderId: String
    ): Response<GetOrderDetail>


    @GET("profile/me")
    suspend fun userDetails(
        @Query(
            "userId"

        )
        userId: String
    ): Response<GetProfileData>


    companion object {
        var URL =
            "https://grocery-city-mart.herokuapp.com/api/v1/"
        var OTP_URL = "https://2factor.in/API/V1/"

        var retrofitService: RetrofitService? = null
        var retrofitOtpService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

        fun getInstanceOtp(): RetrofitService {
            if (retrofitOtpService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(OTP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitOtpService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitOtpService!!
        }
    }
}
