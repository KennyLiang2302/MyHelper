package com.example.tutorme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TutorsAdapter(private var tutorList: MutableList<TutorList>,
                    private val mAdapterOnClickHandler: AdapterOnClickHandler
)
    : RecyclerView.Adapter<TutorsAdapter.ViewHolder>() {
    interface AdapterOnClickHandler {
        // you can define the parameters to be what you need
        fun onClick(position: Int, tutorList: MutableList<TutorList>)
    }

    class ViewHolder internal constructor(tutorsView: View) :
        RecyclerView.ViewHolder(tutorsView) {
        val layout: CardView = tutorsView.findViewById(R.id.tutorLayout)
        val tutor: TextView = tutorsView.findViewById(R.id.tutor)
        val description: TextView = tutorsView.findViewById(R.id.description)
        val subject: TextView = tutorsView.findViewById(R.id.subject)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tutors, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorsAdapter.ViewHolder, position: Int) {
        holder.tutor.text = tutorList[position].name
        holder.subject.text = tutorList[position].subject
        holder.description.text = "Description: " + tutorList[position].description

        holder.layout.setOnClickListener {
            mAdapterOnClickHandler.onClick(position, tutorList)
        }

    }
    override fun getItemCount(): Int {
        return tutorList.size
    }
}