package com.grocery_app.citymart.ui.activity.main_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.model.cart.CartValue
import kotlinx.coroutines.*

class MainViewModel:ViewModel() {

    var userId = ""
    val errorMessage = MutableLiveData<String>()
    val cartValue = MutableLiveData<CartValue>()
    var job: Job? = null
//    lateinit var count:String

//    val loading = MutableLiveData<Boolean>()
//    fun getCartValue() {
//        val retrofitService = RetrofitService.getInstance()
//        val mainRepository = Repository(retrofitService)
//
//        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
//            loading.postValue(true)
//            val response = mainRepository.getCartValue(userId)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    cartValue.postValue(response.body())
//                    count=response.body()!!.length
//                    loading.value = false
//                } else {
//                    onError("Error : ${response.message()} ")
//                }
//            }
//        }
//    }




//        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
//            loading.postValue(true)
//            val response = mainRepository.getCartValue(userId)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    cartValue.postValue(response.body())
//                    count=response.body()!!.length
//                    loading.value = false
//                } else {
//                    onError("Error : ${response.message()} ")
//                }
//            }
//        }



//    }

//    private fun onError(message: String) {
//        errorMessage.value = message
//        loading.value = false
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        job?.cancel()
//    }
//
//    override fun onAddProduct() {
//
//    }

}