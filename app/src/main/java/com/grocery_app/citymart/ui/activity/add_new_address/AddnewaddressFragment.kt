package com.grocery_app.citymart.ui.activity.add_new_address

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.Adapter_All_Address
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.AddnewaddressFragmentBinding
import com.grocery_app.citymart.ui.activity.add_new_address.manageAddress.Create_New_Address

class AddnewaddressFragment : Fragment() {

    companion object {
        fun newInstance() = AddnewaddressFragment()
    }
lateinit var userId:String
lateinit var sharep:SharedPreferences
    private var adapter = Adapter_All_Address()
    private lateinit var viewModel: AddnewaddressViewModel
    lateinit var binding: AddnewaddressFragmentBinding
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.addnewaddress_fragment,
            container,
            false
        )

        sharep = requireActivity().getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId","").toString()
        binding.rrAddAddress.setOnClickListener {
            val fragment = Create_New_Address()
            val bundle = Bundle()
            val fragmentTransaction: FragmentTransaction =
                activity?.getSupportFragmentManager()!!.beginTransaction()
            bundle.putBoolean("valueOne", true)
            fragment.setArguments(bundle)
            fragmentTransaction.replace(R.id.frag_Add_new_Address, fragment)
            fragmentTransaction.commit()

        }



        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }

        var retrofitService = RetrofitService.getInstance()
        var repository = Repository(retrofitService)
        viewModel = ViewModelProvider(this, AddNewAddressViewModelFactory(repository)).get(
            AddnewaddressViewModel::class.java)

    binding.rvAddressListOfAddNewAddress.adapter = adapter
        appDatabase = AppDatabase.getAppDBInstance(requireContext())

    viewModel.addressData.observe(this, Observer {

        adapter.setAddress(it,requireActivity())
    })


    viewModel.errorMsg.observe(this, Observer{
        Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_SHORT).show()
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