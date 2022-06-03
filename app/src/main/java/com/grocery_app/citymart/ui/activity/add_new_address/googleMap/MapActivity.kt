package com.grocery_app.citymart.ui.activity.add_new_address.googleMap

import android.Manifest
import android.provider.Settings
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.grocery_app.citymart.R
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback{
    var fusedLocationProviderClient : FusedLocationProviderClient? = null
    var currentMarker:Marker? = null
    var currentLocation:Location?=null
    lateinit var tvAddress:TextView
    lateinit var map: GoogleMap
    lateinit var sharepLocation: SharedPreferences
    lateinit var shareLocationBool: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val back_btn =findViewById<ImageView>(R.id.back_btn_map)

        sharepLocation = getSharedPreferences("LocationByMap", Context.MODE_PRIVATE)
        back_btn.setOnClickListener{onBackPressed()}
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this)

            val mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


            val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if(mGPS){
        fetchLocation()
        }else{

                val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

                if (!manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps()
                }

        }


        tvAddress = findViewById(R.id.tv_Address_Map)

    }



    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf( Manifest.permission.ACCESS_FINE_LOCATION), 100)
            return
        }

        val task = fusedLocationProviderClient?.lastLocation
        task?.addOnSuccessListener{location ->
            if (location != null){
                this.currentLocation = location
                val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
                mapFragment.getMapAsync(this)
                }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1000 -> if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setMyLocationEnabled(true)
        val latlong = LatLng(currentLocation?.latitude!!,currentLocation?.longitude!!)

        drawMarker(latlong)


        map.setOnCameraMoveStartedListener(object:GoogleMap.OnCameraMoveStartedListener{
            override fun onCameraMoveStarted(p0: Int) {

                map.setOnCameraMoveStartedListener( {
                       })

                map.setOnCameraIdleListener {
                    map.clear()
                    val latlon = map.cameraPosition.target
                    drawMarker(latlon)
                    map.clear()
                }


            }
        })


       map.setOnMarkerDragListener(
            object: GoogleMap.OnMarkerDragListener{
                override fun onMarkerDrag(p0: Marker) {

                }

                override fun onMarkerDragEnd(p0: Marker) {
                    if (currentMarker != null){
                        currentMarker?.remove()
                    }
                    val newLatlng = LatLng(p0.position.latitude, p0.position.longitude)
                    drawMarker(newLatlng)
                }

                override fun onMarkerDragStart(p0: Marker) {

                }

            }
        )
    }

    private fun drawMarker(latlong:LatLng){

        val markerOption = MarkerOptions().position(latlong).title("I am here").snippet(getAddress(latlong.latitude,latlong.longitude))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong,15f))
      try {
          var addr = getAddress(latlong.latitude,latlong.longitude)
          var city = getCity(latlong.latitude,latlong.longitude)
          var postal = getPostal(latlong.latitude,latlong.longitude)
          var country = getCountry(latlong.latitude,latlong.longitude)
          tv_Address_Map.setText(addr)
          shareLocation(addr,city,postal,country)
      }catch(e:Exception){}


    }

    fun getAddress(lat:Double , lon: Double):String{
        val geo = Geocoder(this, Locale.getDefault())
        val address = geo.getFromLocation(lat,lon,1)
        return address[0].getAddressLine(0).toString()
    }

    fun getCity(lat:Double , lon: Double):String{
        val geo = Geocoder(this, Locale.getDefault())
        val address = geo.getFromLocation(lat,lon,1)
        return address[0].locality.toString()
    }

    fun getPostal(lat:Double , lon: Double):String{
        val geo = Geocoder(this, Locale.getDefault())
        val address = geo.getFromLocation(lat,lon,1)
        return address[0].postalCode.toString()
    }

    fun getCountry(lat:Double , lon: Double):String{
        val geo = Geocoder(this, Locale.getDefault())
        val address = geo.getFromLocation(lat,lon,1)
        return address[0].countryName.toString()
    }

    fun shareLocation(home:String,city:String,postal:String,country:String){
        val editortwo = sharepLocation.edit()
        editortwo.putString("home", home)
        editortwo.putString("city",city)
        editortwo.putString("postal", postal)
        editortwo.putString("country", country)
        editortwo.apply()
        btn_save_location.setOnClickListener{
            shareLocationBool = getSharedPreferences("shareLocationBool", Context.MODE_PRIVATE)
            val locationBool = shareLocationBool.edit()
            locationBool.putBoolean("valueThree", true)
            locationBool.apply()
            onBackPressed()
        }
    }

    fun buildAlertMessageNoGps() {



        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    fetchLocation()
         })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()
    }


    override fun onPause() {
        super.onPause()
        finish()
    }
}