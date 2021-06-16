package com.example.mapsproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.example.mapsproject.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var play: LinearLayout
    lateinit var out: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        play = findViewById(R.id.play)
        out = findViewById(R.id.out)

        play.setOnClickListener{
            openMapActivity()
        }
        out.setOnClickListener{
            openLoginActivity()
        }
    }

    fun openMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
    fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}