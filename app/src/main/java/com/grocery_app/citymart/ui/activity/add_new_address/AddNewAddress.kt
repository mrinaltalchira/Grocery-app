package com.grocery_app.citymart.ui.activity.add_new_address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grocery_app.citymart.R


class AddNewAddress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_add_new_address)

        supportFragmentManager.beginTransaction().replace(R.id.frag_Add_new_Address,
            AddnewaddressFragment()
        ).commit()
 }
}