package com.example.taskup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.taskup.databinding.ActivityInicioPrestadorBinding
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class InicioPrestadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioPrestadorBinding
    private lateinit var map: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay

    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private val elDorado = GeoPoint(4.701594, -74.146928)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioPrestadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))

        map = binding.mapView
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        // Pedir permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            iniciarMapa()
        }

        binding.btnVerRuta.setOnClickListener {
            calcularRuta()
        }

        binding.btnVerSolicitudes.setOnClickListener {
            startActivity(Intent(this, SolicitudesDisponiblesPrestadorActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            iniciarMapa()
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun iniciarMapa() {
        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        locationOverlay.enableMyLocation()
        locationOverlay.enableFollowLocation()
        map.overlays.add(locationOverlay)

        // Marcador en El Dorado
        val marcador = Marker(map)
        marcador.position = elDorado
        marcador.title = "Aeropuerto El Dorado"
        marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marcador)
    }

    // 🚀 NUEVO: ENRUTAMIENTO CON OSRM
    private fun calcularRuta() {
        locationOverlay.runOnFirstFix {
            val startLocation = locationOverlay.myLocation
            if (startLocation == null) {
                runOnUiThread {
                    Toast.makeText(this, "No se pudo obtener tu ubicación", Toast.LENGTH_SHORT).show()
                }
                return@runOnFirstFix
            }

            val startPoint = GeoPoint(startLocation.latitude, startLocation.longitude)

            // RoadManager de OSRM
            val roadManager = OSRMRoadManager(this, "ANDROID")
            roadManager.setMean(OSRMRoadManager.MEAN_BY_CAR) // Ruta en carro, puedes cambiarlo

            val waypoints = ArrayList<GeoPoint>()
            waypoints.add(startPoint)
            waypoints.add(elDorado)

            // Calcular ruta
            val road = roadManager.getRoad(waypoints)

            val roadOverlay: Polyline = RoadManager.buildRoadOverlay(road)
            roadOverlay.color = Color.BLUE
            roadOverlay.width = 10f

            runOnUiThread {
                map.overlays.add(roadOverlay)
                map.controller.animateTo(startPoint)
                map.invalidate()
            }
        }
    }
}
