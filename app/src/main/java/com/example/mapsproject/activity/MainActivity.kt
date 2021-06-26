package com.example.mapsproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import com.example.mapsproject.R
import com.example.mapsproject.data.Country
import com.example.mapsproject.data.User

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var play: LinearLayout
    lateinit var out: LinearLayout
    lateinit var profile: LinearLayout
    lateinit var profile_data: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        play = findViewById(R.id.play)
        out = findViewById(R.id.out)
        profile = findViewById(R.id.profile)
        profile_data = findViewById(R.id.profile_data)

        USER = User(intent.getStringExtra("login")!!)

        play.setOnClickListener{
            openMapActivity()
        }
        out.setOnClickListener{
            openLoginActivity()
        }
        profile.setOnClickListener {
            showHideProfile()
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

    fun showHideProfile(){
        if (profile_data.visibility == View.GONE){
            profile_data.visibility = View.VISIBLE
        } else{
            profile_data.visibility = View.GONE
        }
    }

    companion object{
        val COUNTRIES = ArrayList<Country>()
        lateinit var USER: User
    }
}