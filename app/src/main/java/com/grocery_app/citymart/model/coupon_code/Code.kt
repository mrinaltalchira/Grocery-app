package com.grocery_app.citymart.model.coupon_code

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CouponList")
data class Code(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val idcrop: Int = 1,
    @ColumnInfo(name = "couponId")  val _id: String,
    @ColumnInfo(name = "code")val code: String,
    @ColumnInfo(name = "desc")val desc: String,
    @ColumnInfo(name = "discount")val discount: String,
    @ColumnInfo(name = "fixedAmount")val fixedAmount: String,
    @ColumnInfo(name = "title")val title: String
)