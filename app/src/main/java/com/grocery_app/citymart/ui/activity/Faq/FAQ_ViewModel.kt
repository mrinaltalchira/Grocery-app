package com.grocery_app.citymart.ui.activity.Faq

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.address.getAddress.Addres
import com.grocery_app.citymart.model.faq.FAQ
import com.grocery_app.citymart.model.faq.FaqX
import kotlinx.coroutines.*

class FAQ_ViewModel(var repository: Repository) : ViewModel() {
    var errorMsg = MutableLiveData<String>()
    var faqData = MutableLiveData<List<FaqX>>()

    var loading = MutableLiveData<Boolean>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception Handled : ${throwable.localizedMessage}")
    }

    fun getFAQ() {
        loading.value = true
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            val responce = repository.getFaq()
            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    faqData.postValue(responce.body()?.faq)
                    loading.value = false
                } else {
                    onError("Error ${responce.message()}")
                }
            }
        }
    }


    private fun onError(error: String) {
        errorMsg.postValue(error)
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}