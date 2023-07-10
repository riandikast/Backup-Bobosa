package com.sleepydev.bobosa.View.Fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.sleepydev.bobosa.Database.History
import com.sleepydev.bobosa.Database.HistoryDB

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

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    lateinit var rekomendasi: String
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
            val rec = "Untuk sapi jenis Peranakan Ongole (PO) disarankan menggunakan hasil dari rumus Arjodarmoko"
            binding.starsarjo.visibility = View.VISIBLE
            binding.rekomenval.text = rec
            rekomendasi = rec

        }

        if (getJenis == "Bali Jantan") {
            val rec = "Untuk sapi jenis Bali Jantan disarankan menggunakan hasil dari rumus Winter Indonesia"
            binding.starswid.visibility = View.VISIBLE
            binding.rekomenval.text = rec
            rekomendasi = rec
        }

        if (getJenis == "Bali Betina") {
            val rec = "Untuk sapi jenis Bali Betina disarankan menggunakan hasil dari rumus School Denmark"
            binding.starsdenm.visibility = View.VISIBLE
            binding.rekomenval.text = rec
            rekomendasi = rec

        }

        if (getJenis == "-") {
            val rec = "Masukan jenis sapi untuk mendapatkan rekomendasi rumus dari sistem"
            binding.rekomenval.text = rec
            rekomendasi = rec

        }


        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
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


        val lambToKg = lamb?.div(2.205)
        binding.schoorlvalue.text = "${df.format(sch)} Kg"
        binding.schoorlIDvalue.text = "${df.format(schID)} Kg"
        binding.wintervalue.text = "${df.format(wEuropeToKg)} Kg"
        binding.winterIDvalue.text = "${df.format(wID)} Kg"
        binding.lambournevalue.text = "${df.format(arjo)} Kg"

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
                    "${df.format(sch)} Kg",
                    "${df.format(schID)} Kg",
                    "${df.format(wEuropeToKg)} Kg",
                    "${df.format(wID)} Kg",
                    "${df.format(arjo)} Kg",
                    "$formatted",
                    "$rekomendasi"
                )
            )
        }
        return view

    }


}