package com.grocery_app.citymart.ui.activity.signin.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.signin.loginRespo
import kotlinx.coroutines.*

class signinViewModel( val repository: Repository) : ViewModel() {

    var userResponse = MutableLiveData<loginRespo>()
    var errormsg = MutableLiveData<String>()

    var message = "Default"
    var job:Job? = null
    private  val exceptionHandler = CoroutineExceptionHandler { _,throwable ->
        onError("Exception Handled : ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()
//    fun signin_user(email: String,
//                    password: String):String{
//        Log.d("viewModel", "in VM before Function")
//        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//            loading.postValue(true)
//            val respo = repository.signin_User(email, password)
//            withContext(Dispatchers.Main) {
//                if (respo.isSuccessful){
//            Log.d("viewModel", "${respo.body().toString()}")
//                    message = "successful"
//                   var a = userResponse.postValue(respo.body())
//                    Log.d("hhhh", "$a")
//                    loading.value = false
//                }else {
//                    Log.d("nhi hua kya", respo.toString())
//                    message = "Failed_in"
//                    onError("Error : ${respo.message()}")
//                }
//
//            }
//
//        }
//        return message
//    }

    private fun onError(error: String) {
        errormsg.value = error
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}