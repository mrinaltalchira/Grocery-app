package com.grocery_app.citymart.ui.fragment.Search

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.Adapter_Search
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.ui.fragment.main_category.MainCategoryViewModel
import com.grocery_app.citymart.ui.fragment.main_category.MainCategoryViewModelFactory
import com.grocery_app.citymart.util.AddCartvalue

import android.view.inputmethod.InputMethodManager
import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.SeekBar

import androidx.annotation.RequiresApi
import com.grocery_app.citymart.databinding.SearchFragmentBinding
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.model.Search.Product
import kotlinx.android.synthetic.main.search_fragment.*
import com.appyvet.materialrangebar.RangeBar
import com.appyvet.materialrangebar.RangeBar.OnRangeBarChangeListener
import kotlinx.android.synthetic.main.custome_dialoug_box_delete_previour_cart.view.*
import kotlinx.android.synthetic.main.search_item_recyclerview_layout.*


class SearchFragment: Fragment(),Adapter_Search.ItemAddtocart {

    companion object {
        fun newInstance() = SearchFragment()
    }

    var min:Int = 0
    var max:Int = 0
    var value:String? = null
    lateinit var database: AppDatabase
    lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchViewModel
    lateinit var adapter:Adapter_Search
    lateinit var sharep: SharedPreferences
    lateinit var userId:String
    private var bottomSheetBehavior : BottomSheetBehavior<*>? = null
    lateinit var shareMartRestroTrueFalse : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.search_fragment,container,false)
        sharep = requireActivity().getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId","").toString()
        shareMartRestroTrueFalse = requireActivity().getSharedPreferences("shareMartRestroTrueFalse",
            Context.MODE_PRIVATE)
        return binding.root
    }
    private var productId: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val retrofitService = RetrofitService.getInstance()
        val repository = Repository(retrofitService)
        viewModel = ViewModelProvider(this, SearchViewModelFactory(repository)).get(
            SearchViewModel::class.java)

        database = AppDatabase.getAppDBInstance(requireActivity())
        adapter = Adapter_Search(this)
        binding.rvSearch.adapter = adapter


        binding.backBtn.setOnClickListener{
            activity!!.onBackPressed()
        }

        binding.etSearchProduct.addTextChangedListener {

            value =  binding.etSearchProduct.text.toString().trim()
            val v = ""
            if (value != v){
                binding.rvSearch.visibility = View.VISIBLE
                binding.imgSearchPlace.visibility = View.GONE
                viewModel.searchResult.observe(this, Observer {
                    if (it != null){
                        adapter.adapterFunction(it,requireContext())
                    }
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

                if (max == 0){
                    var max = 1000
                    var min =0
                    viewModel.searchAdd(value!!,max,min)
                }else{
                    viewModel.searchAdd(value!!,max,min)
                }

            }else {
                binding.rvSearch.visibility = View.GONE
                binding.imgSearchPlace.visibility = View.VISIBLE
            }


        }

        ww_RB_id.setOnRangeBarChangeListener(object : OnRangeBarChangeListener {
            override fun onRangeChangeListener(
                rangeBar: RangeBar,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String,
                rightPinValue: String
            ) {

                 min = leftPinValue.toInt()
                 max = rightPinValue.toInt()
                binding.intialValue.setText(min.toString())
                binding.finalValue.setText(max.toString())

            }

            override fun onTouchEnded(rangeBar: RangeBar) {
                Toast.makeText(requireActivity(), "$min $max", Toast.LENGTH_SHORT).show()
            }
            override fun onTouchStarted(rangeBar: RangeBar) {}
        })


        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        binding.ivFilter.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.rvSetPrice.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            try {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

                if (max == 0){
                    var max = 1000
                    var min = 0
                    viewModel.searchAdd(value!!,max,min)
                }else{
                    viewModel.searchAdd(value!!,max,min)
                }

            }catch (e:Exception){

            }}
        binding.ivClose.setOnClickListener{
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

        }
        binding.llResult.setOnClickListener(){
            hideKeyboard(requireActivity())
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

        }


    }

    override fun itemAddtoCart(productId: Product) {

        var viewModel = ViewModelProvider(
            this,
            MainCategoryViewModelFactory()
        ).get(MainCategoryViewModel::class.java)

        (requireActivity() as AddCartvalue).onAddProduct()
        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
        val rnds = (10000..2000000).random()


        if (productId.productType == "Mart") {
            var a = shareMartRestroTrueFalse.getString("isMart", "")

            if (a == "Mart" || a == "") {

                appDatabaseObj.getAppDao().addCart(
                    products(
                        rnds,
                        productId._id!!,
                        productId.category!!,
                        productId.desc!!,
                        productId.image!!,
                        productId.name!!,
                        productId.price!!,
                        productId.mrp.toString(),
                        productId.quantity!!,
                        "1",
                        productId.productType.toString()
                    )
                )
               btn_add_to_cart.setText("Added to cart")
                var editor = shareMartRestroTrueFalse.edit()
                editor.putString("isMart", "Mart")
                editor.apply()

            } else {
                val view = View.inflate(requireActivity(), R.layout.custome_dialoug_box_delete_previour_cart, null)
                val builder = AlertDialog.Builder(requireActivity())
                builder.setView(view)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                view.yes.setOnClickListener {
                    dialog.dismiss()
                    val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
                    shareMartRestroTrueFalse.edit().clear().apply()

                    appDatabaseObj.getAppDao().deleteAllCart()
                    appDatabaseObj.getAppDao().deleteCoupon()

                    Toast.makeText(requireActivity(), "cart Cleared", Toast.LENGTH_SHORT).show()

                }
                view.no.setOnClickListener {
                    dialog.dismiss()
                    Toast.makeText(requireActivity(), "cancel request", Toast.LENGTH_SHORT).show()

                }
            }


        }

        if (productId.productType == "Restaurant") {

            var a = shareMartRestroTrueFalse.getString("isMart", "")

            if (a == "Restaurant" || a == "") {

                appDatabaseObj.getAppDao().addCart(
                    products(
                        rnds,
                        productId._id!!,
                        productId.category!!,
                        productId.desc!!,
                        productId.image!!,
                        productId.name!!,
                        productId.price!!,
                        productId.mrp.toString(),
                        productId.quantity!!,
                        "1",
                        productId.productType.toString()
                    )
                )
                btn_add_to_cart.setText("Added to cart")
                var editor = shareMartRestroTrueFalse.edit()
                editor.putString("isMart", "Restaurant")
                editor.apply()
            } else {
                val view = View.inflate(requireActivity(), R.layout.custome_dialoug_box_delete_previour_cart, null)
                val builder = AlertDialog.Builder(requireActivity())
                builder.setView(view)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                view.yes.setOnClickListener {
                    dialog.dismiss()
                    val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())
                    shareMartRestroTrueFalse.edit().clear().apply()

                    appDatabaseObj.getAppDao().deleteAllCart()
                    appDatabaseObj.getAppDao().deleteCoupon()

                    Toast.makeText(requireActivity(), "cart Cleared", Toast.LENGTH_SHORT).show()

                }
                view.no.setOnClickListener {
                    dialog.dismiss()
                    Toast.makeText(requireActivity(), "cancel request", Toast.LENGTH_SHORT).show()

                }
                return

            }
        }

    }


    private fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            activity.window.decorView
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }



}