package com.grocery_app.citymart.ui.fragment.EditProfile

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
lateinit var binding: ActivityEditProfileBinding
    lateinit var viewModel: EditProfileViewModel
    lateinit var sharep: SharedPreferences
    lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val retrofitService: RetrofitService = RetrofitService.getInstance()
        var repo = Repository(retrofitService)
        viewModel = ViewModelProvider(this, EditProfileVMFactory(repo)).get(EditProfileViewModel::class.java)

        sharep = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId", "").toString()

        viewModel.getDetails(userId)


    }
}