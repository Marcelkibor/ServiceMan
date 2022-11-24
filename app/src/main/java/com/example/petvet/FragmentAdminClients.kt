package com.example.petvet
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_admin_clients.view.*

private lateinit var dbRef:DatabaseReference
private lateinit var clientRecyclerView: RecyclerView
private lateinit var clientList: ArrayList<CustomCustomer>
class FragmentAdminClients : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_clients,container,false)
        clientList = arrayListOf<CustomCustomer>()
        clientRecyclerView = view.rvAdminClients
        clientRecyclerView.layoutManager = LinearLayoutManager(activity)
        clientRecyclerView.setHasFixedSize(true)
        fetchClientList()
        return view
    }
    private fun fetchClientList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Client")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val item = snap.getValue(CustomCustomer::class.java)
                            clientList.add(item!!)
                        }
                        val adapter = ClientListAdapter(clientList)
                        clientRecyclerView.adapter = adapter
                        adapter.setOnItemClickListener(object : ClientListAdapter.OnItemClickListener{
                            override fun onItemClick(position: Int) {
                              Toast.makeText(activity,"i can be clicked!!",Toast.LENGTH_SHORT).show()
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