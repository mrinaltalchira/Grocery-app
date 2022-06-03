package com.grocery_app.citymart.ui.fragment.cart

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.cart.CartData
import com.grocery_app.citymart.model.cart.GetCartItems
import com.grocery_app.citymart.model.cart.OrderCheckout
import com.grocery_app.citymart.model.cart.updateCart.UpdateCartQty
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Activity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.model.order.OrderProduct
import org.json.JSONArray


class CartViewModel : ViewModel() {
    var userId = ""
    val errorMessage = MutableLiveData<String>()
    val cartItems = MutableLiveData<List<CartData>>()
    var job: Job? = null
    val retrofitService = RetrofitService.getInstance()
    val mainRepository = Repository(retrofitService)
    var clearCartResponse = MutableLiveData<String>()
    var updateData = MutableLiveData<UpdateCartQty>()
    var orderCheckoutMessage = ""
    lateinit var context: Context


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()
    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    fun orderCheckout(
        userId: String,
        orderProduct: OrderProduct,
        context: Context
    ) {
        this.context = context


        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)
        loading.postValue(true)
        val call: Call<OrderCheckout> = mainRepository.orderCheckout(userId, orderProduct)
        call.enqueue(object : Callback<OrderCheckout> {
            override fun onResponse(
                call: Call<OrderCheckout>,
                response: Response<OrderCheckout>
            ) {
                if (response.isSuccessful) {
                    val message = response.body()?.message
                    orderCheckoutMessage = response.message()
                    loading.value = false
                    if (orderCheckoutMessage == "Created") {
                        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(context)
                        appDatabaseObj.getAppDao().deleteAllCart()
                    }
                }
                loading.value = false

            }

            override fun onFailure(call: Call<OrderCheckout>, t: Throwable) {
                Log.d("Error", t.toString())

                onError("Error : $t")
            }
        })


    }


}