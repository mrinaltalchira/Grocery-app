package com.grocery_app.citymart.ui.fragment.order_history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grocery_app.citymart.R

class CompleteOrderFragment : Fragment() {

    companion object {
        fun newInstance() = CompleteOrderFragment()
    }

    private lateinit var viewModel: CompleteOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.complete_order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompleteOrderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}