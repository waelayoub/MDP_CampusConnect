package com.example.campusconnect

import android.app.Activity
import android.app.Application
import android.os.Bundle


import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.campusconnect.databinding.FragmentHomeBinding
import com.google.firebase.database.*


class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var adapter: EventModelAdapter
    private lateinit var eventRecyclerView: RecyclerView

    private var eventlist= arrayListOf<EventModel>()



    private fun getEventData(){
        dbref= FirebaseDatabase.getInstance().getReference("Events")
        dbref.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot){
                if (snapshot.exists()){

                    for (eventSnapshot in snapshot.children){
                        val event=eventSnapshot.getValue(EventModel::class.java)
                        event!!.eventId=eventSnapshot.key
                        eventlist.add(event!!)
                    }
                    eventRecyclerView.adapter=EventModelAdapter(requireContext(),eventlist)
                }
            }
            override fun onCancelled(error: DatabaseError){

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater, container, false)

        eventlist.clear()

        eventRecyclerView = binding.eventlist
        adapter = EventModelAdapter(requireContext(),eventlist)

        eventRecyclerView.layoutManager=LinearLayoutManager(context)
        eventRecyclerView.setHasFixedSize(true)
        eventRecyclerView.adapter=adapter

        getEventData()

        return binding.root
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        eventlist.clear()
//        getEventData()
//        val layoutManager=LinearLayoutManager(context)
//        eventRecyclerView=view.findViewById(R.id.eventlist)
//        eventRecyclerView.layoutManager=layoutManager
//        eventRecyclerView.setHasFixedSize(true)
//        adapter= EventModelAdapter(requireContext(),eventlist)
//        eventRecyclerView.adapter=adapter
//
//
//
//    }


}