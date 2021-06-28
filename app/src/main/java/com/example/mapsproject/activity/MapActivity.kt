package com.example.mapsproject.activity


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mapsproject.R
import com.example.mapsproject.activity.LauncherActivity.Companion.COUNTRIES
import com.example.mapsproject.activity.LoginActivity.Companion.USER
import com.example.mapsproject.data.Country
import com.example.mapsproject.data.DBHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Exception
import kotlin.math.roundToLong
import kotlin.properties.Delegates


@Suppress("DEPRECATION")
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var randCountry: Country
    lateinit var marker: Marker

    lateinit var a: Button
    lateinit var b: Button
    lateinit var c: Button
    lateinit var d: Button
    var randID by Delegates.notNull<Int>()
    lateinit var answers: ArrayList<Button>

    var points: Int = 0
    var mistakes: Int = 0

    lateinit var pointsTV: TextView
    lateinit var mistakesTV: TextView
    lateinit var dist: TextView
    lateinit var line: Polyline

    var myLocation: LatLng = LatLng(0.0, 0.0)
    lateinit var mymarker: Marker

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        pointsTV = findViewById(R.id.points)
        mistakesTV = findViewById(R.id.mistakes)
        dist = findViewById(R.id.dist)

        pointsTV.text = "Points: $points"
        mistakesTV.text = "Mistakes: $mistakes"

        initAnswerButtons()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initMarkerButton()
        initHintButton()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        runGame()
    }

    private fun runGame() {
        randCountry = COUNTRIES.random()
        randID = (0..3).random()

        marker = mMap.addMarker(
            MarkerOptions()
                .position(randCountry.coords)
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLng(randCountry.coords))

        setAnswers()
    }

    private fun initMarkerButton() {
        val show_marker = findViewById<Button>(R.id.show_marker)
        show_marker.setOnClickListener {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(randCountry.coords))
        }
    }

    private fun initAnswerButtons() {
        a = findViewById(R.id.c1)
        b = findViewById(R.id.c2)
        c = findViewById(R.id.c3)
        d = findViewById(R.id.c4)

        answers = arrayListOf(a, b, c, d)
    }

    @SuppressLint("SetTextI18n")
    private fun setAnswers() {
        var ctr = 0
        val used = arrayListOf(randCountry.name)
        for (i in answers) {
            if (randID == ctr) {
                i.text = randCountry.name
                i.setOnClickListener {
                    i.isEnabled = false
                    handleAnswers()
                    addPoints()
                    refresh()
                }
                ctr++
            } else {
                var r = COUNTRIES.random()
                while (used.contains(r.name)) {
                    r = COUNTRIES.random()
                }
                i.text = r.name
                i.setOnClickListener {
                    handleAnswers()
                    subPoints()
                    mistakes++
                    mistakesTV.text = "Mistakes: $mistakes"
                    checkMistakes()
                    refresh()
                }
                used += r.name
                ctr++
            }
        }
    }

    private fun handleAnswers() {
        var ctr = 0
        for (i in answers) {
            if (randID == ctr) {
                i.isEnabled = false
                i.setBackgroundColor(resources.getColor(R.color.green_my))
                ctr++
            } else {
                i.isEnabled = false
                i.setBackgroundColor(resources.getColor(R.color.red_my))
                ctr++
            }
        }
    }

    private fun refresh() {
        Handler().postDelayed(
            {
                for (i in answers) {
                    i.isEnabled = true
                    i.setBackgroundColor(resources.getColor(R.color.gray))
                }
                marker.remove()
                dist.text = ""
                try {
                    mymarker.remove()
                    line.remove()
                } catch (e: Exception){}
                runGame()
            },
            2000
        )
    }

    private fun checkMistakes() {
        if (mistakes >= 3) {
            endGame()
        }
    }

    private fun endGame() {
        Handler().postDelayed(
            {
                insertScore()
                openMainAcitvity()
            },
            2000
        )
    }

    private fun openMainAcitvity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun addPoints() {
        points += 5
        pointsTV.text = "Points: $points"
    }

    @SuppressLint("SetTextI18n")
    private fun subPoints() {
        points -= 0
        pointsTV.text = "Points: $points"
    }

    fun insertScore() {
        val method = "insert score"
        val dbHelper = DBHelper(this)
        dbHelper.execute(method, USER.login, points.toString())
    }

    @SuppressLint("SetTextI18n")
    fun initHintButton() {
        val hint = findViewById<Button>(R.id.hint)
        hint.setOnClickListener {
            handleMyPosition()

            try {
                mymarker.remove()
            }catch (e: Exception){}

            if (myLocation != LatLng(0.0, 0.0)){
                mymarker = mMap.addMarker(
                    MarkerOptions()
                        .position(myLocation)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                )
                line = mMap.addPolyline(PolylineOptions().add(myLocation, randCountry.coords)
                    .width(7f).color(Color.RED))

                val l1 = Location("")
                l1.latitude = myLocation.latitude
                l1.longitude = myLocation.longitude

                val l2 = Location("")
                l2.latitude = randCountry.coords.latitude
                l2.longitude = randCountry.coords.longitude

                val d = (l1.distanceTo(l2) / 100.0).roundToLong()
                dist.text = "Distance: ${d}km"
            }
        }
    }

    fun handleMyPosition() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f,
                locationListener
            )
        } catch (ex: SecurityException) {
            Toast.makeText(applicationContext, "No location available", Toast.LENGTH_LONG).show()
        }
    }


    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            myLocation = LatLng(location.latitude, location.longitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}