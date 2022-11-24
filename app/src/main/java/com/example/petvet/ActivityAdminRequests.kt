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
import com.example.petvet.databinding.FragmentRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var requestList: ArrayList<ClientRequest>
private lateinit var requestRecyclerView: RecyclerView

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentRequestBinding
private lateinit var dbRef: DatabaseReference
class ActivityAdminRequests : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequests()
        requestRecyclerView = binding.requestRv
        requestRecyclerView.layoutManager = LinearLayoutManager(this)
        requestRecyclerView.setHasFixedSize(true)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AdminPanel::class.java)
            startActivity(intent)
            finish()
        }
        requestList = arrayListOf<ClientRequest>()
    }

    private fun setRequests() {
        dbRef = FirebaseDatabase.getInstance().getReference("CompletedRequest")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(ClientRequest::class.java)
                            requestList.add(item!!)
                        }
                        val adapter = ClientRequestAdapter(requestList)
                        binding.requestRv.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            ClientRequestAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val intent = Intent(this@ActivityAdminRequests,ActivityAdminServices::class.java)
                                val serviceName = requestList[position].serviceName
                                val category =requestList[position].category
                                val serviceID =requestList[position].requestID
                                val time = requestList[position].requestTime
                                val imageUrl = requestList[position].imageUri
                                intent.putExtra("serviceName",serviceName)
                                intent.putExtra("category",category)
                                intent.putExtra("serviceID",serviceID)
                                intent.putExtra("time",time)
                                intent.putExtra("imageUrl",imageUrl)
                                startActivity(intent)
                                finish()
                            }
                        })
                    }else{
                        binding.tvListStatus.visibility = View.VISIBLE

                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ActivityAdminRequests, e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityAdminRequests, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


}

