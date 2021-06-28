package com.example.mapsproject.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mapsproject.R
import com.example.mapsproject.activity.LoginActivity.Companion.USER
import com.example.mapsproject.data.DBHelper

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_prof, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val login_data = view.findViewById<TextView>(R.id.data_login)
            best_score = view.findViewById(R.id.best_score)
            login_data.text = USER.login
            getBest()
        }
    }

    fun getBest() {
        val method = "get best"
        val dbHelper = activity?.let { DBHelper(it) }
        dbHelper!!.execute(method, USER.login)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var best_score: TextView
        @SuppressLint("SetTextI18n")
        fun setBest(scr: String) {
            best_score.text = "Best Score: $scr"
        }
    }
}