package com.sleepydev.bobosa.View.Fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.sleepydev.bobosa.Datastore.StateManager
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentHomeBinding
import com.sleepydev.bobosa.databinding.FragmentSplashBinding
import com.sleepydev.bobosa.databinding.InputDialogBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
        val dataManager = StateManager(requireContext())
        dataManager.tempState.asLiveData().observe(requireActivity()) {
            if (it){
                binding.parentHome.setBackgroundColor(Color.parseColor("#262626"))
                binding.lainyaText.setTextColor(Color.parseColor("#ffffff"));
            }else{
                binding.parentHome.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.lainyaText.setTextColor(Color.parseColor("#0a6165"));
            }
        }

        binding.startestimasi.setOnClickListener {

            val inputBinding = InputDialogBinding.inflate(inflater, container, false)
            val inputView = inputBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            inputBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            val textWatcher = object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val ldData = inputBinding.inputlingkardada.text.toString().trim()
                    val pbData = inputBinding.inputpanjangbadan.text.toString().trim()


//                    inputBinding.btnhitung.isEnabled = ldData.isNotEmpty() && pbData.isNotEmpty()
                  if ( ldData.isNotEmpty() && pbData.isNotEmpty()){
                      inputBinding.btnhitung.alpha = 1.0f
                  } else {
                      inputBinding.btnhitung.alpha = .5f
                  }


                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int,
                    after: Int
                ) {

                }

                override fun afterTextChanged(s: Editable) {
                }
            }
            inputBinding.inputlingkardada.addTextChangedListener(textWatcher)
            inputBinding.inputpanjangbadan.addTextChangedListener(textWatcher)

            val jenis = listOf( "Peranakan Ongole (PO)", "Bali Jantan", "Bali Betina")
            val adapter = ArrayAdapter(requireActivity(), R.layout.list_jenis, jenis)
            inputBinding.jenisoption.setAdapter(adapter)
            inputBinding.jenisoption.onItemClickListener = AdapterView.OnItemClickListener {
                    adapterView, view, i, l ->
                var selected = adapterView.getItemAtPosition(i)
                inputBinding.textFieldJenis.hint = ""
//                Toast.makeText(requireContext(), "$selected", Toast.LENGTH_SHORT).show()
            }

            inputBinding.btnhitung.setOnClickListener {
                val bundle = Bundle()
                val ld = inputBinding.inputlingkardada.text.toString()
                val pb = inputBinding.inputpanjangbadan.text.toString()
                val jenis = inputBinding.jenisoption.text.toString()


                val ldData = inputBinding.inputlingkardada.text.toString().trim()
                val pbData = inputBinding.inputpanjangbadan.text.toString().trim()


                if (ldData.isEmpty() ){
                    inputBinding.inputlingkardada.error = "Harus Dilengkapi"
                }
                if (pbData.isEmpty() ){
                    inputBinding.inputpanjangbadan.error = "Harus Dilengkapi"
                }

                if (ldData.isNotEmpty() && pbData.isNotEmpty()){
                    bundle.putString("ld", ld)
                    bundle.putString("pb", pb)
                    bundle.putString("sel", jenis)
                    a.dismiss()
                    findNavController().navigate(R.id.resultFragment, bundle)
                }


            }
            a.show()

        }

        binding.perpustakaan.setOnClickListener {
            findNavController().navigate(R.id.libFragment)
        }
        binding.panduan.setOnClickListener {
            findNavController().navigate(R.id.panduanFragment)
        }

        binding.cardHistory.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
        binding.cardAkurasi.setOnClickListener {
            findNavController().navigate(R.id.accuracyFragment)
        }

        binding.cardBatch.setOnClickListener {
            findNavController().navigate(R.id.batchFragment)
        }


        return view
    }


}