package com.example.mapsproject.data

import android.os.AsyncTask
import com.example.mapsproject.activity.LauncherActivity
import com.example.mapsproject.activity.MainActivity.Companion.COUNTRIES
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class APIHelper(private val activity: LauncherActivity) :
    AsyncTask<Void, Void, ArrayList<Country>>() {

    override fun doInBackground(vararg params: Void?): ArrayList<Country>? {
        for (i in 0..1) {
            val url = "https://countriesnow.space/api/v0.1/countries/positions"
            val connection = URL(url)
            val httpURLConnection = connection.openConnection() as HttpURLConnection
            httpURLConnection.connectTimeout = 3000
            httpURLConnection.connect()
            if (httpURLConnection.responseCode == 200) {
                val text = connection.readText()
                if (text.compareTo("") == 0) {
                    return null
                }
                COUNTRIES += getCountriesFromResponse(text)
            } else {
                return null
            }
        }
        return COUNTRIES
    }

    override fun onPostExecute(result: ArrayList<Country>?) {
        if (result != null) {
            activity.openLoginActivity()
        }
        super.onPostExecute(result)
    }


    private fun getCountriesFromResponse(response: String): ArrayList<Country> {
        val ctries = ArrayList<Country>()
        val jsonObject = JSONObject(response)
        val countriesJSON = jsonObject.get("data") as JSONArray
        for (i in 0 until countriesJSON.length()) {
            val c = countriesJSON[i] as JSONObject
            val country = Country(
                c.getString("name"),
                LatLng(c.getInt("lat").toDouble(), c.getInt("long").toDouble())
            )
            ctries.add(country)
        }
        return ctries
    }
}