package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.databinding.FragmentLibBinding
import com.sleepydev.bobosa.databinding.FragmentPanduanBinding
import com.sleepydev.bobosa.databinding.PanduanDialogBinding
import com.sleepydev.bobosa.databinding.RumusDialogBinding


class PanduanFragment : Fragment() {

    private var _binding: FragmentPanduanBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentPanduanBinding.inflate(inflater, container, false)
        val view = binding.root
        val apptitle = activity?.findViewById<TextView>(R.id.apptitle)
        apptitle?.text = "Panduan"
        binding.lihatLD.setOnClickListener {
            val inputBinding = PanduanDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaPanduan.text = "Mengukur Lingkar Dada"
            inputBinding.isiPanduan.text = "Untuk mengukur lingkar dada bisa dilakukan dengan melingkarkan pita ukur pada bagian yang ditandai dengen angka 5 pada gambar di atas"
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            a.show()
        }

        binding.lihatPB.setOnClickListener {
            val inputBinding = PanduanDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaPanduan.text = "Mengukur Panjang Badan"
            inputBinding.isiPanduan.text = "Panjang badan bisa diketahui dengan mengukur panjang bongkol bahu sampai dengan ujung tulang duduk yang terdapat pada angka 6 dalam gambar"
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            a.show()
        }

        binding.lihatTB.setOnClickListener {
            val inputBinding = PanduanDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.namaPanduan.text = "Mengukur Tinggi Badan"
            inputBinding.isiPanduan.text = "Tinggi badan diukur dari tanah ke titik tertinggi gumba di ilustrasikan pada angka 1 dalam gambar, pengukuran dilakukan dengan sapi dalam keadaan tegak dan berpijak pada permukaan yang datar"
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            a.show()
        }
        return view

    }


}