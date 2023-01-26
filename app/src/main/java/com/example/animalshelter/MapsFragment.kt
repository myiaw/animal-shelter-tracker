package com.example.animalshelter

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.R.id.message
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.animalshelter.AnimalClasses.Shelter.Companion.SelectedShelter
import com.example.animalshelter.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random


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
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireActivity())
        app = (requireActivity().application as MyApplication)
        createChannel()
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
        for (shelter in app.mShelters) {
            if (shelter.latitude == null || shelter.longitude == null || shelter.color == null) {
                shelter.setLocation(lastLatLng)
            }
            val randomLatLng = LatLng(shelter.latitude!!, shelter.longitude!!)
            map.addMarker(
                MarkerOptions()
                    .position(randomLatLng)
                    .title(shelter.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(shelter.color!!.toFloat()))
            )
        }

        map.setOnMarkerClickListener { marker ->

            if (marker.title == "Me") {
                marker.showInfoWindow()
                return@setOnMarkerClickListener true
            }

            marker.showInfoWindow()
            showNotification()
            for (i in 0 until app.mShelters.size) {
                if (app.mShelters[i].name == marker.title) {
                    SelectedShelter = app.mShelters[i]
                    break
                }
            }



            true
        }
    }


    @SuppressLint("MissingPermission")
    private fun showNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("KEY", "ENTER")
        val pendingIntent =
            PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


        val builder = NotificationCompat.Builder(requireContext(), "ClickShelterNotification")
            .setContentTitle("Open Shelter")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentText("You can edit the shelter here.")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(requireContext()).notify(1, builder.build())

    }


    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ClickShelterNotification",
                "ClickShelterNotification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
    


