package com.example.mapsproject.fragment

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mapsproject.R
import com.example.mapsproject.activity.LoginActivity
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
            val dark_mode = view.findViewById<Switch>(R.id.dmode)
            val lsensor = view.findViewById<Switch>(R.id.lsensor)
            val mute = view.findViewById<ImageView>(R.id.iv_mute)
            val nmute = view.findViewById<ImageView>(R.id.iv_nmute)

            dark_mode.setOnClickListener {
                if (!lsensor.isChecked) {

                    if (dark_mode.isChecked) {
                        dark_mode.text = "Dark Mode"
                    } else {
                        dark_mode.text = "Light Mode"
                    }
                }
            }
            lsensor.setOnClickListener {
                if (lsensor.isChecked) {
                    lsensor.text = "Light sensor On"
                    dark_mode.visibility = View.GONE
                } else {
                    lsensor.text = "Light sensor Off"
                    dark_mode.visibility = View.VISIBLE
                }
            }
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