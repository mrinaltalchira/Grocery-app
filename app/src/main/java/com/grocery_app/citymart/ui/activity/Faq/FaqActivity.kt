package com.grocery_app.citymart.ui.activity.Faq

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.adapter.Adapter_FAQ
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.model.faq.FAQ
import com.grocery_app.citymart.ui.activity.addressActivity.AddressActivityViewMFactory
import com.grocery_app.citymart.ui.activity.addressActivity.AddressActivityViewModel
import kotlinx.android.synthetic.main.activity_faq.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaqActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        var retrofitService = RetrofitService.getInstance()
        var repository = Repository(retrofitService)
        var viewModel = ViewModelProvider(this, FAQ_VM_Factory(repository)).get(
            FAQ_ViewModel::class.java)




        viewModel.errorMsg.observe(this, Observer{
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                 progressDialog.visibility = View.VISIBLE
            } else {
                progressDialog.visibility = View.GONE
            }
        })
        viewModel.faqData.observe(this, Observer {
            var adapter = Adapter_FAQ()
                    rv_list.layoutManager =
                        LinearLayoutManager(this@FaqActivity, LinearLayoutManager.VERTICAL, false)
                    rv_list.adapter = adapter
                    adapter.setList(it, this@FaqActivity)
        })

        viewModel.getFAQ()





//        var call = RetrofitService.getInstance().getFAQ()
//        call.enqueue(object : Callback<FAQ> {
//            override fun onResponse(call: Call<FAQ>, response: Response<FAQ>) {
//                var respo = response.body()
//                if (respo != null) {
//                    var adapter = Adapter_FAQ()
//                    rv_list.layoutManager =
//                        LinearLayoutManager(this@FaqActivity, LinearLayoutManager.VERTICAL, false)
//                    rv_list.adapter = adapter
//                    adapter.setList(respo.faq, this@FaqActivity)
//                }
//            }
//
//            override fun onFailure(call: Call<FAQ>, t: Throwable) {
//                Toast.makeText(this@FaqActivity, "$t", Toast.LENGTH_SHORT).show()
//
//            }
//        })


    }
}