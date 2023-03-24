package com.example.campusconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.campusconnect.databinding.FragmentEventInfoBinding


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

        return binding.root
    }

}