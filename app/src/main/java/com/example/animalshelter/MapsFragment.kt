package com.example.animalshelter

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.animalshelter.AnimalClasses.Shelter
import com.example.animalshelter.AnimalClasses.Shelter.Companion.SelectedShelter

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.animalshelter.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var app: MyApplication
    private lateinit var lastLocation: Location
    private lateinit var fusedLocation: FusedLocationProviderClient
    private val permissionCode = 101
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireActivity())
        app = (requireActivity().application as MyApplication)
        getCurrentLocationUser()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationUser() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                permissionCode
            )
            return
        }

        fusedLocation.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                Toast.makeText(
                    this.context,
                    lastLocation.latitude.toString() + " " + lastLocation.longitude.toString(),
                    Toast.LENGTH_SHORT
                ).show()


                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationUser()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
        //Starting point
        val markerOptions = MarkerOptions().position(latLng).title("Me")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)
        addRandomMarkers(googleMap)

    }


    private fun addRandomMarkers(map: GoogleMap) {
        val lastLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonStr = sharedPref?.getString("markers", null)

        if (jsonStr != null) {
            val markers = gson.fromJson(jsonStr, Array<MarkerData>::class.java).toList()
            for (marker in markers) {
                map.addMarker(MarkerOptions().position(marker.position).title(marker.title))
            }
            map.setOnMarkerClickListener { marker ->
                if (marker.title == "Me") {
                    marker.showInfoWindow()
                    return@setOnMarkerClickListener true
                }

                marker.showInfoWindow()
                for (i in 0 until app.mShelters.size) {
                    if (app.mShelters[i].name == marker.title) {
                        SelectedShelter = app.mShelters[i]
                    }
                }
                true
            }
            return
        }
        val markers = mutableListOf<MarkerData>()
        for (i in 1..5) {
            val tempShelter = Shelter("Shelter $i")
            val randomLat = lastLatLng.latitude + (Math.random() - 1) / 100
            val randomLng = lastLatLng.longitude + (Math.random() - 1) / 100
            val randomLatLng = LatLng(randomLat, randomLng)
            val markerOptions = MarkerOptions()
                .position(randomLatLng)
                .title(tempShelter.name)
            val marker = map.addMarker(markerOptions)
            markers.add(MarkerData(marker!!.position, marker.title.toString()))
            app.mShelters.add(tempShelter)
        }
        val editor = sharedPref?.edit()
        editor?.putString("markers", gson.toJson(markers))
        editor?.apply()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
    


