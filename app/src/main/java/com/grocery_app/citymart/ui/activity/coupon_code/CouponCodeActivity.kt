package com.grocery_app.citymart.ui.activity.coupon_code

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.Adapter_Coupon_code
import com.grocery_app.citymart.databinding.ActivityCouponCodeBinding
import com.grocery_app.citymart.model.coupon_code.Code
import com.grocery_app.citymart.ui.fragment.cart.CartFragment
import com.grocery_app.citymart.ui.fragment.restaurant_mart.RestautantMartFragment
import com.grocery_app.citymart.util.SharedPreferenceData

class CouponCodeActivity : Fragment(), Adapter_Coupon_code.ClickCouponCode {
    lateinit var couponCodeViewModel: CouponCodeViewModel
    private var adapterCouponCode = Adapter_Coupon_code(this)


    lateinit var binding: ActivityCouponCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.activity_coupon_code, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        couponCodeViewModel = ViewModelProvider(this).get(CouponCodeViewModel::class.java)
        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()


        }



        binding.rvCoupon.adapter = adapterCouponCode
        val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")
        couponCodeViewModel.couponCodeList.observe(this, Observer {
            adapterCouponCode.setList(it, userId, requireActivity())
        })
        couponCodeViewModel.getCouponCode()
    }

    override fun applyCoupon(id: Code) {
        val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")
        var itemTotalAmount = 0.0


//        if (userId != null) {
//            couponCodeViewModel.applyCoupon(userId,id)
//        }

        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
        val cartlist = appDatabaseObj.getAppDao().getCart()
        cartlist.observe(this, Observer {
            for (add in 0..it.size - 1) {
                itemTotalAmount += it[add].qty.toInt() * it[add].price.toInt()
            }

            if (itemTotalAmount >= id.fixedAmount.toInt()) {
                appDatabaseObj.getAppDao().deleteCoupon()
                appDatabaseObj.getAppDao().applyCoupon(id)

//                var bundle = bundleOf(
//
//                    "discount" to id.discount.toString(),
//                    "fixedAmount" to id.fixedAmount.toString(),
//                    "newPrice" to id.title.toString(),
//                    "message" to "Applied"
//                )

                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainActivityDefaultFragment,
                    CartFragment())?.addToBackStack(null)?.commit()
            }
            else  {
                val sweetAlertDialog =
                    SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE)
                sweetAlertDialog.setTitle("Coupon is applicable only on above ${id.fixedAmount}")

                sweetAlertDialog.confirmButtonBackgroundColor = Color.parseColor("#328541")
                sweetAlertDialog.show()

            }
        })

//        couponCodeViewModel.errorMessage.observe(this, Observer {
//            Toast.makeText(requireActivity(), "error:$it", Toast.LENGTH_SHORT).show()
//        })
    }

    override fun applyMessage(message: String) {


    }
}