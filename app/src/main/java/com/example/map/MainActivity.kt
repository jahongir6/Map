package com.example.map

import android.annotation.SuppressLint
import android.app.TaskStackBuilder.create
import android.content.Intent
import android.content.IntentFilter.create
import android.hardware.HardwareBuffer.create
import android.media.audiofx.Equalizer.Settings
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager.LoaderCallbacks
import com.example.map.databinding.ActivityMainBinding
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import java.net.URI.create

private const val TAG = "MainActivity"

//realtimeda ishledigon locatsiya aniqlash
class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMainBinding
    private var map: GoogleMap? = null
    var polyline: Polyline? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myAskPermission()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.tv_loacation) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    fun myAskPermission() {
        askPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) {
            startLocation()
        }.onDeclined { e ->
            if (e.hasDenied()) {
                AlertDialog.Builder(this)
                    .setMessage("please")
                    .setPositiveButton("yes") { dialog, which ->
                        e.askAgain()
                    }
                    .setNegativeButton("no") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
            if (e.hasForeverDenied()) {
                e.goToSettings()
            }
        }
    }

    fun startLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
        //bu xaritani nech sekunda yangilab turishi
        locationRequest.setInterval(1000)
        locationRequest.setFastestInterval(2000)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)//bu qanday xolatda yanigilashi xaritani

        val request = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        val client = LocationServices.getSettingsClient(this)

        var locationSettingsResponseTask: Task<LocationSettingsResponse> =
            client.checkLocationSettings(request)

        locationSettingsResponseTask.addOnSuccessListener {
            startLocationUpdates()
        }
        locationSettingsResponseTask.addOnFailureListener {
            openGpsEnableSetting()
        }

    }

    private fun openGpsEnableSetting() {
        var intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, 1)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    var marker: Marker? = null
    var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            for (location in p0.locations) {
                val sydney = LatLng(location.latitude, location.longitude)
                if (marker == null) {

                    marker =
                        map?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                } else {
                    marker?.position = sydney
                }
                // binding.tvLoacation.text = location.toString()
                val cameraPosition = CameraPosition.Builder()
                    .target(sydney)// sydney keladi
                    .zoom(17f) // 17 marotaba kattalashgan bolsin
                    .bearing(90f) //shimoldan 90 gradus bolsin
                    .tilt(30f) // 30 gradus yotiqroq bolsin
                    .build()
                map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                if (polyline == null) {
                    polyline = map?.addPolyline(PolylineOptions().add(sydney))
                } else {
                    var list = polyline?.points
                    list?.add(sydney)
                    polyline?.points = list!!
                }

            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }
}