package com.sleepydev.bobosa.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.sleepydev.bobosa.Database.Batch
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.databinding.AdapterBatchBinding
import com.sleepydev.bobosa.databinding.DialogDetailBatchBinding
import com.sleepydev.bobosa.databinding.DialogDetailHistoryBinding

data class AdapterBatch(var listHis: List<Batch>): RecyclerView.Adapter<AdapterBatch.ViewHolder>(){
    private var _binding: AdapterBatchBinding? = null
    private val binding get() = _binding!!
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = AdapterBatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root

        return ViewHolder(view)

    }

    override fun onBindViewHolder( holder: ViewHolder, position: Int) {
        val db = HistoryDB.getInstance(holder.itemView.context)
        binding.est.text = listHis[position].hasil
        binding.timeEst.text = listHis[position].waktu
        binding.rumus.text = listHis[position].rumus

        binding.cardbatch.setOnClickListener {
            val batchBinding = DialogDetailBatchBinding.inflate(LayoutInflater.from(holder.itemView.context))
            val inputView = batchBinding.root
            val a = AlertDialog.Builder(holder.itemView.context)
                .setView(inputView)
                .create()
            batchBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            a.show()
            batchBinding.datalingkardada.text = listHis[position].lingkar_dada
            batchBinding.datapanjangbadan.text = listHis[position].panjang_badan
            batchBinding.rumusvalue.text =  listHis[position].rumus
            batchBinding.hasilvalue.text =  listHis[position].hasil



        }

    }

    override fun getItemCount(): Int {
        return listHis.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}