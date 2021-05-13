package com.example.tutorme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class InviteAdapter(private var inviteList: List<InviteData>,
                    private val mAdapterOnClickHandler: AdapterOnClickHandler
)
    : RecyclerView.Adapter<InviteAdapter.ViewHolder>() {
    interface AdapterOnClickHandler {
        // you can define the parameters to be what you need
        fun onClick(position: Int, tutorList: List<InviteData>)
    }

    class ViewHolder internal constructor(invitesView: View) :
        RecyclerView.ViewHolder(invitesView) {
        val layout: CardView = invitesView.findViewById(R.id.invite_layout)
        val student: TextView = invitesView.findViewById(R.id.student)
        val date: TextView = invitesView.findViewById(R.id.date)
        val subject: TextView = invitesView.findViewById(R.id.subject)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.invites, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InviteAdapter.ViewHolder, position: Int) {
        holder.student.text = inviteList[position].student
        holder.subject.text = inviteList[position].subject
        holder.date.text = inviteList[position].toString()

        holder.layout.setOnClickListener {
            mAdapterOnClickHandler.onClick(position, inviteList)
        }

    }
    override fun getItemCount(): Int {
        return inviteList.size
    }

}