package com.grocery_app.citymart.ui.fragment.MyAccount_EditAccount

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.databinding.ActivityMyaccountBinding
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import kotlinx.android.synthetic.main.activity_myaccount.*

class MYAccountActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyaccountBinding
    lateinit var appDatabase: AppDatabase

    companion object {
        fun newInstance() = MYAccountViewModel()
    }

    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var sharep: SharedPreferences
    lateinit var userId: String
    var name: String? = null
    var phone: String? = null
    lateinit var viewModel: MYAccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myaccount)

        sharep = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId", "").toString()
        appDatabase = AppDatabase.getAppDBInstance(this)
        viewModel = ViewModelProvider(this).get(MYAccountViewModel::class.java)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }


        viewModel.userInfo.observe(this, Observer {
            if (it.image != null){
                    Glide.with(this).load(it.image).placeholder(R.drawable.profilebackground).into(binding.imgProfile)
                }
            try {
                binding.tvUserNameProsonalInfo.setText(it.fullName.toString())
                binding.tvPhoneNoProsonalInfo.setText(it.mobile.toString())

            }catch (e:Exception){  }

        })

        viewModel.getProfileDetails(userId)


        tv_logout.setOnClickListener {
            val preferences = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
            var shareAddress = getSharedPreferences("userAddress", Context.MODE_PRIVATE)
            preferences.edit().clear().commit()
            shareAddress.edit().clear().commit()
            val appDatabase: AppDatabase
            appDatabase = AppDatabase.getAppDBInstance(this)
            appDatabase.getAppDao().deleteProfile()
            appDatabase.getAppDao().deleteCoupon()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        binding.tvUserNameProsonalInfo.isEnabled = false
        binding.tvPhoneNoProsonalInfo.isEnabled = false
        binding.ivEditName.setOnClickListener {
            binding.tvUserNameProsonalInfo.isEnabled = true
            binding.tvPhoneNoProsonalInfo.isEnabled = true
            binding.ivEditName.visibility = View.GONE
            binding.ivEditPhone.visibility = View.GONE
            if (binding.ivEditName.visibility == View.GONE && binding.ivEditPhone.visibility == View.GONE) {
                binding.btnSave.visibility = View.VISIBLE
                binding.btnSave.setOnClickListener {
                    updateCredentials()
                    binding.tvUserNameProsonalInfo.isEnabled = false
                    binding.tvPhoneNoProsonalInfo.isEnabled = false
                    binding.btnSave.visibility = View.GONE
                    binding.ivEditName.visibility = View.VISIBLE
                    binding.ivEditPhone.visibility = View.VISIBLE
                }

            }
        }
        binding.ivEditPhone.setOnClickListener {
            binding.ivEditName.performClick()
        }
        binding.btnSave.setOnClickListener {
            binding.btnSave.performClick()
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }


        img_ProfileUpload.setOnClickListener {
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        appDatabase.getAppDao().getProfile().observe(this, {
            try {
                binding.tvUserNameProsonalInfo.setText(it.fullName.toString())
                binding.tvPhoneNoProsonalInfo.setText(it.mobile.toString())
            } catch (e: Exception) {

            }
        })

    }

    fun updateCredentials() {

        val name = binding.tvUserNameProsonalInfo.text.toString().trim()
        val phone = binding.tvPhoneNoProsonalInfo.text.toString().trim()
        if (!name.isBlank() && !phone.isBlank()) {

            viewModel.updateProfileDetails(userId, name, phone)
            viewModel.profileDataUpdate.observe(this, Observer {
                Log.d("personal", it.toString())

                appDatabase.getAppDao().deleteProfile()
                appDatabase.getAppDao().insertProfile(it.user)

            })

            viewModel.error.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

            })
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            Glide.with(this).load(imageUri).placeholder(R.drawable.profilebackground)
                .dontAnimate().into(binding.imgProfile)
            uploadImage(imageUri)
        }
    }

    private fun uploadImage(imageUri: Uri?) {
        if (imageUri != null) {
            val fileName = userId.toString() +".jpg"

            val database = FirebaseDatabase.getInstance()
            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(imageUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                           viewModel.uploadProfile(userId,imageUrl)

                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }

    }


}