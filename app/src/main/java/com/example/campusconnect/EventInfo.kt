package com.example.campusconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.campusconnect.databinding.FragmentEventInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*
import java.text.SimpleDateFormat
import kotlin.collections.HashMap


class EventInfo(private val eventName: String,
                private val eventDate: String,
                private val eventTime: String,
                private val eventLocation: String,
                private val eventOrganizer: String,
                private val eventType: String,
                private val eventCapacity: String,
                private val eventDescription: String,
                private val eventFlyer: String,
                private val eventIcon: String,
                private val eventId: String) : Fragment() {

    private lateinit var binding: FragmentEventInfoBinding
    private val auth: FirebaseAuth = Firebase.auth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEventInfoBinding.inflate(inflater, container, false)
        binding.eventNameInfoID.setText(eventName)
        binding.eventTimeInfoID.setText(eventTime)
        binding.eventDescriptionInfoID.setText(eventDescription)
        binding.eventLocationInfoID.setText(eventLocation)
        binding.eventDateInfoID.setText(eventDate)
        Glide.with(requireContext()).load(eventFlyer).into(binding.eventFlyerInfoID)
        Glide.with(requireContext()).load(eventIcon).into(binding.eventLogoInfoID)
        binding.closebtn.setOnClickListener{
            val activity=it!!.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().apply{
                val popUp=Home()
                replace(R.id.frame_layout,popUp).commit()
            }
        }
        var dbref: DatabaseReference


        binding.registerButton.setOnClickListener {
            dbref= FirebaseDatabase.getInstance().getReference("registrations")


            val userId = auth.currentUser!!.uid


            // adding time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            // Format the Date object as a string using the SimpleDateFormat object
            val dateString = dateFormat.format(Date())
            // end time


            dbref.child(eventId).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val usersMap = snapshot.value as? HashMap<String, String>
                        if(usersMap?.contains(userId) == true){
                        }else{
                            usersMap?.set(userId, dateString)
                            if (usersMap != null) {
                                dbref.child(eventId).updateChildren(usersMap as kotlin.collections.Map<String, String>)
                            }
                        }
                    }
                    else{
                        val usersMap = HashMap<String, String>()
                        usersMap.set(userId, dateString)
                        dbref.child(eventId).updateChildren(usersMap as Map<String, String>)

                        println("Not exist but created")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


            println("The current user is: "+auth.currentUser!!.uid)
            println("The current event is : "+eventId)
        }

        return binding.root
    }

}