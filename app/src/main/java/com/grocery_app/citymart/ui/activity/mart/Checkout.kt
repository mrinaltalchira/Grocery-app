package com.grocery_app.citymart.ui.activity.mart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.databinding.ActivityCheckoutBinding
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.ui.fragment.cart.OrderPlacedActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class Checkout : AppCompatActivity(), PaymentResultListener {
    private var amount=0
    private lateinit var address:String
    lateinit var bindig:ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       bindig= DataBindingUtil.setContentView(this,R.layout.activity_checkout)
        amount= intent.getStringExtra("amount").toString().toInt()
        address= intent.getStringExtra("address").toString()

        startPayment()

        bindig.button.performClick()

        bindig.button.setOnClickListener {

        }
    }


    private fun startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
        */
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_iE8gouxJlMVlQz")

        try {
            val options = JSONObject()
            options.put("name","Ashok")
            options.put("description","City mart shopping charges")
            //You can omit the image option to fetch the image from dashboard
//            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#328541")
            options.put("currency","INR")
//            options.put("order_id", "order_DBJOWzybf0sJbb")
            options.put("amount",amount*100)//pass amount in currency subunits

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email","ashok.kumawat@iskylar.in")
            prefill.put("contact","9887275527")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        // this method is called on payment success.


        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(this)
        appDatabaseObj.getAppDao().deleteAllCart()
        appDatabaseObj.getAppDao().deleteCoupon()
        var shareMartRestroTrueFalse : SharedPreferences
        shareMartRestroTrueFalse = getSharedPreferences("shareMartRestroTrueFalse",
            Context.MODE_PRIVATE)
        shareMartRestroTrueFalse.edit().clear().apply()


            val intent=Intent(this, OrderPlacedActivity::class.java)
            startActivity(intent)
            finish()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed " , Toast.LENGTH_SHORT).show()

        val sweetAlert=SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
        sweetAlert.setCanceledOnTouchOutside(false)
        sweetAlert.setTitle("Payment Failed")
        sweetAlert.confirmButtonBackgroundColor=Color.GREEN


        sweetAlert.setConfirmClickListener {
            finish()
        }
        sweetAlert.show()
        finish()
    }



}