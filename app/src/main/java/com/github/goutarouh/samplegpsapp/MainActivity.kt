package com.github.goutarouh.samplegpsapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    /** 緯度 */
    private var _latitude = 0.0
    /** 軽度 */
    private var _longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.my_search_button).setOnClickListener {
            onMapSearchButton(it)
        }

        findViewById<Button>(R.id.show_tizu).setOnClickListener {
            onMapShowCurrentButtonClick(it)
        }

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = GPSLocationListener()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, 1000)

            return
        }

        findViewById<Button>(R.id.checkBt).apply {
            isEnabled = true
            setOnClickListener {
                onMapShowCurrentButtonClick(it)
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
    }

    fun onMapSearchButton(view: View) {
        val editWord = findViewById<EditText>(R.id.edit_text)
        val word = editWord.text.toString()

        val uriStr = "geo:0.0?q=${word}"

        val uri = Uri.parse(uriStr)

        val intent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(intent)
    }

    fun onMapShowCurrentButtonClick(view: View) {
        val uriStr = "geo:${_latitude},${_longitude}"
        val uri = Uri.parse(uriStr)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val locationListener = GPSLocationListener()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                ActivityCompat.requestPermissions(this, permissions, 1000)

                return
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }

    private inner class GPSLocationListener: LocationListener {

        override fun onLocationChanged(location: Location) {
            //緯度
            _latitude = location.latitude
            findViewById<TextView>(R.id.keido_text).text = _latitude.toString()

            //経度
            _longitude = location.longitude
            findViewById<TextView>(R.id.ido_text).text = _longitude.toString()
        }
    }
}