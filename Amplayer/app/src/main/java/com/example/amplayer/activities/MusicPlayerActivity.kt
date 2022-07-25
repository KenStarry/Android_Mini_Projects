package com.example.amplayer.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.example.amplayer.R
import com.example.amplayer.models.SongsModel
import com.example.amplayer.singletons.MyMediaPlayer
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : AppCompatActivity(), Runnable {

    private var songTitle: TextView? = null
    private var currentTime: TextView? = null
    private var totalTime: TextView? = null
    private var seekbar: SeekBar? = null
    private var pausePlay: ImageView? = null
    private var nextBtn: ImageView? = null
    private var prevBtn: ImageView? = null
    private var musicIcon: ImageView? = null

    private var songsModelArrayList: ArrayList<SongsModel>? = null
    private var currentSongModel: SongsModel? = null
    private var mediaPlayer: MediaPlayer? = MyMediaPlayer().getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        songTitle = findViewById(R.id.playSongTitle)
        currentTime = findViewById(R.id.currentTime)
        totalTime = findViewById(R.id.totalTime)
        seekbar = findViewById(R.id.seekbar)
        pausePlay = findViewById(R.id.pause_play)
        nextBtn = findViewById(R.id.next)
        prevBtn = findViewById(R.id.prev)
        musicIcon = findViewById(R.id.playSongImage)

        setResourcesWithCurrentMusic()

        this@MusicPlayerActivity.runOnUiThread {

            if (mediaPlayer != null) {
                seekbar?.progress = mediaPlayer?.currentPosition!!
                currentTime?.text = convertToMMSS(mediaPlayer?.currentPosition!!.toString())
            }
        }
    }

    private fun setResourcesWithCurrentMusic() {

        currentSongModel = intent.getSerializableExtra("CURR_SONG_MODEL") as SongsModel?
        songsModelArrayList = intent.getSerializableExtra("SONGS_ARRAYLIST") as ArrayList<SongsModel>

        songTitle?.text = currentSongModel?.songTitle
        //  For marquee effect
        songTitle?.isSelected = true
        totalTime?.text = convertToMMSS(currentSongModel?.songDuration)

        pausePlay?.setOnClickListener { pausePlay() }
        nextBtn?.setOnClickListener { playNextSong() }
        prevBtn?.setOnClickListener { playPreviousSong() }

        playMusic()

    }

    fun playMusic() {

        //  reset the media player
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(currentSongModel?.songPath)
        mediaPlayer?.prepare()
        mediaPlayer?.start()

        //  working with the seekbar
        seekbar?.progress = 0
        seekbar?.max = mediaPlayer?.duration!!
    }

    fun playNextSong() {

        if (MyMediaPlayer().currentIndex ==  1)
            return

        MyMediaPlayer().currentIndex += 1
        mediaPlayer?.reset()
        setResourcesWithCurrentMusic()

    }

    fun playPreviousSong() {

        if (MyMediaPlayer().currentIndex == 0)
            return

        MyMediaPlayer().currentIndex -= 1
        mediaPlayer?.reset()
        setResourcesWithCurrentMusic()

    }

    fun pausePlay() {

        if (mediaPlayer?.isPlaying == true)
            mediaPlayer?.pause()
        else
            mediaPlayer?.start()

    }

    //  Convert time
    fun convertToMMSS(duration: String?): String {

        //  convert to milliseconds first
        val millis: Long? = duration?.toLong()

        return String.format("%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millis!!) % TimeUnit.DAYS.toHours(1),
            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        )
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}