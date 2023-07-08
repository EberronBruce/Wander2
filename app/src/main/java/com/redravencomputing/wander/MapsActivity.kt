package com.redravencomputing.wander

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.redravencomputing.wander.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

	private lateinit var binding: ActivityMapsBinding
	private lateinit var map: GoogleMap

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMapsBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)

	}

	override fun onMapReady(googleMap: GoogleMap) {
		// Use the Google Map object here
		map = googleMap
		val sydney = LatLng(-34.0, 151.0)
		map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
		map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
	}
}