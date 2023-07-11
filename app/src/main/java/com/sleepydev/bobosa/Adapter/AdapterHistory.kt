package com.sleepydev.bobosa.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.sleepydev.bobosa.Database.History
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.databinding.AdapterHistoryBinding
import com.sleepydev.bobosa.databinding.DialogDetailHistoryBinding
import com.sleepydev.bobosa.databinding.InputDialogBinding

data class AdapterHistory(var listHis: List<History>):RecyclerView.Adapter<AdapterHistory.ViewHolder>(){
    private var _binding: AdapterHistoryBinding? = null
    private val binding get() = _binding!!
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = AdapterHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root

        return ViewHolder(view)

    }

    override fun onBindViewHolder( holder: ViewHolder, position: Int) {
        val db = HistoryDB.getInstance(holder.itemView.context)
        binding.est.text = listHis[position].jenis_sapi
        binding.timeEst.text = listHis[position].waktu
        binding.cardhistory.setOnClickListener {
            val historyBinding = DialogDetailHistoryBinding.inflate(LayoutInflater.from(holder.itemView.context))
            val inputView = historyBinding.root
            val a = AlertDialog.Builder(holder.itemView.context)
                .setView(inputView)
                .create()
            historyBinding.btnclose.setOnClickListener {
                a.dismiss()
            }
            a.show()
            historyBinding.datalingkardada.text = listHis[position].lingkar_dada
            historyBinding.datapanjangbadan.text = listHis[position].panjang_badan
            historyBinding.jenissapi.text =  listHis[position].jenis_sapi
            historyBinding.schoorlvalue.text =  listHis[position].schoorl_denmark
            historyBinding.schoorlIDvalue.text =  listHis[position].schoorl_ind
            historyBinding.wintervalue.text =  listHis[position].winter_eropa
            historyBinding.winterIDvalue.text = listHis[position].winter_indonesia
            historyBinding.lambournevalue.text = listHis[position].arjo
            historyBinding.rekomenval.text = "${listHis[position].rec} \n"

            if (listHis[position].jenis_sapi == "Peranakan Ongole (PO)") {
//                historyBinding.starsarjo.visibility = View.VISIBLE
                historyBinding.lambournetitle.setTextColor(Color.parseColor("#ffca3a"))
            }

            if (listHis[position].jenis_sapi == "Bali Jantan") {
//                historyBinding.starswid.visibility = View.VISIBLE
                historyBinding.winterIDtitle.setTextColor(Color.parseColor("#ffca3a"))
            }

            if (listHis[position].jenis_sapi == "Bali Betina") {
//                historyBinding.starsdenm.visibility = View.VISIBLE
                historyBinding.schoorltitle.setTextColor(Color.parseColor("#ffca3a"))
            }


        }
    }

    override fun getItemCount(): Int {
        return listHis.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}
