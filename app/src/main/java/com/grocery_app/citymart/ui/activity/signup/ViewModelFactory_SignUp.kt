package com.grocery_app.citymart.ui.activity.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository

class ViewModelFactory_SignUp(private val repository: Repository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewModel_SignUp::class.java)) {
             ViewModel_SignUp(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}