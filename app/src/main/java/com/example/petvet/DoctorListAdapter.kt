package com.example.petvet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.DoctorListAdapter.DoctorViewHolder
import kotlinx.android.synthetic.main.doctor_list_details.view.*


class DoctorListAdapter(
    private var doctor: MutableList<VetDoctor>):RecyclerView.Adapter<DoctorViewHolder>() {
class DoctorViewHolder(itemView: View,listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }


}
    @SuppressLint("NotifyDataSetChanged")
     fun filterList(list:ArrayList<VetDoctor>) {
        this.doctor = list
        notifyDataSetChanged()
    }
    private lateinit var mylistener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mylistener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
val itemView = LayoutInflater.from(parent.context).inflate(
    R.layout.doctor_list_details,parent,false
)
        return (DoctorViewHolder(itemView,mylistener))

    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
val currentDoctor = doctor[position]
        holder.itemView.apply {
            tvDocName.text = currentDoctor.doctorName
            tvDocEmail.text = currentDoctor.doctorEmail
            tvDocPhone.text = currentDoctor.docPhone
        }
    }

    override fun getItemCount(): Int {
return doctor.size
    }

}