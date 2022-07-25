package com.example.amplayer.utility

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.amplayer.models.SongsModel
import java.io.File

class QuerySongs(private var context: Context) {

    fun queryMusic(): ArrayList<SongsModel> {

        val songsArrayList: ArrayList<SongsModel> = ArrayList()

        //  Array of our Mediastore projection
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM
        )

        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val cursor: Cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )!!

        while (cursor.moveToNext()) {

            val model = SongsModel(
                cursor.getString(1),
                cursor.getString(0),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
            )

            //  If the song path exists, we can add it
            if (File(model.songPath).exists())
                songsArrayList.add(model)
        }

        cursor.close()

        return songsArrayList
    }
}