package com.example.petvet
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.petvet.databinding.VetRegisterActivityBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
@SuppressLint("StaticFieldLeak")
private lateinit var binding: VetRegisterActivityBinding
private lateinit var auth: FirebaseAuth
private lateinit var VetLat:String
private lateinit var VetLong:String
private lateinit var doctorID: String
@SuppressLint("StaticFieldLeak")
private lateinit var fusedLocationProviderClient:FusedLocationProviderClient
class ServiceManRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        auth = FirebaseAuth.getInstance()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate(savedInstanceState)
        binding = VetRegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,AdminPanel::class.java)
            startActivity(intent)
            finish()
        }

        binding.btRegister.setOnClickListener{
            checkCredentials()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getServiceManLocation() {
        if (checkPermission()){

            if (locationIsEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        this,android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )!= PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    enablePermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){
                    val location: Location?=it.result
                    if (location==null){
                        Toast.makeText(this,"Location Null", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val vetLocationDB = FirebaseDatabase.getInstance().getReference("ServiceManLocation")
                        VetLat = location.latitude.toString()
                        VetLong = location.longitude.toString()
                        val doctorLocation = DoctorLocation(VetLong, VetLat, doctorID)
                        vetLocationDB.child(doctorID).setValue(doctorLocation).addOnCompleteListener {
                        }
                    }
                }
            }
//        enable location
            else{
                Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        //        enable permission
        else{
            enablePermission()
        }
    }
    private fun locationIsEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun enablePermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }
    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION =100
    }
    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)==
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.
            Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getServiceManLocation()
            }
            else{
            }
        }
    }

    //verify the credentials
private fun checkCredentials() {

    val username = binding.edUsername
    val email = binding.edEmail
    val password = binding.edPassword
    if (email.text.toString().isEmpty()) {
        email.error = "Empty email"
        email.requestFocus()
        return
    }

    if (password.text.toString().isEmpty()) {
        password.error = "Password cannot be empty"
        password.requestFocus()
        return
    }
    if (password.length() < 5) {
        password.error = "Password too short!"
        password.requestFocus()
        return
    }
    if (username.text.toString().isEmpty()) {
        username.error = "Empty Username"
    } else {
        val dialog = Dialog(this@ServiceManRegisterActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)
        dialog.show()
//        initialize the storage reference
        auth.createUserWithEmailAndPassword(
            binding.edEmail.text.toString(),
            binding.edPassword.text.toString(),
        )
.addOnCompleteListener { task->
    if (task.isSuccessful){
        val doctorDbRef = FirebaseDatabase.getInstance().getReference("ServiceMan")
        val userId = auth.currentUser?.uid
        val doctorName = binding.edUsername.text.toString()
        val doctorEmail = binding.edEmail.text.toString()
        if (userId != null) {
            val newDoc = VetDoctor(doctorName,doctorEmail,userId,binding.edPassword.text.toString(),binding.edLastName.text.toString(),binding.edPhone.text.toString())
            doctorDbRef.child(userId).setValue(newDoc).addOnCompleteListener {
                val intent = Intent(this,ActivityAdminServiceProviders::class.java)
                startActivity(intent)
                doctorID = userId
                Toast.makeText(this@ServiceManRegisterActivity,"Successfully Registered",Toast.LENGTH_SHORT).show()
                getServiceManLocation()
                finish()
            }
        }
    else{
    Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show()
    }    }
    else
    {
        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
    }
}
    }
}

}