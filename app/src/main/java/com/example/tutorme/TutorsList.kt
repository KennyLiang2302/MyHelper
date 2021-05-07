package com.example.tutorme

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var layoutManager: LinearLayoutManager
private lateinit var adapter: TutorsAdapter
private lateinit var recyclerView: RecyclerView
class TutorsList() : Fragment(), TutorsAdapter.AdapterOnClickHandler {
    // TODO: Rename and change types of parameters
    interface DataPassListener {
        fun onPress(position: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_tutors_list, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)

        val context: Context? = context
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        var list1: MutableList<String> = mutableListOf("math", "Chem")

        val tutor1 = Tutors(
                "Paul Suh", list1, "Cornell", 3.5, 5.0,
                "Cornell University"
        )


        var tutorList:MutableList<Tutors> = mutableListOf()
        tutorList.add(tutor1)
        adapter = TutorsAdapter(tutorList, this)
        recyclerView.adapter = adapter



        return rootView

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Context) =
            TutorsList().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun onClick(position: Int, tutorList: MutableList<Tutors>) {
        TODO("Not yet implemented")
    }


}
