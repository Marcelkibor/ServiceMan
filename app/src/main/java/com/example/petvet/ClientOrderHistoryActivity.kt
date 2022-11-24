package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.AuthenticatedClientBinding
import com.example.petvet.databinding.FragmentOrderBinding
import com.example.petvet.databinding.FragmentSearchVetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_order.view.*
import kotlinx.android.synthetic.main.fragment_search_vet.view.*
import kotlin.collections.ArrayList
@SuppressLint("StaticFieldLeak")
private lateinit var binding:FragmentOrderBinding
private lateinit var auth: FirebaseAuth
private lateinit var dbRef: DatabaseReference
private lateinit var myOrdersList: ArrayList<ClientRequest>
private lateinit var rvClientOrders: RecyclerView
class ClientOrderHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvClientOrders = binding.rvClientOrders
        auth = FirebaseAuth.getInstance()
        rvClientOrders.layoutManager = LinearLayoutManager(this)
        rvClientOrders.setHasFixedSize(true)
        myOrdersList = arrayListOf<ClientRequest>()
        fetchClientRequest()
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,AuthenticatedClientActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun fetchClientRequest() {
        //need to update request table to include client id, then replace with the used database reference to get detail specifics
        try{
            dbRef = FirebaseDatabase.getInstance().getReference("CompletedRequest")
            dbRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children){
                            val item = snap.getValue(ClientRequest::class.java)
                            val clientUId = auth.currentUser?.uid
                            if (item?.clientID==clientUId){
                                myOrdersList.add(item!!)
                            }
                            val adapter = ClientRequestAdapter(myOrdersList)
                            rvClientOrders.adapter = adapter
                            adapter.setOnItemClickListener(object : ClientRequestAdapter.OnItemClickListener{
                                override fun onItemClick(position: Int) {
                                    val intent = Intent(this@ClientOrderHistoryActivity,ActivityClientRequestedService::class.java)
                                    val serviceName = myOrdersList[position].serviceName
                                    val category =myOrdersList[position].category
                                    val serviceID =myOrdersList[position].requestID
                                    val time = myOrdersList[position].requestTime
                                    val imageUri = myOrdersList[position].imageUri
                                    intent.putExtra("serviceName",serviceName)
                                    intent.putExtra("category",category)
                                    intent.putExtra("serviceID",serviceID)
                                    intent.putExtra("time",time)
                                    intent.putExtra("imageUri",imageUri)
                                    startActivity(intent)
                                    finish()
                                }
                            })
                        }
                    }
                    else{
                        binding.tvListStatus.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }catch (e:Exception){
            Toast.makeText(this@ClientOrderHistoryActivity,e.message, Toast.LENGTH_SHORT).show()
        }
    }
}