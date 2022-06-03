package com.grocery_app.citymart.ui.fragment.cart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import kotlinx.android.synthetic.main.activity_order_placed.*

class OrderPlacedActivity : AppCompatActivity() {
   lateinit var shareMartRestroTrueFalse : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)
        back_to_home.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            shareMartRestroTrueFalse = getSharedPreferences("shareMartRestroTrueFalse",
                Context.MODE_PRIVATE)
            shareMartRestroTrueFalse.edit().clear().apply()
            val appDatabaseObj: AppDatabase =
                AppDatabase.getAppDBInstance(this)
            appDatabaseObj.getAppDao().deleteAllCart()
            finish()

        }
    }
}