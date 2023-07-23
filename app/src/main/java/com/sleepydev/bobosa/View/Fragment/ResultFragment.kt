package com.sleepydev.bobosa.View.Fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sleepydev.bobosa.Database.History
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.Datastore.StateManager
import com.sleepydev.bobosa.R

import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentResultBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.exp

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    lateinit var rekomendasi: String
    lateinit var minValue : String
    lateinit var maxValue : String
    lateinit var output2 : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as MainActivity).refreshTitle()

        val dataManager = StateManager(requireContext())
        dataManager.tempState.asLiveData().observe(requireActivity()) {
            if (it){
                binding.parentHasil.setBackgroundColor(Color.parseColor("#262626"))
                binding.topLine.setBackgroundColor(Color.parseColor("#262626"))
                binding.bottomLine.setBackgroundColor(Color.parseColor("#262626"))
            }else{
                binding.parentHasil.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.topLine.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.bottomLine.setBackgroundColor(Color.parseColor("#ffffff"))

            }
        }


        val getLd = arguments?.getString("ld")
        val getPb = arguments?.getString("pb")
        val getSel = arguments?.getString("sel")
        val ldInch = getLd?.toDouble()?.div(2.54)
        val pbInch = getPb?.toDouble()?.div(2.54)
        val pbPound = getPb?.toDouble()?.div(2.205)
        binding.datalingkardada.text = "$getLd Cm"
        binding.datapanjangbadan.text = "$getPb Cm"

        if (getSel != null) {
            if (getSel.toString() != "" && getSel.toString() != "Other") {
                binding.jenissapi.text = "$getSel"
            }
        }

        val getJenis = binding.jenissapi.text.toString()

        if (getJenis == "Peranakan Ongole (PO)") {
            val rec = "Untuk sapi jenis Peranakan Ongole (PO) disarankan menggunakan hasil dari rumus Winter Eropa \n"
//            binding.starsarjo.visibility = View.VISIBLE
//            binding.wintertitle.setTextColor(Color.parseColor("#ffca3a"))
            binding.rekomenval.text = rec
            rekomendasi = rec

        }

        if (getJenis == "Kupang") {
            val rec = "Untuk sapi jenis Kupang disarankan menggunakan hasil dari rumus Winter Eropa \n"
//            binding.starswid.visibility = View.VISIBLE
//            binding.wintertitle.setTextColor(Color.parseColor("#ffca3a"))
            binding.rekomenval.text = rec
            rekomendasi = rec
        }

        if (getJenis == "Bali") {
            val rec = "Untuk sapi jenis Bali disarankan menggunakan hasil dari rumus Winter Indonesia \n"
//            binding.starsdenm.visibility = View.VISIBLE
//            binding.winterIDtitle.setTextColor(Color.parseColor("#ffca3a"))
            binding.rekomenval.text = rec
            rekomendasi = rec

        }

        if (getJenis == "-") {
            val rec = "Masukan jenis sapi untuk mendapatkan rekomendasi rumus dari sistem \n"
            binding.rekomenval.text = rec
            rekomendasi = rec

        }


        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        val df2 = DecimalFormat("#.###")
        df2.roundingMode = RoundingMode.CEILING
        val formattedLDInch = (df2.format(ldInch)).toDouble()
        val formattedPBInch = (df2.format(pbInch)).toDouble()

        val sch =
            ((getLd?.toInt()?.plus(22))?.times((getLd?.toInt()?.plus(22)!!)))?.toDouble()?.div(100)
        val schID =
            ((getLd?.toInt()?.plus(18))?.times((getLd?.toInt()?.plus(18)!!)))?.toDouble()?.div(100)
        val wEurope = ((ldInch?.times(ldInch))?.times(pbInch!!))?.div(300)
        val wEuropeToKg = wEurope?.div(2.205)
        val wID =
            ((getLd?.toDouble()?.times(getLd.toDouble()))?.times(getPb!!.toDouble()))?.div(10815.15)
        val lamb =
            (getLd?.toDouble()?.times(getLd.toDouble()))?.times(getPb!!.toDouble())?.div(10840)
        val arjo =
            ((getLd?.toDouble()?.times(getLd.toDouble()))?.times(getPb!!.toDouble()))?.div(10000)
        val djagra =
            ((getLd?.toDouble()?.times(getLd.toDouble()))?.times(getPb!!.toDouble()))?.div(11045)

        val lambToKg = lamb?.div(2.205)




        val ldNR = (getLd?.toDouble()?.minus(82))!!.div((160-82))
        val pbNR = (getPb?.toDouble()?.minus(109))!!.div((170-109))
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

        binding.schoorlvalue.text = "${df2.format(sch)} Kg"
        binding.schoorlIDvalue.text = "${df2.format(schID)} Kg"
        binding.wintervalue.text = "${df2.format(wEuropeToKg)} Kg"
        binding.winterIDvalue.text = "${df2.format(wID)} Kg"
        binding.lambournevalue.text = "${df2.format(lamb)} Kg"
        binding.djagravalue.text = "${df2.format(djagra)} Kg"
        binding.neuralvalue.text = "${df2.format(dnrOutput1)} Kg"

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)

        val db = HistoryDB.getInstance(requireContext())
        GlobalScope.launch {
            db?.HistoryDao()?.insertHistory(
                History(
                    null,
                    "$getLd",
                    "$getPb",
                    "$getJenis",
                    "${df2.format(sch)} Kg",
                    "${df2.format(schID)} Kg",
                    "${df2.format(wEuropeToKg)} Kg",
                    "${df2.format(wID)} Kg",
                    "${df2.format(lamb)} Kg",
                    "${df2.format(djagra)} Kg",
                    "${df2.format(dnrOutput1)} Kg",
                    "$formatted",
                    "$rekomendasi"
                )
            )
        }

        binding.btnback.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }
        return view

    }


}