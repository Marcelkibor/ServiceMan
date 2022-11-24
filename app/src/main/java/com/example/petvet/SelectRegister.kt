package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.SelectRegisterBinding
@SuppressLint("StaticFieldLeak")
private lateinit var binding: SelectRegisterBinding
class SelectRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btVetRegister.setOnClickListener{
            val intent = Intent(this,VetLoginActivity::class.java)
            startActivity(intent)
        }
        binding.btClientRegister.setOnClickListener {
            val intent = Intent(this,ClientLoginActivity::class.java)
            startActivity(intent)
        }
        binding.btAdminLogin.setOnClickListener{
            val intent = Intent(this,AdminLogin::class.java)
            startActivity(intent)
        }
    }
}