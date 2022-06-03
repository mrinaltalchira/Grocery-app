package com.grocery_app.citymart.ui.activity.product_details

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.Adapter_productBycategory
import com.grocery_app.citymart.databinding.ActivityDetailProductBinding
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.util.SharedPreferenceData
import com.grocery_app.citymart.util.SweetAlert
import kotlinx.android.synthetic.main.customedialoug.view.*
import java.util.*

class DetailProductActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var productId: String
    var message: String = ""
    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var shareMartRestroTrueFalse : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shareMartRestroTrueFalse = getSharedPreferences("shareMartRestroTrueFalse",
            Context.MODE_PRIVATE)
        bindUIViews()

        val userId = SharedPreferenceData.getSharedPreference(this, "userId")

        productDetailViewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModelFactory()
            ).get(
                ProductDetailViewModel::class.java
            )

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.ivPlus.setOnClickListener {
            productDetailViewModel.increment()
            binding.tvCounter.setText(productDetailViewModel.count.toString())

        }

        binding.ivMinus.setOnClickListener {
            productDetailViewModel.decrement()
            binding.tvCounter.setText(productDetailViewModel.count.toString())
        }


        binding.btnAddToCart.setOnClickListener {
            SweetAlert.showDialog(this)
            addToCart()
        }


    }

    private fun addToCart() {
        val bundle = intent.extras
        productId = bundle?.getString("id").toString()
        var productName = bundle?.getString("title")
        var productImg = bundle?.getString("productimage")
        var productPrice = bundle?.getString("price")
        var productmrp = bundle?.getString("mrp")
        var productSize = bundle?.getString("size")
        var category = bundle?.getString("category")
        var productDesc = bundle?.getString("description")
        var productType = bundle?.getString("productType")

        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(this)
        val rnds = (10000..2000000).random()

            if (productType == "Mart") {
                var a = shareMartRestroTrueFalse.getString("isMart", "")

                if (a == "Mart" || a == "") {

                    appDatabaseObj.getAppDao().addCart(
                        products(
                            rnds,
                            productId,
                            category!!,
                            productDesc!!,
                            productImg!!,
                            productName!!,
                            productPrice!!,
                            productmrp.toString(),
                            productSize!!,
                            productDetailViewModel.count.toString(),
                            productType.toString()
                        ))
                    var editor = shareMartRestroTrueFalse.edit()
                    editor.putString("isMart", "Mart")
                    editor.apply()
                    Toast.makeText(this, "Item added to Cart", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    SweetAlert.hidediloag()

                    startActivity(intent)
                    finish()

                } else {
                    val view = View.inflate(this, R.layout.custome_dialoug_box_delete_previour_cart, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                    view.yes.setOnClickListener {
                        dialog.dismiss()
                        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(this)
                        shareMartRestroTrueFalse.edit().clear().apply()

                        appDatabaseObj.getAppDao().deleteAllCart()
                        appDatabaseObj.getAppDao().deleteCoupon()

                    SweetAlert.hidediloag()
                    }
                    view.no.setOnClickListener {
                        dialog.dismiss()
                        Toast.makeText(this, "cancel request", Toast.LENGTH_SHORT).show()
                    SweetAlert.hidediloag()
                    }

                }


            }

            if (productType == "Restaurant") {

                var a = shareMartRestroTrueFalse.getString("isMart", "")

                if (a == "Restaurant" || a == "") {

                    appDatabaseObj.getAppDao().addCart(
                        products(
                            rnds,
                            productId,
                            category!!,
                            productDesc!!,
                            productImg!!,
                            productName!!,
                            productPrice!!,
                            productmrp.toString(),
                            productSize!!,
                            productDetailViewModel.count.toString(),
                            productType.toString()
                        ))

                    var editor = shareMartRestroTrueFalse.edit()
                    editor.putString("isMart", "Restaurant")
                    editor.apply()
                    Toast.makeText(this, "Item added to Cart", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    SweetAlert.hidediloag()

                    startActivity(intent)
                    finish()
                } else {
                    val view = View.inflate(this, R.layout.custome_dialoug_box_delete_previour_cart, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                    view.yes.setOnClickListener {
                        dialog.dismiss()
                        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(this)
                        shareMartRestroTrueFalse.edit().clear().apply()

                        appDatabaseObj.getAppDao().deleteAllCart()
                        appDatabaseObj.getAppDao().deleteCoupon()

                        SweetAlert.hidediloag()
                    }
                    view.no.setOnClickListener {
                        dialog.dismiss()
                        Toast.makeText(this, "cancel request", Toast.LENGTH_SHORT).show()
                        SweetAlert.hidediloag()
                    }
                    SweetAlert.hidediloag()

                    return

                }
            }


    }


    private fun bindUIViews() {

        val bundle = intent.extras
        productId = bundle?.getString("id").toString()
        var productName = bundle?.getString("title")
        var productImg = bundle?.getString("productimage")
        var productPrice = bundle?.getString("price")
        var productmrp = bundle?.getString("mrp")
        var productSize = bundle?.getString("size")
        var category = bundle?.getString("category")
        var productDesc = bundle?.getString("description")


        binding.tvTitleProductDetail.text = productName
        binding.tvPriceProductdetail.text = "₹" + productPrice
        binding.tvMrpProductdetail.text = "₹" + productmrp
        binding.tvMrpProductdetail.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding.tvQuntityProductdetail.text = productSize
        binding.tvDescrptionProductDetail.text = productDesc
        Glide.with(this).load(productImg).into(binding.ivProductDetail)


    }


}