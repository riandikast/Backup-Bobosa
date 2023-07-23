package com.sleepydev.bobosa.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.sleepydev.bobosa.Database.Accuracy
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.databinding.AdapterAccuracyBinding
import com.sleepydev.bobosa.databinding.AdapterBatchBinding
import com.sleepydev.bobosa.databinding.DialogDetailAccuracyBinding
import com.sleepydev.bobosa.databinding.DialogDetailBatchBinding

data class AdapterAccuracy (var listAcc: List<Accuracy>): RecyclerView.Adapter<AdapterAccuracy.ViewHolder>(){
    private var _binding: AdapterAccuracyBinding? = null
    private val binding get() = _binding!!
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = AdapterAccuracyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root

        return ViewHolder(view)

    }

    override fun onBindViewHolder( holder: ViewHolder, position: Int) {
        val db = HistoryDB.getInstance(holder.itemView.context)
        binding.est.text = " ${listAcc[position].bobot_badan}"
        binding.timeEst.text = listAcc[position].waktu


        binding.cardacc.setOnClickListener {
            val accBinding = DialogDetailAccuracyBinding.inflate(LayoutInflater.from(holder.itemView.context))
            val inputView = accBinding.root
            val a = AlertDialog.Builder(holder.itemView.context)
                .setView(inputView)
                .create()
            accBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            a.show()
            accBinding.datalingkardada.text = listAcc[position].lingkar_dada
            accBinding.datapanjangbadan.text = listAcc[position].panjang_badan
            accBinding.bbvalue.text = listAcc[position].bobot_badan
            accBinding.schoorlvalue.text = listAcc[position].sch
            accBinding.schoorlIDvalue.text = listAcc[position].schID
            accBinding.wintervalue.text = listAcc[position].winter
            accBinding.winterIDvalue.text = listAcc[position].wID
            accBinding.lambournevalue.text = listAcc[position].lamb
            accBinding.djagravalue.text = listAcc[position].djagra
            accBinding.neuralvalue.text = listAcc[position].neural_network

        }

    }

    override fun getItemCount(): Int {
        return listAcc.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}