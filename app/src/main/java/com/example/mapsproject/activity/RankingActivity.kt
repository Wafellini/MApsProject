package com.example.mapsproject.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapsproject.R
import com.example.mapsproject.data.DBHelper
import com.example.mapsproject.message.Message
import com.example.mapsproject.message.MessageAdapter

@Suppress("DEPRECATION")
class RankingActivity : AppCompatActivity() {

    companion object{
        val RANKING = mutableMapOf<String,Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()
        getRanking()
    }

    fun getRanking() {
        val method = "get ranking"
        val dbHelper = DBHelper(this)
        dbHelper.execute(method)
    }

    fun setRanking(){
        val messageAdapter = MessageAdapter(mutableListOf())
        val messageList = findViewById<RecyclerView>(R.id.messageList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)

        val result = RANKING.toList().sortedBy { (_, value) -> value }.reversed().toMap()

        for (entry in result) {
            val new = Message(entry.key, entry.value.toString())
            messageAdapter.addMessage(new)
        }
    }
}