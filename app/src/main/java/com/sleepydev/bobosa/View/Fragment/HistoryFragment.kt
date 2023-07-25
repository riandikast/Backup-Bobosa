package com.sleepydev.bobosa.View.Fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.sleepydev.bobosa.Adapter.AdapterHistory
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.Datastore.StateManager
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.AdapterHistoryBinding
import com.sleepydev.bobosa.databinding.DeleteDialogBinding
import com.sleepydev.bobosa.databinding.FragmentHistoryBinding
import com.sleepydev.bobosa.databinding.FragmentHomeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as MainActivity).refreshTitle()



        getHistory()
        ViewCompat.setNestedScrollingEnabled(binding.listHistory, false);
        binding.deleteIcon.setOnClickListener {
            val deleteBinding = DeleteDialogBinding.inflate(inflater, container, false)
            val inputView = deleteBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()
            deleteBinding.canceldelete.setOnClickListener {
                a.dismiss()
            }

            deleteBinding.continuedelete.setOnClickListener {
                GlobalScope.async {
                    val db = HistoryDB.getInstance(requireContext())
                    db?.HistoryDao()?.deleteHistory()


                    getHistory()
                }
                a.dismiss()
            }
            a.show()
        }
        return view
    }

    fun getHistory() {
        GlobalScope.launch {
            val db = HistoryDB.getInstance(requireContext())
            val listdata = db?.HistoryDao()?.getAllHistory()
            requireActivity().runOnUiThread {
                listdata.let {
                    if (listdata?.size == 0) {
                        binding.checkdata.visibility = View.VISIBLE
                        binding.checkdata.text = "Belum ada data"
                    } else {
                        binding.checkdata.text = ""
                        binding.checkdata.visibility = View.GONE
                    }
                    binding.listHistory.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    val sorted = it?.sortedByDescending { it.waktu }
                    val adapt = AdapterHistory(sorted!!)
                    binding.listHistory.adapter = adapt

                }
            }
        }
    }


}