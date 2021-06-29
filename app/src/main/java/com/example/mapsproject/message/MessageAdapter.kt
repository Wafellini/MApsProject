package com.example.mapsproject.message

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapsproject.R

class MessageAdapter(
    private val messages: MutableList<Message>
) : RecyclerView.Adapter<MessageAdapter.messageViewHolder>() {
    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rankingLogin = itemView.findViewById<TextView>(R.id.rankingLogin)
        val rankingScore = itemView.findViewById<TextView>(R.id.rankingScore)

        fun bind(curMessage: Message,position: Int){
            if (position <= 2)
                setTopColors(position)
            rankingLogin.text = curMessage.rankingLogin
            rankingScore.text = curMessage.rankingScore
        }

        fun setTopColors(position: Int){
            if (position == 0)
                rankingLogin.setTextColor(Color.parseColor("#FFD700"))
            else if (position == 1)
                rankingLogin.setTextColor(Color.parseColor("#c0c0c0"))
            else if (position == 2)
                rankingLogin.setTextColor(Color.parseColor("#cd7f32"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ranking_item, parent, false)
        )
    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curMessage = messages[position]
        holder.bind(curMessage,position)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}