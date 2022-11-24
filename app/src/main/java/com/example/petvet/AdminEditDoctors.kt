package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.AdminEditDoctorRequestBinding
import com.example.petvet.databinding.VetEditRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.vet_edit_request.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: AdminEditDoctorRequestBinding
private lateinit var dbRef: DatabaseReference
@SuppressLint("StaticFieldLeak")
lateinit var radioButtons: RadioButton
private lateinit var auth: FirebaseAuth
class AdminEditDoctors : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?){
super.onCreate(savedInstanceState)
binding = AdminEditDoctorRequestBinding.inflate(layoutInflater)
setContentView(binding.root)
auth = FirebaseAuth.getInstance()
    //fetch bundles
    val bundle: Bundle? = intent.extras
    val  vetDocUid = bundle?.getString("VetDocUID").toString()
    val  vetDocEmail  = bundle?.getString("VetDocEmail").toString()
    val  vetDocName  = bundle?.getString("VetDocName").toString()
    //set text to tViews
    binding.edDocName.text = vetDocName
    binding.edDoctorId.text  = vetDocUid
    binding.edEmail.text = vetDocEmail
    binding.backArrow.setOnClickListener {
val intent = Intent(this,AdminPanel::class.java)
startActivity(intent)
}
val  radioGroup = binding.radioGroup
binding.btConfirm.setOnClickListener {
//     vetClearRequest()
    }
}
//    private fun vetClearRequest() {
//        try {
//            val intSelectButton: Int = radioGroup.checkedRadioButtonId
//            radioButton = findViewById(intSelectButton)
//            val clearedRequestDb = FirebaseDatabase.getInstance().getReference("${radioButton.text}")
//            val key = FirebaseDatabase.getInstance().getReference("${radioButton.text}").push().key.toString()
//            val userId = auth.currentUser?.uid
//            if (userId != null) {
//                val clientConfirmRequest = ClientConfirmRequest(clientUsername,key,
//                    client_uid,svCategory,svService)
//                clearedRequestDb.child(key).setValue(clientConfirmRequest).addOnCompleteListener {
//                    deleteRequest()
//                }
//               //push the  request under declined/ accepted requests, and clear it from current list of items
//            }
//            else{
//                Toast.makeText(this, "Null User Id!", Toast.LENGTH_SHORT).show()
//            }
//        }catch (e:Exception) {
//            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun deleteRequest() {
////first fetch service data to obtain the postID
//try {
//dbRef = FirebaseDatabase.getInstance().getReference("ClientRequest").child(request_id)
//dbRef.addValueEventListener(object : ValueEventListener {
//override fun onDataChange(snapshot: DataSnapshot) {
//if (snapshot.exists()){
//    snapshot.ref.removeValue()
//    Toast.makeText(this@AdminEditDoctors,"Success!", Toast.LENGTH_SHORT).show()
//    val intent = Intent(this@AdminEditDoctors,AuthenticatedVetActivity::class.java)
//    startActivity(intent)}}
//override fun onCancelled(error: DatabaseError) {
//Toast.makeText(this@AdminEditDoctors,error.message, Toast.LENGTH_SHORT).show()
//}
//})
//}
//catch (e:Exception){
//Toast.makeText(this@AdminEditDoctors,e.message, Toast.LENGTH_SHORT).show()
//}
//    }
}

