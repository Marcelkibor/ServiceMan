package com.example.petvet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_service_details.view.*
class ServiceListAdapter( private val service: MutableList<VetService>):RecyclerView.Adapter<ServiceListAdapter.ServiceListViewHolder>() {
    class ServiceListViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView){
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
        mylistener = listener }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.single_service_details,parent,false
        )
        return (ServiceListViewHolder(itemView,mylistener))
    }
    override fun onBindViewHolder(holder: ServiceListViewHolder, position: Int) {
        val currentService = service[position]
        holder.itemView.apply {
            tvServName.text = currentService.serviceName
            tvCategory.text = currentService.category
        }
    }
    override fun getItemCount(): Int {
        return  service.size
    }
}