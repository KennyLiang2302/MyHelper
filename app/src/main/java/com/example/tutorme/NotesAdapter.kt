package com.example.tutorme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var noteList: MutableList<Notes>?,
                   private val mAdapterOnClickHandler: AdapterOnClickHandler)
    : RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    interface AdapterOnClickHandler {

        // you can define the parameters to be what you need
        fun onClick(position: Int)

    }

    class ViewHolder internal constructor(noteView: View):
            RecyclerView.ViewHolder(noteView){
        val layout: ConstraintLayout = noteView.findViewById(R.id.noteLayout)
        val title: TextView = noteView.findViewById(R.id.title)
        val detail: TextView = noteView.findViewById(R.id.detail)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.note, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        holder.title.text = noteList!![position].title
        holder.detail.text = noteList!![position].detail

        holder.layout.setOnClickListener {
            mAdapterOnClickHandler.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return noteList!!.size
    }
}