package com.grocery_app.citymart.ui.fragment.accounts

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.grocery_app.citymart.R
import com.grocery_app.citymart.databinding.AccountsFragmentBinding
import com.grocery_app.citymart.ui.activity.signin.SigninActivity
import com.grocery_app.citymart.ui.activity.signup.ActivitySignUp

class AccountsFragment : Fragment() {

    companion object {
        fun newInstance() = AccountsFragment()
    }

    private lateinit var viewModel: AccountsViewModel
    lateinit var binding:AccountsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {   binding = DataBindingUtil.inflate(layoutInflater,R.layout.accounts_fragment ,container,false)

        binding.tvSigninAccounts.setOnClickListener {

            var intent: Intent? = Intent(
                requireActivity(),
                SigninActivity::class.java
            )
            startActivity(intent)

        }

        binding.tvSignupAccounts.setOnClickListener {

            var intent: Intent? = Intent(
                requireActivity(),
                ActivitySignUp::class.java
            )
            startActivity(intent)



        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountsViewModel::class.java)



    }

}