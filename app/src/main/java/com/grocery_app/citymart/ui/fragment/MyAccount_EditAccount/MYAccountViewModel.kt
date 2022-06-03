package com.grocery_app.citymart.ui.fragment.MyAccount_EditAccount

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.myProfile.GetProfileData
import com.grocery_app.citymart.model.myProfile.UpdateProfile
import com.grocery_app.citymart.model.signin.loginRespo
import com.grocery_app.citymart.model.uploadImage.UserImage
import com.grocery_app.citymart.model.userInfo.GetProfileInfo
import com.grocery_app.citymart.model.userInfo.User
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MYAccountViewModel : ViewModel() {

    var error = MutableLiveData<String>()
    var profileData = MutableLiveData<GetProfileData>()
    var profileImage = MutableLiveData<UserImage>()
    var errormsgUpdate = MutableLiveData<String>()
    var profileDataUpdate = MutableLiveData<UpdateProfile>()
    var job: Job? = null
    var jobUpdate: Job? = null
    var userInfo = MutableLiveData<User>()


    private var exceptionHandler = CoroutineExceptionHandler { _, throwable ->

        onError("Exception Handeled : ${throwable.localizedMessage}")
    }
    private var exceptionHandlerUpdate = CoroutineExceptionHandler { _, throwable ->

        onError("Exception Handeled : ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getProfileDetails(userId: String) {

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val respon = mainRepository.getUserInfo(userId)
            withContext(Dispatchers.Main) {
                if (respon.isSuccessful) {
                    userInfo.postValue(respon.body()?.user)
                    loading.value = false
                } else {
                    Log.d("data", respon.body()?.user.toString())
                    error("error ${respon.message()}")
                }
            }
        }
    }

    fun updateProfileDetails(userId: String, fullname: String, mobile: String) {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        jobUpdate = CoroutineScope(Dispatchers.Main + exceptionHandlerUpdate).launch {
            val respon = mainRepository.updateProfile(userId, fullname, mobile)
            withContext(Dispatchers.Main) {
                if (respon.isSuccessful) {
                    profileDataUpdate.postValue(respon.body())
                    Log.d("isSuccessfull", respon.body()?.user.toString())
                    loading.value = false
                } else {
                    Log.d("data", respon.body()?.user.toString())
                    error("error ${respon.message()}")
                }
            }
        }
    }

    fun uploadProfile(userId: String, image: String) {


        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        jobUpdate = CoroutineScope(Dispatchers.Main + exceptionHandlerUpdate).launch {
            val respon = mainRepository.uploadProfile(userId, image)
            withContext(Dispatchers.Main) {
                if (respon.isSuccessful) {
                    loading.value = false
                    profileImage.value = respon.body()
                    Log.d("image", respon.body().toString())
                } else {
                    error("error ${respon.message()}")
                }
            }
        }
    }


    private fun onError(errorMsg: String) {

        try {
            error.value = errorMsg
            Log.d("error", errorMsg.toString())
            loading.value = false
        } catch (e: Exception) {
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}