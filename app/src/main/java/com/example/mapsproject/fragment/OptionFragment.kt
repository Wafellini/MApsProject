package com.example.mapsproject.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.example.mapsproject.R
import com.example.mapsproject.service.BackgroundSoundService
import kotlin.properties.Delegates


class OptionFragment : Fragment() {

    var length by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_opt, container, false)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "SetTextI18n")
    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
//            val dark_mode = view.findViewById<Switch>(R.id.dmode)
//            val lsensor = view.findViewById<Switch>(R.id.lsensor)
            val mute = view.findViewById<ImageView>(R.id.iv_mute)
            val nmute = view.findViewById<ImageView>(R.id.iv_nmute)

//            lsensor.setOnClickListener {
//                if (lsensor.isChecked) {
//                    lsensor.text = "Light sensor On"
//                    dark_mode.visibility = View.GONE
//                } else {
//                    lsensor.text = "Light sensor Off"
//                    dark_mode.visibility = View.VISIBLE
//                }
//            }
//            dark_mode.setOnClickListener {
//                if (!lsensor.isChecked) {
//                    if (dark_mode.isChecked) {
//                        dark_mode.text = "Dark Mode"
////                        requireActivity().theme.applyStyle(R.style.darkTheme, true)
//                        //setTheme("darkTheme");
//                    } else {
//                        dark_mode.text = "Light Mode"
////                        requireActivity().theme.applyStyle(R.style.lightTheme, true)
//                        //setTheme("lightTheme");
//                    }
//                }
//            }
            nmute.setOnClickListener {
                mute.visibility = View.VISIBLE
                nmute.visibility = View.GONE
                BackgroundSoundService.mediaPlayer!!.pause()
                length = BackgroundSoundService.mediaPlayer!!.currentPosition
            }

            mute.setOnClickListener {
                mute.visibility = View.GONE
                nmute.visibility = View.VISIBLE
                BackgroundSoundService.mediaPlayer!!.seekTo(length)
                BackgroundSoundService.mediaPlayer!!.start()
            }

        }
    }
}