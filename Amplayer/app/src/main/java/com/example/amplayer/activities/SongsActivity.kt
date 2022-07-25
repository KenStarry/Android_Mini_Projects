package com.example.amplayer.activities

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.amplayer.utility.QuerySongs
import java.util.ArrayList

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
        songsArrayList = QuerySongs(this).queryMusic()

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
