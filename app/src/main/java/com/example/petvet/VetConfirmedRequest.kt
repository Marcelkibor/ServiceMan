package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.petvet.databinding.VetConfirmedServiceDetailsBinding
import com.example.petvet.databinding.VetEditRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.vet_edit_request.*
@SuppressLint("StaticFieldLeak")
private lateinit var binding: VetConfirmedServiceDetailsBinding
private lateinit var dbRef: DatabaseReference
@SuppressLint("StaticFieldLeak")
private lateinit var auth: FirebaseAuth
private lateinit var client_uid: String
private lateinit var request_id: String
private lateinit var svService: String
private lateinit var clientUsername: String
private lateinit var imageUrl:String
private lateinit var svCategory: String
private lateinit var reqTime:String
class VetConfirmedRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VetConfirmedServiceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val bundle: Bundle? = intent.extras
        clientUsername = bundle?.getString("ClientName").toString()
        svService = bundle?.getString("ServiceName").toString()
        svCategory = bundle?.getString("AnimalCategory").toString()
        client_uid = bundle?.getString("clientUserID").toString()
        request_id = bundle?.getString("RequestId").toString()
        imageUrl =  bundle?.getString("imageUrl").toString()
        reqTime  = bundle?.getString("reqTime").toString()
        binding.tvClientDesc.text = clientUsername
        binding.tvAnimalCategory.text = svCategory
        Glide.with(baseContext).load(imageUrl).into(binding.requestImage)
        binding.tvServiceRequest.text = svService
        binding.tvRequestedTimeDesc.text = reqTime
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, ActivityVetPendingRequests::class.java)
            startActivity(intent)
            finish()
        }
    }
}

