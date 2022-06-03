package com.grocery_app.citymart.ui.activity.add_new_address.manageAddress

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.address.Address
import com.grocery_app.citymart.model.address.setAddress.SetAddressX
import kotlinx.coroutines.*

class CreateNewAddressViewModel(val repository: Repository): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val AddressResponce = MutableLiveData<SetAddressX>()
    val updateAddressResponce = MutableLiveData<Address>()
    var job:Job? = null


    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("exceptionHandler ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()
    fun setAddress(userId:String,houseNumber:String,city:String,postal:String,country:String,instruction:String,firstName: String,lastName: String, mobile: String){
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = repository.setAddress(userId,houseNumber,city,postal,country,instruction,firstName,lastName,mobile)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    AddressResponce.postValue(response.body())
                    loading.value = false
                }else{
                    onError("Error : ${response.message()}")

                }
            }
        }


    }

    fun updateAddress(id:String,houseNumber:String,city:String,postal:String,country:String,instruction:String,firstName: String,lastName: String, mobile: String){
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = repository.updateAdd(id,houseNumber,city,postal,country,instruction,firstName,lastName,mobile)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    updateAddressResponce.postValue(response.body()?.address)
Log.d("xxx","${response.body()?.address}")
                    loading.value = false
                }else{
                    onError("Error : ${response.message()}")

                }
            }
        }


    }

    private fun onError(error: String) {

        errorMessage.value = error
        loading.value = false
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}