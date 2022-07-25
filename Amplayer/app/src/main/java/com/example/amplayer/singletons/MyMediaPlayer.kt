package com.example.amplayer.singletons

import android.media.MediaPlayer

class MyMediaPlayer {

    private var instance: MediaPlayer? = null

    @JvmName("getInstance1")
    fun getInstance(): MediaPlayer? {

        if (instance == null) instance = MediaPlayer()

        return instance
    }

    var currentIndex = 0
}