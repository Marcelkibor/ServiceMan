package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.petvet.databinding.ClientSelectServiceBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


@SuppressLint("StaticFieldLeak")
private lateinit var binding: ClientSelectServiceBinding

@SuppressLint("StaticFieldLeak")
private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
private lateinit var txGender: String
private lateinit var txCategory: String
private lateinit var locationList: ArrayList<DoctorLocation>
private lateinit var txService: String
private var finDist by Delegates.notNull<Double>()
private lateinit var vet_id: String
private lateinit var currTime: String
private lateinit var clientLat: String
private lateinit var imageUri: Uri
private lateinit var clientLong: String
private lateinit var vetLongitude: String
private lateinit var clientDb: DatabaseReference
private lateinit var vetLatitude: String
private lateinit var clientUsername: String
private lateinit var locationRequest: com.google.android.gms.location.LocationRequest
private lateinit var auth: FirebaseAuth

class ClientSelectServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getClientLocation()
        getVetLocation()
        fetchClientName()
        super.onCreate(savedInstanceState)
//        println("This is the activity")
        binding = ClientSelectServiceBinding.inflate(layoutInflater)
        val bundle: Bundle? = intent.extras
        vet_id = bundle?.getString("VetUID").toString()
        val firstName = bundle?.getString("docFirstName").toString()
        binding.tvDocName.text = firstName
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.servicePhoto.setOnClickListener {
            val galleryInt = Intent()
            galleryInt.action = Intent.ACTION_GET_CONTENT
            galleryInt.type = "image/*"
            startActivityForResult(galleryInt, 2)
        }
        locationList = arrayListOf<DoctorLocation>()
        val genderList = listOf("Male", "Female", "Other")
        val serviceList = listOf("AI", "Injury", "Vaccination")
        val categoryList = listOf("Dog", "Sheep", "Cattle", "Goat")
        val categoryAdapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categoryList
        )
        binding.spCategory.adapter = categoryAdapter
        val serviceAdapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, serviceList
        )
        binding.spServices.adapter = serviceAdapter
        val genderAdapter = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, genderList
        )
        binding.spSex.adapter = genderAdapter
//        initialize category adapter
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = adapterView?.getItemAtPosition(position).toString()
                txCategory = category
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
//        initialize service spinner
        binding.spServices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val service = adapterView?.getItemAtPosition(position).toString()
                txService = service
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //initialize gender spinner
        binding.spSex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val gender = adapterView?.getItemAtPosition(position).toString()
                txGender = gender
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.servicePhoto.setOnClickListener {
            val galleryInt = Intent()
            galleryInt.action = Intent.ACTION_GET_CONTENT
            galleryInt.type = "image/*"
            startActivityForResult(galleryInt, 2)
        }
        binding.btRequest.setOnClickListener {
            if (imageUri !== null) {
                submitRequest(imageUri)
            } else {
                Toast.makeText(this, "Select image!", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun fetchClientName() {
        try {
            clientDb = FirebaseDatabase.getInstance().getReference("Client")
            clientDb.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(CustomCustomer::class.java)
                            val cuId = auth.currentUser?.uid
                            if (cuId == item!!.customerUid.toString()) {
                                clientUsername = item.firstName.toString()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@ClientSelectServiceActivity,
                            "C-Error: 100",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ClientSelectServiceActivity,
                        error.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@ClientSelectServiceActivity, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun getVetLocation() {
        try {
            val dbRef = FirebaseDatabase.getInstance().getReference("DoctorLocation")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(DoctorLocation::class.java)
                            val docSt = vet_id
                            if (item!!.doctorUid == docSt) {
                                vetLatitude = item.doctorLatitude.toString()
                                vetLongitude = item.doctorLongitude.toString()
                            }
                            locationList.add(item)
                        }
                    } else {
                        Toast.makeText(
                            this@ClientSelectServiceActivity,
                            "Failed getting location!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ClientSelectServiceActivity,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@ClientSelectServiceActivity, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun getClientLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            checkGps()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )
        }
    }

    private fun checkGps() {
        locationRequest = com.google.android.gms.location.LocationRequest.create()
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result = LocationServices.getSettingsClient(this.applicationContext)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener {
            //when the gps is on
            try {
                val response = it.getResult(ApiException::class.java)
                thisClientLocation()
            } catch (e: ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        //sending the request to enable the gps
                        val resolveApiException = e as ResolvableApiException
                        resolveApiException.startResolutionForResult(this, 200)

                    } catch (sendIntentException: IntentSender.SendIntentException) {

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        //when the setting is unavailable
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun thisClientLocation() {
        try {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                val location = it.result
                if (location !== null) {
                    val geoCoder = Geocoder(this, Locale.getDefault())
                    val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
//       Toast.makeText(this, address[0].getAddressLine(0),Toast.LENGTH_SHORT).show()
                    clientLat = location.latitude.toString()
                    clientLong = location.longitude.toString()
                } else {
                    Toast.makeText(this, "C-ERROR 101", Toast.LENGTH_SHORT).show()

                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

    }


    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    private fun submitRequest(uri: Uri) {
//        Toast.makeText(this@ClientSelectServiceActivity, clientUsername,Toast.LENGTH_SHORT).show()
        val animalAge = binding.edAge
        val requestDescription = binding.edIssue
        if (animalAge.text.toString().isEmpty()) {
            animalAge.error = "Add age!"
            animalAge.requestFocus()
            return
        }
        if (requestDescription.text.toString().isEmpty()) {
            requestDescription.error = "Add details!"
            requestDescription.requestFocus()
        } else {
            try {
                val dialog = Dialog(this@ClientSelectServiceActivity)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.custom_dialog)
                dialog.setCancelable(false)
                dialog.show()
                val key = FirebaseDatabase.getInstance().getReference("Request")
                    .push().key.toString()
                val userId = auth.currentUser?.uid
                val fileRef =
                    FirebaseStorage.getInstance().getReference("images/" + getFileExtension(uri))
                        .child(key)
                fileRef.putFile(uri).addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        val currDay = Calendar.getInstance()
                        currTime = SimpleDateFormat("M / d / Y").format(currDay.time).toString()
                        val firebaseUri = uri.toString()
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MapActivity::class.java)
                        intent.putExtra("clientLongitude", clientLong)
                        intent.putExtra("clientLatitude", clientLat)
                        intent.putExtra("animalCategory", txCategory)
                        intent.putExtra("clientService", txService)
                        intent.putExtra("vetLatitude", vetLatitude)
                        intent.putExtra("vetLongitude", vetLongitude)
                        intent.putExtra("vetDocId", vet_id)
                        intent.putExtra("clientName", clientUsername)
                        intent.putExtra("requestTime", currTime)
                        intent.putExtra("imageUri", firebaseUri)
                        intent.putExtra("animalAge", binding.edAge.text.toString())
                        intent.putExtra("animalGender", txGender)
                        intent.putExtra("requestDesc", binding.edIssue.text.toString())
                        startActivity(intent)
                        finish()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
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
            binding.servicePhoto.setImageURI(imageUri)
        }
    }
}