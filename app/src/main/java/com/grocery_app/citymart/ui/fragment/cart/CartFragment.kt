package com.grocery_app.citymart.ui.fragment.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.Adapter_cart_Item
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.CartFragmentBinding
import com.grocery_app.citymart.model.cart.CartData
import com.grocery_app.citymart.model.cart.OrderCheckout
import com.grocery_app.citymart.model.order.OrderProduct
import com.grocery_app.citymart.ui.activity.add_new_address.AddNewAddress
import com.grocery_app.citymart.ui.activity.add_new_address.manageAddress.CreateNewAddressViewModel
import com.grocery_app.citymart.ui.activity.add_new_address.manageAddress.Create_New_Address
import com.grocery_app.citymart.ui.activity.coupon_code.CouponCodeActivity
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.ui.activity.mart.Checkout
import com.grocery_app.citymart.ui.activity.signin.SigninActivity
import com.grocery_app.citymart.ui.fragment.home.HomeFragment
import com.grocery_app.citymart.ui.fragment.restaurant_mart.RestautantMartFragment
import com.grocery_app.citymart.util.CartItemClick
import com.grocery_app.citymart.util.SharedPreferenceData
import kotlinx.android.synthetic.main.customedialoug.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment(), Adapter_cart_Item.CartItemClick {
    var itemList = mutableListOf<CartData>()
    var itemTotalAmount = 0.0
    var deliveryCharges = 40.0
    var discountAmount = 0.0
    var discountPercentage = 0.0
    var fixedAmount = 0.0
    var message = ""
    lateinit var sharep: SharedPreferences
    lateinit var userId: String
    var grandTotalAmount = 0.0
    val liveGrandTotalAmount = MutableLiveData<String>()
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    lateinit var radioButtonMain: RadioButton
    lateinit var shareAddress: SharedPreferences
    var address: String? = null
    lateinit var sweetAlertDialog: SweetAlertDialog
    var checkdata=0
    lateinit var shareMartRestroTrueFalse : SharedPreferences

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CartViewModel

    var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapterCartItem = Adapter_cart_Item(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = CartFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharep = requireActivity().getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId", "").toString()
        shareAddress = requireActivity().getSharedPreferences("userAddress", Context.MODE_PRIVATE)

        sweetAlertDialog = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        sweetAlertDialog.titleText = getString(R.string.loading)
        shareMartRestroTrueFalse = requireActivity().getSharedPreferences("shareMartRestroTrueFalse",
            Context.MODE_PRIVATE)


        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemTotalAmount = 0.0
        grandTotalAmount = 0.0
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        radioButtonMain = binding.cashondelivery

        binding.rvCart.adapter = adapterCartItem

        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
        val cartlist = appDatabaseObj.getAppDao().getCart()
        cartlist.observe(this, Observer {
            itemTotalAmount = 0.0
            grandTotalAmount = 0.0
            discountAmount = 0.0
            adapterCartItem.setCategoty(it)
            if (it.isNullOrEmpty()) {
                binding.emptyCartLayout.visibility = View.VISIBLE
                binding.nestedscrollViewRvCart.visibility = View.GONE
                binding.tvClearCart.visibility = View.GONE


            } else {
                binding.nestedscrollViewRvCart.visibility = View.VISIBLE
                binding.emptyCartLayout.visibility = View.GONE
                binding.tvClearCart.visibility = View.VISIBLE

                val couponData = appDatabaseObj.getAppDao().getCoupon()
                couponData.observe(this, Observer {
                    if (it != null) {

                        if (it.fixedAmount.toInt() <= itemTotalAmount.toInt()) {
                            binding.appledCouponLyout.visibility = View.VISIBLE
                            binding.llPromocode.visibility = View.GONE
                            binding.tvOffPercentage.text =
                                it.discount + "% OFF applied!"
                            binding.tvDiscountOffer.text =
                                it.discount + "% OFF applied!"
                            discountAmount = itemTotalAmount / 100 * it.discount.toInt()

                            itemTotalAmount -= discountAmount
                            grandTotalAmount = itemTotalAmount + deliveryCharges
                            discountPercentage = it.discount.toDouble()

                            binding.tvGrandTotal.text = "Rs. " + grandTotalAmount.toInt().toString()
                            binding.btnPrice.text = ("Rs.${grandTotalAmount.toInt()}")

                            binding.tvDiscountPrice.text = "Rs." + discountAmount.toInt().toString()


                        } else {
                            appDatabaseObj.getAppDao().deleteCoupon()
                            discountAmount = 0.0
                            binding.tvDiscountOffer.text = ""
                            binding.appledCouponLyout.visibility = View.GONE
                            binding.llPromocode.visibility = View.VISIBLE

                        }

                    }


                })


            }
            for (add in 0..it.size - 1) {
                itemTotalAmount += it[add].qty.toInt() * it[add].price.toInt()
            }
            binding.tvSubTotalOrderSummary.text = "Rs. " + itemTotalAmount.toString()

            if (itemTotalAmount < 400) {
                deliveryCharges = 40.0

                binding.tvDeliveryFee.text = deliveryCharges.toString()
            } else {
                deliveryCharges = 0.0
                binding.tvDeliveryFee.text = deliveryCharges.toString()
            }
            itemTotalAmount -= discountAmount
            grandTotalAmount = itemTotalAmount + deliveryCharges

            binding.tvGrandTotal.text = "Rs. " + grandTotalAmount.toString()
            binding.btnPrice.text = ("Rs.${grandTotalAmount.toInt()}")

            binding.tvDiscountPrice.text = "Rs." + discountAmount.toString()


        })



        if (userId != null) {
            viewModel.userId = userId
        }


        val data = MutableLiveData<String>()
        data.postValue(binding.tvSubTotalOrderSummary.text.toString())


        if (itemTotalAmount > fixedAmount) {
            discountAmount = itemTotalAmount / 100 * discountPercentage

            grandTotalAmount = itemTotalAmount - discountAmount


        }

        binding.tvGrandTotal.text = ("Rs.${grandTotalAmount.toInt()}")
        binding.btnPrice.text = ("Rs.${grandTotalAmount.toInt()}")
        binding.tvSubTotalOrderSummary.text = "Rs. ${itemTotalAmount.toInt().toString()}"
        binding.tvDeliveryFee.setText("Rs." + deliveryCharges.toInt())
        binding.tvDiscountPrice.text = "Rs. ${discountAmount.toInt()}"

        binding.tvClearCart.setOnClickListener {
            clearCart()
        }

        binding.tvCouponcodeCart.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.mainActivityDefaultFragment,
                CouponCodeActivity()
            )?.addToBackStack(null)?.commit()
        }

        binding.appledCouponLyout.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.mainActivityDefaultFragment,
                CouponCodeActivity()
            )?.addToBackStack(null)?.commit()
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            radioButtonMain = activity!!.findViewById(i)


        }




        binding.btnPrice.setOnClickListener {
            binding.btnCheckout.performClick()

        }

        binding.rlAddrssSaved.setOnClickListener {

            if (userId.isNotEmpty()) {

                val intent = Intent(requireActivity(), AddNewAddress::class.java)
                startActivity(intent)

            } else {
                var intent = Intent(
                    requireActivity(),
                    SigninActivity::class.java
                )
                startActivity(intent)
            }
        }


        binding.rlAddnewAddress.setOnClickListener {


            if (userId.isNotEmpty()) {

                val bundle = Bundle()
                bundle.putBoolean("valueOne", true)
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.mainActivityDefaultFragment,
                    Create_New_Address::class.java, bundle
                )?.addToBackStack(null)?.commit()

            } else {

                var intent = Intent(
                    requireActivity(),
                    SigninActivity::class.java
                )
                startActivity(intent)
            }

        }


    }

    fun payByOthers(address: String) {
        val intent = Intent(
            requireContext(),
            Checkout::class.java
        )
        val amount = grandTotalAmount.toInt().toString()
        intent.putExtra("amount", amount)
        intent.putExtra("name", "Ashok kumawat")
        intent.putExtra("email", "MobileNo")
        intent.putExtra("address", address)

        shareMartRestroTrueFalse.edit().clear().apply()
        shareAddress.edit().clear().commit()
        startActivity(intent)

    }

    fun cashOndelivery(address: String) {
        sweetAlertDialog.show()
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN

        val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")
        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
        val cartData = appDatabaseObj.getAppDao().getCart()
        cartData.observe(this, Observer {
            var orderProducts: OrderProduct
            orderProducts =
                OrderProduct(
                    address,
                    deliveryCharges.toString(),
                    grandTotalAmount.toString(),
                    radioButtonMain.text.toString(),
                    it,
                    itemTotalAmount.toString(),
                    discountAmount.toString(),
                    discountPercentage.toInt().toString()
                )

            if (userId != null && checkdata==0) {


//                viewModel.orderCheckout(
//                    userId, orderProducts, requireContext()
//                )
                val retrofitService = RetrofitService.getInstance()
                val mainRepository = Repository(retrofitService)
//                loading.postValue(true)
                val call: Call<OrderCheckout> = mainRepository.orderCheckout(userId, orderProducts)
                call.enqueue(object : Callback<OrderCheckout> {
                    override fun onResponse(
                        call: Call<OrderCheckout>,
                        response: Response<OrderCheckout>
                    ) {
                        if (response.isSuccessful) {
                            val message = response.body()?.message
//                            orderCheckoutMessage = response.message()
//                            loading.value = false
                            if (response.message() == "Created") {
                                val activity: Activity? = activity
                                if (activity != null) {
                                    val intent =
                                        Intent(requireContext(), OrderPlacedActivity::class.java)

                                    shareMartRestroTrueFalse.edit().clear().apply()
                                    shareAddress.edit().clear().apply()
                                    sweetAlertDialog.dismiss()
                                    checkdata=1

                                    startActivity(intent)
                                    activity.finish()
                                }


                            }
                            else
                            {
                            sweetAlertDialog.dismiss()
                                Toast.makeText(
                                    requireActivity(),
                                    "Your oder couldn't be created",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                        else
                        {
                            Toast.makeText(
                                requireActivity(),
                                "Your oder couldn't be created",
                                Toast.LENGTH_SHORT
                            ).show()
                        sweetAlertDialog.dismiss()
                        }


                    }

                    override fun onFailure(call: Call<OrderCheckout>, t: Throwable) {
                        Log.d("Error", t.toString())
                        Toast.makeText(
                            requireActivity(),
                            "Your oder couldn't be created",
                            Toast.LENGTH_SHORT
                        ).show()
                        sweetAlertDialog.dismiss()

                    }
                })


            }


        })
//        sweetAlertDialog.dismiss()


    }


    private fun clearCart() {
        val view = View.inflate(context, R.layout.customedialoug, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        view.yes.setOnClickListener {
            dialog.dismiss()
            val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
            shareMartRestroTrueFalse.edit().clear().apply()

            appDatabaseObj.getAppDao().deleteAllCart()
            appDatabaseObj.getAppDao().deleteCoupon()

            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        view.no.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context, "cancel request", Toast.LENGTH_SHORT).show()
        }
    }

    override fun changeQty(
        qty: String,
        productId: Int
    ) {

        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
        val cartlist = appDatabaseObj.getAppDao().updateCart(qty, productId)
        itemTotalAmount = 0.0
        discountAmount = 0.0

        val couponData = appDatabaseObj.getAppDao().getCoupon()
        couponData.observe(this, Observer {
            if (it != null) {
                if (it.fixedAmount.toInt() >= 0) {
                    binding.appledCouponLyout.visibility = View.VISIBLE
                    binding.llPromocode.visibility = View.GONE
                    binding.tvOffPercentage.text =
                        it.discount + "% OFF applied!"
                    binding.tvDiscountOffer.text =
                        it.discount + "% OFF applied!"
                    discountAmount = itemTotalAmount / 100 * it.discount.toInt()

                    itemTotalAmount -= discountAmount
                    grandTotalAmount = itemTotalAmount + deliveryCharges

                    binding.tvGrandTotal.text = "Rs. " + grandTotalAmount.toString()

                    binding.tvDiscountPrice.text = "Rs." + discountAmount.toString()


                } else {
                    binding.appledCouponLyout.visibility = View.GONE
                    binding.llPromocode.visibility = View.VISIBLE

                }

            }


        })


    }

    override fun deleteCartItem(cartId: String, position: Int) {
        (requireActivity() as CartItemClick).deleteCartItem()
        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
        appDatabaseObj.getAppDao().deleteCart(position)


    }

    override fun onStart() {
        super.onStart()

        var addressss = shareAddress.getString("address", "").toString()
        var name = shareAddress.getString("nameOnAddress", "").toString()
        this.address = addressss

        if (addressss.isNotBlank()) {
            binding.tvAddress.setText(address)
            binding.nameOnAddress.setText(name)
        }


        binding.btnCheckout.setOnClickListener {
            val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")

            if (SharedPreferenceData.getSharedPreference(requireActivity(), "userId")
                    .isNullOrEmpty()
            ) {
                var intent = Intent(context, SigninActivity::class.java)
                startActivity(intent)
            }

            if (this.address.isNullOrEmpty()){
                if (this.userId.isNotEmpty()) {

                    val intent = Intent(requireActivity(), AddNewAddress::class.java)
                    startActivity(intent)

                } else {
                    var intent = Intent(
                        requireActivity(),
                        SigninActivity::class.java
                    )
                    startActivity(intent)
                }
            }
            else {


                bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }

        }

        binding.ivClose.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

        }
        binding.rlRl.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.rvCart.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.confirmbtn.setOnClickListener {
            val cash_on_delivery: String = getString(R.string.cash_on_delivery)
            val pay_by_others: String = getString(R.string.pay_by_others)
            Log.d("radio", radioButtonMain.text.toString())
            when (radioButtonMain.text) {
                cash_on_delivery -> cashOndelivery(addressss)
                pay_by_others -> payByOthers(addressss)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}