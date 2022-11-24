package com.example.petvet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petvet.DoctorListAdapter.DoctorViewHolder
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.client_payment_details.view.*
import kotlinx.android.synthetic.main.client_payment_list_details.view.*


class VetSharesAdapter(
    private val payment: MutableList<VetAccount>):RecyclerView.Adapter<VetSharesAdapter.PaymentViewHolder>() {
class PaymentViewHolder(itemView: View,listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }

}
    private lateinit var mylistener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mylistener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
val itemView = LayoutInflater.from(parent.context).inflate(
    R.layout.client_payment_list_details,parent,false
)
    return (PaymentViewHolder(itemView,mylistener))
    }
    override fun getItemCount(): Int {
return payment.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val currentPayment = payment[position]
        holder.itemView.apply {
            tvPaymentAmount.text = currentPayment.paymentAmount+"/="
            tvPaymentServiceID.text = currentPayment.serviceId
//            tvAmount.text = currentPayment.paymentAmount
        }    }
}