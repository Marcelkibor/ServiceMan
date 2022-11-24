package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.AdminClientDetailsBinding
import com.example.petvet.databinding.AuthenticatedClientBinding
import com.example.petvet.databinding.FragmentOrderBinding
import com.example.petvet.databinding.FragmentSearchVetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_order.view.*
import kotlinx.android.synthetic.main.fragment_search_vet.view.*
import kotlin.collections.ArrayList
@SuppressLint("StaticFieldLeak")
private lateinit var binding:AdminClientDetailsBinding
private lateinit var firstName:String
private lateinit var lastName:String
private lateinit var email:String
private lateinit var clientId:String
private lateinit var phoneNumber:String


class ActivityAdminClientDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminClientDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, ActivityAdminClients::class.java)
            startActivity(intent)
            finish()
        }
         val bundle: Bundle? = intent.extras
        firstName = bundle?.getString("firstName").toString()
        lastName = bundle?.getString("lastName").toString()
        email = bundle?.getString("email").toString()
        clientId = bundle?.getString("clientId").toString()
        phoneNumber = bundle?.getString("phoneNumber").toString()

        binding.tvEmail.text = email
        binding.tvLastName.text = lastName
        binding.tvPhone.text = phoneNumber
        binding.tvFirstName.text = firstName
        binding.tvUid.text = clientId
    }
}