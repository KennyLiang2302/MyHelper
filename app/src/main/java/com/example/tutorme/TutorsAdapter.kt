package com.example.tutorme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TutorsAdapter(private var tutorList: MutableList<Tutors>,
                     private val mAdapterOnClickHandler: AdapterOnClickHandler
)
    : RecyclerView.Adapter<TutorsAdapter.ViewHolder>() {
    interface AdapterOnClickHandler {
        // you can define the parameters to be what you need
        fun onClick(position: Int, tutorList: MutableList<Tutors>)
    }

    class ViewHolder internal constructor(tutorsView: View) :
        RecyclerView.ViewHolder(tutorsView) {
        val layout: ConstraintLayout = tutorsView.findViewById(R.id.tutorsLayout)
        val name: TextView = tutorsView.findViewById(R.id.name)
        val education: TextView = tutorsView.findViewById(R.id.education)
        val gpa: TextView = tutorsView.findViewById(R.id.gpa)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tutors, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorsAdapter.ViewHolder, position: Int) {
        holder.name.text = tutorList[position].name
        holder.education.text = "Education: " + tutorList[position].education
        holder.gpa.text = "GPA: " + tutorList[position].GPA.toString()

        holder.layout.setOnClickListener {
            mAdapterOnClickHandler.onClick(position, tutorList)
        }

    }
    override fun getItemCount(): Int {
        return tutorList.size
    }
}