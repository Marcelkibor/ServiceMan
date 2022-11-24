package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import java.util.*
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.petvet.databinding.FragmentAddServiceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentAddServiceBinding
private lateinit var auth: FirebaseAuth
private lateinit var st: String
private lateinit var dialog: Dialog
class ActivityVetAddService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val genderList = listOf("Male", "Female", "Other")
        auth = FirebaseAuth.getInstance()
        val adapter =
            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, genderList)
        binding.spCategory.adapter = adapter
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedVetActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                st = (adapterView?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.btAdd.setOnClickListener {
            checkCredentials()
        }
    }

    private fun checkCredentials() {
        val serviceName = binding.sName
        val category = binding.spCategory
        if (serviceName.text.toString().isEmpty()) {
            serviceName.error = "Add service!"
            serviceName.requestFocus()
            return
        }
        if (category.isEmpty()) {
            binding.spCategory.prompt
        } else {
            try {
                 dialog = Dialog(this@ActivityVetAddService)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(com.example.petvet.R.layout.custom_dialog)
                dialog.setCancelable(false)
                dialog.show()
                val doctorDbRef = FirebaseDatabase.getInstance().getReference("Service")
                val key =
                    FirebaseDatabase.getInstance().getReference("Service").push().key.toString()
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    val docService = VetService(key, userId, serviceName.text.toString(), st)
                    doctorDbRef.child(key).setValue(docService).addOnCompleteListener {
                        val intent = Intent(this, ActivityVetServiceList::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Service Added!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Null User Id!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}