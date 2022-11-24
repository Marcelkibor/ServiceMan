package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.webkit.MimeTypeMap
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.ClientEditAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ClientEditAccountBinding
private lateinit var dbRef: DatabaseReference
private lateinit var auth: FirebaseAuth
private lateinit var vetID: String
private lateinit var imageUri: Uri


class ActivityVetEditAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        fetchCardViewDetails()
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ClientEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedVetActivity::class.java)
            startActivity(intent)
            finish()

        }
        binding.tvEdit.setOnClickListener {
            val intent = Intent(this, ActivityVetChangeAccount::class.java)
            intent.putExtra("vetId", vetID)
            startActivity(intent)
            finish()
        }
        binding.imageEdit
        binding.clientProfile.setOnClickListener {
            val galleryInt = Intent()
            galleryInt.action = Intent.ACTION_GET_CONTENT
            galleryInt.type = "image/*"
            startActivityForResult(galleryInt, 2)
        }
        binding.tvEditProfile.setOnClickListener {
            if (imageUri !== null) {
                submitRequest(imageUri)
            } else {
                Toast.makeText(this, "Select image!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitRequest(uri: Uri) {
        val fileRef =
            FirebaseStorage.getInstance().getReference("profile/" + getFileExtension(uri))
                .child(auth.currentUser?.uid.toString())
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val clientProfileData = ProfilePhoto(auth.currentUser?.uid.toString(),uri.toString())
                val clientProfile = FirebaseDatabase.getInstance().getReference("Profile")
                clientProfile.child(auth.currentUser?.uid.toString()).setValue(clientProfileData).addOnCompleteListener{
                    Toast.makeText(this@ActivityVetEditAccount,"Profile Updated",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,AuthenticatedClientActivity::class.java)
                    startActivity(intent)
                }
    }
        }
    }

    private fun fetchCardViewDetails() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Doctor")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currUser = auth.currentUser?.uid.toString()
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetDoctor::class.java)
                            if (item?.doctorUid == currUser) {
//                            set layout details on cardView
                                val firstName = item.doctorName
                                vetID = item.doctorUid
                                val lastName = item.docLastName
                                val phoneNumber = item.docPhone
                                val email = item.doctorEmail
                                binding.tvFirstName.text = firstName
                                binding.tvLastName.text = lastName
                                binding.tvPhoneNumber.text = phoneNumber
                                binding.tvAddress.text = email
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
    private fun getFileExtension(mUri: Uri): String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(mUri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            binding.clientProfile.setImageURI(imageUri)
        }
    }
}