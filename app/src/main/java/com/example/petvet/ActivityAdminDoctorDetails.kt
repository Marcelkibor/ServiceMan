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
import com.example.petvet.databinding.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_order.view.*
import kotlinx.android.synthetic.main.fragment_search_vet.view.*
import kotlin.collections.ArrayList
@SuppressLint("StaticFieldLeak")
private lateinit var binding:AdminDoctorDetailsBinding
private lateinit var docName:String
private lateinit var docEmail:String
private lateinit var docUid:String
class ActivityAdminDoctorDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminDoctorDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        docName = bundle?.getString("docName").toString()
        docEmail = bundle?.getString("docEmail").toString()
        docUid =  bundle?.getString("docUid").toString()

        binding.backArrow.setOnClickListener {
            val intent = Intent(this, ActivityAdminDoctors::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvDocEmail.text = docEmail
        binding.tvDocName.text = docName
        binding.tvDocUid.text = docUid
    }
}