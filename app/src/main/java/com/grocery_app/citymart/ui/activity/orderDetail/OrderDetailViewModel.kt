package com.grocery_app.citymart.ui.activity.orderDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.pastOrder.Order
import com.grocery_app.citymart.model.pastOrder.OrderX
import kotlinx.coroutines.*

class OrderDetailViewModel:ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val orderList = MutableLiveData<OrderX>()
    var job: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()
    fun getOrderId(orderId: String) {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getOrderDetail(orderId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    orderList.postValue(response.body()?.order)
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
    }}