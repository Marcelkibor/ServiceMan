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
private lateinit var paymentList: ArrayList<BusinessAccount>

class ActivityBusinessPayShares : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentVetSharesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paymentList = arrayListOf<BusinessAccount>()
        auth = FirebaseAuth.getInstance()
        paymentRecyclerView = binding.rvCompletedPaymentss
        paymentRecyclerView.layoutManager = LinearLayoutManager(this@ActivityBusinessPayShares)
        paymentRecyclerView.setHasFixedSize(true)
        fetchPaymentList()
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedVetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchPaymentList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("BusinessAccount")
            dbRef.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.tvListStatuss.visibility = View.INVISIBLE
                        for (snap in snapshot.children) {
                            val item = snap.getValue(BusinessAccount::class.java)
                                paymentList.add(item!!)
                        }
                        val adapter = BusinessSharesAdapter(paymentList)
                        paymentRecyclerView.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            BusinessSharesAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val serviceId = paymentList[position].serviceId.toString()
                                val requestID = paymentList[position].paymentId.toString()
                                val payTime = paymentList[position].paymentTime.toString()
                                val payAmount = paymentList[position].paymentAmount.toString()
                                val intent = Intent(this@ActivityBusinessPayShares,ActivityBusinessSharesDetails::class.java)
                                intent.putExtra("requestID",requestID)
                                intent.putExtra("payTime",payTime)
                                intent.putExtra("payAmount",payAmount)
                                intent.putExtra("serviceId",serviceId)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        binding.tvListStatuss.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ActivityBusinessPayShares, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@ActivityBusinessPayShares, e.message, Toast.LENGTH_SHORT).show()

        }

    }
}