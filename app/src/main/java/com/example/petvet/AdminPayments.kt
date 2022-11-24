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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentAdminBillingsBinding
private lateinit var dbRef: DatabaseReference
private lateinit var paymentRecyclerView: RecyclerView
private lateinit var paymentList: ArrayList<Payment>

class AdminPayments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAdminBillingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paymentList = arrayListOf<Payment>()
        paymentRecyclerView = binding.rvCompletedPayments
        paymentRecyclerView.layoutManager = LinearLayoutManager(this@AdminPayments)
        paymentRecyclerView.setHasFixedSize(true)
        fetchPaymentList()
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AdminPanel::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchPaymentList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Payment")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(Payment::class.java)
                            paymentList.add(item!!)
                        }
                        val adapter = PaymentListAdapter(paymentList)
                        paymentRecyclerView.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            PaymentListAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val requestID = paymentList[position].requestUid.toString()
                                val paymentId = paymentList[position].paymentId.toString()
                                val payTime = paymentList[position].paymentTime.toString()
                                val payAmount = paymentList[position].paymentAmount.toString()
                                val intent = Intent(this@AdminPayments,ActivityAdminPayDetails::class.java)
                                intent.putExtra("requestID",requestID)
                                intent.putExtra("paymentId",paymentId)
                                intent.putExtra("payTime",payTime)
                                intent.putExtra("payAmount",payAmount)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        binding.tvListStatus.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AdminPayments, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@AdminPayments, e.message, Toast.LENGTH_SHORT).show()

        }

    }
}