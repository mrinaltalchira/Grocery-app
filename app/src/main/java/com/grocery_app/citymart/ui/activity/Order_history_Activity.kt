package com.grocery_app.citymart.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.ActivityOrderHistoryBinding
import com.grocery_app.citymart.ui.fragment.order_history.CompleteOrderFragment
import com.grocery_app.citymart.ui.fragment.order_history.PendingOrderFragment
import com.grocery_app.citymart.viewPager.ViewPagerAdapter

class Order_history_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_history)
//        setViewPagerData()

    }

    private fun setViewPagerData() {

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(
            PendingOrderFragment(),
            "Pending"
        )
        viewPagerAdapter.addFragment(
            CompleteOrderFragment(),
            "Completed"
        )

        binding.tabViewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.tabViewPager)

    }
}