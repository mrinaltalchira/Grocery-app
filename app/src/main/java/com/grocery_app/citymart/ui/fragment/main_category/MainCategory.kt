package com.grocery_app.citymart.ui.fragment.main_category

import android.app.AlertDialog
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
import androidx.lifecycle.Observer
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.Adapter_productBycategory
import com.grocery_app.citymart.adapter.Adapter_subCategory
import com.grocery_app.citymart.databinding.MainCategoryFragmentBinding
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.util.AddCartvalue
import com.grocery_app.citymart.util.SharedPreferenceData
import com.grocery_app.citymart.util.SweetAlert
import kotlinx.android.synthetic.main.customedialoug.view.*

class MainCategory : Fragment(), Adapter_subCategory.SelectItem,
    Adapter_productBycategory.ItemAddtocart {
    lateinit var binding: MainCategoryFragmentBinding
    private val adapter = Adapter_subCategory(this)
    private val productadapter = Adapter_productBycategory(this)
    private var productId: String = ""
    lateinit var shareMartRestroTrueFalse : SharedPreferences


    companion object {
        fun newInstance() = MainCategory()
    }

    private lateinit var viewModel: MainCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.main_category_fragment,
            container,
            false
        )

        shareMartRestroTrueFalse = requireActivity().getSharedPreferences("shareMartRestroTrueFalse",
            Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvSubcategory.adapter = adapter
        binding.rvProductBycategory.adapter = productadapter
        val categoryName = arguments?.getString("category").toString()
        val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")


        val mainCategoryName = arguments?.getString("mainCategory").toString()

        binding.tvTitleRestaurantMart.setText(categoryName)
        viewModel = ViewModelProvider(
            this,
            MainCategoryViewModelFactory()
        ).get(MainCategoryViewModel::class.java)
        viewModel.categoryName = categoryName
        viewModel.maincategoryName = mainCategoryName


        // Set Category
        viewModel.homeCategoryList.observe(this, Observer {

            adapter.setCategoty(it, categoryName)
        })
        viewModel.getSubCategory()

        viewModel.errorMessage.observe(this, {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        //Get All Product by category
        viewModel.productItemList.observe(this, Observer {
            productadapter.setCategoty(it, requireActivity())

        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressbarCategoryProduct.visibility = View.VISIBLE
            } else {
                binding.progressbarCategoryProduct.visibility = View.GONE
            }
        })
        viewModel.getProduct()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    //Click Add to cart
    override fun itemAddtoCart(productId: products) {
            val appDatabaseObj:AppDatabase= AppDatabase.getAppDBInstance(requireActivity())

        if (productId.productType == "Mart") {
            var a = shareMartRestroTrueFalse.getString("isMart","")

            if (a == "Mart" || a == ""){

                appDatabaseObj.getAppDao().addCart(productId)
                var editor = shareMartRestroTrueFalse.edit()
                editor.putString( "isMart" , "Mart")
                editor.apply()

            }else{
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

                  }
                view.no.setOnClickListener {
                    dialog.dismiss()
                    Toast.makeText(requireActivity(), "cancel request", Toast.LENGTH_SHORT).show()

                }
                return
            }


        }

        if(productId.productType == "Restaurant") {

            var a = shareMartRestroTrueFalse.getString("isMart", "")

            if (a == "Restaurant" || a == "") {

                appDatabaseObj.getAppDao().addCart(productId)
                var editor = shareMartRestroTrueFalse.edit()
                editor.putString("isMart", "Restaurant")
                editor.apply()
            }else{
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

                    SweetAlert.hidediloag()
                }
                view.no.setOnClickListener {
                    dialog.dismiss()
                    Toast.makeText(requireActivity(), "cancel request", Toast.LENGTH_SHORT).show()
                    SweetAlert.hidediloag()
                }
                return

            }
        }




//            this.productId = productId
        viewModel = ViewModelProvider(
            this,
            MainCategoryViewModelFactory()
        ).get(MainCategoryViewModel::class.java)

//        viewModel.addToCart(productId)
        (requireActivity() as AddCartvalue).onAddProduct()
    }

    //Get Select Category in adapter
    override fun onIemSelect(subCategoryName: String) {
        binding.tvTitleRestaurantMart.setText(subCategoryName)
        viewModel.categoryName = subCategoryName
        viewModel.getProduct()
        adapter.notifyDataSetChanged()

    }


}