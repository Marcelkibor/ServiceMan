package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.FragmentAdminDoctorsBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_admin_clients.view.*
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentAdminDoctorsBinding
private lateinit var dbRef: DatabaseReference
private lateinit var doctorRecyclerView: RecyclerView
private lateinit var doctorList: ArrayList<VetDoctor>
private lateinit var docAdapter: DoctorListAdapter
private lateinit var tempArrayList: ArrayList<VetDoctor>
private lateinit var backUpArray:ArrayList<VetDoctor>


class ActivityAdminServiceProviders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = FragmentAdminDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doctorList = arrayListOf<VetDoctor>()
        tempArrayList = arrayListOf<VetDoctor>()
        backUpArray = arrayListOf<VetDoctor>()
        doctorRecyclerView = binding.rvAdminDoc
        doctorRecyclerView.layoutManager = LinearLayoutManager(this)
        doctorRecyclerView.setHasFixedSize(true)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AdminPanel::class.java)
            startActivity(intent)
            finish()


        }
        fetchDoctorList()

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun  filterRec(text:String){
        for (item in doctorList) {
            if (item.doctorName.toLowerCase().contains(text.toLowerCase())) {
                tempArrayList.add(item)
            }
        }
        docAdapter.filterList(tempArrayList)
        docAdapter = DoctorListAdapter(tempArrayList)
        doctorRecyclerView.adapter = docAdapter
        docAdapter.notifyDataSetChanged()
//        doctorList.clear()
    }

    private fun fetchDoctorList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("ServiceMan")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetDoctor::class.java)
                            doctorList.add(item!!)
                        }
                        backUpArray.addAll(doctorList)
                        docAdapter = DoctorListAdapter(doctorList)
                        doctorRecyclerView.adapter = docAdapter
                        docAdapter.setOnItemClickListener(object :
                            DoctorListAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val intent = Intent(
                                    this@ActivityAdminServiceProviders,
                                    ActivityAdminDoctorDetails::class.java
                                )
                                val docName = doctorList[position].doctorName
                                val docEmail = doctorList[position].doctorEmail
                                val docUID = doctorList[position].doctorUid
                                intent.putExtra("docName", docName)
                                intent.putExtra("docEmail", docEmail)
                                intent.putExtra("docUid", docUID)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        Toast.makeText(this@ActivityAdminServiceProviders, "No Data!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ActivityAdminServiceProviders, error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@ActivityAdminServiceProviders, e.message, Toast.LENGTH_SHORT).show()

        }

    }
}