package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.databinding.FragmentHomeBinding
import com.sleepydev.bobosa.databinding.FragmentLibBinding


class LibFragment : Fragment() {
    private var _binding: FragmentLibBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentLibBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }


}