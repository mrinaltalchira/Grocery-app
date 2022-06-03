package com.grocery_app.citymart.ui.fragment.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.grocery_app.citymart.Choose_LanguageFragment
import com.grocery_app.citymart.R
import com.grocery_app.citymart.adapter.Adapter_OrderLIst
import com.grocery_app.citymart.databinding.FragmentProfileFragmentBinding
import com.grocery_app.citymart.ui.activity.Faq.FaqActivity
import com.grocery_app.citymart.ui.activity.addressActivity.AddressActivity
import com.grocery_app.citymart.ui.activity.signin.SigninActivity
import com.grocery_app.citymart.ui.fragment.MyAccount_EditAccount.MYAccountActivity
import com.grocery_app.citymart.ui.fragment.privacy.PrivacyPolicy
import com.grocery_app.citymart.util.SharedPreferenceData
import kotlinx.android.synthetic.main.fragment_profile_fragment.*

class ProfileFragment : Fragment() {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    lateinit var binding: FragmentProfileFragmentBinding
    private lateinit var viewModel: ProfileViewModel
    private var adapterOrderlist = Adapter_OrderLIst()
    lateinit var sharep: SharedPreferences
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile_fragment,
            container,
            false
        )

        binding.recylerviewProfileFrag.adapter = adapterOrderlist

        sharep = requireActivity().getSharedPreferences("UserIdForUser", Context.MODE_PRIVATE)
        userId = sharep.getString("userId", "").toString()
        if (userId.isEmpty())
        {
            binding.tvSignAppbar.visibility=View.VISIBLE
        }


binding.tvPrivacyPolicy.setOnClickListener{
    if (userId.isNotEmpty()) {

        val intent = Intent(
            requireActivity(),
            PrivacyPolicy::class.java
        )
        startActivity(intent)

    } else {

        var intent = Intent(
            requireActivity(),
            SigninActivity::class.java
        )
        startActivity(intent)
    }
}
        binding.personalInfo.setOnClickListener {


            if (userId.isNotEmpty()) {

                var intent = Intent(
                    requireActivity(),
                    MYAccountActivity::class.java
                )
                startActivity(intent)

            } else {

                var intent = Intent(
                    requireActivity(),
                    SigninActivity::class.java
                )
                startActivity(intent)
            }

        }

        binding.tvAddress.setOnClickListener {

            if (userId.isNotEmpty()) {
                var intent = Intent(
                    requireActivity(),
                    AddressActivity::class.java
                )
                startActivity(intent)

            } else {

                var intent = Intent(
                    requireActivity(),
                    SigninActivity::class.java
                )
                startActivity(intent)
            }
        }
        binding.tvSignAppbar.setOnClickListener {

            var intent = Intent(
                requireActivity(),
                SigninActivity::class.java
            )
            startActivity(intent)

        }

        binding.tvLanguage.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainActivityDefaultFragment, Choose_LanguageFragment())
                ?.addToBackStack(null)?.commit()
        }



        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)



        val userId = SharedPreferenceData.getSharedPreference(requireActivity(), "userId")


        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.cartItems.observe(this, Observer {

            adapterOrderlist.setCategoty(it,requireActivity())

        })

        if (userId != null) {
            viewModel.getCartItemList(userId)
        }


        binding.btnRestaurantOrder.setOnClickListener {

            viewModel.cartItems.observe(this, Observer {

                adapterOrderlist.setCategoty(it,requireActivity())

            })

            viewModel.loading.observe(this, Observer {
                if (it) {
                    binding.progressDialog.visibility = View.VISIBLE
                    recylerview_ProfileFrag.visibility = View.GONE
                } else {
                    binding.progressDialog.visibility = View.GONE
                    recylerview_ProfileFrag.visibility = View.VISIBLE
                }
            })

            if (userId != null) {
                viewModel.getCartItem("rest",userId)
            }

        }

        binding.faqLink.setOnClickListener{
            var intent = Intent(
                requireActivity(),
                FaqActivity::class.java
            )
            startActivity(intent)
        }

        binding.btnMartOrder.setOnClickListener {

            viewModel.cartItems.observe(this, Observer {
                adapterOrderlist.setCategoty(it,requireActivity())
            })

            if (userId != null) {
                viewModel.getCartItem("mart",userId)
            }

            viewModel.loading.observe(this, Observer {
                if (it) {
                    binding.progressDialog.visibility = View.VISIBLE
                    recylerview_ProfileFrag.visibility = View.GONE
                } else {
                    binding.progressDialog.visibility = View.GONE
                    recylerview_ProfileFrag.visibility = View.VISIBLE
                }
            })

        }

    }

}