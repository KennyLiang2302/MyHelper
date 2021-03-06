package com.example.tutorme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * Use the [InvitesList.newInstance] factory method to
 * create an instance of this fragment.
 */

private val client = OkHttpClient()
private lateinit var layoutManager: LinearLayoutManager
private lateinit var adapter: InviteAdapter
private lateinit var recyclerView: RecyclerView
class InvitesList : Fragment(), InviteAdapter.AdapterOnClickHandler {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        val rootView: View = inflater.inflate(R.layout.fragment_invites_list, container, false)
        CoroutineScope(Dispatchers.Main).launch {

            val id = arguments!!.get("userID")

            val request = Request.Builder()
                .url("https://my-helper-appdev.herokuapp.com/api/user/invites/$id/")
                .build()
            withContext(Dispatchers.IO) {

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()

                    val userAdapter = moshi.adapter(InviteList::class.java)


                    val users = userAdapter.fromJson(response.body!!.string())


                    withContext(Dispatchers.Main) {
                        val context: Context? = context

                        layoutManager = LinearLayoutManager(context)
                        recyclerView = rootView.findViewById(R.id.recyclerViewInvites)
                        recyclerView.layoutManager = layoutManager


                        val session = arguments!!.getString("session")
                        var inviteList = mutableListOf<InviteData>()
                        for (invite in users!!.data){
                            val subject = findSubjectID(invite.subject_id)
                            val inviteData = session?.let {
                                InviteData(invite.sender[0].name, subject, invite.id,
                                    it
                                )
                            }
                            if (inviteData != null) {
                                inviteList.add(inviteData)
                            }
                        }
                        val inviteID = arguments?.getInt("inviteID")
                        if (inviteID != null){
                            var inviteNumber = -1
                            for (invite in inviteList){
                                inviteNumber += 1
                                if (inviteID == invite.invite_id)
                                    inviteList.removeAt(inviteNumber)

                            }
                        }




                        adapter = InviteAdapter(inviteList, this@InvitesList)
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
         * @return A new instance of fragment InvitesList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InvitesList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance(userID: Int?, session: String, inviteID: Int): InvitesList? {
            val args = Bundle()
            if (userID != null) {
                args.putInt("userID", userID)


            }
            args.putInt("inviteID", inviteID)
            args.putString("session", session)
            val f = InvitesList()
            f.setArguments(args)
            return f
        }
    }

    private fun findSubjectID(subjectID: Int): String {
        if (subjectID == 1){return "History"}
        if (subjectID == 2){return "Math"}
        if (subjectID == 3){return "Computer Science"}
        if (subjectID == 4){return "Physics"}
        return "Chemistry"
    }

    override fun onClick(position: Int, inviteList: List<InviteData>) {
        val intent = Intent(context, AcceptInvite::class.java)
        intent.putExtra("inviteID", inviteList[position].invite_id)
        intent.putExtra("session",  inviteList[position].session)
        startActivity(intent)
    }

}