package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import com.example.petvet.databinding.MapActivityBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var clientLat: String
    private lateinit var clientLong: String
    private lateinit var auth: FirebaseAuth
    private lateinit var vetLat: String
    private lateinit var clientUsername: String
    private lateinit var clientDb: DatabaseReference
    private lateinit var dbRef: DatabaseReference
    private lateinit var requestTime: String
    private lateinit var imageUri: String
    private lateinit var locationServiceArray: ArrayList<String>
    private lateinit var vetDocId: String
    private lateinit var vetLong: String
    private lateinit var requestId: String
    private lateinit var binding: MapActivityBinding
    private lateinit var currentService: String
    private lateinit var finDist: String
    private lateinit var txLocationArea:String
    private lateinit var animalCategory: String
    private lateinit var requestDesc: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = MapActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        fetch bundle
        val bundle: Bundle? = intent.extras
        locationServiceArray = arrayListOf<String>()
        clientLong = bundle?.getString("clientLongitude").toString()
        clientLat = bundle?.getString("clientLatitude").toString()
        currentService = bundle?.getString("clientService").toString()
        animalCategory = bundle?.getString("animalCategory").toString()
        vetLat = bundle?.getString("vetLatitude").toString()
        txLocationArea = bundle?.getString("txLocationArea").toString()
        vetLong = bundle?.getString("vetLongitude").toString()
        vetDocId = bundle?.getString("vetDocId").toString()
        requestTime = bundle?.getString("requestTime").toString()
        imageUri = bundle?.getString("imageUri").toString()
        requestDesc = bundle?.getString("requestDesc").toString()
        clientUsername = bundle?.getString("clientName").toString()
        Toast.makeText(this, "Client longitude $clientLong", Toast.LENGTH_SHORT).show()
        val vetCord = LatLng(vetLat.toDouble(), vetLong.toDouble())
        val clientCord = LatLng(clientLat.toDouble(), clientLong.toDouble())
        locationServiceArray.add(vetCord.toString())
        locationServiceArray.add(clientCord.toString())
        binding.tvAddCategory.text = animalCategory
        binding.tvAddService.text = txLocationArea
        vetLocationDistance()
        binding.btRequestService.setOnClickListener {
            showPaymentDialog()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun showPaymentDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.botton_sheet)
        val safLayout = dialog.findViewById<LinearLayout>(R.id.ltMpesa)
        val paypalLayout = dialog.findViewById<LinearLayout>(R.id.ltPaypal)
        val simulateLayout = dialog.findViewById<LinearLayout>(R.id.ltSimulate)
        safLayout.setOnClickListener {
            Toast.makeText(this, "Pending Integration!", Toast.LENGTH_SHORT).show()
        }
        paypalLayout.setOnClickListener {
            Toast.makeText(this, "Pending Integration!", Toast.LENGTH_SHORT).show()
        }
        simulateLayout.setOnClickListener {
            submitClientRequest()
        }
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.attributes?.windowAnimations = R.style.PaymentDialog
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    private fun submitClientRequest() {
        try {
            val dialog = Dialog(this@MapActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.setCancelable(false)
            dialog.show()
            dbRef = FirebaseDatabase.getInstance().getReference("Request")
            requestId = FirebaseDatabase.getInstance().getReference("Request").push().key.toString()
            val currDay = Calendar.getInstance()
            val currTime = SimpleDateFormat("M / d / Y").format(currDay.time).toString()
            val clientRequest = ClientRequest(
                requestId, currentService, requestDesc,
                txLocationArea, animalCategory, auth.currentUser?.uid, imageUri,
                currTime, clientUsername, vetDocId
            )
            val historyDb = FirebaseDatabase.getInstance().getReference("CompletedRequest")
            dbRef.child(requestId).setValue(clientRequest).addOnCompleteListener {
                historyDb.child(requestId).setValue(clientRequest).addOnCompleteListener {
                    setOrderDetails()
                }

            }

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    private fun setOrderDetails() {
        try {
            val dialog = Dialog(this@MapActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.setCancelable(false)
            dialog.show()
            val clientRequestId = requestId
            val clientId = auth.currentUser?.uid.toString()
            val currDay = Calendar.getInstance()
            val currTime = SimpleDateFormat("M / d / Y / H:m:s ").format(currDay.time).toString()

            dbRef = FirebaseDatabase.getInstance().getReference("OrderDetails")
            val order = OrderDetails(clientRequestId, clientId, currTime, vetDocId)
            dbRef.child(requestId).setValue(order).addOnCompleteListener {
                includePayment()
            }

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    private fun includePayment() {
        val dialog = Dialog(this@MapActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)
        try {
            dialog.show()
            dbRef = FirebaseDatabase.getInstance().getReference("Payment")
            val key = FirebaseDatabase.getInstance().getReference("Payment").push().key.toString()
            val oldAmount = binding.tvAddPrice.text.toString()
            val serviceAmount = oldAmount.toDouble()
            val vetShare = (0.3 * serviceAmount).toString()
            val businessShare = (serviceAmount.toDouble() - vetShare.toDouble()).toString()
            val currDay = Calendar.getInstance()
            val currTime = SimpleDateFormat("M / d / Y / H:mm:s ").format(currDay.time).toString()
            val payment = Payment(
                key,
                requestId,
                serviceAmount.toString(),
                currTime,
                auth.currentUser?.uid.toString()
            )
            dbRef.child(key).setValue(payment).addOnCompleteListener {
                val businessAccountDb =
                    FirebaseDatabase.getInstance().getReference("BusinessAccount")
                val vetAccountDb = FirebaseDatabase.getInstance().getReference("VetAccount")
                val locationRequestDb =
                    FirebaseDatabase.getInstance().getReference("LocationRequest")
                val businessAccountValue =
                    BusinessAccount(key, businessShare, currTime, vetDocId, requestId)
                val vetAccountValue =
                    VetAccount(key, vetShare, currTime, vetDocId, requestId, clientUsername)
                //include location request
                val clientLongPos = clientLong
                val locationRequest = LocationRequest(
                    vetLong,
                    vetLat,
                    vetDocId,
                    clientLat,
                    clientLongPos,
                    auth.currentUser?.uid
                )
                val locationPush = locationRequestDb.push().key.toString()
                locationRequestDb.child(locationPush).setValue(locationRequest)
                businessAccountDb.child(businessAccountDb.push().key.toString())
                    .setValue(businessAccountValue)
                vetAccountDb.child(vetAccountDb.push().key.toString()).setValue(vetAccountValue)
                val intent = Intent(this, AuthenticatedClientActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Request Received!", Toast.LENGTH_SHORT).show()
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            dialog.hide()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun vetLocationDistance() {
        val el1 = 0.0
        val el2 = 0.0
        val R = 6371.0088 // Radius of the earth
        val latDistance = Math.toRadians(vetLat.toDouble() - clientLat.toDouble())
        val lonDistance = Math.toRadians(vetLong.toDouble() - clientLong.toDouble())
        val a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + (Math.cos(Math.toRadians(clientLat.toDouble())) * Math.cos(Math.toRadians(vetLat.toDouble()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        var distance = R * c * 1000 // convert to m
        val height: Double = el1 - el2 // further convert to kilometers
//        distance = Math.pow(distance, 2.0) + Math.pow(height, 2.0)
//        val finDist = Math.sqrt(distance)/1000
//        val wholeValueDistance = finDist.toInt()
        //set display of distance
        finDist = "%.2f".format(distance)
        binding.tvDistance2.text = finDist + "m"
        setPrice()
    }

    @SuppressLint("SetTextI18n")
    private fun setPrice() {
        try {
            //pricing of 20/= per kilometer
            val clientDistance = finDist
            val serviceFee = 50
            val minDist = 5000.toString()
            if (clientDistance < minDist) {
                binding.tvAddPrice.setText("$serviceFee")
                println("true")
            } else {
                val extraDistance = clientDistance.toInt() - minDist.toInt()
                val extraDistPrice = (extraDistance / 1000) * 20
                val newPrice = extraDistPrice + serviceFee
                binding.tvAddPrice.setText(newPrice.toString())
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            println(e.message)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        try {
            // Add a marker in my current location and move the camera
            mMap = googleMap
            mMap.uiSettings.isZoomControlsEnabled = true
            val clientPos = LatLng(clientLat.toDouble(), clientLong.toDouble())
            mMap.addMarker(
                MarkerOptions()
                    .position(clientPos)
                    .title("Client location")
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(clientPos, 16f))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
