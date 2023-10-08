package com.apex.codeassesment.ui.location

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.apex.codeassesment.R
import com.apex.codeassesment.databinding.ActivityLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng


// TODO (Optional Bonus 8 points): Calculate distance between 2 coordinates using phone's location
class LocationActivity : AppCompatActivity() {

    lateinit var binding: ActivityLocationBinding
    private val LOCATION_PERMISSION_REQUEST_CODE = 101
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var latitudeRandomUser : String
    lateinit var longitudeRandomUser : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
         latitudeRandomUser = intent.getStringExtra("user-latitude-key")!!
         longitudeRandomUser = intent.getStringExtra("user-longitude-key")!!

        binding.locationRandomUser.text =
            getString(R.string.location_random_user, latitudeRandomUser, longitudeRandomUser)
        binding.locationCalculateButton.setOnClickListener {
            Toast.makeText(
                this,
                "TODO (8): Bonus - Calculate distance between 2 coordinates using phone's location",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (permissionGranted() && lastKnownLocation != null) {
            checkDistanceTraveled(lastKnownLocation!!)
        }else
            requestPermission()
//
        binding.locationCalculateButton.setOnClickListener {
            checkDistanceTraveled(lastKnownLocation!!)
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permissions are granted, proceed to get the location.
            getLocation()
        }
    }

    private fun permissionGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED)
    }


    private fun getLocation() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(10000)
            .setMaxUpdateDelayMillis(5000)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private var lastKnownLocation: LatLng? = null
    private fun checkDistanceTraveled(location: LatLng) {
        if (lastKnownLocation != null) {
            val results = FloatArray(2)
            Location.distanceBetween(
                latitudeRandomUser.toDouble(),
                longitudeRandomUser.toDouble(),
                location.latitude,
                location.longitude,
                results
            )
            binding.locationDistance.text =
                getString(R.string.location_result_miles, results.firstOrNull()?.toDouble())
        }
        lastKnownLocation = location

    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            binding.locationPhone.text =
                getString(
                    R.string.location_random_user,
                    lastLocation?.latitude.toString(),
                    lastLocation?.longitude.toString()
                )
            lastKnownLocation = LatLng(lastLocation?.latitude!!, lastLocation.longitude)
            // Use the 'lastLocation' to get the user's current location.
            // Don't forget to stop location updates when you're done.
//            fusedLocationClient.removeLocationUpdates(this)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocation()
                else
                    Log.i("Permission denied", "Not agree microphone permission")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}

