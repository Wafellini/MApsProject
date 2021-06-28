package com.example.mapsproject.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mapsproject.R
import com.example.mapsproject.activity.LoginActivity

class OptionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_opt, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val dark_mode = view.findViewById<Switch>(R.id.dmode)
            val lsensor = view.findViewById<Switch>(R.id.lsensor)
            dark_mode.setOnClickListener{
                //dark mode on brrrrr
                if (!lsensor.isChecked) {

                    if (dark_mode.isChecked) {
                        dark_mode.text = "Dark Mode"
                    } else {
                        dark_mode.text = "Light Mode"
                    }
                }
            }
            lsensor.setOnClickListener{
                if (lsensor.isChecked) {
                    lsensor.text = "Light sensor On"
                    dark_mode.visibility = View.GONE
                } else {
                    lsensor.text = "Light sensor Off"
                    dark_mode.visibility = View.VISIBLE
                }
            }

        }
    }
}