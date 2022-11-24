package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.AuthenticatedClientBinding
import com.example.petvet.databinding.FragmentServiceListBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentServiceListBinding
private lateinit var dbRef: DatabaseReference
private lateinit var serviceList: ArrayList<VetService>
private lateinit var tempArray:ArrayList<VetService>
private lateinit var doctorRecyclerView: RecyclerView
class ServiceListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentServiceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doctorRecyclerView = binding.serRv
        binding.backArrow.isClickable = true
        binding.backArrow.setOnClickListener{
            val intent = Intent(this,AuthenticatedServiceManActivity::class.java)
            startActivity(intent)
        }

        doctorRecyclerView.layoutManager = LinearLayoutManager(this@ServiceListActivity)
        doctorRecyclerView.setHasFixedSize(true)
        serviceList = arrayListOf<VetService>()
        tempArray = arrayListOf<VetService>()
        fetchServiceList()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_service,menu)
        val item = menu?.findItem(R.id.icon_search_service)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {

                tempArray.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    serviceList.forEach{
                        if(it.serviceName.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArray.add(it)
                        }
                    }
                    doctorRecyclerView.adapter!!.notifyDataSetChanged()
                }else{
                    tempArray.clear()
                    tempArray.addAll(serviceList)
                    doctorRecyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun fetchServiceList() {
        dbRef = FirebaseDatabase.getInstance().getReference("Service")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val item = snap.getValue(VetService::class.java)
                        serviceList.add(item!!)
                    }
                    tempArray.addAll(serviceList)
                    val adapter = ServiceListAdapter(tempArray)
                    binding.serRv.adapter = adapter
                    adapter.setOnItemClickListener(object : ServiceListAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@ServiceListActivity,EditServiceActivity::class.java)
                            val servName = serviceList[position].serviceName
                            val servCategory = serviceList[position].category
                            intent.putExtra("serviceName",servName)
                            intent.putExtra("serviceCategory",servCategory)
                            startActivity(intent)
                            finish()
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ServiceListActivity,error.message,Toast.LENGTH_SHORT).show()
            }
        })    }
    }

