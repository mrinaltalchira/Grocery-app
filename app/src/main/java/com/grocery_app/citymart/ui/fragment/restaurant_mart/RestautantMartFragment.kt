package com.grocery_app.citymart.ui.fragment.restaurant_mart

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grocery_app.citymart.R
import com.grocery_app.citymart.RoomDatabase.AppDatabase
import com.grocery_app.citymart.adapter.Adapter_productBycategory
import com.grocery_app.citymart.adapter.Adapter_subCategory
import com.grocery_app.citymart.databinding.RestautantMartFragmentBinding
import com.grocery_app.citymart.model.Product.products
import com.grocery_app.citymart.ui.fragment.main_category.MainCategoryViewModel
import com.grocery_app.citymart.ui.fragment.main_category.MainCategoryViewModelFactory
import com.grocery_app.citymart.util.AddCartvalue
import com.grocery_app.citymart.util.SharedPreferenceData
import com.grocery_app.citymart.util.SweetAlert
import kotlinx.android.synthetic.main.customedialoug.view.*

class RestautantMartFragment : Fragment(), Adapter_subCategory.SelectItem,
    Adapter_productBycategory.ItemAddtocart {
    lateinit var binding: RestautantMartFragmentBinding
    private val adapter = Adapter_subCategory(this)
    private val productadapter = Adapter_productBycategory(this)
    private var productId: String = ""
    lateinit var a: List<products>
    lateinit var shareMartRestroTrueFalse: SharedPreferences

    companion object {
        fun newInstance() = RestautantMartFragment()
    }

    private lateinit var viewModel: MainCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.restautant_mart_fragment,
            container,
            false
        )
        shareMartRestroTrueFalse = requireActivity().getSharedPreferences(
            "shareMartRestroTrueFalse",
            Context.MODE_PRIVATE
        )
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        binding.rvSubcategory.adapter = adapter
        binding.rvProductBycategory.adapter = productadapter
        var categoryName = arguments?.getString("category").toString()

        val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")

        val mainCategoryName = arguments?.getString("RestaurantMart").toString()
//        binding.tvTitleRestaurantMart.setText(mainCategoryName)
        if (userId != null) {
        }
        viewModel = ViewModelProvider(
            this,
            MainCategoryViewModelFactory()
        ).get(MainCategoryViewModel::class.java)


        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.categoryName = categoryName
        viewModel.maincategoryName = mainCategoryName

        // Set Category
        viewModel.homeCategoryList.observe(this, Observer {
            viewModel.categoryName = it[0].defaultName
            viewModel.getProduct()

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
    }

    //Click Add to cart
    override fun itemAddtoCart(productId: products) {
        //        this.productId = productId

        val appDatabaseObj: AppDatabase = AppDatabase.getAppDBInstance(requireActivity())

        if (productId.productType == "Mart") {
            var a = shareMartRestroTrueFalse.getString("isMart", "")

            if (a == "Mart" || a == "") {

                appDatabaseObj.getAppDao().addCart(productId)
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
                return
            }


        }

        if (productId.productType == "Restaurant") {

            var a = shareMartRestroTrueFalse.getString("isMart", "")

            if (a == "Restaurant" || a == "") {

                appDatabaseObj.getAppDao().addCart(productId)
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

