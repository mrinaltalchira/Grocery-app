package com.grocery_app.citymart.ui.activity.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.grocery_app.citymart.R
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.smarteist.autoimageslider.SliderPager


class Splash2 : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash2)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) actionBar.hide()
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )


        setNextArrowColor(Color.parseColor("#328541"))
        showSkipButton(true)
        selectedIndicatorColor=Color.parseColor("#328541")
        unselectedIndicatorColor=Color.GRAY
        setColorSkipButton(Color.parseColor("#328541"))
        setColorSkipButton(Color.parseColor("#328541"))
        setColorDoneText(Color.parseColor("#328541"))
        setBarColor(Color.parseColor("#D2FBBB"))

        addSlide(
            AppIntroFragment.newInstance(
                "Order Food",
                "Now you can order food & grocery\n" +
                        "any time right from your mobile",
                R.drawable.intro1,
                Color.parseColor("#D2FBBB"),
                Color.parseColor("#328541"),
                Color.parseColor(
                    "#328541"
                )
            )

        )
        addSlide(
            AppIntroFragment.newInstance(
                "Free delivery",
                "Order your favorite meals will be\n" +
                        "immediately and first three orders\n" +
                        "delivery free ",
                R.drawable.intro2,
                Color.parseColor("#D2FBBB"),
                Color.parseColor("#328541"),
                Color.parseColor("#328541")
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                "Discounts",
                "Get attractive discount on grocery\n" +
                        "and mart.",
                R.drawable.intro3,
                Color.parseColor("#D2FBBB"),
                Color.parseColor("#328541"),
                Color.parseColor("#328541")

            )
        )


    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun setColorDoneText(@ColorInt colorDoneText: Int) {
        val doneText = findViewById<View>(R.id.done) as TextView
        doneText.setTextColor(colorDoneText)
    }

}