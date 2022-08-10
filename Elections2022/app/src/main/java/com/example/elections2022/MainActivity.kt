package com.example.elections2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ElectionsRecyclerAdapter
    private lateinit var arrayList: ArrayList<CandidateModel>

    private val url = "https://static.nation.africa/2022/president.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayList = ArrayList()
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ElectionsRecyclerAdapter(this, arrayList)

        queryData()
    }

    private fun queryData() {
        val requestQueue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,  url, null, {
            response ->

            Toast.makeText(this, response.length().toString(), Toast.LENGTH_SHORT).show()

        }, {
            error ->

            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        })

        requestQueue.add(jsonArrayRequest)
    }

    private fun buildRecyclerView(arrayList: ArrayList<CandidateModel>) {

        val allCandidatesRecyclerArrayList = java.util.ArrayList<CandidateModel>()

        for (i in 0 until arrayList.size) {
            allCandidatesRecyclerArrayList.add(arrayList.get(i))
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}