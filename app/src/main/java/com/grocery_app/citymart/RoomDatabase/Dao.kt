package com.grocery_app.citymart.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.model.Search.Product
import com.grocery_app.citymart.model.cart.CartValue
import com.grocery_app.citymart.model.cart.Products
import com.grocery_app.citymart.model.coupon_code.Code
import com.grocery_app.citymart.model.homeCategory.MainCategory
import com.grocery_app.citymart.model.myProfile.User
import com.grocery_app.citymart.model.slider.SliderX

@Dao
interface Dao {

    //Home Main category Query
    @Query("SELECT * FROM `Category list`")
    fun getHomeCategory(): LiveData<List<MainCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( getNewsAlert:List<MainCategory>)

    @Query("DELETE FROM `Category list`")
    fun deleteAllRecords()


    //Cart length Query
    @Query("SELECT * FROM CartValue")
    fun getCartValue(): LiveData<CartValue>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCartValue( cartValue: CartValue)

    @Query("DELETE FROM CartValue")
    fun deleteCartValue()

    //Cart Product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCart(addCart: products)
//
    @Query("SELECT * FROM cartList")
    fun getCart():LiveData<List<products>>

    @Query("UPDATE cartList SET qty=:qty WHERE id = :productId ")
    fun updateCart(qty:String, productId:Int)

    @Query("DELETE FROM cartList WHERE  id=:id ")
    fun deleteCart( id:Int)

    @Query("DELETE FROM cartList ")
    fun deleteAllCart()


    //CouponApply
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun applyCoupon(addCoupon: Code)
    //
    @Query("SELECT * FROM couponlist")
    fun getCoupon():LiveData<Code>

    @Query("DELETE FROM CouponList")
    fun deleteCoupon()




    // get Profile

    @Query("SELECT * FROM userProfileInfo")
    fun getProfile(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile( user: User)

    @Query("DELETE FROM userProfileInfo")
    fun deleteProfile()


    //Search product
    @Query("SELECT * FROM products")
    fun getProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct( product: List<Product>)

    @Query("DELETE FROM products")
    fun deleteProducts()



    //Slidre data
    @Query("SELECT * FROM slider")
    fun getSlider(): LiveData<List<SliderX>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSlider( slider: List<SliderX>)

    @Query("DELETE FROM slider")
    fun deleteSlider()


}