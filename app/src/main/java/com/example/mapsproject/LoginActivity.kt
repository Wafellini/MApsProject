//https://captaindroid.com/nice-login-ui-design-in-android/
package com.example.mapsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mapsproject.common.Common
import com.example.mapsproject.model.APIResponse
import com.example.mapsproject.remote.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    internal lateinit var mService: MyAPI
    lateinit var register_intent: TextView
    lateinit var login_submit: TextView
    lateinit var login: EditText
    lateinit var pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mService = Common.api
        register_intent = findViewById(R.id.register_intent)
        login_submit = findViewById(R.id.login_submit)
        login = findViewById(R.id.login_login)
        pass = findViewById(R.id.login_pass)

        register_intent.setOnClickListener{
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }

        login_submit.setOnClickListener{
            verifyUser(login.text.toString(),pass.text.toString())
        }

    }

    private fun verifyUser(login: String, pass: String) {
        mService.loginUser(login,pass)
                .enqueue(object: Callback<APIResponse>{
                    override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                        if (response.body()!!.is_error!!)
                            Toast.makeText(applicationContext, response.body()!!.error_msg,Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(applicationContext, "Login Success!",Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message,Toast.LENGTH_LONG).show()
                    }
                })
    }
}
