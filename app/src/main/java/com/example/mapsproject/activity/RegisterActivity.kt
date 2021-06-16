package com.example.mapsproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mapsproject.R
import com.example.mapsproject.data.DBHelper

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    lateinit var login_intent: TextView
    lateinit var register_submit: TextView
    lateinit var login: EditText
    lateinit var pass: EditText
    lateinit var pass2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        login_intent = findViewById(R.id.login_intent)
        register_submit = findViewById(R.id.registerButton)
        login = findViewById(R.id.register_login)
        pass = findViewById(R.id.register_pass)
        pass2 = findViewById(R.id.register_pass2)

        login_intent.setOnClickListener{
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        register_submit.setOnClickListener{
            insertUser(login.text.toString(),pass.text.toString(),pass2.text.toString())
        }
    }

    fun insertUser(login: String, password: String, password2: String) {
        if (password == password2 && password != "" && login != "") {
            val method = "register"
            val dbHelper = DBHelper(this)
            dbHelper.execute(method, login, password)
        } else {
            Toast.makeText(applicationContext,"Correct the mistake!",Toast.LENGTH_SHORT).show()
        }
    }

    fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}