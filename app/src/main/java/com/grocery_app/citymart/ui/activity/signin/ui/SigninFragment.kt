package com.grocery_app.citymart.ui.activity.signin.ui

import android.content.Intent
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
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.FragmentSigninBinding
import com.grocery_app.citymart.ui.activity.main_activity.MainActivity
import com.grocery_app.citymart.ui.activity.signup.ActivitySignUp

class SigninFragment : Fragment() {

    companion object {
        fun newInstance() = SigninFragment()
    }

    lateinit var  viewModel: signinViewModel
    lateinit var binding: FragmentSigninBinding
    val retrofitService = RetrofitService.getInstance()
    val mainRepository = Repository(retrofitService)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_signin,container,false)

        binding.btnLogin.setOnClickListener {


            var email = binding.edEmail.text.toString().trim()
            var pass = binding.edPassword.text.toString().trim()

            if (email.isEmpty()){
               binding.edEmail.error = "Empity Field is not allowed"
            return@setOnClickListener
            }
            if(pass.isEmpty()){
                binding.edEmail.error = "Empity Field is not allowed"
            return@setOnClickListener
            }else{
                viewModel.userResponse.observe(this, Observer {

                })
                viewModel.errormsg.observe(this, Observer {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                })
                viewModel.loading.observe(this, Observer {
                    if (it) {
                        binding.progressDialog.visibility = View.VISIBLE
                    } else {
                        binding.progressDialog.visibility = View.GONE
                    }
                })
//                viewModel.signin_user(email,pass)

            }

        }

        binding.tvCreateNewAcc.setOnClickListener {
            var intent = Intent(requireActivity(), ActivitySignUp::class.java)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      viewModel = ViewModelProvider(this, ViewModelFactory_SignIn(mainRepository)).get(
          signinViewModel::class.java)
        // TODO: Use the ViewModel
    }

}