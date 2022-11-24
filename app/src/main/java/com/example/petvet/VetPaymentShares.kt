package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.FragmentAdminBillingsBinding
import com.example.petvet.databinding.FragmentVetSharesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentVetSharesBinding
private lateinit var dbRef: DatabaseReference
private lateinit var paymentRecyclerView: RecyclerView
private lateinit var auth: FirebaseAuth
private lateinit var paymentList: ArrayList<VetAccount>

class VetPaymentShares : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentVetSharesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paymentList = arrayListOf<VetAccount>()
        auth = FirebaseAuth.getInstance()
        paymentRecyclerView = binding.rvCompletedPaymentss
        paymentRecyclerView.layoutManager = LinearLayoutManager(this@VetPaymentShares)
        paymentRecyclerView.setHasFixedSize(true)
        fetchPaymentList()
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedServiceManActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchPaymentList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("VetAccount")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.tvListStatuss.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetAccount::class.java)
                            if (item?.vetId==auth.currentUser?.uid){
                                paymentList.add(item!!)
                            }
                        }
                        val adapter = VetSharesAdapter(paymentList)
                        paymentRecyclerView.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            VetSharesAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val requestID = paymentList[position].serviceId.toString()
                                val paymentId = paymentList[position].paymentId.toString()
                                val payTime = paymentList[position].paymentTime.toString()
                                val payAmount = paymentList[position].paymentAmount.toString()
                                val intent = Intent(this@VetPaymentShares,ActivityVetSharesDetails::class.java)
                                intent.putExtra("requestID",requestID)
                                intent.putExtra("paymentId",paymentId)
                                intent.putExtra("payTime",payTime)
                                intent.putExtra("payAmount",payAmount)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        binding.tvListStatuss.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@VetPaymentShares, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@VetPaymentShares, e.message, Toast.LENGTH_SHORT).show()

        }

    }
}