package com.grocery_app.citymart.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grocery_app.citymart.R
import com.grocery_app.citymart.Retrofit.RetrofitService
import com.grocery_app.citymart.databinding.RecyclerAddressBinding
import com.grocery_app.citymart.model.address.getAddress.Addres
import com.grocery_app.citymart.model.address.getAddress.DeleteAdd
import kotlinx.android.synthetic.main.customedialoug.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterSavedAddress:RecyclerView.Adapter<AdapterSavedAddress.ViewHolderClass>() {

        var addressList = mutableListOf<Addres>()
        var context: Context? = null

        @SuppressLint("NotifyDataSetChanged")
        fun setAddress(item: List<Addres>, context: Context) {

            this.addressList = item as MutableList<Addres>
            this.context = context
            notifyDataSetChanged()
         }

    class ViewHolderClass(var binding: RecyclerAddressBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {

        val infleter = LayoutInflater.from(parent.context)
        val binding = RecyclerAddressBinding.inflate(infleter, parent, false)
        return ViewHolderClass(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
       var place = addressList[position]

        val f = place.firstName
        val g = place.lastName
        val a = place.houseNumber
        var b = place.postal
        var c = place.city
        var d = place.country
        var e = place.instruction
        holder.binding.tvAddressHome.setText(" $f $g \n Address :-  $a $b $c $d $e")

        holder.binding.btnEditDelete.setOnClickListener {
            val view = View.inflate(context, R.layout.custome_dialoug_box_delete_address, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.color.transparent)
            view.yes.setOnClickListener {
                dialog.dismiss()

                RetrofitService.getInstance().deleteAddress(place.Id.toString())
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

    }


        override fun getItemCount(): Int {
            return addressList.size
        }

    }
