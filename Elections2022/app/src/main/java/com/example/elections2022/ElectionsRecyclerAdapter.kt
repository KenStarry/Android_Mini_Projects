package com.example.elections2022

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ElectionsRecyclerAdapter(
    private val context: Context,
    private val arrayList: ArrayList<CandidateModel>

) : RecyclerView.Adapter<ElectionsRecyclerAdapter.ElectionViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.candidate_row,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {

        val model: CandidateModel = arrayList.get(position)
        holder.candidateName.text = model.candidateName
        holder.candidateParty.text = model.candidateParty
        holder.candidateVotes.text = model.candidateVotes.toString()
        Picasso.get().load(model.candidatePicture).into(holder.candidateImage)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ElectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val candidateName: TextView = itemView.findViewById(R.id.candidateName)
        val candidateParty: TextView = itemView.findViewById(R.id.candidateParty)
        val candidateVotes: TextView = itemView.findViewById(R.id.candidateVotes)
        val candidateImage: ImageView = itemView.findViewById(R.id.candidateImage)

    }
}