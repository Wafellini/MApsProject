@file:Suppress("DEPRECATION")

package com.example.mapsproject.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.mapsproject.activity.LauncherActivity
import com.example.mapsproject.activity.LoginActivity
import com.example.mapsproject.activity.MapActivity
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


@Suppress("DEPRECATION")
class DBHelper(private val activity: Activity) : AsyncTask<String?, Void?, String?>() {
    @SuppressLint("StaticFieldLeak")
    lateinit var method: String
    lateinit var username: String
    override fun doInBackground(vararg params: String?): String? {
        method = params[0].toString()
        if (method == "register") {
            val name = params[1]
            val password = params[2]
            val reg_url = "https://192.168.0.15/mapp/register.php"
            try {
                val url = URL(reg_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("name", "UTF-8").toString() + "=" + URLEncoder.encode(
                        name, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
                        password, "UTF-8"
                    )

                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                os.close()
                val `is`: InputStream = httpURLConnection.inputStream
                `is`.close()
                return "Registration success"
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (method == "login") {
            val name = params[1]
            username = name.toString()
            val password = params[2]
            lateinit var response: String
            val log_url = "https://192.168.0.15/mapp/login.php"
            try {
                val url = URL(log_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("name", "UTF-8").toString() + "=" + URLEncoder.encode(
                        name, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
                        password, "UTF-8"
                    )

                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                os.close()

                val `is`: InputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(`is`))

                try {
                    while ((bufferedReader.readLine().also { response = it }) != null) {
                    }
                } catch (e: Exception) {
                }

                bufferedReader.close()
                `is`.close()
                httpURLConnection.disconnect()
                return response
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return null
    }

    override fun onPostExecute(result: String?) {
        if (method == "login" && result == "Success" && activity is LoginActivity) {
            Toast.makeText(activity.applicationContext, "Success", Toast.LENGTH_SHORT).show()
            activity.openMainActivity()
        } else if (method == "login" && result == "Error") {
            Toast.makeText(activity.applicationContext, "Error", Toast.LENGTH_SHORT).show()
        } else if (method == "register") {
            Toast.makeText(activity.applicationContext, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onProgressUpdate(vararg values: Void?) {
        super.onProgressUpdate(*values)
    }
}