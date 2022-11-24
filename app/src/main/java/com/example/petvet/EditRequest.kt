package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.petvet.databinding.VetEditRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.vet_edit_request.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: VetEditRequestBinding
private lateinit var dbRef: DatabaseReference

@SuppressLint("StaticFieldLeak")
lateinit var radioButton: RadioButton
private lateinit var auth: FirebaseAuth
private lateinit var client_uid: String
private lateinit var request_id: String
private lateinit var svService: String
private lateinit var requestTime:String
private lateinit var clientUsername: String
private lateinit var svCategory: String
private lateinit var svDesc:String
private lateinit var serviceArea:String
private lateinit var animAge:String
private lateinit var imageUri:Uri
private lateinit var vet_id:String
class EditRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VetEditRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val bundle: Bundle? = intent.extras
        svDesc = bundle?.getString("svDesc").toString()
        animAge = bundle?.getString("animAge").toString()
        serviceArea =  bundle?.getString("serviceArea").toString()
        clientUsername = bundle?.getString("ClientName").toString()
        svService = bundle?.getString("ServiceName").toString()
        svCategory = bundle?.getString("AnimalCategory").toString()
        client_uid = bundle?.getString("clientUserID").toString()
        requestTime = bundle?.getString("requestTime").toString()
        request_id = bundle?.getString("RequestId").toString()
         imageUri = bundle?.getString("imageUri")?.toUri()!!
        vet_id = bundle.getString("vet_id").toString()
            animAge = bundle.getString("animAge").toString()
        Glide.with(baseContext).load(imageUri).into(binding.servPhoto)
        binding.tvClientDesc.text = clientUsername
        binding.tvServiceArea.text = serviceArea
        binding.tvServiceRequest.text = svCategory
        binding.tvRequestedTimeDesc.text = requestTime
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, ActivityPendingRequests::class.java)
            startActivity(intent)
        }
        binding.btConfirm.setOnClickListener {
            vetClearRequest()
        }
    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    private fun vetClearRequest() {
        try {
            val dialog = Dialog(this@EditRequest)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.setCancelable(false)
            dialog.show()
            val intSelectButton: Int = radioGroup.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            val clearedRequestDb =
                FirebaseDatabase.getInstance().getReference("${radioButton.text}")
            val key = FirebaseDatabase.getInstance().getReference("${radioButton.text}")
                .push().key.toString()
            val userId = auth.currentUser?.uid
            val currDay = Calendar.getInstance()
            val currTime = SimpleDateFormat("M / d / Y / H:m:s ").format(currDay.time).toString()
            if (userId != null) {
                val clientConfirmRequest = ClientRequest(
                   request_id, svService, svDesc, serviceArea, svCategory, client_uid,
                    imageUri.toString(), requestTime, clientUsername,vet_id
                )
                clearedRequestDb.child(key).setValue(clientConfirmRequest).addOnCompleteListener {
                    deleteRequest()
                }
            } else {
                Toast.makeText(this, "Null User Id!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteRequest() {
//first fetch service data to obtain the postID
        try {
            val progressBar = ProgressDialog(this)
            progressBar.setMessage("Processing...")
            progressBar.setCancelable(true)
            progressBar.show()
            dbRef = FirebaseDatabase.getInstance().getReference("Request").child(request_id)
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        snapshot.ref.removeValue()
                        Toast.makeText(this@EditRequest, "Success!", Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(this@EditRequest, AuthenticatedServiceManActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@EditRequest, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@EditRequest, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}

