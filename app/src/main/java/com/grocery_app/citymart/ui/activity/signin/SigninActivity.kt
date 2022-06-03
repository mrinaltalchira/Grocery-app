package com.grocery_app.citymart.ui.activity.signin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.SigninActivityBinding
import com.grocery_app.citymart.model.Otp.GetOtp
import com.grocery_app.citymart.model.signin.loginRespo
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.ui.activity.signin.ui.ViewModelFactory_SignIn
import com.grocery_app.citymart.ui.activity.signin.ui.signinViewModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninActivity : AppCompatActivity() {


    object SavedPreference {

        const val EMAIL= "email"
        const val USERNAME="username"

        private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        private fun  editor(context: Context, const:String, string: String){
            getSharedPreference(
                context
            )?.edit()?.putString(const,string)?.apply()
        }

        fun getEmail(context: Context)= getSharedPreference(
            context
        )?.getString(EMAIL,"")

        fun setEmail(context: Context, email: String){
            editor(
                context,
                EMAIL,
                email
            )
        }

        fun setUsername(context: Context, username:String){
            editor(
                context,
                USERNAME,
                username
            )
        }

        fun getUsername(context: Context) = getSharedPreference(
            context
        )?.getString(USERNAME,"")

    }



    lateinit var viewModel: signinViewModel
    lateinit var binding: SigninActivityBinding
    val retrofitService = RetrofitService.getInstance()
    val mainRepository = Repository(retrofitService)
    lateinit var sharePrefone: SharedPreferences
    lateinit var sharePreTwo: SharedPreferences
 var token = ""
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    val firebaseAuth= FirebaseAuth.getInstance()

    lateinit var sweetAlertDialog: SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        binding = DataBindingUtil.setContentView(this, R.layout.signin_activity)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)


        binding.btnGoogle.setOnClickListener {
            signInGoogle()

        }


        sharePrefone = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        sharePreTwo = getSharedPreferences("UserExistOrNot", Context.MODE_PRIVATE)


        viewModel = ViewModelProvider(this, ViewModelFactory_SignIn(mainRepository)).get(
            signinViewModel::class.java
        )

        binding.backBtn.setOnClickListener {
            onBackPressed()

        }
        binding.btnSendOtp.setOnClickListener {

            var numstr = binding.edtphone.text
            if (numstr?.length != 10) {
                binding.edtPhoneLayout.setError("Please Enter valid Number")
                return@setOnClickListener
            } else {
                sweetAlertDialog.show()
                sendOtp()
            }

        }

    }

    private fun sendOtp() {

        val retrofitService = RetrofitService.getInstanceOtp()
        val mainRepository = Repository(retrofitService)
        var phoneNumber = binding.edtphone.text.toString()
        var apiKey = "6f5b5d9a-c61a-11ec-9c12-0200cd936042"
        var call: retrofit2.Call<GetOtp> = mainRepository.getOtp(apiKey, phoneNumber)
        call.enqueue(object : Callback<GetOtp> {
            override fun onResponse(
                call: retrofit2.Call<GetOtp>,
                response: Response<GetOtp>
            ) {
                token = response.body()?.Details.toString()
                val intent = Intent(this@SigninActivity, Otp_Activity::class.java)
                intent.putExtra("token", token)
                intent.putExtra("phoneNumber", phoneNumber)
                sweetAlertDialog.dismiss()

                startActivity(intent)
                finish()
            }

            override fun onFailure(call: retrofit2.Call<GetOtp>, t: Throwable) {
                sweetAlertDialog.dismiss()
                Toast.makeText(
                    this@SigninActivity,
                    "Please enter correct number",
                    Toast.LENGTH_SHORT
                ).show()


            }
        })
    }

    private fun signInGoogle() {
        val signInIntent:Intent=mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)

    }



    private fun loginUser(email: String) {
        sweetAlertDialog.show()

        if (email != null) {
            val retrofitService = RetrofitService.getInstance()
            val mainRepository = Repository(retrofitService)
            val call: Call<loginRespo> =
                mainRepository.googleSignin_User(email)
            call.enqueue(object : Callback<loginRespo> {
                override fun onResponse(
                    call: Call<loginRespo>,
                    response: Response<loginRespo>
                ) {

                    if (response.isSuccessful) {


                        val respo = response.body()
                        Log.d("data", respo.toString())
                        Log.d("dataerror", response.message().toString())
                        val editor = sharePrefone.edit()
                        editor.putString("userId", respo?.user?._id.toString())
                        editor.putString("phone", respo?.user?.mobile.toString())
                        editor.apply()
                        val editortwo = sharePreTwo.edit()
                        editortwo.putBoolean("Permission", true)
                        editortwo.apply()
                        sweetAlertDialog.dismiss()

                        var intent = Intent(this@SigninActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()


                    }
                    sweetAlertDialog.dismiss()


                }

                override fun onFailure(call: Call<loginRespo>, t: Throwable) {

                    sweetAlertDialog.dismiss()
                    Log.d("Error", t.toString())

                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e:ApiException){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                if(GoogleSignIn.getLastSignedInAccount(this)!=null){
                    val firebaseUser = firebaseAuth.currentUser
                    if (firebaseUser != null) {
                        val userId = firebaseUser.uid
                        val userEmail = firebaseUser.email
//                            val userEmail = firebaseUser.email

                        val editor = sharePrefone.edit()
                        editor.putString("userId", userEmail)
                        editor.apply()

                        Log.d("btaobhai", "this is $userEmail")
                    }

                    val editortwo = sharePreTwo.edit()
                    editortwo.putBoolean("Permission", true)
                    editortwo.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
//                SavedPreference.setEmail(this,account.email.toString())
//                SavedPreference.setUsername(this,account.displayName.toString())
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            val editortwo = sharePreTwo.edit()
            editortwo.putBoolean("Permission", true)
            editortwo.apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }

}