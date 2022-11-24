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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentBillingsBinding
private lateinit var auth: FirebaseAuth
private lateinit var dbRef: DatabaseReference
private lateinit var paymentList: ArrayList<Payment>
private lateinit var rvClientPayments: RecyclerView

class ActivityAdminPayments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBillingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvClientPayments = binding.rvClientPayments
        auth = FirebaseAuth.getInstance()
        rvClientPayments.layoutManager = LinearLayoutManager(this)
        rvClientPayments.setHasFixedSize(true)
        paymentList = arrayListOf<Payment>()
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedClientActivity::class.java)
            startActivity(intent)

        }
        fetchPayments()
    }

    private fun fetchPayments() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Payment")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.tvListStatus.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(Payment::class.java)
                                paymentList.add(item!!)
                            val adapter = PaymentListAdapter(paymentList)
                            rvClientPayments.adapter = adapter
                            adapter.setOnItemClickListener(object :
                                PaymentListAdapter.OnItemClickListener {
                                override fun onItemClick(position: Int) {
                                    val requestID = paymentList[position].requestUid.toString()
                                    val paymentId = paymentList[position].paymentId.toString()
                                    val payTime = paymentList[position].paymentTime.toString()
                                    val payAmount = paymentList[position].paymentAmount.toString()
                                    val intent = Intent(this@ActivityAdminPayments,ActivityAdminPayDetails::class.java)
                                   intent.putExtra("requestID",requestID)
                                    intent.putExtra("paymentId",paymentId)
                                    intent.putExtra("payTime",payTime)
                                    intent.putExtra("payAmount",payAmount)
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
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }
    }
}

