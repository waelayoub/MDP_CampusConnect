package com.example.campusconnect

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.campusconnect.databinding.FragmentMyEventsBinding


class MyEvents : Fragment() {

    private lateinit var binding: FragmentMyEventsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMyEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

}