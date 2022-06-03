package com.grocery_app.citymart.ui.fragment.EditProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.myProfile.GetProfileData
import kotlinx.coroutines.*

class EditProfileViewModel(val repository: Repository):ViewModel() {

    var image:String? = null
    var message:String? = null
    var job: Job? = null
    val userDetails = MutableLiveData<GetProfileData>()
    val errorMsg = MutableLiveData<String>()

    private  var exception = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handler : ${throwable.localizedMessage}")
    }
    private fun onError(error: String) {
     errorMsg.value = error
    }
    fun getDetails(userID:String){
        job = CoroutineScope(Dispatchers.IO + exception).launch {
            val respo = repository.getProfileDetails(userID)
            withContext(Dispatchers.IO){
                if (respo.isSuccessful){
                    message = "Successfull"
                     userDetails.value = respo.body()

                }else{
                    message = "Failed"
                    onError("Error : ${respo.message()}")
                }
            }
        }
    }
}