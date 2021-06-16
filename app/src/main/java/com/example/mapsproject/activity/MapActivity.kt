package com.example.mapsproject.activity


import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.mapsproject.R
import com.example.mapsproject.activity.MainActivity.Companion.COUNTRIES
import com.example.mapsproject.data.Country
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.properties.Delegates


@Suppress("DEPRECATION")
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var randCountry: Country

    lateinit var a: Button
    lateinit var b: Button
    lateinit var c: Button
    lateinit var d: Button
    var randID by Delegates.notNull<Int>()
    lateinit var answers: ArrayList<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        initAnswerButtons()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initMarkerButton()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        randCountry = COUNTRIES.random()
        randID = (0..3).random()

        mMap.addMarker(
            MarkerOptions()
                .position(randCountry.coords)
                .title("Marker in ${randCountry.name}")
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

    private fun initAnswerButtons(){
        a = findViewById(R.id.c1)
        b = findViewById(R.id.c2)
        c = findViewById(R.id.c3)
        d = findViewById(R.id.c4)

        answers = arrayListOf(a,b,c,d)
    }

    private fun setAnswers(){
        var ctr = 0
        val used = arrayListOf(randCountry.name)
        for (i in answers){
            if(randID == ctr){
                i.text = randCountry.name
                ctr++
            } else {
                var r = COUNTRIES.random()
                while (used.contains(r.name)){
                    r = COUNTRIES.random()
                }
                i.text = r.name
                used += r.name
                ctr++
            }
        }
    }
}