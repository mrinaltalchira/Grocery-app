package com.grocery_app.citymart.ui.activity.main_activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grocery_app.citymart.*
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.ActivityMainBinding
import com.grocery_app.citymart.model.cart.CartValue
import com.grocery_app.citymart.ui.fragment.Search.SearchFragment
import com.grocery_app.citymart.ui.fragment.cart.CartFragment
import com.grocery_app.citymart.ui.fragment.home.HomeFragment
import com.grocery_app.citymart.ui.fragment.profile.ProfileFragment
import com.grocery_app.citymart.util.AddCartvalue
import com.grocery_app.citymart.util.CartItemClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AddCartvalue, CartItemClick {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var badgeDrawable: BadgeDrawable
    lateinit var appDatabaseObj: AppDatabase
    var count = 0
    lateinit var sharep: SharedPreferences
    lateinit var userId: String
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        sharep = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId", "").toString()

        val sharedPreference =  getSharedPreferences("intro",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("intro","true")
        editor.apply()


        binding.bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        badgeDrawable = binding.bottomNavBar.getOrCreateBadge(R.id.cartFragment)
        badgeDrawable.setVisible(true)
        badgeDrawable.backgroundColor = Color.parseColor("#44AC48")
        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(this)
        val cartlist = appDatabaseObj.getAppDao().getCart()
        cartlist.observe(this, Observer {
            badgeDrawable.number = it.size
        })

    }

    override fun onAddProduct() {
        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(this)
        val cartlist = appDatabaseObj.getAppDao().getCart()
        cartlist.observe(this, Observer {
            badgeDrawable.number = it.size
        })
        Toast.makeText(this, "Item Added to Cart", Toast.LENGTH_SHORT).show()

    }


    override fun deleteCartItem() {
    }


 private fun setContent(content: String) {
     setTitle(content)

 }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.homeFragment -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.mainActivityDefaultFragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.searchFragment -> {
                val fragment = SearchFragment()
                supportFragmentManager.beginTransaction().replace(R.id.mainActivityDefaultFragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.cartFragment -> {
                val fragment = CartFragment()
                supportFragmentManager.beginTransaction().replace(R.id.mainActivityDefaultFragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.profileFragment -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.mainActivityDefaultFragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}