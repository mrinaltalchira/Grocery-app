package com.grocery_app.citymart.ui.activity.add_new_address.manageAddress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository

class Create_New_Address_View_Model_factory(var repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CreateNewAddressViewModel::class.java)){
            CreateNewAddressViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("View Model Not Found")
        }

    }
}