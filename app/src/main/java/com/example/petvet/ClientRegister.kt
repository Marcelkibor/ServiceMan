package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.ClientRegisterActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
@SuppressLint("StaticFieldLeak")
private lateinit var binding: ClientRegisterActivityBinding
private lateinit var auth:FirebaseAuth
class ClientRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ClientRegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btRegister.setOnClickListener {
            checkCredentials()
        }
        binding.tvAccount.setOnClickListener {
            val intent = Intent(this,ClientLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    //verify the credentials
    private fun checkCredentials() {

        val clientFirstName =binding.edClientFirstName
        val clientLastName = binding.edClientLastName
        val clientPhone = binding.edClientPhone
        val clientEmail = binding.edClientEmail
        val clientPassword = binding.edClientPassword

        if (clientEmail.text.toString().isEmpty()) {
            clientEmail.error = "Enter Email!"
            clientEmail.requestFocus()
            return
        }
        if (clientFirstName.text.toString().isEmpty()){
            clientFirstName.error = "Enter First Name!"
        }
        if (clientLastName.text.toString().isEmpty()){
            clientLastName.error = "Enter Last Name!"
        }
        if (clientPhone.text.toString().isEmpty()){
            clientPhone.error = "Enter Phone Number!"
        }
        if (clientPassword.text.toString().isEmpty()) {
            clientPassword.error = "Enter Password!"
            clientPassword.requestFocus()
            return
        }
        if (clientPassword.length() < 5) {
            clientPassword.error = "Password too short!"
            clientPassword.requestFocus()
            return
        }
        else {
//        initialize the storage reference
            auth.createUserWithEmailAndPassword(
                binding.edClientEmail.text.toString(),
                binding.edClientPassword.text.toString(),
            )
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        val doctorDbRef = FirebaseDatabase.getInstance().getReference("Client")
                        val userId = auth.currentUser?.uid

                        if (userId != null) {
                            val myCustomUser = CustomCustomer(clientFirstName.text.toString(),clientLastName.text.toString(),clientPhone.text.toString(),clientEmail.text.toString(),userId)
                            doctorDbRef.child(userId).setValue(myCustomUser).addOnCompleteListener {
                                val intent = Intent(this,AuthenticatedClientActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        else{
                            Toast.makeText(this, "Null User Id!", Toast.LENGTH_SHORT).show()

                        }
                    }
                    else
                    {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}