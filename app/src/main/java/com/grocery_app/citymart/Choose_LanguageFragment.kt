package com.grocery_app.citymart

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.util.DataUtils
import com.grocery_app.citymart.databinding.AccountsFragmentBinding.inflate
import com.grocery_app.citymart.databinding.AccountsFragmentBindingImpl
import com.grocery_app.citymart.databinding.FragmentChooseLanguageBinding
import com.grocery_app.citymart.ui.activity.splash.Splash
import java.util.*

class Choose_LanguageFragment : Fragment() {

    lateinit var binding: FragmentChooseLanguageBinding
    lateinit var radio: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_choose__language, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setradioBtn()
        radio = RadioButton(requireActivity())

        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
        
        binding.RgSelectLang.setOnCheckedChangeListener { radioGroup, i ->
            radio = view.findViewById(i)
            when (radio) {

                binding.RbTelugu -> binding.btnSelectLang.setText("కొనసాగుతుంది")
                binding.RbEnglish -> binding.btnSelectLang.setText("Continue")

                else -> Toast.makeText(
                    context?.applicationContext,
                    "select language",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.btnSelectLang.setOnClickListener {
            when (radio) {
                binding.RbEnglish -> {
                    val locale = Locale("en")
                    Locale.setDefault(locale)
                    val config = Configuration()
                    config.locale = locale
                    requireActivity().getResources().updateConfiguration(
                        config,
                        requireActivity().getResources().getDisplayMetrics()
                    )
                    setLanguage("en")

                    Toast.makeText(requireActivity(), "Locale in English !", Toast.LENGTH_LONG)
                        .show()
                    val intet= Intent(activity,Splash::class.java)
                    activity?.startActivity(intet)
                }
                binding.RbTelugu -> {
                    val locale3 = Locale("te")
                    Locale.setDefault(locale3)
                    val config3 = Configuration()
                    config3.locale = locale3
                    requireActivity().getResources().updateConfiguration(
                        config3,
                        requireActivity().getResources().getDisplayMetrics()
                    )
                    setLanguage("te")
                    Toast.makeText(requireActivity(), "Locale in Telugu !", Toast.LENGTH_LONG).show()
                    val intet= Intent(activity,Splash::class.java)
                    activity?.startActivity(intet)
                }

            }

        }


    }
    private fun setradioBtn() {
        val sharedPreferences = context?.getSharedPreferences("lang", Context.MODE_PRIVATE)
        val message = sharedPreferences?.getString("selectLang", "")

        when(message)
        {
            "en"-> binding.RbEnglish.isChecked=true
            "te"-> binding.RbTelugu.isChecked=true
            else->binding.RbEnglish.isChecked=true

        }
    }

    private fun setLanguage(lang: String) {
        val sharedPreferences =
            activity?.getSharedPreferences("lang", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        if (editor != null) {
            editor.putString("selectLang", lang)
            editor.apply()
        }

    }


}