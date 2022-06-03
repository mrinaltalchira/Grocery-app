package com.grocery_app.citymart.ui.fragment.EditProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository
import java.lang.IllegalArgumentException

class EditProfileVMFactory(var repository: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            EditProfileViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("View Model not Found")
        }
    }

}