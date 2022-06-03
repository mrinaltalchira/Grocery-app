package com.grocery_app.citymart.ui.activity.signin

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.database.DatabaseUtils
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.ActivityOtpBinding
import com.grocery_app.citymart.model.Otp.GetOtp
import com.grocery_app.citymart.model.signin.loginRespo
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.util.SmsBroadcastReciver
import com.grocery_app.citymart.util.SweetAlert
import kotlinx.android.synthetic.main.activity_otp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Otp_Activity : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    private var smsBroadcastReciver: SmsBroadcastReciver? = null
    private val REQ_USER = 200
    lateinit var sweetAlertDialog:SweetAlertDialog


    lateinit var sharePrefone: SharedPreferences
    lateinit var sharePreTwo: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sweetAlertDialog=SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
//        pDialog.titleText =context.getString(R.string.loading)
        sweetAlertDialog.setCancelable(false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)

        binding.etOt1.addTextChangedListener(GenericTextWatcher(binding.etOt1, binding.etOt2))
        binding.etOt2.addTextChangedListener(GenericTextWatcher(binding.etOt2, binding.etOt3))
        binding.etOt3.addTextChangedListener(GenericTextWatcher(binding.etOt3, binding.etOt4))
        binding.etOt4.addTextChangedListener(GenericTextWatcher(binding.etOt4, binding.etOt5))
        binding.etOt5.addTextChangedListener(GenericTextWatcher(binding.etOt5, binding.etOt6))
        binding.etOt6.addTextChangedListener(GenericTextWatcher(binding.etOt6, null))

        //GenericKeyEvent here works for deleting the element and to switch back to previous EditText
        //first parameter is the current EditText and second parameter is previous EditText
        binding.etOt1.setOnKeyListener(GenericKeyEvent(binding.etOt1, null))
        binding.etOt2.setOnKeyListener(GenericKeyEvent(binding.etOt2, binding.etOt1))
        binding.etOt3.setOnKeyListener(GenericKeyEvent(binding.etOt3, binding.etOt2))
        binding.etOt4.setOnKeyListener(GenericKeyEvent(binding.etOt4, binding.etOt3))
        binding.etOt5.setOnKeyListener(GenericKeyEvent(binding.etOt5, binding.etOt4))
        binding.etOt6.setOnKeyListener(GenericKeyEvent(binding.etOt6, binding.etOt5))
        startSmartUserConsent()
        binding.btnLogin.setOnClickListener {
            val sweetAlertDialog=SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
            sweetAlertDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
//        pDialog.titleText =context.getString(R.string.loading)
            sweetAlertDialog.setCancelable(false)
            sweetAlertDialog.show()

            sharePrefone = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
            sharePreTwo = getSharedPreferences("UserExistOrNot", Context.MODE_PRIVATE)
            val retrofitService = RetrofitService.getInstanceOtp()
            val mainRepository = Repository(retrofitService)

            var otp =
                et_Ot1.text.toString() + et_Ot2.text.toString() + et_Ot3.text.toString() + et_Ot4.text.toString() + et_Ot5.text.toString() + et_Ot6.text.toString()
            var apiKey = "6f5b5d9a-c61a-11ec-9c12-0200cd936042"
            var sessionKey: String? = intent.getStringExtra("token")


            if (otp.length == 6) {
                var call = mainRepository.matchOtp(apiKey, sessionKey.toString(), otp)
                call.enqueue(object : Callback<GetOtp> {
                    override fun onResponse(
                        call: Call<GetOtp>,
                        response: Response<GetOtp>
                    ) {
                        Log.d("otpMatch", response.body().toString())
                        if (response.isSuccessful && response.body()?.Details.toString() == "OTP Matched") {



                            userLogin()
                            sweetAlertDialog.dismiss()
                        } else {
                            sweetAlertDialog.dismiss()
                            Toast.makeText(
                                this@Otp_Activity,
                                "OTP verification failed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        sweetAlertDialog.dismiss()
                    }

                    override fun onFailure(call: Call<GetOtp>, t: Throwable) {
                        sweetAlertDialog.dismiss()
                        Toast.makeText(this@Otp_Activity, "OTP Missmatch", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
        }

    }

    private fun userLogin() {
        sweetAlertDialog.show()
        var strphone: String? = intent.getStringExtra("phoneNumber")
        if (strphone!=null){
            val retrofitService = RetrofitService.getInstance()
            val mainRepository = Repository(retrofitService)
            val call: Call<loginRespo> =
                mainRepository.signin_User(strphone)
            call.enqueue(object : Callback<loginRespo> {
                override fun onResponse(
                    call: Call<loginRespo>,
                    response: Response<loginRespo>
                ) {

                    if (response.isSuccessful) {


                        val respo=response.body()
                        Log.d("data",respo.toString())
                        Log.d("dataerror",response.message().toString())
                        val editor = sharePrefone.edit()
                        editor.putString("userId", respo?.user?._id.toString())
                        editor.putString("phone", respo?.user?.mobile.toString())
                        editor.apply()
                        val editortwo = sharePreTwo.edit()
                        editortwo.putBoolean("Permission", true)
                        editortwo.apply()
                        sweetAlertDialog.dismiss()

                            var intent = Intent(this@Otp_Activity, MainActivity::class.java)
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


        if (requestCode == REQ_USER) {

            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                getOtpFromMessage(message)
            }
        }

    }

    private fun startSmartUserConsent() {
        val clint = SmsRetriever.getClient(this)
        clint.startSmsUserConsent(null)
    }

    private fun getOtpFromMessage(message: String?) {
        val otpPatter = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPatter.matcher(message)
        var code = ""
        while (matcher.find()) {
            code = matcher.group(0)
        }
        et_Ot1.setText(code[0].toString())
        et_Ot2.setText(code[1].toString())
        et_Ot3.setText(code[2].toString())
        et_Ot4.setText(code[3].toString())
        et_Ot5.setText(code[4].toString())
        et_Ot6.setText(code[5].toString())

        if (code.length == 6) {
            binding.btnLogin.performClick()
        }

        Log.d("otp3", code)
    }

    fun registerBroadcastReciver() {
        smsBroadcastReciver = SmsBroadcastReciver()
        smsBroadcastReciver!!.smsBroadcastReciverListner =
            object : SmsBroadcastReciver.SmsBroadcastReciverListner {
                override fun onSuccedd(intent: Intent?) {
                    startActivityForResult(intent, REQ_USER)
                }

                override fun onFailure() {

                }


            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReciver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReciver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReciver)
    }
}

class GenericKeyEvent internal constructor(
    private val currentView: EditText,
    private val previousView: EditText?
) : View.OnKeyListener {

    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.et_Ot1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }


}

class GenericTextWatcher internal constructor(
    private val currentView: View,
    private val nextView: View?
) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
        val text = editable.toString()
        when (currentView.id) {
            R.id.et_Ot1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_Ot2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_Ot3 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_Ot4 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_Ot5 -> if (text.length == 1) nextView!!.requestFocus()
            //You can use EditText6 same as above to hide the keyboard
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int,

        ) { // TODO Auto-generated method stub
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO Auto-generated method stub
    }

}