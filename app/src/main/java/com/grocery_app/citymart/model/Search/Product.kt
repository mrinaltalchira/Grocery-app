package com.grocery_app.citymart.model.Search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 1,
    @ColumnInfo(name = "_id") val _id: String? = null,
    @ColumnInfo(name = "category") val category: String ? = null,
    @ColumnInfo(name = "desc") val desc: String ? = null,
    @ColumnInfo(name = "mrp") val mrp: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "price") val price: String? = null,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "productType") val productType: String? = null,
    @ColumnInfo(name = "quantity") val quantity: String? = null

)