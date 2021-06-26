package com.example.mapsproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.mapsproject.R
import com.example.mapsproject.data.DBHelper

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var register_intent: TextView
    lateinit var login_submit: TextView
    lateinit var login: EditText
    lateinit var pass: EditText

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

        register_intent.setOnClickListener{
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }

        login_submit.setOnClickListener{
            login_submit.isEnabled = false
            verifyUser(login.text.toString(),pass.text.toString())
        }
    }

    fun verifyUser(login: String, password: String) {
        val method = "login"
        val dbHelper = DBHelper(this)
        dbHelper.execute(method, login, password)
    }

    fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("login",login.text.toString())
        startActivity(intent)
        finish()
    }
}
