package com.example.mapsproject.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.mapsproject.R
import com.example.mapsproject.data.DBHelper
import com.example.mapsproject.data.User

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var register_intent: TextView
    lateinit var login: EditText
    lateinit var pass: EditText
    lateinit var rankingButton: TextView

    companion object{
        lateinit var USER: User
        @SuppressLint("StaticFieldLeak")
        lateinit var login_submit: TextView
        fun reanable(){
            login_submit.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        register_intent = findViewById(R.id.register_intent)
        login_submit = findViewById(R.id.login_submit)
        login = findViewById(R.id.login_login)
        pass = findViewById(R.id.login_pass)
        rankingButton = findViewById(R.id.rankingButton)

        register_intent.setOnClickListener{
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }

        login_submit.setOnClickListener{
            login_submit.isEnabled = false
            verifyUser(login.text.toString(),pass.text.toString())
        }
        rankingButton.setOnClickListener{
            openRankingActivity()
        }
    }

    fun verifyUser(login: String, password: String) {
        val method = "login"
        val dbHelper = DBHelper(this)
        dbHelper.execute(method, login, password)
    }

    fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        USER = User(login.text.toString())
        startActivity(intent)
        finish()
    }
    fun openRankingActivity() {
        val intent = Intent(this, RankingActivity::class.java)
        startActivity(intent)
    }

}
