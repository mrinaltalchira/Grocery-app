package com.grocery_app.citymart.ui.activity.addressActivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.address.getAddress.Addres
import kotlinx.coroutines.*

class AddressActivityViewModel(var repository: Repository) : ViewModel() {
    var errorMsg = MutableLiveData<String>()
    var addressData = MutableLiveData<List<Addres>>()

    var loading = MutableLiveData<Boolean>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception Handled : ${throwable.localizedMessage}")
    }

    fun getAddress(userId: String) {
        loading.value = true
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            val responce = repository.getAllAddress(userId)
            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    addressData.postValue(responce.body()?.address)
                    Log.d("fun","${responce.body()?.address}")
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