package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentHomeBinding
import com.sleepydev.bobosa.databinding.FragmentSplashBinding
import com.sleepydev.bobosa.databinding.InputDialogBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async



class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val navbar = activity?.findViewById<AppBarLayout>(R.id.parentbar)
        navbar?.visibility = View.VISIBLE
        (activity as MainActivity).refreshTitle()
        binding.startestimasi.setOnClickListener {
            val inputBinding = InputDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            inputBinding.btnhitung.setOnClickListener {
                a.dismiss()
                findNavController().navigate(R.id.resultFragment)
            }
            a.show()

        }
        return view
    }


}