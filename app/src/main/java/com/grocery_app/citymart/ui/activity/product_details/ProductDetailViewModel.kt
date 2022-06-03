package com.grocery_app.citymart.ui.activity.product_details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.cart.AddtoCartProduct
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailViewModel(


    ) : ViewModel() {
    var count = 1
    var errorMsg = MutableLiveData<String>()
    var message = MutableLiveData<String>()
    var userResponse = MutableLiveData<AddtoCartProduct>()
    val loading = MutableLiveData<Boolean>()

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")

    }

    fun increment() {
        count++

    }

    fun decrement() {
        if (count > 1) {
            count--
        }


    }

    fun addToCart(

    ) {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        loading.postValue(true)
        val call: Call<AddtoCartProduct> =
            mainRepository.addtoCart("","", count.toString())
        call.enqueue(object : Callback<AddtoCartProduct> {
            override fun onResponse(
                call: Call<AddtoCartProduct>,
                response: Response<AddtoCartProduct>
            ) {
                if (response.isSuccessful) {

                    userResponse.postValue(response.body())
                    message.postValue(response.body()?.message.toString())

                    loading.value = false
                }
                loading.value = false

            }

            override fun onFailure(call: Call<AddtoCartProduct>, t: Throwable) {
                Log.d("Error", t.toString())

                onError("Error : $t")
            }
        })


    }

    private fun onError(error: String) {
        errorMsg.value = error

        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}