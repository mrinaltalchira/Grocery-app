package com.grocery_app.citymart.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.model.Search.Product
import com.grocery_app.citymart.model.cart.CartValue
import com.grocery_app.citymart.model.coupon_code.Code
import com.grocery_app.citymart.model.homeCategory.MainCategory
import com.grocery_app.citymart.model.myProfile.User
import com.grocery_app.citymart.model.slider.SliderX


@Database(entities = [MainCategory::class, CartValue::class, User::class, Product::class, SliderX::class,products::class,Code::class], version = 6, exportSchema = false)
//@Database(entities = [New::class,Crop::class,Data::class, Scheme::class,Roomdata::class], version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAppDao(): Dao

    companion object {
        private var dbInstance: AppDatabase?= null

        fun getAppDBInstance(context: Context): AppDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "APP_DB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbInstance!!
        }
    }

}