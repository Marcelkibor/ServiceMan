package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.FragmentBillingsBinding
import com.example.petvet.databinding.VetConfirmedOrdersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var binding: VetConfirmedOrdersBinding
private lateinit var auth: FirebaseAuth
private lateinit var dbRef: DatabaseReference
private lateinit var confirmedServiceList: ArrayList<ClientRequest>
private lateinit var rvAcceptedOrders: RecyclerView

class ActivityDoctorConfirmedOrders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VetConfirmedOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvAcceptedOrders = binding.completedRv
        auth = FirebaseAuth.getInstance()
        rvAcceptedOrders.layoutManager = LinearLayoutManager(this)
        rvAcceptedOrders.setHasFixedSize(true)
        confirmedServiceList = arrayListOf<ClientRequest>()
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedServiceManActivity::class.java)
            startActivity(intent)
            finish()

        }
        fetchAcceptedRequests()
    }

    private fun fetchAcceptedRequests() {
        dbRef = FirebaseDatabase.getInstance().getReference("Accept")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(ClientRequest::class.java)
                            if (item?.vetID==auth.currentUser?.uid){
                                confirmedServiceList.add(item!!)
                            }
                        }
                        val adapter = ClientRequestAdapter(confirmedServiceList)
                        binding.completedRv.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            ClientRequestAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val svName = confirmedServiceList[position].serviceName
                                val client = confirmedServiceList[position].clientName
                                val imageUrl = confirmedServiceList[position].imageUri
                                val requestId = confirmedServiceList[position].requestID
                                val clientId = confirmedServiceList[position].clientID
                                val time = confirmedServiceList[position].requestTime
                                val animCategory = confirmedServiceList[position].category
                                val intent = Intent(this@ActivityDoctorConfirmedOrders, VetConfirmedRequest::class.java)
                                intent.putExtra("ClientName", client)
                                intent.putExtra("ServiceName", svName)
                                intent.putExtra("clientUserID", clientId)
                                intent.putExtra("AnimalCategory", animCategory)
                                intent.putExtra("RequestId", requestId)
                                intent.putExtra("imageUrl", imageUrl)
                                intent.putExtra("reqTime", time)
                                startActivity(intent)
                                finish()
                            }
                        })
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@ActivityDoctorConfirmedOrders,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@ActivityDoctorConfirmedOrders,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

