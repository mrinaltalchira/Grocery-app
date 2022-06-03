package com.grocery_app.citymart.ui.activity.Faq

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository

class FAQ_VM_Factory (var repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FAQ_ViewModel::class.java)){
            FAQ_ViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("View Model Not Found")
        }
    }
}