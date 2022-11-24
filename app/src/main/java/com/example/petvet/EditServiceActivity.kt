package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.EditServiceActivityBinding
import com.google.firebase.database.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: EditServiceActivityBinding
private lateinit var dbRef:DatabaseReference
private lateinit var postID: String
class EditServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getPostId()
        super.onCreate(savedInstanceState)
        binding = EditServiceActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.serviceImage.isClickable = true
        binding.serviceImage.setOnClickListener(){
            deleteService()
        }
//        binding.backArrow.isClickable=  true
//        binding.backArrow.setOnClickListener {
//            val intent = Intent(this,ServiceListActivity::class.java)
//            startActivity(intent)
//        }


        setDetails()
}
    private fun getPostId() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Service")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val item = snap.getValue(VetService::class.java)
                            postID = item!!.serviceID
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@EditServiceActivity,error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e:Exception){
            Toast.makeText(this@EditServiceActivity,e.message, Toast.LENGTH_SHORT).show()

        }
    }
    private fun deleteService() {
//first fetch service data to obtain the postID
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Service").child(postID)
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        snapshot.ref.removeValue()
                        Toast.makeText(this@EditServiceActivity,"Service deleted!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@EditServiceActivity,AuthenticatedServiceManActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@EditServiceActivity,error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
        catch (e:Exception){
            Toast.makeText(this@EditServiceActivity,e.message, Toast.LENGTH_SHORT).show()

        }
    }
    private fun setDetails() {
        val bundle: Bundle? = intent.extras
        val servName = bundle?.getString("vetService")
        val servCategory = bundle?.getString("serviceCategory")
        binding.tvServiceName.text = servName
        binding.tvCategory.text = servCategory
    }
}


