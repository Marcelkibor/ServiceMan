package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_admin_register.view.*

@SuppressLint("StaticFieldLeak")
private lateinit var dbRef: DatabaseReference
private lateinit var doctorRecyclerView: RecyclerView
private lateinit var doctorList: ArrayList<VetDoctor>
class FragmentAdminRegister : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_register, container, false)
        doctorList = arrayListOf<VetDoctor>()
        doctorRecyclerView = view.rvAdminPendingRegister
        doctorRecyclerView.layoutManager = LinearLayoutManager(activity)
        doctorRecyclerView.setHasFixedSize(true)
        fetchDoctorList()
        // Inflate the layout for this fragment
        return view
    }
    private fun fetchDoctorList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Doctor")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val item = snap.getValue(VetDoctor::class.java)
    doctorList.add(item!!)
}
val adapter = DoctorListAdapter(doctorList)
doctorRecyclerView.adapter = adapter
adapter.setOnItemClickListener(object : DoctorListAdapter.OnItemClickListener{
    override fun onItemClick(position: Int) {
        val intent = Intent(activity,AdminEditDoctors::class.java)
        val currentDoctor = doctorList[position]
        val docUID = currentDoctor.doctorUid
        val docName = currentDoctor.doctorName
        val doctorEmail = currentDoctor.doctorEmail
        intent.putExtra("VetDocUID",docUID)
        intent.putExtra("VetDocEmail",doctorEmail)
        intent.putExtra("VetDocName",docName)
        startActivity(intent)
    }
}) }
    else{
        Toast.makeText(activity,"No Data!!", Toast.LENGTH_SHORT).show()
    }
}
override fun onCancelled(error: DatabaseError) {
    Toast.makeText(activity,error.message, Toast.LENGTH_SHORT).show()
}
})
}catch (e:Exception){
Toast.makeText(activity,e.message, Toast.LENGTH_SHORT).show()
}
}




}