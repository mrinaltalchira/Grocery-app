package com.grocery_app.citymart.ui.fragment.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.cart.CartData
import com.grocery_app.citymart.model.cart.GetCartItems
import com.grocery_app.citymart.model.order.GetOrderList
import com.grocery_app.citymart.model.order.Order
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val cartItems = MutableLiveData<List<com.grocery_app.citymart.model.pastOrder.Order>>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()


    fun getCartItem(str:String,userId:String) {

        loading.value = true
        val retrofitService= RetrofitService.getInstance()
        val repository= Repository(retrofitService)
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            val response = repository.getPastOrderList(str,userId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cartItems.postValue(response.body()?.orders)
                    loading.value = false
                } else {
                    loading.value = false
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun getCartItemList(userId:String) {

        loading.value = true
        val retrofitService= RetrofitService.getInstance()
        val repository= Repository(retrofitService)
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            val response = repository.orderList(userId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cartItems.postValue(response.body()?.orders)
                    loading.value = false
                } else {
                    loading.value = false
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}