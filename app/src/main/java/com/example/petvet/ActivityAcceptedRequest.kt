package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.AcceptedRequestBinding
import com.google.firebase.database.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var binding: AcceptedRequestBinding
private lateinit var dbRef: DatabaseReference
private lateinit var requestList: ArrayList<VetService>
private lateinit var tempArray:ArrayList<VetService>
private lateinit var rvAcceptedRyc: RecyclerView
class ActivityAcceptedRequest : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
    binding = AcceptedRequestBinding.inflate(layoutInflater)
    setContentView(binding.root)
    rvAcceptedRyc = binding.rvAccepted
    binding.backArrow.isClickable = true
    binding.backArrow.setOnClickListener{
    val intent = Intent(this,AuthenticatedServiceManActivity::class.java)
    startActivity(intent)
    }
    rvAcceptedRyc.layoutManager = LinearLayoutManager(this@ActivityAcceptedRequest)
    rvAcceptedRyc.setHasFixedSize(true)
    requestList = arrayListOf<VetService>()
    tempArray = arrayListOf<VetService>()
    fetchRequestList()
    }
    private fun fetchRequestList() {
        try{
            dbRef = FirebaseDatabase.getInstance().getReference("Service")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val item = snap.getValue(VetService::class.java)
                            requestList.add(item!!)
                        }
                        tempArray.addAll(requestList)
                        val adapter = ServiceListAdapter(tempArray)
                        binding.rvAccepted.adapter = adapter
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ActivityAcceptedRequest,error.message,Toast.LENGTH_SHORT).show()
                }

    })
    }catch (e:Exception){
            Toast.makeText(this@ActivityAcceptedRequest,e.message,Toast.LENGTH_SHORT).show()
  }    }
}

