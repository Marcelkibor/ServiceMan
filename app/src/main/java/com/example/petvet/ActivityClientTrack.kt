package com.example.petvet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.databinding.FragmentTrackRequestBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

@SuppressLint("StaticFieldLeak")
private lateinit var binding: FragmentTrackRequestBinding
private lateinit var dbRef: DatabaseReference
private lateinit var mMap: GoogleMap
private lateinit var auth: FirebaseAuth
private lateinit var requestList: ArrayList<VetService>
private lateinit var clientLatitude: String
private lateinit var clientLongitude: String
private lateinit var vetLatitude: String
private lateinit var vetLongitude: String
private lateinit var tempArray: ArrayList<VetService>
private lateinit var rvAcceptedRyc: RecyclerView

class ActivityClientTrack : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        fetchLocationRequest()
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = FragmentTrackRequestBinding.inflate(layoutInflater)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, AuthenticatedClientActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)
        // client coordinates

        // Vet Coordinates

        // Fetching API_KEY which we wrapped
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()
        // Initializing the Places API with the help of our API_KEY
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.btTrack.setOnClickListener {
            if (::clientLatitude.isInitialized || ::clientLatitude.isInitialized || ::vetLatitude.isInitialized || ::vetLongitude.isInitialized) {
                mapFragment.getMapAsync {
                    mMap = it
                    val originLocation =
                        LatLng(clientLatitude.toDouble(), clientLongitude.toDouble())
                    mMap.addMarker(MarkerOptions().position(originLocation))
                    val destinationLocation =
                        LatLng(vetLatitude.toDouble(), vetLongitude.toDouble())
                    mMap.addMarker(MarkerOptions().position(destinationLocation))
                    val urll = getDirectionURL(originLocation, destinationLocation, apiKey)
                    GetDirection(urll).execute()
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 12F))
                }
            } else {
                Toast.makeText(this, "No service request!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchLocationRequest() {
        dbRef = FirebaseDatabase.getInstance().getReference("LocationRequest")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val item = snap.getValue(LocationRequest::class.java)
                    if (item!!.clientUid == auth.currentUser?.uid) {
                        clientLatitude = item.clientLat
                        clientLongitude = item.clientLong
                        vetLatitude = item.doctorLat
                        vetLongitude = item.doctorLong
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (::clientLatitude.isInitialized || ::clientLatitude.isInitialized) {
            val mMap = googleMap
            val originLocation = LatLng(clientLatitude.toDouble(), clientLongitude.toDouble())
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(originLocation))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 18F))
        } else {
            Toast.makeText(this, "No service request!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path = ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices) {
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.GREEN)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }
}