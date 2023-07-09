package com.redravencomputing.wander

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.redravencomputing.wander.databinding.ActivityMapsBinding
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

	private lateinit var binding: ActivityMapsBinding
	private lateinit var map: GoogleMap
	private val TAG = MapsActivity::class.java.simpleName

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
		val latitude = 37.422160
		val longitude = -122.084270
		val zoomLevel = 15f

		val homeLatLng = LatLng(latitude, longitude)
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
		map.addMarker(MarkerOptions().position(homeLatLng))

		setMapLongClick(map)
		setPoiClick(map)
		setMapStyle(map)
	}

	private fun setMapLongClick(map: GoogleMap) {
		map.setOnMapLongClickListener { latLng ->
			// A Snippet is Additional text that's displayed below the title.
			val snippet = String.format(
				Locale.getDefault(),
				"Lat: %1$.5f, Long: %2$.5f",
				latLng.latitude,
				latLng.longitude
			)
			map.addMarker(MarkerOptions()
				.position(latLng)
				.title(getString(R.string.dropped_pin))
				.snippet(snippet)
			)
		}
	}

	private fun setPoiClick(map: GoogleMap) {
		map.setOnPoiClickListener { poi ->
			val poiMarker = map.addMarker(
				MarkerOptions()
					.position(poi.latLng)
					.title(poi.name)
			)
			poiMarker?.showInfoWindow()
		}
	}

	private fun setMapStyle(map: GoogleMap) {
		try {
			// Customize the styling of the base map using a JSON object defined
			// in a raw resource file.
			val success = map.setMapStyle(
				MapStyleOptions.loadRawResourceStyle(
					this,
					R.raw.map_style
				)
			)
			if (!success) {
				Log.e(TAG, "Style parsing failed")
			}
		} catch (e: Resources.NotFoundException) {
			Log.e(TAG, "Can't find style. Error: ", e)
		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.map_options, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId){
		// Change the map type based on the user's selection.
		R.id.normal_map -> {
			map.mapType = GoogleMap.MAP_TYPE_NORMAL
			true
		}
		R.id.hybrid_map -> {
			map.mapType = GoogleMap.MAP_TYPE_HYBRID
			true
		}
		R.id.satellite_map -> {
			map.mapType = GoogleMap.MAP_TYPE_SATELLITE
			true
		}
		R.id.terrain_map -> {
			map.mapType = GoogleMap.MAP_TYPE_TERRAIN
			true
		}
		else -> super.onOptionsItemSelected(item)
	}
}