package com.grocery_app.citymart.model.slider

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "slider")
data class SliderX(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 1,
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "title") val title: String
)