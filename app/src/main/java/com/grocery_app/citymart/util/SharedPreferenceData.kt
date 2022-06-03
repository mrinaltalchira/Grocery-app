package com.grocery_app.citymart.util

import android.content.Context

object SharedPreferenceData {

    fun setSharedPreference(context: Context, key: String, message: String) {
        val sharedPreferences = context.getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, message)
        editor.apply()

    }

    fun getSharedPreference(context: Context, key: String)
            : String? {
        val sharedPreferences = context.getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        var message = sharedPreferences.getString(key, " ")
        if (message.isNullOrBlank())
        {
            message="not found"
            return null
        }


        return message


    }

}