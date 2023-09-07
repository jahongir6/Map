package com.example.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.map.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task

private const val TAG = "MapsActivity"

/*map da Polylines and Polygons chiziqlari bor
* 1->Polylines bu yurgan yolimiz polylines boladi
* 2->polygons esa aysidir hududni belgilash polygons deyiladi*/
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        /*// Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0) // bu kordinatalari qayerni bersan osha joyga olib boradi
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))//bu marker
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))//bu camera qayerni belgilagan bolsan camera osha joyga boradi*/


        /* // bu yerda marker joyi qayerga olib borsa boradi yani maker ozgaryapti va toast ham chiqadi
         val sydney = LatLng(-34.0, 151.0) // bu kordinatalari qayerni bersan osha joyga olib boradi
         val marker = mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))//marker
         mMap.setOnMapClickListener {
             marker?.position = it
             Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
             mMap.mapType =
                 GoogleMap.MAP_TYPE_HYBRID // bu map qanday turda chiqishi uylar korinishida va h.k
             val cameraPosition = CameraPosition.Builder()
                 .target(it)// sydney keladi
                 .zoom(17f) // 17 marotaba kattalashgan bolsin
                 .bearing(90f) //shimoldan 90 gradus bolsin
                 .tilt(30f) // 30 gradus yotiqroq bolsin
                 .build()
             mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
         }*/


        /*//Polyline
        val sydney = LatLng(40.38193715781804, 71.78273165428492)
        val marker =
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))//marker
        mMap.setOnMapClickListener {
            marker?.position = it
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            mMap.mapType =
                GoogleMap.MAP_TYPE_HYBRID // bu map qanday turda chiqishi uylar korinishida va h.k
            val cameraPosition = CameraPosition.Builder()
                .target(it)// sydney keladi
                .zoom(17f) // 17 marotaba kattalashgan bolsin
                .bearing(90f) //shimoldan 90 gradus bolsin
                .tilt(30f) // 30 gradus yotiqroq bolsin
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
        val cameraPosition = CameraPosition.Builder()
            .target(sydney)// sydney keladi
            .zoom(17f) // 17 marotaba kattalashgan bolsin
            .bearing(90f) //shimoldan 90 gradus bolsin
            .tilt(30f) // 30 gradus yotiqroq bolsin
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))//camera animatsiyali yuradu

        val polyline = mMap.addPolyline(
            PolylineOptions()
                .add(
                    LatLng(40.38497730059829, 71.78066098889514),
                    LatLng(40.38361252228979, 71.78337538445791),
                    LatLng(40.38193715781804, 71.78273165428492)
                )
                .clickable(true)//bu chiziqni bosganda borib olmaydi marker
        )
        //bu polyline listeneri
        mMap.setOnPolylineClickListener {
            Toast.makeText(this, "bu yol oddiy yol", Toast.LENGTH_SHORT).show()
        }*/


        /* //Polygons
         val sydney = LatLng(40.38193715781804, 71.78273165428492)
         val marker =
             mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))//marker
         mMap.setOnMapClickListener {
             marker?.position = it
             Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
             mMap.mapType =
                 GoogleMap.MAP_TYPE_HYBRID // bu map qanday turda chiqishi uylar korinishida va h.k
             val cameraPosition = CameraPosition.Builder()
                 .target(it)// sydney keladi
                 .zoom(17f) // 17 marotaba kattalashgan bolsin
                 .bearing(90f) //shimoldan 90 gradus bolsin
                 .tilt(30f) // 30 gradus yotiqroq bolsin
                 .build()
             mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
         }
         val cameraPosition = CameraPosition.Builder()
             .target(sydney)// sydney keladi
             .zoom(17f) // 17 marotaba kattalashgan bolsin
             .bearing(90f) //shimoldan 90 gradus bolsin
             .tilt(30f) // 30 gradus yotiqroq bolsin
             .build()
         mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))//camera animatsiyali yuradu

         val polygon = mMap.addPolygon(
             PolygonOptions()
                 .fillColor(Color.BLUE)//bu belgilagan joyimizni ichini rangini bersan boladi
                 .strokeColor(Color.RED)//bu chiqlarni rangini bersan boladi
                 .add(
                     LatLng(40.38497730059829, 71.78066098889514),
                     LatLng(40.38361252228979, 71.78337538445791),
                     LatLng(40.38193715781804, 71.78273165428492)
                 )
                 .clickable(true)//bu chiziqni bosganda borib olmaydi marker
         )
         //bu polyline listeneri
         mMap.setOnPolygonClickListener {
             Toast.makeText(this, "bu yol oddiy yol", Toast.LENGTH_SHORT).show()
         }*/


       /* bu locatsiyani aniqlaydi qayda turganizdi
       val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val locationTask: Task<Location> = fusedLocationProviderClient.lastLocation
        locationTask.addOnSuccessListener { it: Location? ->
            if (it != null) {
                Log.d(TAG, "getLastLocation: ${it.toString()}")
                Log.d(TAG, "getLastLocation: ${it.latitude}")
                Log.d(TAG, "getLastLocation: ${it.longitude}")
                val sydney = LatLng(
                    it.latitude, it.longitude
                ) // bu kordinatalari qayerni bersan osha joyga olib boradi
                val marker = mMap.addMarker(
                    MarkerOptions().position(sydney).title("Marker in Sydney")
                )//marker

                val cameraPosition = CameraPosition.Builder()
                    .target(sydney)// sydney keladi
                    .zoom(17f) // 17 marotaba kattalashgan bolsin
                    .bearing(90f) //shimoldan 90 gradus bolsin
                    .tilt(30f) // 30 gradus yotiqroq bolsin
                    .build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            } else {
                Log.d(TAG, "getLastLocation:LLoacation was null .....")
            }
        }
        locationTask.addOnFailureListener {
            Log.d(TAG, "getLastLoacation: ${it.message}")
        }*/
    }

}