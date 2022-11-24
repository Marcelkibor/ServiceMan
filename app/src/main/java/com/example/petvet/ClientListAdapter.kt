package com.example.petvet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petvet.DoctorListAdapter.DoctorViewHolder
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.admin_edit_doctor_request.view.*
import kotlinx.android.synthetic.main.client_list_details.view.*


class ClientListAdapter(
    private var customer: MutableList<CustomCustomer>):RecyclerView.Adapter<ClientListAdapter.ClientViewHolder>() {
    class ClientViewHolder(itemView: View,listener: ClientListAdapter.OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(list:ArrayList<CustomCustomer>) {
        this.customer = list
        notifyDataSetChanged()
    }
    private lateinit var mylistener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mylistener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.client_list_details,parent,false
        )
        return (ClientViewHolder(itemView,mylistener))
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val currentCustomer = customer[position]
        holder.itemView.apply {
            tvClientName.text = currentCustomer.firstName
            tvLastName.text = currentCustomer.lastName
            tvClientPhone.text = currentCustomer.phone
        }
    }
    override fun getItemCount(): Int {
return customer.size
    }

}