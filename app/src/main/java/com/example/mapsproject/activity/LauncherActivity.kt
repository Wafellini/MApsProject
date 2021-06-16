package com.example.mapsproject.activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.mapsproject.R
import kotlin.properties.Delegates

@Suppress("DEPRECATION")
class LauncherActivity : AppCompatActivity() {

    lateinit var lantext: TextView
    lateinit var top: ImageView
    lateinit var icMap: ImageView
    lateinit var bot: ImageView

    lateinit var chSequence: CharSequence
    var id by Delegates.notNull<Int>()
    val delay: Long = 200
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        lantext = findViewById(R.id.lan_text)
        top = findViewById(R.id.iv_top)
        icMap = findViewById(R.id.iv_map)
        bot = findViewById(R.id.iv_bot)

        initAnimation()

         openLoginActivity()
    }

    fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun initAnimation(){
        val topAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.top_wave)
        top.animation = topAnim

        val objectAnimator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            icMap,
            PropertyValuesHolder.ofFloat("scaleX",1.2f),
            PropertyValuesHolder.ofFloat("scaleY",1.2f)
        )
        objectAnimator.duration = 500
        objectAnimator.repeatCount = ValueAnimator.INFINITE
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.start()

        animateText("MApp")

        val botAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.bot_wave)
        bot.animation = botAnim
    }

    private val runnable = object : Runnable {
        override fun run() {
            lantext.text = chSequence.subSequence(0,id++)
            if (id <= chSequence.length){
                handler.postDelayed(this,delay)
            }
        }
    }

    fun animateText(cs: CharSequence){
        chSequence = cs
        id = 0
        lantext.text = ""
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable,delay)
    }

}