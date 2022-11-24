package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.petvet.databinding.AuthenticatedClientBinding
import com.example.petvet.databinding.ClientRequestedServiceDetailsBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDateTime
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
private lateinit var binding: AuthenticatedClientBinding
private lateinit var dbRef: DatabaseReference
@SuppressLint("StaticFieldLeak")
private lateinit var clientDisplayName: TextView
@SuppressLint("StaticFieldLeak")
private lateinit var vetDb: DatabaseReference
private lateinit var auth: FirebaseAuth
private lateinit var toggle: ActionBarDrawerToggle
private lateinit var displayName:String
private lateinit var displayEmail:String

class AuthenticatedClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthenticatedClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        fetchClientName()
        fetchClientPhoto()
        setGreeting()
        clientDisplayName = binding.clientDisplayName
        toggle =
            ActionBarDrawerToggle(this, binding.ClientDrawerLayout, R.string.open, R.string.close)
        binding.ClientDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.drawerIcon.setOnClickListener {
            binding.ClientDrawerLayout.open()
        }
        binding.clientNavBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                //top fragments: home
                R.id.client_home -> goHome()
                R.id.client_search -> clientSearch()
                R.id.client_orders -> ordersHistory()
                //services fragments
                R.id.client_account -> openAccount()
                R.id.client_logout -> (Logout())
            }
            true
        }
        binding.cardSearch.setOnClickListener {
            val intent = Intent(this, ClientSearchServiceManActivity::class.java)
            startActivity(intent)
        }
        binding.accountCardView.setOnClickListener {
            openAccount()

        }
        binding.cardCompletedOrders.setOnClickListener {
            val intent = Intent(this, ClientOrderHistoryActivity::class.java)
            startActivity(intent)
        }
        binding.cardWallet.setOnClickListener {
            val intent = Intent(this, ActivityClientPayments::class.java)
            startActivity(intent)
        }
    }

    private fun fetchClientPhoto() {
        dbRef = FirebaseDatabase.getInstance().getReference("Profile")
        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(snap in snapshot.children){
                        val item = snap.getValue(ProfilePhoto::class.java)
                        if (item?.userId == auth.currentUser?.uid.toString()){
                            val imageUri = item.imageUri
                            val navigationView = binding.clientNavBar as NavigationView
                            val header = navigationView.getHeaderView(0)
                            val clientImageView = header.findViewById<ImageView>(R.id.clientImage)
                            Glide.with(baseContext).load(imageUri).into(clientImageView)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun openAccount() {
        val intent = Intent(this, ActivityClientEditAccount::class.java)
        startActivity(intent)
    }

    @SuppressLint("NewApi")
    private fun setGreeting() {
        val current = LocalDateTime.now()
        if (current.hour <= 11) {
            binding.greetingText.text = getString(R.string.morning)
        } else if (current.hour <= 16) {
            binding.greetingText.text = getString(R.string.afternoon)
        } else if (current.hour <= 21) {
            binding.greetingText.text =        getString(R.string.evening)
        } else {
            binding.greetingText.text = getString(R.string.night)
        }
    }

    private fun fetchClientName() {
        try {
            val clientUid = auth.currentUser?.uid
            vetDb = FirebaseDatabase.getInstance().getReference("Client")
            vetDb.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(CustomCustomer::class.java)
                            if (item?.customerUid == clientUid) {
                                clientDisplayName.setText("Welcome, " + item?.firstName)
                                clientDisplayName.visibility = View.VISIBLE
                                displayEmail = item?.email.toString()
                                displayName = item?.firstName.toString()
                                val navigationView = binding.clientNavBar as NavigationView
                                val header = navigationView.getHeaderView(0)
                                val clientName = header.findViewById<TextView>(R.id.tvClientName)
                                val clientEmail = header.findViewById<TextView>(R.id.tvClientEmail)
                                clientName.setText(displayName)
                                clientEmail.setText(displayEmail)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@AuthenticatedClientActivity,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@AuthenticatedClientActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun clientSearch() {
        val intent = Intent(this, ClientSearchServiceManActivity::class.java)
        startActivity(intent)
        binding.ClientDrawerLayout.close()
    }

    private fun ordersHistory() {
        val intent = Intent(this, ClientOrderHistoryActivity::class.java)
        startActivity(intent)
        binding.ClientDrawerLayout.close()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goHome() {
        val intent =
            Intent(this@AuthenticatedClientActivity, AuthenticatedClientActivity::class.java)
        startActivity(intent)
    }

    private fun Logout() {
        val intent = Intent(this@AuthenticatedClientActivity, SelectRegister::class.java)
        startActivity(intent)
        Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()
        finish()
    }
}