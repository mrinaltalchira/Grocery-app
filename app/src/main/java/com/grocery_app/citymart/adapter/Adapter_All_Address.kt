package com.grocery_app.citymart.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.data.Repository
import com.grocery_app.citymart.databinding.AddreslayoutForAddnewaddressBinding
import com.grocery_app.citymart.model.address.getAddress.Addres
import com.grocery_app.citymart.model.address.getAddress.DeleteAdd
import com.grocery_app.citymart.ui.activity.add_new_address.AddNewAddress
import com.grocery_app.citymart.ui.activity.add_new_address.manageAddress.Create_New_Address
import kotlinx.android.synthetic.main.customedialoug.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Adapter_All_Address : RecyclerView.Adapter<Adapter_All_Address.ViewHolder>() {

    var addressList = mutableListOf<Addres>()
    var context: Context? = null

    fun setAddress(item: List<Addres>, context: Context) {
        this.addressList = item as MutableList<Addres>
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val infleter = LayoutInflater.from(parent.context)
        val binding = AddreslayoutForAddnewaddressBinding.inflate(infleter, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(var binding: AddreslayoutForAddnewaddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posi = addressList[position]
        val a = posi.houseNumber
        var b = posi.postal
        var c = posi.city
        var d = posi.country
        var e = posi.instruction
        holder.binding.tvAddress.setText("${posi.firstName} ${posi.lastName} \n Address :-  $a $b $c $d $e")
        Log.d("delete", "${posi.Id}")
        holder.binding.tvSetDefault.setOnClickListener {

            var share: SharedPreferences =
                context!!.getSharedPreferences("userAddress", Context.MODE_PRIVATE)
            val editor = share.edit()
            editor.putString("address", "${a} ${b} ${c} ${d} ${e}")
            editor.putString("nameOnAddress","${posi.firstName}${ posi.lastName}")
            editor.apply()
            val activity: AddNewAddress = context as AddNewAddress
            activity.finish()
        }

        holder.binding.tvDeleteAddress.setOnClickListener {

            val view = View.inflate(context, R.layout.custome_dialoug_box_delete_address, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.color.transparent)
            view.yes.setOnClickListener {
                dialog.dismiss()

                RetrofitService.getInstance().deleteAddress(posi.Id.toString())
                    .enqueue(object : Callback<DeleteAdd> {
                        override fun onResponse(
                            call: Call<DeleteAdd>,
                            response: Response<DeleteAdd>
                        ) {
                            var response = response.body()
                            if (response?.message == "Address Deleted") {
                                Toast.makeText(
                                    context,
                                    response.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                addressList.removeAt(holder.adapterPosition)
                                notifyDataSetChanged()
                            } else {
                                Toast.makeText(
                                    context,
                                    "${response?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<DeleteAdd>, t: Throwable) {
                            Toast.makeText(context, "${t}", Toast.LENGTH_SHORT).show()

                        }
                    })


            }
            view.no.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(context, "cancel request", Toast.LENGTH_SHORT).show()
            }


        }
        holder.binding.ivEditAddress.setOnClickListener {

            val activity = context as AppCompatActivity
            val fragment = Create_New_Address() // replace your custom fragment class
            val bundle = Bundle()
            val fragmentTransaction: FragmentTransaction =
                activity.getSupportFragmentManager().beginTransaction()
            bundle.putString("addId", posi.Id)
            bundle.putString("houseNumber", posi.houseNumber)
            bundle.putString("city", posi.city)
            bundle.putString("country", posi.country)
            bundle.putString("postal", posi.postal)
            bundle.putString("instruction", posi.instruction)
            bundle.putBoolean("valueTwo", true)
            fragment.setArguments(bundle)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.frag_Add_new_Address, fragment)
            fragmentTransaction.commit()


        }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }
}