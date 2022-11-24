package com.example.petvet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.client_request_details.view.*
import kotlinx.android.synthetic.main.single_cleared_request.view.*

class VetConfirmRequestAdapter(
    private val client_request: MutableList<ClientConfirmRequest>):RecyclerView.Adapter<VetConfirmRequestAdapter.ClientRequestViewHolder>() {
class ClientRequestViewHolder(itemView: View,listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientRequestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
    R.layout.single_cleared_request,parent,false)
        return (ClientRequestViewHolder(itemView,mylistener))
    }
    override fun onBindViewHolder(holder: ClientRequestViewHolder, position: Int) {
        val currentRequest = client_request[position]
        holder.itemView.apply {
            tvClientRequestName.text = currentRequest.clientName
            tvCategory.text = currentRequest.animCat
           tvClientServiceName.text = currentRequest.actualService
        }
    }
    override fun getItemCount(): Int {
        return client_request.size
    }
}