package com.grocery_app.citymart.ui.fragment.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grocery_app.citymart.R
import kotlinx.android.synthetic.main.activity_order_placed.*

class OrderPlacedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        back_to_home.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return inflater.inflate(R.layout.fragment_order_placed, container, false)
    }

}