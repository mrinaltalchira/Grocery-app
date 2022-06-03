package com.grocery_app.citymart.ui.fragment.Search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.data.Repository

class SearchViewModelFactory(var repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}