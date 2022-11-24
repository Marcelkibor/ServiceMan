package com.example.petvet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.accepted_request_details.view.*
class AcceptedRequestAdapter(
    private val acceptedRequest: MutableList<ClientConfirmRequest>):RecyclerView.Adapter<AcceptedRequestAdapter.AcceptedRequestViewHolder>() {
class AcceptedRequestViewHolder(itemView: View,listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptedRequestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
    R.layout.accepted_request_details,parent,false)
        return (AcceptedRequestViewHolder(itemView,mylistener))
    }
    override fun onBindViewHolder(holder: AcceptedRequestViewHolder, position: Int) {
        val currentRequest = acceptedRequest[position]
        holder.itemView.apply {
            tvAClientRequestName.text = currentRequest.clientName
           tvAClientServiceName.text = currentRequest.actualService
        }
    }
    override fun getItemCount(): Int {
        return acceptedRequest.size
    }
}