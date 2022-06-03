package com.grocery_app.citymart.ui.fragment.profile.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.R
import com.grocery_app.citymart.adapter.Adapter_OrderLIst
import com.grocery_app.citymart.databinding.FragmentProfileFragmentBinding
import com.grocery_app.citymart.databinding.FragmentViewMoreOrderhistoryBinding
import com.grocery_app.citymart.ui.fragment.profile.ProfileViewModel
import com.grocery_app.citymart.util.SharedPreferenceData

class ViewMore_orderhistory : Fragment() {
    lateinit var binding: FragmentViewMoreOrderhistoryBinding
    private lateinit var viewModel: ProfileViewModel
    private var adapterOrderlist= Adapter_OrderLIst()
    lateinit var sharep: SharedPreferences
    lateinit var userId:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_view_more_orderhistory,container,false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.recylerviewProfileFrag.adapter=adapterOrderlist

        binding.backBtn.setOnClickListener {requireActivity().onBackPressed()}

        sharep = requireActivity().getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId","").toString()

        val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")

        viewModel.cartItems.observe(this, Observer {
            adapterOrderlist.setCategoty(it,requireActivity())

        })

        if (userId != null) {
            viewModel.getCartItemList(userId)
        }

        return binding.root
    }
}