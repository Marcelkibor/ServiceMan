package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.ClientPaymentDetailsBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ClientPaymentDetailsBinding

class ActivityAdminPayDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClientPaymentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        val requestID = bundle?.getString("requestID")
        val paymentId = bundle?.getString("paymentId")
        val payTime = bundle?.getString("payTime")
        val payAmount = bundle?.getString("payAmount")
        binding.tvPayment.text = paymentId
        binding.tvAmount.text = payAmount
        binding.tvTime.text = payTime
        binding.tvServiceId.text = requestID
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,AdminPayments::class.java)
            startActivity(intent)
            finish()
        }
    }
}