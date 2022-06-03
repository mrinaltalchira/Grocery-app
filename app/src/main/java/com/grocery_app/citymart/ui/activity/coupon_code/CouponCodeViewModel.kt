package com.grocery_app.citymart.ui.activity.coupon_code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.coupon_code.ApplyCoupon
import com.grocery_app.citymart.model.coupon_code.Code
import kotlinx.coroutines.*

class CouponCodeViewModel:ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val couponCodeList = MutableLiveData<List<Code>>()
    val couponCode = MutableLiveData<ApplyCoupon>()
    var job: Job? = null
    val successMsg=MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()
    fun getCouponCode() {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getCouponCode()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    couponCodeList.postValue(response.body()?.code)
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

    fun applyCoupon(userId:String,couponId:String){
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.applyCouponCode(userId, couponId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    couponCode.postValue(response.body())
                    successMsg.postValue(response.isSuccessful)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }
}