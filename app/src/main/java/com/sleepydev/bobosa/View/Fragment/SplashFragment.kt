package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding ? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        val navbar = activity?.findViewById<AppBarLayout>(R.id.parentbar)
        navbar?.visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed({

            view.findNavController().navigate(
                R.id.homeFragment, null,
                NavOptions.Builder()
                    .setPopUpTo(
                        R.id.splashFragment,
                        true
                    ).build())

        }, 2000)
        return view
    }


}