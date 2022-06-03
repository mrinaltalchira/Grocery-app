package com.grocery_app.citymart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.grocery_app.citymart.adapter.Adapter_Alerts_News
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.ProductFragmentBinding
import com.grocery_app.citymart.model.homeCategory.MainCategory

class productFragment : Fragment() {
    lateinit var binding:ProductFragmentBinding
    var adapterCartProduct= Adapter_Alerts_News()

    companion object {
        fun newInstance() = productFragment()
    }

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(layoutInflater, R.layout.product_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTemp.setText("Mrinal")

        var data= mutableListOf<MainCategory>()
        var a:Int=0
        data.add(MainCategory(++a,"dl", "ashok","android"))
        data.add(MainCategory(++a,"dl", "ashok","android"))
        data.add(MainCategory(++a,"dl", "ashok","android"))
        data.add(MainCategory(++a,"dl", "ashok","android"))

//        binding.rvTemp.layoutManager=LinearLayoutManager(requireActivity())
        adapterCartProduct.setList(data,requireActivity())
        binding.rvTemp.adapter=adapterCartProduct


    }

}