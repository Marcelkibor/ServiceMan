package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.system.Os.close
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import com.example.petvet.databinding.AuthenticatedVatActivityBinding
import com.example.petvet.databinding.ClearedRequestActivityBinding
import com.example.petvet.databinding.FragmentRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_request.view.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding:FragmentRequestBinding
private lateinit var dbRef:DatabaseReference
private lateinit var requestList: ArrayList<ClientConfirmRequest>
class ClearedRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRequestBinding.inflate(layoutInflater)
        requestList = arrayListOf()
        setContentView(binding.root)
//        setTabs()
        setRequests()
    }
    private fun setTabs() {
        val adapter = FragmentsAdapter(supportFragmentManager)
        adapter.addFrag(FragmentAccepted(),"Accepted")
//        adapter.addFrag(FragmentRejected(),"Rejected")
//        binding.viewPager.adapter = adapter
//        binding.tabLayout.setupWithViewPager(binding.viewPager)
        //set icons
//        binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.account)
//        binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.vet)

    }
    private fun setRequests() {
        dbRef = FirebaseDatabase.getInstance().getReference("Accept")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val item = snap.getValue(ClientConfirmRequest::class.java)
                            requestList.add(item!!)
                            Toast.makeText(this@ClearedRequestActivity,item.actualService,Toast.LENGTH_SHORT).show()
                        }
                        val adapter = AcceptedRequestAdapter(requestList)
                        binding.requestRv.adapter = adapter
                    }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ClearedRequestActivity,error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}