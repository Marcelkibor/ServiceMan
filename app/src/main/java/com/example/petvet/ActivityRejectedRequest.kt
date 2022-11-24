package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.RejectRequestBinding
import com.google.firebase.database.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var binding: RejectRequestBinding
private lateinit var dbRef: DatabaseReference
private lateinit var requestList: ArrayList<ClientRequest>
private lateinit var tempArray:ArrayList<ClientRequest>
private lateinit var rvRejectedRyc: RecyclerView
class ActivityRejectedRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RejectRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvRejectedRyc = binding.rvRejected
        binding.backArrow.isClickable = true
        binding.backArrow.setOnClickListener{
            val intent = Intent(this,AuthenticatedVetActivity::class.java)
            startActivity(intent)
        }
        rvRejectedRyc.layoutManager = LinearLayoutManager(this@ActivityRejectedRequest)
        rvRejectedRyc.setHasFixedSize(true)
        requestList = arrayListOf()
        tempArray = arrayListOf()
        fetchRequestList()
    }
    private fun fetchRequestList() {
dbRef = FirebaseDatabase.getInstance().getReference("Reject")
dbRef.addValueEventListener(object : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()){
            for (snap in snapshot.children){
                val item = snap.getValue(ClientRequest::class.java)
                requestList.add(item!!)
            }
            tempArray.addAll(requestList)
            val adapter = ClientRequestAdapter(tempArray)
            binding.rvRejected.adapter = adapter
        }
    }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityRejectedRequest,error.message,Toast.LENGTH_SHORT).show()
            }
        })    }
    }

