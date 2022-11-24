package com.example.petvet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.client_request_details.view.*
class ClientRequestAdapter(
    private val client_request: MutableList<ClientRequest>):RecyclerView.Adapter<ClientRequestAdapter.ClientRequestViewHolder>() {
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
    R.layout.client_request_details,parent,false)
        return (ClientRequestViewHolder(itemView,mylistener))
    }
    override fun onBindViewHolder(holder: ClientRequestViewHolder, position: Int) {
        val currentRequest = client_request[position]
        holder.itemView.apply {
            tvClientRequestName.text = currentRequest.clientName
           tvClientServiceName.text = currentRequest.serviceName
            Glide.with(context).load(currentRequest.imageUri).into(imgRequest)
        }
    }
    override fun getItemCount(): Int {
        return client_request.size
    }
}