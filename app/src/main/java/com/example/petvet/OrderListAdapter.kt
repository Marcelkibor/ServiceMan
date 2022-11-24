package com.example.petvet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petvet.DoctorListAdapter.DoctorViewHolder
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.order_list_details.view.*


class OrderListAdapter(
    private val order: MutableList<ClientConfirmRequest>):RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {
class OrderViewHolder(itemView: View,listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
val itemView = LayoutInflater.from(parent.context).inflate(
    R.layout.order_list_details,parent,false
)
        return (OrderViewHolder(itemView,mylistener))
    }
    override fun getItemCount(): Int {
return order.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrder = order[position]
        holder.itemView.apply {
            tvOrderID.text = currentOrder.requestId
//            tvOrderClientId.text = currentOrder.clientId
            tvOrderTime.text = currentOrder.requestedTime
        }    }
}