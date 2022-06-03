package com.grocery_app.citymart.ui.fragment.privacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.grocery_app.citymart.R

class PrivacyPolicy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        var web: WebView = findViewById(R.id.web)
        web.loadUrl("https://city-mart-privacy-policy.netlify.app/")


    }
}