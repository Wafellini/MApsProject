package com.example.mapsproject.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.example.mapsproject.R
import com.example.mapsproject.service.BackgroundSoundService


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var options: LinearLayout
    lateinit var play: LinearLayout
    lateinit var out: LinearLayout
    lateinit var profile: LinearLayout
    lateinit var profile_data: FragmentContainerView
    lateinit var options_data: FragmentContainerView
    lateinit var rankingButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        options = findViewById(R.id.options)
        play = findViewById(R.id.play)
        out = findViewById(R.id.out)
        profile = findViewById(R.id.profile)
        profile_data = findViewById(R.id.profile_data)
        options_data = findViewById(R.id.options_data)
        rankingButton = findViewById(R.id.rankingButton)

        playMusic()

        options.setOnClickListener {
            showHideOptions()
        }
        play.setOnClickListener {
            openMapActivity()
        }
        out.setOnClickListener {
            openLoginActivity()
        }
        profile.setOnClickListener {
            showHideProfile()
        }
        rankingButton.setOnClickListener {
            openRankingActivity()
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

    fun openRankingActivity() {
        val intent = Intent(this, RankingActivity::class.java)
        startActivity(intent)
    }

    fun showHideProfile() {
        if (profile_data.visibility == View.GONE) {
            profile_data.visibility = View.VISIBLE
        } else {
            profile_data.visibility = View.GONE
        }
    }

    fun showHideOptions() {
        if (options_data.visibility == View.GONE) {
            options_data.visibility = View.VISIBLE
        } else {
            options_data.visibility = View.GONE
        }
    }

    fun playMusic() {
        val intent = Intent(this@MainActivity, BackgroundSoundService::class.java)
        startService(intent)
    }

}