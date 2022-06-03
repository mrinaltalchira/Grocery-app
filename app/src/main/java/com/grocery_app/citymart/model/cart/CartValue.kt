package com.grocery_app.citymart.model.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CartValue")
data class CartValue(
    @PrimaryKey() @ColumnInfo(name = "id") val idcrop: Int = 1,
    @ColumnInfo(name = "cartValue") val length: String
)