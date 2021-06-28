@file:Suppress("DEPRECATION")

package com.example.mapsproject.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.mapsproject.activity.*
import com.example.mapsproject.activity.LoginActivity.Companion.USER
import com.example.mapsproject.fragment.ProfileFragment
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
    val ip = "https://192.168.0.19/mapp/"
    override fun doInBackground(vararg params: String?): String? {
        method = params[0].toString()
        if (method == "register") {
            val name = params[1]
            val password = params[2]
            lateinit var response: String
            val reg_url = ip+"register.php"
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
                val bufferedReader = BufferedReader(InputStreamReader(`is`))

                try {
                    while ((bufferedReader.readLine().also { response = it }) != null) {
                    }
                } catch (e: Exception) {
                }

                bufferedReader.close()
                `is`.close()
                return response
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
            val log_url = ip+"login.php"
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

        } else if (method == "insert score") {
            val login = params[1]
            val score = params[2]
            val res_url = ip+"insertscore.php"
            try {
                val url = URL(res_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("login", "UTF-8").toString() + "=" + URLEncoder.encode(
                        login, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("score", "UTF-8")
                                .toString() + "=" + URLEncoder.encode(
                        score, "UTF-8"
                    )

                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                os.close()
                val `is`: InputStream = httpURLConnection.inputStream
                `is`.close()
                return "Success"
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (method == "get best") {
            val login = params[1]
            lateinit var response: String
            val log_url = ip+"getbest.php"
            try {
                val url = URL(log_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("login", "UTF-8").toString() + "=" + URLEncoder.encode(
                        login, "UTF-8"
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
                Log.i("aa",response)
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
        } else if (method == "register" && result == "Success" && activity is RegisterActivity) {
            Toast.makeText(activity.applicationContext, "Success", Toast.LENGTH_SHORT).show()
            activity.openLoginActivity()
        } else if (method == "register" && result == "Error") {
            Toast.makeText(activity.applicationContext, "Database Error", Toast.LENGTH_SHORT).show()
        } else if (method == "get best") {
            ProfileFragment.setBest(result!!)
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onProgressUpdate(vararg values: Void?) {
        super.onProgressUpdate(*values)
    }
}