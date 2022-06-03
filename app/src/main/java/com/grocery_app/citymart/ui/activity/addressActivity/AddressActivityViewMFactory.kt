package com.grocery_app.citymart.ui.activity.addressActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository

class AddressActivityViewMFactory(var repository: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddressActivityViewModel::class.java)){
            AddressActivityViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("View Model Not Found")
        }
    }
}