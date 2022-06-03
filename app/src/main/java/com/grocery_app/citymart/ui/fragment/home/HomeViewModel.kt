package com.grocery_app.citymart.ui.fragment.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.homeCategory.MainCategory
import com.grocery_app.citymart.model.slider.SliderX
import kotlinx.coroutines.*

class HomeViewModel() : ViewModel() {
    //    lateinit var  mainRepository: Repository
    val errorMessage = MutableLiveData<String>()
    val homeCategoryList = MutableLiveData<List<MainCategory>>()
    val sliderList = MutableLiveData<List<SliderX>>()
    val appDatabaseobj = AppDatabase
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getHomeCategory() {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getHomeCategory()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    homeCategoryList.postValue(response.body()?.mainCategory)
                    loading.value = false
                } else {
                    Log.d("data", response.body().toString())
                    onError("Error : ${response.message()} ")
                }
            }

        }

    }




    fun getSlider() {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getSlider()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    sliderList.postValue(response.body()?.slider)
                    loading.value = false
                } else {
                    Log.d("data", response.body().toString())
                    onError("Error : ${response.message()} ")
                }
            }

        }

    }


    private fun onError(message: String) {
try {
        errorMessage.value = message
        loading.value = false
    return
}catch (e:Exception){}
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}