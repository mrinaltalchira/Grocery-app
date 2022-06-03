package com.grocery_app.citymart.ui.activity.add_new_address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository

class AddNewAddressViewModelFactory(var repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddnewaddressViewModel::class.java)){
            AddnewaddressViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("View Model Not Found")
        }
    }
}