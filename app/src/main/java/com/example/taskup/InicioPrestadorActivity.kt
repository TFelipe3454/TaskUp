package com.example.taskup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import com.example.taskup.databinding.ActivityInicioPrestadorBinding

class InicioPrestadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioPrestadorBinding
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var map: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay
    private val elDorado = GeoPoint(4.701594, -74.146928) // Aeropuerto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioPrestadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))
        map = binding.mapView
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        // Verificar permisos
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

        // Botón "Ver Ruta"
        binding.btnVerRuta.setOnClickListener {
            mostrarRuta()
        }

        binding.btnVerSolicitudes.setOnClickListener {
            val intent = Intent(this, SolicitudesDisponiblesPrestadorActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarMapa()
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun iniciarMapa() {
        // Overlay para la ubicación actual
        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        locationOverlay.enableMyLocation()
        locationOverlay.enableFollowLocation()
        map.overlays.add(locationOverlay)

        // Marcador en el Aeropuerto El Dorado
        val marcador = Marker(map)
        marcador.position = elDorado
        marcador.title = "Aeropuerto El Dorado"
        marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marcador)

        // Centrar mapa inicialmente
        locationOverlay.runOnFirstFix {
            runOnUiThread {
                val startLocation = locationOverlay.myLocation
                if (startLocation != null) {
                    val startPoint = GeoPoint(startLocation.latitude, startLocation.longitude)
                    map.controller.setZoom(15.0)
                    map.controller.setCenter(startPoint)
                }
            }
        }
    }

    private fun mostrarRuta() {
        locationOverlay.runOnFirstFix {
            runOnUiThread {
                val startLocation = locationOverlay.myLocation
                if (startLocation != null) {
                    val startPoint = GeoPoint(startLocation.latitude, startLocation.longitude)

                    val polyline = org.osmdroid.views.overlay.Polyline()
                    polyline.setPoints(listOf(startPoint, elDorado))
                    polyline.width = 5f
                    polyline.color = android.graphics.Color.RED

                    map.overlays.add(polyline)
                    map.invalidate()
                } else {
                    Toast.makeText(this, "No se pudo obtener tu ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

