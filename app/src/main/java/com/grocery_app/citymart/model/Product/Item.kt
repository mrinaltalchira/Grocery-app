package com.grocery_app.citymart.model.Product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartList")
data class products(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val idcrop: Int = 1,
    @ColumnInfo(name = "productId")   val _id: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "image" ,defaultValue = "img") val image: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "mrp") val mrp: String,
    @ColumnInfo(name = "quantity") val quantity: String,
    @ColumnInfo(name = "qty",  defaultValue = "1") val qty: String="1",
    @ColumnInfo(name = "productType") val productType: String
)