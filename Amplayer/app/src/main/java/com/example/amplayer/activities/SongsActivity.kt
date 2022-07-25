package com.example.amplayer.activities

import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amplayer.R
import com.example.amplayer.adapters.SongsRecyclerAdapter
import com.example.amplayer.models.SongsModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.util.ArrayList
import java.util.jar.Manifest

class SongsActivity : AppCompatActivity() {

    private var songsRecyclerAdapter: SongsRecyclerAdapter? = null
    private var songsArrayList: ArrayList<SongsModel>? = null
    private var layoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var noSongsText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs)

        noSongsText = findViewById(R.id.noSongs)

        if (!checkPermission()) {
            requestPermission()
            return
        }

        songsArrayList = ArrayList()
        queryMusic()

    }

    private fun queryMusic() {

        //  Array of our Mediastore projection
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM
        )

        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val cursor: Cursor = contentResolver.query(
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
                songsArrayList!!.add(model)
        }

        //  If no songs have been found,
        if (songsArrayList?.size == 0) {
            noSongsText?.visibility = View.VISIBLE

        } else {

            //  Setup recycler view
            layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView = findViewById(R.id.allSongsRecycler)
            songsRecyclerAdapter = SongsRecyclerAdapter(this, songsArrayList!!)

            recyclerView?.layoutManager = layoutManager
            recyclerView?.adapter = songsRecyclerAdapter
        }

        cursor.close()
    }

    private fun checkPermission(): Boolean {

        val result = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
            Toast.makeText(this, "Please allow storage permission", Toast.LENGTH_SHORT).show()
        else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                123
            )

    }
}
