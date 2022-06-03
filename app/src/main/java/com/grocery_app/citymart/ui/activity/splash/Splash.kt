package com.grocery_app.citymart.ui.activity.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.ApiParam
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.ui.activity.signin.SigninActivity
import java.util.*

class Splash : AppCompatActivity() {

    lateinit var imgSplash: ImageView
    lateinit var sharep: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val sharedPreferences = getSharedPreferences("lang", Context.MODE_PRIVATE)
        val message = sharedPreferences.getString("selectLang", "")

        Log.d("lang",message.toString())
        if (message != null) {
            ApiParam.lang=message
        }

        if(!message.isNullOrBlank())
        {
            setLocale(message)
        }

        imgSplash = findViewById(R.id.imgSplash)

        sharep = getSharedPreferences("UserExistOrNot", Context.MODE_PRIVATE)
        var isTrueOrNot: Boolean = sharep.getBoolean("Permission", false)
        val data=sharep.all

        imgSplash.alpha = 0f
        val intro=getSharedPreferences("intro",Context.MODE_PRIVATE)
        val introMessage=intro.getString("intro","")

        if (introMessage=="true")
        {
            imgSplash.animate().setDuration(1500).alpha(1f).withEndAction {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }

        }
        else

        {
            imgSplash.animate().setDuration(1500).alpha(1f).withEndAction {

                val intent = Intent(this, Splash2::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }

    }

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
    }
}