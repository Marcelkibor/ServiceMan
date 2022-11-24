package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.FragmentRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var requestList: ArrayList<ClientRequest>
private lateinit var requestRecyclerView: RecyclerView
private lateinit var auth:FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentRequestBinding
private lateinit var dbRef: DatabaseReference

class ActivityPendingRequests : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequests()
        auth = FirebaseAuth.getInstance()
        requestRecyclerView = binding.requestRv
        requestRecyclerView.layoutManager = LinearLayoutManager(this)
        requestRecyclerView.setHasFixedSize(true)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedServiceManActivity::class.java)
            startActivity(intent)
            finish()
        }
        requestList = arrayListOf<ClientRequest>()
    }

    private fun setRequests() {
        dbRef = FirebaseDatabase.getInstance().getReference("Request")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(ClientRequest::class.java)
                            if (item?.vetID == auth.currentUser?.uid){
                                requestList.add(item!!)
                            }
                        }
                        val adapter = ClientRequestAdapter(requestList)
                        binding.requestRv.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            ClientRequestAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val svName = requestList[position].serviceName
                                val client = requestList[position].clientName
                                val serviceArea = requestList[position].serviceArea
                                val requestId = requestList[position].requestID
                                val clientId = requestList[position].clientID
                                val svDesc = requestList[position].requestDescription
                                val animCategory = requestList[position].category
                                val imageUri = requestList[position].imageUri
                                val requestTime = requestList[position].requestTime
                                val vet_id = requestList[position].vetID
                                binding.backArrow.setOnClickListener {
                                    val intent = Intent(
                                        this@ActivityPendingRequests,
                                        ActivityPendingRequests::class.java
                                    )
                                    startActivity(intent)
                                }
                                val intent = Intent(
                                    this@ActivityPendingRequests,
                                    EditRequest::class.java
                                )
                                intent.putExtra("ClientName", client)
                                intent.putExtra("serviceArea", serviceArea)
                                intent.putExtra("ServiceName", svName)
                                intent.putExtra("clientUserID", clientId)
                                intent.putExtra("AnimalCategory", animCategory)
                                intent.putExtra("RequestId", requestId)
                                intent.putExtra("requestTime", requestTime)
                                intent.putExtra("imageUri", imageUri)
                                intent.putExtra("svDesc", svDesc)
                                intent.putExtra("vet_id", vet_id)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        binding.tvListStatus.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ActivityPendingRequests, e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityPendingRequests, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


}

