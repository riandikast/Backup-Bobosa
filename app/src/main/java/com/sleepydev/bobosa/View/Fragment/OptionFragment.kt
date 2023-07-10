package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.sleepydev.bobosa.Datastore.StateManager
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentOptionBinding
import com.sleepydev.bobosa.databinding.FragmentPanduanBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OptionFragment : Fragment() {

    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentOptionBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as MainActivity).refreshTitle()
        val dataManager = StateManager(requireContext())
        dataManager.tempState.asLiveData().observe(requireActivity()) {
           binding.switch1.isChecked = it
        }

        binding.switch1.setOnCheckedChangeListener{ _, isChecked ->

            if (binding.switch1.isChecked) {
                GlobalScope.launch {
                    dataManager.saveTemporaryState(true)
                }

            } else {
                GlobalScope.launch {
                    dataManager.saveTemporaryState(false)
                }
            }

        }
        return  view
    }


}