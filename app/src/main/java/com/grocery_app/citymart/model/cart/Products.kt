package com.grocery_app.citymart.model.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartProduct")
data class Products(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val idcrop: Int = 1,
    @ColumnInfo(name = "cartId")   val _id: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "desc") val desc: String,
//    @ColumnInfo(name = "image") val image: Image,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "productType") val productType: String,
    @ColumnInfo(name = "quantity") val quantity: String
)