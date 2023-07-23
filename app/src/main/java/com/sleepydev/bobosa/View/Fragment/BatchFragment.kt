package com.sleepydev.bobosa.View.Fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.style.BackgroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sleepydev.bobosa.Adapter.AdapterBatch
import com.sleepydev.bobosa.Database.Batch
import com.sleepydev.bobosa.Database.History
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.DeleteDialogBinding
import com.sleepydev.bobosa.databinding.FragmentBatchBinding
import com.sleepydev.bobosa.databinding.InputDialogBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.exp


class BatchFragment : Fragment() {
    private var _binding: FragmentBatchBinding? = null
    private val binding get() = _binding!!
    lateinit var selected:String
    lateinit var hasil:String
    lateinit var output2:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBatchBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as MainActivity).refreshTitle()
        selected = ""
        getBatch()

        ViewCompat.setNestedScrollingEnabled(binding.listBatch, false);
        val rumus = listOf( "Schoorl Denmark", "Schoorl Indonesia", "Winter Eropa", "Winter Indonesia", "Lambourne", "Djagra", "Neural Network")
        val adapter = ArrayAdapter(requireActivity(), R.layout.list_rumus, rumus)
        binding.jenisoption.setDropDownBackgroundResource(R.color.lightgreen)

        binding.jenisoption.setAdapter(adapter)
        binding.jenisoption.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            selected = adapterView.getItemAtPosition(i).toString()
            binding.jenisoption.setError(null)
            binding.textFieldJenis.hint = ""
//                Toast.makeText(requireContext(), "$selected", Toast.LENGTH_SHORT).show()
        }

        
        binding.btnhitung.setOnClickListener {

            val ld = binding.inputlingkardada.text.toString()
            val pb = binding.inputpanjangbadan.text.toString()

            
            val ldData = binding.inputlingkardada.text.toString().trim()
            val pbData = binding.inputpanjangbadan.text.toString().trim()



            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)


            if (ldData.isEmpty() ){
                binding.inputlingkardada.error = "Harus Dilengkapi"
            }
            if (pbData.isEmpty() ){
                binding.inputpanjangbadan.error = "Harus Dilengkapi"
            }

            if (selected.isEmpty()){
                binding.jenisoption.error = "Harus Dilengkapi"
            }



            if (ldData.isNotEmpty() && pbData.isNotEmpty() && selected.isNotEmpty()){
                val ldInch = ldData?.toDouble()?.div(2.54)
                val pbInch = pbData?.toDouble()?.div(2.54)

                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.CEILING
                val df2 = DecimalFormat("#.###")
                df2.roundingMode = RoundingMode.CEILING
                val sch =
                    ((ldData?.toInt()?.plus(22))?.times((ldData?.toInt()?.plus(22)!!)))?.toDouble()?.div(100)
                val schID =
                    ((ldData?.toInt()?.plus(18))?.times((ldData?.toInt()?.plus(18)!!)))?.toDouble()?.div(100)
                val wEurope = ((ldInch?.times(ldInch))?.times(pbInch!!))?.div(300)
                val wEuropeToKg = wEurope?.div(2.205)
                val wID =
                    ((ldData?.toDouble()?.times(ldData.toDouble()))?.times(pbData!!.toDouble()))?.div(10815.15)
                val lamb =
                    (ldData?.toDouble()?.times(ldData.toDouble()))?.times(pbData!!.toDouble())?.div(10840)
                val arjo =
                    ((ldData?.toDouble()?.times(ldData.toDouble()))?.times(pbData!!.toDouble()))?.div(10000)
                val djagra =
                    ((ldData?.toDouble()?.times(ldData.toDouble()))?.times(pbData!!.toDouble()))?.div(11045)


                val ldNR = (ldData?.toDouble()?.minus(82))!!.div((160-82))
                val pbNR = (pbData?.toDouble()?.minus(109))!!.div((170-109))
                val bias = (0.1.times(0.1345)).plus(-0.7)
                val weight1 = ((0.1.times(0.1345).times(0.37705)).plus(2.8))
                val weight2 = ((0.1.times(0.1345).times(0)).plus(1.9))
                val jumlah = pbNR?.times(weight1)!!.plus(ldNR!!.times(weight2)).plus(bias)
                val output1 = (1.div((1+ exp(-jumlah))))
                val dnrOutput1 = output1.times((266.toDouble().plus(70)))
                val err1 = -0.30177341
                if (err1 < 0){

                    output2 = (output1.plus(err1)).toString()
                }else{
                    output2 = (output1.minus(-err1).toString())
                }
                val dnrOutput2 = output2.toDouble().times((266.toDouble().plus(70)))


                if (selected == "Schoorl Denmark"){
                    hasil = df2.format(sch)
                }
                if (selected == "Schoorl Indonesia"){
                    hasil = df2.format(schID)
                }
                if (selected == "Winter Eropa"){
                    hasil = df2.format(wEuropeToKg)
                }
                if (selected == "Winter Indonesia"){
                    hasil = df2.format(wID)
                }
                if (selected == "Lambourne"){
                    hasil = df2.format(lamb)
                }
                if (selected == "Djagra"){
                    hasil = df2.format(djagra)
                }
                if (selected == "Neural Network"){
                    hasil = df2.format(dnrOutput1)
                }


                it.hideKeyboard()
                val db = HistoryDB.getInstance(requireContext())
                GlobalScope.async {
                    db?.BatchDao()?.insertBatch(
                        Batch(
                            null,
                            "$ldData Cm",
                            "$pbData Cm",
                            "$hasil Kg",
                            "$selected",
                            "$formatted"
                        )
                    )
                    getBatch()
                }
            }





        }

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

                    db?.BatchDao()?.deleteBatch()


                    getBatch()
                }
                a.dismiss()
            }
            a.show()
        }
        return view
    }

    fun getBatch() {
        GlobalScope.launch {
            val db = HistoryDB.getInstance(requireContext())
            val listdata = db?.BatchDao()?.getAllBatch()
            requireActivity().runOnUiThread {
                listdata.let {
                    if (listdata?.size == 0) {
                        binding.checkdata.visibility = View.VISIBLE
                        binding.checkdata.text = "Belum ada data"
                    } else {
                        binding.checkdata.text = ""
                        binding.checkdata.visibility = View.GONE
                    }
                    binding.listBatch.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                    val sorted = it?.sortedByDescending { it.waktu }
                    val adapt = AdapterBatch(sorted!!)

                    binding.listBatch.adapter = adapt


                }
            }
        }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


}