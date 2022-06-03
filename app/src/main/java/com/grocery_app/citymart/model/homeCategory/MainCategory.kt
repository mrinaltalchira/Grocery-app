package com.grocery_app.citymart.model.homeCategory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category list")
data class MainCategory(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val idcrop: Int = 1,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "categoryType") val categoryType: String
)