package com.example.amplayer.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amplayer.R
import com.example.amplayer.activities.MusicPlayerActivity
import com.example.amplayer.models.SongsModel
import com.example.amplayer.singletons.MyMediaPlayer
import java.util.ArrayList

class SongsRecyclerAdapter(
    private val context: Context,
    private val songsArrayList: ArrayList<SongsModel>

) : RecyclerView.Adapter<SongsRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val songName: AppCompatTextView = itemView.findViewById(R.id.songName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.song_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model: SongsModel = songsArrayList[position]

        holder.songName.text = model.songTitle

        holder.itemView.setOnClickListener {
            //  Reset the media player
            MyMediaPlayer().getInstance()?.reset()
            MyMediaPlayer().currentIndex = position

            val intent = Intent(context, MusicPlayerActivity::class.java)
            intent.putExtra("LIST", songsArrayList)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)

            toast(model.songDuration)
        }
    }

    override fun getItemCount(): Int {
        return songsArrayList.size
    }

    //  Miscellaneous Functions
    private fun toast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}