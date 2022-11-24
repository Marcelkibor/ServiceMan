package com.example.petvet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.petvet.databinding.AuthenticatedVatActivityBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@SuppressLint("StaticFieldLeak")
private lateinit var binding: AuthenticatedVatActivityBinding
private lateinit var serviceList: ArrayList<VetService>
private lateinit var toggle: ActionBarDrawerToggle
private lateinit var auth: FirebaseAuth
private lateinit var displayName:String
private lateinit var displayEmail:String
@SuppressLint("StaticFieldLeak")

class AuthenticatedServiceManActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        fetchDisplayName()
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        serviceList = arrayListOf<VetService>()
        binding = AuthenticatedVatActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.drawerIcon.setOnClickListener {
            binding.vetDrawerLayout.open()
        }
        binding.cardMyAccount.setOnClickListener{
            openAccount()
        }
//
        toggle = ActionBarDrawerToggle(this, binding.vetDrawerLayout, R.string.open, R.string.close)

        binding.vetDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.drawerIcon.isClickable = true
        binding.vetNavBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                //home
                R.id.vet_home -> goHome()
                R.id.pending_request -> openPending()
                R.id.track_request -> openTrack()
                R.id.vet_service -> openServices()
                R.id.add_service -> addServices()
                R.id.billings -> openBillings()
                R.id.cleared_request -> confirmedRequest()
                R.id.vet_account -> replaceFragments(FragmentVetProfile())
                R.id.vet_logout -> vetLogout()
            }
            true
        }
        binding.cardWallet.setOnClickListener {
            openBillings()
        }
        binding.cardPending.setOnClickListener {
            val intent = Intent(this, ActivityPendingRequests::class.java)
            startActivity(intent)
        }
        binding.cardTrack.setOnClickListener {
            openTrack()
        }
        binding.cardMyServices.setOnClickListener {
            openServices()
        }
        binding.cardAddService.setOnClickListener {
            val intent = Intent(this, ActivityVetAddService::class.java)
            startActivity(intent)
        }
        binding.cardCompletedOrders.setOnClickListener {
            val intent = Intent(this, ActivityDoctorConfirmedOrders::class.java)
            startActivity(intent)
        }
    }

    private fun openAccount() {
        val intent = Intent(this,ActivityVetEditAccount::class.java)
        startActivity(intent)
    }

    private fun fetchDisplayName() {
        try {
            val vetDb = FirebaseDatabase.getInstance().getReference("ServiceMan")
            vetDb.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetDoctor::class.java)
                            if (item?.doctorUid == auth.currentUser?.uid) {
                                binding.vetDisplayName.setText("Welcome," + item?.doctorName)
                                binding.vetDisplayName.visibility = View.VISIBLE
                                displayEmail = item?.doctorEmail.toString()
                                displayName = item?.doctorName.toString()
                                val navigationView = binding.vetNavBar as NavigationView
                                val header = navigationView.getHeaderView(0)
                                val vetName = header.findViewById<TextView>(R.id.tvVetNameNav)
                                val vetEmail = header.findViewById<TextView>(R.id.tvVetEmail)
                                vetName.setText(displayName)
                                vetEmail.setText(displayEmail)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@AuthenticatedServiceManActivity,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@AuthenticatedServiceManActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openTrack() {
        val intent = Intent(this, ActivityTrackRequest::class.java)
        startActivity(intent)
    }

    private fun openBillings() {
        val intent = Intent(this, VetPaymentShares::class.java)
        startActivity(intent)
    }

    private fun confirmedRequest() {
        val intent = Intent(this, ActivityDoctorConfirmedOrders::class.java)
        startActivity(intent)
    }

    private fun openServices() {
        val intent = Intent(this, ActivityVetServiceList::class.java)
        startActivity(intent)
    }

    private fun addServices() {
        val intent = Intent(this, ActivityVetAddService::class.java)
        startActivity(intent)
    }

    private fun openPending() {
        val intent = Intent(this, ActivityPendingRequests::class.java)
        startActivity(intent)
    }

    private fun goHome() {
        val intent = Intent(this, AuthenticatedServiceManActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragments(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.vet_frame_layout, fragment)
        fragmentTransaction.commit()
        binding.vetDrawerLayout.closeDrawers()
    }

    private fun vetLogout() {
        val intent = Intent(this, SelectRegister::class.java)
        startActivity(intent)
        Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()
        finish()
        auth.signOut()
    }

    private fun clearedRequest() {
        val intent = Intent(this, ClearedRequestActivity::class.java)
        startActivity(intent)
        finish()
    }
}

