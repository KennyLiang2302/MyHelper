package com.example.tutorme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.lang.NullPointerException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditNoteFragment(boolean: Boolean) : Fragment() {
    // TODO: Rename and change types of parameters


    private var boolean: Boolean = boolean


    lateinit var mCallback: DataPassListener

    interface DataPassListener {
        fun onButtonPress(position: Int)
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
        val rootView: View = inflater.inflate(R.layout.fragment_edit_note, container, false)
        val editTitle: EditText = rootView.findViewById(R.id.editTitle)
        val editDetail: EditText = rootView.findViewById(R.id.editDetail)


        val notes1 = Notes("Title", "Detail")
        val notes2 = Notes("Title2", "Detail2")
        var noteList: MutableList<Notes> = mutableListOf()
        noteList.add(notes1)
        noteList.add(notes2)

        if (!boolean) {
            val b: Bundle? = getArguments()
            val position: Int = b!!.getInt("position")
            editTitle.setText(noteList[position].title)
            editDetail.setText(noteList[position].detail)

            val saveButton: Button = rootView.findViewById(R.id.saveButton)
            saveButton.setOnClickListener {
                noteList[position].title = editTitle.text.toString()
                noteList[position].detail = editDetail.text.toString()
                mCallback = activity as DataPassListener
                mCallback.onButtonPress(position)
            }
        }else if (boolean){
            val saveButton: Button = rootView.findViewById(R.id.saveButton)
            saveButton.setOnClickListener {
                val note = Notes(editTitle.text.toString(), editDetail.text.toString())
                noteList.add(note)
                mCallback = activity as DataPassListener
                mCallback.onButtonPress(0)
            }
        }





        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditNoteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(boolean: Boolean) =
            EditNoteFragment(boolean).apply {
                arguments = Bundle().apply {
                }
            }
    }
}