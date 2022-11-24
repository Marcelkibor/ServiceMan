package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.petvet.databinding.AdminPanelBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@SuppressLint("StaticFieldLeak")
private lateinit var binding: AdminPanelBinding
private lateinit var toggle: ActionBarDrawerToggle
private lateinit var clientList: ArrayList<CustomCustomer>
private lateinit var doctorList: ArrayList<VetDoctor>
private lateinit var paymentList: ArrayList<Payment>
private lateinit var requestList: ArrayList<ClientRequest>
private lateinit var serviceList: ArrayList<VetService>
private lateinit var clientsNum: String
private lateinit var vetsNum: String
private lateinit var paymentsNum: String
private lateinit var requestsNum: String
private lateinit var servicesNum: String
private lateinit var auth:FirebaseAuth
private lateinit var displayName:String
private lateinit var displayEmail:String
private lateinit var dbRef: DatabaseReference


class AdminPanel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        fetchAdminDetails()
        super.onCreate(savedInstanceState)
        binding = AdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(this, binding.adminDrawer, R.string.open, R.string.close)
        binding.adminDrawer.addDrawerListener(toggle)
        toggle.syncState()
        auth = FirebaseAuth.getInstance()
        clientList = arrayListOf<CustomCustomer>()
        paymentList = arrayListOf<Payment>()
        doctorList = arrayListOf<VetDoctor>()
        requestList = arrayListOf<ClientRequest>()
        serviceList = arrayListOf<VetService>()
        fetchClients()
        fetchVets()
        fetchRequests()
        fetchServices()
        fetchPayments()
        binding.drawerIcon.setOnClickListener {
            binding.adminDrawer.open()
        }
        binding.adminNavBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.admin_home -> adminPanel()
                R.id.admin_register -> registerDoctor()
                R.id.admin_orders -> openRequest()
                R.id.admin_payments -> openPayments()
                R.id.admin_clients -> openClientList()
                R.id.admin_vets -> openDoctorsList()
                R.id.admin_logout -> adminLogout()
            }
            true
        }
        binding.cardDocLists.setOnClickListener {
            val intent = Intent(this, ActivityAdminServiceProviders::class.java)
            startActivity(intent)
        }
        binding.cardPayments.setOnClickListener {
            openPayments()
        }
        binding.cardRequests.setOnClickListener {
            openRequest()

        }
        binding.cardClients.setOnClickListener {
            openClientList()
        }
        binding.cardAnalytics.setOnClickListener {
            openAnalytics()
        }
        binding.cardShares.setOnClickListener {
            openShares()
        }
    }
    private fun fetchAdminDetails() {
        try {
            val vetDb = FirebaseDatabase.getInstance().getReference("Admin")
            vetDb.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(CustomCustomer::class.java)
                            if (item?.customerUid == auth.currentUser?.uid) {
//                                binding.vetDisplayName.setText("Welcome, Dr " + item?.doctorName)
//                                binding.vetDisplayName.visibility = View.VISIBLE
                                displayEmail = item?.email.toString()
                                displayName = item?.firstName.toString()
                                val navigationView = binding.adminNavBar as NavigationView
                                val header = navigationView.getHeaderView(0)
                                val vetName = header.findViewById<TextView>(R.id.tvAdminName)
                                val vetEmail = header.findViewById<TextView>(R.id.tvAdminEmail)
                                vetName.setText(displayName)
                                vetEmail.setText(displayEmail)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@AdminPanel,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@AdminPanel, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    private fun openShares() {
        val intent = Intent(this, ActivityBusinessPayShares::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchServices() {
        dbRef = FirebaseDatabase.getInstance().getReference("Service")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetService::class.java)
                            serviceList.add(item!!)
                        }
                        servicesNum = serviceList.size.toString()
                    } else {
                        Toast.makeText(
                            this@AdminPanel,
                            "error fetching vet services",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } catch (e: Exception) {
                    Toast.makeText(this@AdminPanel, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminPanel, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchRequests() {
        dbRef = FirebaseDatabase.getInstance().getReference("CompletedRequest")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(ClientRequest::class.java)
                            requestList.add(item!!)
                        }
                        requestsNum = requestList.size.toString()
                    } else {
                        Toast.makeText(
                            this@AdminPanel,
                            "failed fetching requests",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@AdminPanel, e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminPanel, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun fetchPayments() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Payment")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(Payment::class.java)
                            paymentList.add(item!!)
                        }
                        paymentsNum = paymentList.size.toString()
                    } else {
                        sequenceOf(
                            Toast.makeText(
                                this@AdminPanel,
                                "error fetching payments",
                                Toast.LENGTH_SHORT
                            ).show()
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AdminPanel, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@AdminPanel, e.message, Toast.LENGTH_SHORT).show()

        }

    }

    private fun fetchClients() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Client")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(CustomCustomer::class.java)
                            clientList.add(item!!)
                        }
                        clientsNum = clientList.size.toString()
                    } else {
                        Toast.makeText(this@AdminPanel, "No Data!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AdminPanel, error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@AdminPanel, e.message, Toast.LENGTH_SHORT).show()

        }
    }

    private fun fetchVets() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Doctor")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetDoctor::class.java)
                            doctorList.add(item!!)
                        }
                        vetsNum = doctorList.size.toString()
                    } else {
                        Toast.makeText(this@AdminPanel, "No Data!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AdminPanel, error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@AdminPanel, e.message, Toast.LENGTH_SHORT).show()

        }

    }

    private fun openAnalytics() {
        if (::clientsNum.isInitialized ||
            ::vetsNum.isInitialized || ::servicesNum.isInitialized ||
            ::requestsNum.isInitialized || ::paymentsNum.isInitialized
        ) {
            val dialog = Dialog(this@AdminPanel)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.setCancelable(true)
            dialog.show()
            val intent = Intent(this, ActivityAdminAnalytics::class.java)
            intent.putExtra("totalClients", clientsNum)
            intent.putExtra("totalVets", vetsNum)
            intent.putExtra("totalServices", servicesNum)
            intent.putExtra("totalPayments", paymentsNum)
            intent.putExtra("totalRequests", requestsNum)
            startActivity(intent)
            dialog.hide()
        } else {
            Toast.makeText(this, "Fetching,Please try again!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun adminLogout() {
        val progressBar = ProgressDialog(this)
        progressBar.setMessage("Logging out...")
        progressBar.setCancelable(true)
        progressBar.show()
        val intent = Intent(this, SelectRegister::class.java)
        startActivity(intent)
        Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun adminPanel() {
        val intent = Intent(this, AdminPanel::class.java)
        startActivity(intent)
        finish()
        binding.adminDrawer.close()
    }

    private fun openDoctorsList() {
        val intent = Intent(this, ActivityAdminServiceProviders::class.java)
        startActivity(intent)
        binding.adminDrawer.close()
    }

    private fun openClientList() {
        val intent = Intent(this, ActivityAdminClients::class.java)
        startActivity(intent)
        binding.adminDrawer.close()
    }

    private fun openRequest() {
        val intent = Intent(this, ActivityAdminRequests::class.java)
        startActivity(intent)
    }

    private fun openPayments() {
        val intent = Intent(this, AdminPayments::class.java)
        startActivity(intent)
    }

    private fun registerDoctor() {
        val intent = Intent(this, ServiceManRegisterActivity::class.java)
        startActivity(intent)
    }

}
