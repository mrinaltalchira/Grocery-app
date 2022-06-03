package com.grocery_app.citymart.ui.activity.signup

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.SignUp.SignUpRespo
import com.grocery_app.citymart.model.SignUp.UserRespo
import kotlinx.coroutines.*

class ViewModel_SignUp(private val repositorySignup: Repository) : ViewModel() {

    var errorMsg = MutableLiveData<String>()
    lateinit var message: String

    var userResponse = MutableLiveData<SignUpRespo>()
    var job: Job? = null
    var userId : String? = null
lateinit var sharedPreferences: SharedPreferences
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")

    }


    val loading = MutableLiveData<Boolean>()

//    fun Signup_User(
//        firstname: String,
//        lastname: String,
//        email: String,
//        password: String,
//        country: String,
//        mobile: String,
//        context: Context
//    ): String {
//        job = CoroutineScope(Dispatchers.IO  + exceptionHandler).launch {
//            loading.postValue(true)
//            val respo =
//                repositorySignup.signUp_User(firstname, lastname, email, password, country, mobile)
//            withContext(Dispatchers.Main) {
//                if (respo.isSuccessful) {
//                   message= respo.message()
//                    userResponse.postValue(respo.body())
//
//                    Log.d("UserId", respo.body().toString())
//                    Toast.makeText(context, "${respo.body()?.message}", Toast.LENGTH_SHORT).show()
////                    userId  = respo.body()?.user?._id.toString()
//
//                    //624a7797f248ada33e1dc4d6
//                    loading.value = false
//                } else {
//                    Toast.makeText(context, "${respo.body()?.message}", Toast.LENGTH_SHORT).show()
//                    Log.d("userRespo", respo.body().toString())
//                    message = respo.message()
//                    onError("Error : ${respo.message()}")
//                }
//                Toast.makeText(context, "${respo.body()?.message}", Toast.LENGTH_SHORT).show()
//
//            }
//
//        }
//        return message
//    }





    private fun onError(error: String) {
        errorMsg.value = error
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}