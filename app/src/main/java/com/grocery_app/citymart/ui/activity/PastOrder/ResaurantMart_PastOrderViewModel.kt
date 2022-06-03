package com.grocery_app.citymart.ui.activity.PastOrder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.coupon_code.ApplyCoupon
import com.grocery_app.citymart.model.coupon_code.Code
import com.grocery_app.citymart.model.pastOrder.Order
import kotlinx.coroutines.*

class ResaurantMart_PastOrderViewModel:ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val orderList = MutableLiveData<List<Order>>()
    var job: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()
    fun getPastorder(orderType:String,userId: String) {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getPastOrderList(orderType,userId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    orderList.postValue(response.body()?.orders)
                    loading.value = false
                } else {
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