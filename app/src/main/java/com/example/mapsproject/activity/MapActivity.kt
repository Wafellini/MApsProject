package com.example.mapsproject.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mapsproject.R
import com.example.mapsproject.activity.MainActivity.Companion.COUNTRIES
import com.example.mapsproject.data.Country
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        pointsTV = findViewById(R.id.points)
        mistakesTV = findViewById(R.id.mistakes)

        pointsTV.text = "Points: $points"
        mistakesTV.text = "Mistakes: $mistakes"

        initAnswerButtons()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initMarkerButton()
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
                runGame()
            },
            2000
        )
    }

    private fun checkMistakes(){
        if (mistakes >= 3){
            endGame()
        }
    }

    private fun endGame(){
        Handler().postDelayed(
            {
                openMainAcitvity()
            },
            2000
        )
    }

    private fun openMainAcitvity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun addPoints(){
        points += 5
        pointsTV.text = "Points: $points"
    }

    @SuppressLint("SetTextI18n")
    private fun subPoints(){
        points -= 5
        pointsTV.text = "Points: $points"
    }
}