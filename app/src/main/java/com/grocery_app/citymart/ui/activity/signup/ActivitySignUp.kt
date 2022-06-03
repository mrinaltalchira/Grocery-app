package com.grocery_app.citymart.ui.activity.signup

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.ActivitySignUpBinding
import com.grocery_app.citymart.model.SignUp.SignUpRespo
import com.grocery_app.citymart.model.cart.AddtoCartProduct
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.ui.activity.signin.SigninActivity
import com.grocery_app.citymart.util.SweetAlert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivitySignUp : AppCompatActivity() {
    lateinit var viewModel: ViewModel_SignUp
    lateinit var binding: ActivitySignUpBinding
    lateinit var sharePrefone: SharedPreferences
    lateinit var sharePreTwo: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val model: ViewModel_SignUp by viewModels()
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory_SignUp(mainRepository)
            ).get(ViewModel_SignUp::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        sharePrefone = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        sharePreTwo = getSharedPreferences("UserExistOrNot", Context.MODE_PRIVATE)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }



        binding.btnLogin.setOnClickListener {


            var name = binding.edFirstname.text.toString()
            var lastName = binding.edLastname.text.toString()
            var email = binding.edEmail.text.toString()
            var country = binding.edCountry.text.toString()
            var m_number = binding.edMobileNumber.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (name.isEmpty()) {
                binding.edFirstname.error = "Empity Field is not allowed"
                return@setOnClickListener
            }
            if (lastName.isEmpty()) {
                binding.edLastname.error = "Empity Field is not allowed"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                binding.edEmail.error = "Empity Field is not allowed"
                return@setOnClickListener
            }
            if (!email.matches(emailPattern.toRegex())) {
                binding.edEmail.error = "Email pattern does not matched"
                return@setOnClickListener
            }
            if (country.isEmpty()) {
                binding.edCountry.error = "Empity Field is not allowed"
                return@setOnClickListener
            }
            if (m_number.isEmpty()) {
                binding.edMobileNumber.error = "Empity Field is not allowed"
                return@setOnClickListener
            } else {
                SweetAlert.showDialog(this@ActivitySignUp)
                val retrofitService = RetrofitService.getInstance()
                val mainRepository = Repository(retrofitService)


                val call: Call<SignUpRespo> =
                    mainRepository.signUp_User(name, lastName, email, country, m_number)
                call.enqueue(object : Callback<SignUpRespo> {
                    override fun onResponse(
                        call: Call<SignUpRespo>,
                        response: Response<SignUpRespo>
                    ) {

                        if (response.isSuccessful) {

                            val respo = response.body()


                            SweetAlert.hidediloag()
                            if (respo != null) {
                                if (respo.message == "Success") {
                                    val editor = sharePrefone.edit()
                                    editor.putString("userId", respo!!.user._id.toString())
                                    editor.putString("firstName", respo.user!!.firstName.toString())
                                    editor.putString("lastName", respo.user!!.lastName.toString())
                                    editor.putString("phone", respo.user!!.mobile.toString())
                                    editor.apply()
                                    val editortwo = sharePreTwo.edit()
                                    editortwo.putBoolean("Permission", true)
                                    editortwo.apply()
                                    var intent =
                                        Intent(this@ActivitySignUp, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    Toast.makeText(
                                        this@ActivitySignUp,
                                        "User ${response.body()?.message.toString()}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        SweetAlert.hidediloag()


                    }

                    override fun onFailure(call: Call<SignUpRespo>, t: Throwable) {
                        SweetAlert.hidediloag()
                        Log.d("Error", t.toString())

                    }
                })



                viewModel.userResponse.observe(this, Observer {
                    Log.d("userRespo", it.toString())

                })

                viewModel.loading.observe(this, Observer {
                    if (it) {
                        binding.progressDialog.visibility = View.VISIBLE
                    } else {
                        binding.progressDialog.visibility = View.GONE
                    }
                })
//                viewModel.Signup_User(name, lastName, email, password, country, m_number,this)
            }
        }

    }


}