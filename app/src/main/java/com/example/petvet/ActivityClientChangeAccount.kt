package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.ActivityClientChangeAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityClientChangeAccountBinding
private lateinit var dbRef: DatabaseReference
private lateinit var auth: FirebaseAuth
private lateinit var firstName: String
private lateinit var lastName: String
private lateinit var phoneNumber: String
private lateinit var cEmail: String
private lateinit var cId: String
//not

class ActivityClientChangeAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        fetchCardViewDetails()
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityClientChangeAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, ActivityClientEditAccount::class.java)
            startActivity(intent)
        }
        binding.btEdit.setOnClickListener {
            submitEditedClient()

        }

    }

    private fun submitEditedClient() {
        val dialog = Dialog(this@ActivityClientChangeAccount)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)
        dialog.show()
        dbRef = FirebaseDatabase.getInstance().getReference("Client")
        val newClientDetails = mapOf<String, String>(
            "customerUid" to auth.currentUser?.uid.toString(),
            "email" to binding.edEmail.text.toString(),
            "firstName" to binding.edFirstName.text.toString(),
            "lastName" to binding.edLastName.text.toString(),
            "phone" to binding.edPhoneNumber.text.toString()
        )
        dbRef.child(auth.currentUser?.uid.toString()).updateChildren(newClientDetails)
            .addOnCompleteListener {
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AuthenticatedClientActivity::class.java)
                startActivity(intent)
                finish()
            }
    }

    private fun fetchCardViewDetails() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Client")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currUser = auth.currentUser?.uid.toString()
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(CustomCustomer::class.java)
                            if (item?.customerUid == currUser) {
//                            set layout details on cardView
                                firstName = item.firstName
                                lastName = item.lastName
                                phoneNumber = item.phone
                                cEmail = item.email
                                binding.edFirstName.setText(firstName)
                                binding.edLastName.setText(lastName)
                                binding.edPhoneNumber.setText(phoneNumber)
                                binding.edEmail.setText(cEmail)
                            }
                        }
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