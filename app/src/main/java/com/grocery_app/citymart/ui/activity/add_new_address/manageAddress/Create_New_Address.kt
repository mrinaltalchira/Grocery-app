package com.grocery_app.citymart.ui.activity.add_new_address.manageAddress

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.CreateNewAddressFragmentBinding
import com.grocery_app.citymart.ui.activity.add_new_address.AddNewAddress
import com.grocery_app.citymart.ui.activity.add_new_address.googleMap.MapActivity
import com.grocery_app.citymart.ui.fragment.cart.CartFragment

class Create_New_Address : Fragment() {

    companion object {
        fun newInstance() = Create_New_Address()
    }

    var valueOne: Boolean = false
    var valueTwo: Boolean = false
    var valueThree: Boolean? = null
    lateinit var userId: String

    lateinit var sharep: SharedPreferences
    lateinit var shareLocationBool: SharedPreferences
    var addressId: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var phone: String = ""
    var houseNumber: String? = null
    var city: String? = null
    var country: String? = null
    var postal: String? = null
    var instruction: String? = null


    lateinit var binding: CreateNewAddressFragmentBinding
    private lateinit var viewModel: CreateNewAddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.create_new_address_fragment,
            container,
            false
        )

        binding.backBtn.setOnClickListener { requireActivity().onBackPressed() }
        val retrofitService = RetrofitService.getInstance()
        val repository = Repository(retrofitService)

        viewModel = ViewModelProvider(this, Create_New_Address_View_Model_factory(repository)).get(
            CreateNewAddressViewModel::class.java)
        sharep = requireActivity().getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)

        val appDatabase = AppDatabase.getAppDBInstance(requireActivity())



        appDatabase.getAppDao().getProfile().observe(this,{
            try {
            binding.numberAddress.setText(it.mobile.toString())
            }
            catch (e:Exception)
            {


            }
        })

        userId = sharep.getString("userId", "").toString()
         firstName = sharep.getString("firstName", "").toString()
       lastName = sharep.getString("lastName", "").toString()
        phone = sharep.getString("phone", "").toString()

        binding.firstNameAddress.setText(firstName)
        binding.lastNameAddress.setText(lastName)

        binding.numberAddress.setText(phone)

        binding.addLocationEdit.setOnClickListener {
            var intent = Intent(requireActivity(), MapActivity::class.java)
            startActivity(intent)
        }

        var mBundle: Bundle? = Bundle()
        mBundle = arguments
        valueOne = mBundle!!.getBoolean("valueOne")

        var bundle: Bundle? = Bundle()
        mBundle = arguments
        valueTwo = mBundle!!.getBoolean("valueTwo")
        addressId = mBundle.getString("addId")
        houseNumber = mBundle.getString("houseNumber")
        city = mBundle.getString("city")
        country = mBundle.getString("country")
        postal = mBundle.getString("postal")
        instruction = mBundle.getString("instruction")


        if (valueTwo) {
            binding.houseAddresss.setText(houseNumber)
            binding.cityAddress.setText(city)
            binding.postalAddress.setText(postal)
            binding.countryAddress.setText(country)
            binding.instructionAddress.setText(instruction)
        }

        binding.saveAddressBtn.setOnClickListener {

            if (valueOne) {

                setAdd()
            }
            if (valueTwo) {
                updateAddress()
            }
        }


        return binding.root
    }




    fun setAdd() {

        var house = binding.houseAddresss.text.toString()
        var city = binding.cityAddress.text.toString()
        var postal = binding.postalAddress.text.toString()
        var country = binding.countryAddress.text.toString()
        var instruction = binding.instructionAddress.text.toString()
        var firstName = binding.firstNameAddress.text.toString()
        var LastName = binding.lastNameAddress.text.toString()
        var phone = binding.numberAddress.text.toString()

        if (house.isEmpty()) {
            binding.houseAddresss.error = "Empity Field is not allowed"
            return
        }
        if (city.isEmpty()) {
            binding.cityAddress.error = "Empity Field is not allowed"
            return
        }
        if (postal.isEmpty()) {
            binding.postalAddress.error = "Empity Field is not allowed"
            return
        }
        if (country.isEmpty()) {
            binding.countryAddress.error = "Empity Field is not allowed"
            return
        }
        if (firstName.isEmpty()) {
            binding.firstNameAddress.error = "Empity Field is not allowed"
            return
        }
        if (LastName.isEmpty()){
            binding.lastNameAddress.error = "Empity Field is not allowed"
            return
        }
        if (phone.isEmpty()) {
            binding.numberAddress.error = "Empity Field is not allowed"
            return
        }



        viewModel.AddressResponce.observe(this, Observer {
            Toast.makeText(requireActivity(), "added successfully", Toast.LENGTH_SHORT).show()

            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainActivityDefaultFragment,
                CartFragment())?.addToBackStack(null)?.commit()
        })
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE

            }
        })
        viewModel.setAddress(userId,house,city,postal,country,instruction,firstName,LastName,phone)

        val a = house
        var b = city
        var c = postal
        var d = country
        var e = instruction

            var share: SharedPreferences =
                context!!.getSharedPreferences("userAddress", Context.MODE_PRIVATE)
            val editor = share.edit()
            editor.putString("address", "${a} ${b} ${c} ${d} ${e}")
        editor.putString("nameOnAddress","${firstName}${ LastName}")
        editor.apply()

        requireActivity().onBackPressed()
    }



    fun updateAddress() {

        var house = binding.houseAddresss.text.toString()
        var city = binding.cityAddress.text.toString()
        var postal = binding.postalAddress.text.toString()
        var country = binding.countryAddress.text.toString()
        var instruction = binding.instructionAddress.text.toString()
        var firstName = binding.firstNameAddress.text.toString()
        var LastName = binding.lastNameAddress.text.toString()
        var phone = binding.numberAddress.text.toString()
        if (house.isEmpty()) {
            binding.houseAddresss.error = "Empity Field is not allowed"
            return
        }
        if (city.isEmpty()) {
            binding.cityAddress.error = "Empity Field is not allowed"
            return
        }
        if (postal.isEmpty()) {
            binding.postalAddress.error = "Empity Field is not allowed"
            return
        }
        if (country.isEmpty()) {
            binding.countryAddress.error = "Empity Field is not allowed"
            return
        }
        if (firstName.isEmpty()) {
            binding.firstNameAddress.error = "Empity Field is not allowed"
            return
        }
        if (LastName.isEmpty()){
            binding.lastNameAddress.error = "Empity Field is not allowed"
            return
        }
        if (phone.isEmpty()) {
            binding.numberAddress.error = "Empity Field is not allowed"
            return
        }
        viewModel.updateAddressResponce.observe(this, Observer {
            Toast.makeText(requireActivity(), "Updated successfully", Toast.LENGTH_SHORT).show()
        })
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_SHORT).show()
        }
        )

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE

            }
        })
        viewModel.updateAddress(addressId.toString(), house, city, postal, country, instruction,firstName,LastName,phone)

    }

    override fun onStart() {
        super.onStart()
        shareLocationBool =
            requireActivity().getSharedPreferences("shareLocationBool", Context.MODE_PRIVATE)
        valueThree = shareLocationBool.getBoolean("valueThree", false)

        val shareLocation: SharedPreferences =
            requireActivity().getSharedPreferences("LocationByMap", Context.MODE_PRIVATE)
        val locationBool = shareLocationBool.edit()
        locationBool.putBoolean("valueThree", false)
        locationBool.apply()

        if (valueThree as Boolean ) {
try {

    var houseNumb: String = shareLocation.getString("home", "").toString()
    var ci: String = shareLocation.getString("city", "").toString()
    var count: String = shareLocation.getString("country", "").toString()
    var post: String = shareLocation.getString("postal", "").toString()

    binding.houseAddresss.setText(houseNumb)
    binding.cityAddress.setText(ci)
    binding.postalAddress.setText(count)
    binding.countryAddress.setText(post)
    shareLocation.edit().clear().commit()
}catch (e:Exception){}

            binding.saveAddressBtn.setOnClickListener {
                setAdd()
                val locationBool = shareLocationBool.edit()
                locationBool.putBoolean("valueThree", false)
                locationBool.apply()
            }
        }

    }
}
