package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.petvet.databinding.ClientPaymentDetailsBinding
import com.example.petvet.databinding.ClientRequestedServiceDetailsBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ClientRequestedServiceDetailsBinding

class ActivityClientRequestedService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClientRequestedServiceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        val serviceName = bundle?.getString("serviceName")
        val time = bundle?.getString("time")
        val category = bundle?.getString("category")
        val serviceID = bundle?.getString("serviceID")
        val imageUri = bundle?.getString("imageUri")
        Glide.with(baseContext).load(imageUri).into(binding.imgService)
        binding.tvServiceId.text =serviceID
        binding.tvAge.text =time
        binding.tvCategory.text =category
        binding.tvServiceName.text = serviceName
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,ClientOrderHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}