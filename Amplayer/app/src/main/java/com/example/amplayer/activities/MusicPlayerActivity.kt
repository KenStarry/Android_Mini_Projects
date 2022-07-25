package com.example.amplayer.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.amplayer.R
import com.example.amplayer.models.SongsModel
import com.example.amplayer.singletons.MyMediaPlayer
import com.example.amplayer.utility.QuerySongs
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
    private var currentSongPosition: Int? = null
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
//        currentSongModel = intent.getSerializableExtra("CURR_SONG_MODEL") as SongsModel?
        currentSongPosition = intent.getIntExtra("POSITION", 0)
        songsModelArrayList = QuerySongs(this).queryMusic()

        setResourcesWithCurrentMusic(currentSongPosition!!)

        Thread(Runnable {

            runOnUiThread {
                if (mediaPlayer != null) {
                    seekbar?.progress = mediaPlayer?.currentPosition!!
                    currentTime?.text = convertToMMSS(mediaPlayer?.currentPosition!!.toString())

                    if (mediaPlayer?.isPlaying!!) {
                        pausePlay?.setImageResource(R.drawable.ic_pause_circle)
                    } else
                        pausePlay?.setImageResource(R.drawable.ic_play_circle)
                } else
                    Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
            }

            Thread.sleep(100)
        }).start()

        seekbar?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, dragged: Boolean) {
                if (mediaPlayer != null && dragged) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun setResourcesWithCurrentMusic(pos: Int) {

        songTitle?.text = songsModelArrayList!![pos].songTitle
        //  For marquee effect
        songTitle?.isSelected = true
        totalTime?.text = convertToMMSS(songsModelArrayList!![pos].songDuration)

        pausePlay?.setOnClickListener { pausePlay() }
        nextBtn?.setOnClickListener { playNextSong() }
        prevBtn?.setOnClickListener { playPreviousSong() }

        playMusic()

    }

    fun playMusic() {

        //  reset the media player
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(songsModelArrayList!![currentSongPosition!!].songPath)
        mediaPlayer?.prepare()
        mediaPlayer?.start()

        //  working with the seekbar
        seekbar?.progress = 0
        seekbar?.max = mediaPlayer?.duration!!
    }

    private fun playNextSong() {

        if (currentSongPosition ==  1)
            return

        currentSongPosition = currentSongPosition?.plus(1)
        mediaPlayer?.reset()
        setResourcesWithCurrentMusic(currentSongPosition!!)

    }

    private fun playPreviousSong() {

        if (currentSongPosition == 0)
            return

        currentSongPosition = currentSongPosition?.minus(1)
        mediaPlayer?.reset()
        setResourcesWithCurrentMusic(currentSongPosition!!)

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