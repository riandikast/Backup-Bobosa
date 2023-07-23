package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentHomeBinding
import com.sleepydev.bobosa.databinding.FragmentLibBinding
import com.sleepydev.bobosa.databinding.InputDialogBinding
import com.sleepydev.bobosa.databinding.RumusDialogBinding


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
        (activity as MainActivity).refreshTitle()
        binding.lihatSDenmark.setOnClickListener {
            val inputBinding = RumusDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaRumus.text = "Rumus Schoorl Denmark"
            inputBinding.gambarRumus.setImageResource(R.drawable.rumus_sdenmark)
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }

            a.show()
        }
        binding.lihatSID.setOnClickListener {
            val inputBinding = RumusDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaRumus.text = "Rumus Schoorl Indonesia"
            inputBinding.gambarRumus.setImageResource(R.drawable.rumus_sindo)
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }

            a.show()
        }
        binding.lihatWEurope.setOnClickListener {
            val inputBinding = RumusDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaRumus.text = "Rumus Winter Eropa"
            inputBinding.gambarRumus.setImageResource(R.drawable.rumus_weurope)
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }

            a.show()
        }

        binding.lihatWID.setOnClickListener {
            val inputBinding = RumusDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaRumus.text = "Rumus Winter Indonesia"
            inputBinding.gambarRumus.setImageResource(R.drawable.rumus_windo)
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }

            a.show()
        }

        binding.lihatArjo.setOnClickListener {
            val inputBinding = RumusDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaRumus.text = "Rumus Lambourne"
            inputBinding.gambarRumus.setImageResource(R.drawable.rumus_lambourne)
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }

            a.show()
        }

        binding.lihatdjagra.setOnClickListener {
            val inputBinding = RumusDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaRumus.text = "Rumus Djagra"
            inputBinding.gambarRumus.setImageResource(R.drawable.rumus_djagra)
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }

            a.show()
        }
        return view
    }


}