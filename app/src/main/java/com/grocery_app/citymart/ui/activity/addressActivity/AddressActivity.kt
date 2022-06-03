package com.grocery_app.citymart.ui.activity.addressActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.adapter.AdapterSavedAddress
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.ActivityAddressBinding
import com.grocery_app.citymart.ui.activity.add_new_address.AddNewAddress
import com.grocery_app.citymart.ui.activity.add_new_address.manageAddress.Create_New_Address

class AddressActivity : AppCompatActivity() {

    lateinit var sharep:SharedPreferences
    private lateinit var adapter: AdapterSavedAddress
    private lateinit var viewModel: AddressActivityViewModel
    lateinit var binding:ActivityAddressBinding
     lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_address)

        var retrofitService = RetrofitService.getInstance()
        var repository = Repository(retrofitService)
        viewModel = ViewModelProvider(this, AddressActivityViewMFactory(repository)).get(
            AddressActivityViewModel::class.java)
        sharep = getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId","").toString()

        adapter = AdapterSavedAddress()
        binding.rvAddress.adapter = adapter

        viewModel.addressData.observe(this, Observer {
            if (it.size<=0)
            {
                binding.btnEditAddress.visibility=View.GONE
                binding.textView7.text="Add Address"
                binding.addNewAddress.visibility=View.VISIBLE
            }
            else
            {
                binding.btnEditAddress.visibility=View.VISIBLE
                binding.addNewAddress.visibility=View.GONE
                binding.textView7.text="Saved Address"

            }

            adapter.setAddress(it,this)
        })
        binding.btnEditAddress.setOnClickListener {
            val intent = Intent(this, AddNewAddress::class.java)
            startActivity(intent)
        }
        binding.addNewAddress.setOnClickListener {
            val intent = Intent(this, AddNewAddress::class.java)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }


        viewModel.errorMsg.observe(this, Observer{
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAddress(userId)
    }
}