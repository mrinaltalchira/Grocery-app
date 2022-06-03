package com.grocery_app.citymart.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.ui.activity.signup.ViewModel_SignUp

class HomeViewModelFactory(private val repositorySignup: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            ViewModel_SignUp(this.repositorySignup) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}