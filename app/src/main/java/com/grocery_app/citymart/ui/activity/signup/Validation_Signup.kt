package com.grocery_app.citymart.ui.activity.signup

import com.grocery_app.citymart.model.SignUp.UserRespo

object Validation_Signup{

    fun validate_Signup(userRespo: UserRespo):Boolean{
        if (userRespo.user!!.firstName!!.isNotEmpty()  && userRespo.user!!.lastName!!.isNotEmpty() && userRespo.user!!.email!!.isNotEmpty() && userRespo.user!!.password!!.isNotEmpty() && userRespo.user!!.country!!.isNotEmpty() && userRespo.user!!.mobile!!.isNotEmpty()){
            return false
        }

    return true}
}