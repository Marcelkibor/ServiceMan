package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.FragmentSearchVetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search_vet.view.*
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("StaticFieldLeak")
private lateinit var binding:FragmentSearchVetBinding
private lateinit var auth: FirebaseAuth
private lateinit var dbRef: DatabaseReference
private lateinit var docAdapter: DoctorListAdapter
private lateinit var doctorList: ArrayList<VetDoctor>
private lateinit var tempArray: ArrayList<VetDoctor>
private lateinit var backUpArray:ArrayList<VetDoctor>
private lateinit var doctorRecyclerView: RecyclerView
class ClientSearchServiceManActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchVetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doctorRecyclerView = binding.clientRecView
        doctorRecyclerView.layoutManager = LinearLayoutManager(this)
        doctorRecyclerView.setHasFixedSize(true)
        doctorList = arrayListOf<VetDoctor>()
        tempArray = arrayListOf<VetDoctor>()
        backUpArray = arrayListOf<VetDoctor>()
        fetchDoctorList()
    }

   private fun  filterRec(text:String){
       for (item in doctorList) {
           if (item.doctorName.toLowerCase().contains(text.toLowerCase())) {
               tempArray.add(item)
           }
       }
       docAdapter.filterList(tempArray)
   }

    private fun fetchDoctorList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("ServiceMan")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children){
                            val item = snap.getValue(VetDoctor::class.java)
                            doctorList.add(item!!)
                        }
                        backUpArray.addAll(doctorList)
                        docAdapter  = DoctorListAdapter(doctorList)
                        doctorRecyclerView.adapter = docAdapter
                        docAdapter.setOnItemClickListener(object : DoctorListAdapter.OnItemClickListener{
                            override fun onItemClick(position: Int) {
//                                 Toast.makeText(this@AuthenticatedClientActivity,"I can be Clicked",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@ClientSearchServiceManActivity,ClientSelectServiceActivity::class.java)
                                val currentDoctor = doctorList[position]
                                val docUID = currentDoctor.doctorUid.toString()
                                val docName = currentDoctor.doctorName
                                intent.putExtra("serviceManUid",docUID)
                                intent.putExtra("serviceManFirstName",docName)
                                startActivity(intent)
                                finish()
                            }
                        })
                    }
                    else{

                        binding.tvListStatus.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ClientSearchServiceManActivity,error.message,Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e:Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()

        }

    }
}