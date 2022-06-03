package com.grocery_app.citymart.model.myProfile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userProfileInfo")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var idUser: Int = 1,
    @ColumnInfo(name = "fullName") var fullName: String ? = null,
    @ColumnInfo(name = "mobile") var mobile: String ? = null
)