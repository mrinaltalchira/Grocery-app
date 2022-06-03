package com.grocery_app.citymart.ui.fragment.Search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.Search.Product
import kotlinx.coroutines.*

class SearchViewModel(var repository: Repository) : ViewModel(){

    var errorMsg = MutableLiveData<String>()
    var searchResult = MutableLiveData<List<Product>>()
    var loading = MutableLiveData<Boolean>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception Handled : ${throwable.localizedMessage}")
    }

    fun searchAdd(name: String,max:Int,min:Int) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val responce = repository.searchProduct(name,max,min)
            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    searchResult.postValue(responce.body()?.product)
                    Log.d("fun","${responce.body()}")
                    loading.value = false
                } else {
                    onError("Error ${responce.message()}")
                }
            }
        }
    }

    private fun onError(error: String) {
        errorMsg.postValue(error)

        try {
            loading.value = false
        }catch (e:Exception){

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}