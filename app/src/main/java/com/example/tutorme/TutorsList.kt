package com.example.tutorme

import android.R.id
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private val client = OkHttpClient()
private lateinit var layoutManager: LinearLayoutManager
private lateinit var adapter: TutorsAdapter
private lateinit var recyclerView: RecyclerView

class TutorsList() : Fragment(), TutorsAdapter.AdapterOnClickHandler {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var listofTutors: MutableList<TutorList>

    interface DataPassListener {
        fun onPress(position: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_tutors_list, container, false)


        CoroutineScope(Dispatchers.Main).launch {

            val request = Request.Builder()
                .url("https://my-helper-appdev.herokuapp.com/api/users/")
                .build()
            withContext(Dispatchers.IO) {

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()

                    val userAdapter = moshi.adapter(UserList::class.java)

                    val users = userAdapter.fromJson(response.body!!.string())


                    withContext(Dispatchers.Main) {
                        val context: Context? = context

                        layoutManager = LinearLayoutManager(context)
                        recyclerView = rootView.findViewById(R.id.recyclerView)
                        recyclerView.layoutManager = layoutManager

                        listofTutors = mutableListOf()
//                        Log.d("users", users?.data.toString())
                        var userNumber = -1
                        for (user in users!!.data) {
                            userNumber += 1
                            if (user.tutor.size == 1 && users.data.size > 0 && user.tutor[0].subjects.size == 1) {
                                Log.d("user", user.name)
//                                Log.d("size", user.tutor.size.toString())
                                val tutor = users.data[userNumber].tutor[0]
                                val tutorList = TutorList(
                                    user.name,
                                    user.netid,
                                    tutor.description,
                                    tutor.subjects[0]?.name,
                                    user.id,
                                )

                                listofTutors.add(tutorList)
                            }
                        }
                        adapter = TutorsAdapter(listofTutors, this@TutorsList)
                        recyclerView.adapter = adapter
                    }

                }
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
         * @return A new instance of fragment ListFragment.
         */

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TutorsList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance(sessionID: String?): TutorsList? {
            val args = Bundle()
            args.putString("sessionID", sessionID)
            val f = TutorsList()
            f.setArguments(args)
            return f
        }
    }


    override fun onClick(position: Int, tutorList: MutableList<TutorList>) {


                    val sessionID = arguments!!.getString("sessionID")
                    val intent = Intent(context, InviteActivity::class.java)
                    intent.putExtra("session_token", sessionID)
                    intent.putExtra("receiverID", tutorList[position].id)
                    intent.putExtra("name", tutorList[position].name)
                    intent.putExtra("user", "student")
                    intent.putExtra("netID", tutorList[position].netid)
                    intent.putExtra("subject", tutorList[position].subject)
                    startActivity(intent)


                }


            }

