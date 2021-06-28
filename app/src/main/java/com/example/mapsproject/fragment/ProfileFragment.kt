package com.example.mapsproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mapsproject.R
import com.example.mapsproject.activity.LoginActivity.Companion.USER

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
            login_data.text = USER.login
        }
    }
}