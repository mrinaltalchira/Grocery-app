package com.grocery_app.citymart.ui.fragment.main_category

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.model.cart.AddtoCartProduct
import com.grocery_app.citymart.model.subCategory.MatchedCategory
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainCategoryViewModel(


    ) : ViewModel() {
    var categoryName = ""
    var maincategoryName = ""
    val count="1"
    var errorMsg = MutableLiveData<String>()
    var message = MutableLiveData<String>()
    var userResponse = MutableLiveData<AddtoCartProduct>()








    val errorMessage = MutableLiveData<String>()
    val homeCategoryList = MutableLiveData<List<MatchedCategory>>()
    val productItemList = MutableLiveData<List<products>>()
    val appDatabaseobj = AppDatabase
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getSubCategory() {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getSubCategory(maincategoryName)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    homeCategoryList.postValue(response.body()?.matched)
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

    fun getProduct()
    {
        val retrofitService= RetrofitService.getInstance()
        val repository= Repository(retrofitService)
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = repository.getAllProduct(categoryName)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    productItemList.postValue(response.body()?.products)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }


}