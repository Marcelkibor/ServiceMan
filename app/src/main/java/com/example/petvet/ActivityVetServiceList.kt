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
import com.example.petvet.databinding.FragmentServiceListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var requestList:ArrayList<VetService>
private lateinit var serviceRecyclerView: RecyclerView
@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentServiceListBinding
private lateinit var auth:FirebaseAuth
private lateinit var dbRef:DatabaseReference
class ActivityVetServiceList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentServiceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequests()
        auth = FirebaseAuth.getInstance()
        serviceRecyclerView = binding.serRv
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,AuthenticatedServiceManActivity::class.java)
            startActivity(intent)
            finish()
        }
        serviceRecyclerView.layoutManager = LinearLayoutManager(this)
        serviceRecyclerView.setHasFixedSize(true)
        requestList = arrayListOf<VetService>()
    }
    private fun setRequests() {
        dbRef = FirebaseDatabase.getInstance().getReference("Service")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetService::class.java)
                            if (item?.vetID==auth.currentUser?.uid){
                                requestList.add(item!!)
                            }
                        }
                        val adapter = ServiceListAdapter(requestList)
                        binding.serRv.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            ServiceListAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val serviceName = requestList[position].serviceName
                                val serviceCategory = requestList[position].category
                                val intent = Intent(this@ActivityVetServiceList,EditServiceActivity::class.java)
                                intent.putExtra("vetService",serviceName)
                                intent.putExtra("serviceCategory",serviceCategory)
                                startActivity(intent)
                                finish()
                            }
                        })
                    }
                    else{
                        binding.tvListStatus.visibility = View.VISIBLE

                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ActivityVetServiceList, e.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityVetServiceList, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

