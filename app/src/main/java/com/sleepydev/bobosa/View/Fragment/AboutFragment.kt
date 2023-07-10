package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentAboutBinding
import com.sleepydev.bobosa.databinding.FragmentAccuracyBinding


class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as MainActivity).refreshTitle()


        return view
    }


}