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
import com.example.petvet.databinding.FragmentAdminClientsBinding
import com.example.petvet.databinding.FragmentAdminDoctorsBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_admin_clients.view.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentAdminClientsBinding
private lateinit var dbRef:DatabaseReference
private lateinit var clientRecyclerView: RecyclerView
private lateinit var clientList: ArrayList<CustomCustomer>
private lateinit var clientAdapter: ClientListAdapter
private lateinit var tempArrayList: ArrayList<CustomCustomer>
private lateinit var backUpArray:ArrayList<CustomCustomer>
class ActivityAdminClients : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAdminClientsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchClientList()
        clientList = arrayListOf<CustomCustomer>()
        tempArrayList = arrayListOf<CustomCustomer>()
        backUpArray = arrayListOf<CustomCustomer>()
        clientRecyclerView = binding.rvAdminClients
        clientRecyclerView.layoutManager = LinearLayoutManager(this)
        clientRecyclerView.setHasFixedSize(true)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,AdminPanel::class.java)
            startActivity(intent)
            finish()
        }
    }

private fun  filterRec(text:String){
    for (item in clientList) {
        if (item.firstName.toLowerCase().contains(text.toLowerCase())) {
            tempArrayList.add(item)
        }
    }
    clientAdapter.filterList(tempArrayList)
}


    private fun fetchClientList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Client")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(CustomCustomer::class.java)
                            clientList.add(item!!)
                        }
                        backUpArray.addAll(clientList)
                        clientAdapter = ClientListAdapter(clientList)
                        clientRecyclerView.adapter = clientAdapter
                        clientAdapter.setOnItemClickListener(object :
                            ClientListAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                Toast.makeText(
                                    this@ActivityAdminClients,
                                    "I can be Clicked",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@ActivityAdminClients,ActivityAdminClientDetails::class.java)
                                val firstName = clientList[position].firstName
                                val lastName = clientList[position].lastName
                                val email = clientList[position].email
                                val clientId = clientList[position].customerUid
                                val phoneNumber  = clientList[position].phone
                                intent.putExtra("firstName",firstName)
                                intent.putExtra("lastName",lastName)
                                intent.putExtra("email",email)
                                intent.putExtra("clientId",clientId)
                                intent.putExtra("phoneNumber",phoneNumber)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        binding.tvListStatus.visibility = View.VISIBLE

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ActivityAdminClients, error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@ActivityAdminClients, e.message, Toast.LENGTH_SHORT).show()

        }

    }
}