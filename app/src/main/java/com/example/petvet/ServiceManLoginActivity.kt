package com.example.petvet
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.VetLoggActivityBinding
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("StaticFieldLeak")
private lateinit var binding: VetLoggActivityBinding
private lateinit var auth: FirebaseAuth
private lateinit var dialog: Dialog
class ServiceManLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = VetLoggActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.setOnClickListener {
            val intent = Intent(this,ServiceManRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btLogin.setOnClickListener {
            authenticate()
        }
    }
    private fun authenticate() {
        try{
             dialog = Dialog(this@ServiceManLoginActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.setCancelable(false)
            dialog.show()
            auth.signInWithEmailAndPassword(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, AuthenticatedVetActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        dialog.dismiss()
                        dialog.hide()
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
        catch (e: Exception){
            Toast.makeText(this,"${e.message}",Toast.LENGTH_SHORT).show()
        }
    }
    }