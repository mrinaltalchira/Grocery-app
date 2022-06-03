package com.grocery_app.citymart.ui.fragment.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.AdapterSlider
import com.grocery_app.citymart.adapter.adapter_home_category
import com.grocery_app.citymart.databinding.HomeFragmentBinding
import com.grocery_app.citymart.ui.fragment.restaurant_mart.RestautantMartFragment
import com.grocery_app.citymart.util.SharedPreferenceData

class HomeFragment : Fragment() {
    private  var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabaseobj: AppDatabase
    private var adapter = adapter_home_category()
    private var adapterSlider = AdapterSlider()
    var count = 0

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),arrayOf( Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabaseobj = AppDatabase.getAppDBInstance(requireContext())

        binding.rvMainCategory.adapter = adapter
        binding.imageSlider.setSliderAdapter(adapterSlider)
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.startAutoCycle()


        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val listrd = appDatabaseobj.getAppDao().getHomeCategory().observe(this, Observer {
            adapter.setCategoty(it,requireActivity())
        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.homeCategoryList.observe(this, Observer {
            appDatabaseobj.getAppDao().deleteAllRecords()
            appDatabaseobj.getAppDao().insertAll(it)
            adapter.setCategoty(it,requireActivity())

        })
        viewModel.getHomeCategory()


        appDatabaseobj.getAppDao().getSlider().observe(this, Observer {
            adapterSlider.setImageList(it, requireActivity())
        })

        viewModel.sliderList.observe(this, Observer {
            appDatabaseobj.getAppDao().deleteSlider()
            appDatabaseobj.getAppDao().insertSlider(it)
            adapterSlider.setImageList(it, requireActivity())
        })
        viewModel.getSlider()
        binding.llAllCatogary.setOnClickListener{
            var bundle = bundleOf(
                "RestaurantMart" to "Mart"
            )
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainActivityDefaultFragment,RestautantMartFragment::class.java,bundle)?.addToBackStack(null)?.commit()
//            findNavController().navigate(R.id.action_homeFragment_to_restautantMartFragment, bundle)

        }
        binding.restaurant.setOnClickListener {
            var bundle = bundleOf(
                "RestaurantMart" to "Restaurant"
            )
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainActivityDefaultFragment,RestautantMartFragment::class.java,bundle)?.addToBackStack(null)?.commit()
//            findNavController().navigate(R.id.action_homeFragment_to_restautantMartFragment, bundle)


        }

        binding.mart.setOnClickListener {
            var bundle = bundleOf(
                "RestaurantMart" to "Mart"
            )
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainActivityDefaultFragment,RestautantMartFragment::class.java,bundle)?.addToBackStack(null)?.commit()
//            findNavController().navigate(R.id.action_homeFragment_to_restautantMartFragment, bundle)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}