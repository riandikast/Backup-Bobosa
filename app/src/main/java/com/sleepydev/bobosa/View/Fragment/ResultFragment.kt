package com.sleepydev.bobosa.View.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentResultBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

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
        val ldInch = getLd?.toDouble()?.div(2.54)
        val pbInch = getPb?.toDouble()?.div(2.54)
        val pbPound= getPb?.toDouble()?.div(2.205)
        binding.datalingkardada.text = "$getLd Cm"
        binding.datapanjangbadan.text = "$getPb Cm"





        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        val sch = ((getLd?.toInt()?.plus(22))?.times((getLd?.toInt()?.plus(22)!!)))?.toDouble()?.div(100)
        val schID = ((getLd?.toInt()?.plus(18))?.times((getLd?.toInt()?.plus(18)!!)))?.toDouble()?.div(100)
        val wEurope = ((ldInch?.times(ldInch))?.times(pbInch!!))?.div(300)
        val wEuropeToKg =  wEurope?.div(2.205)
        val wID = ((getLd?.toDouble()?.times(getLd.toDouble()))?.times(getPb!!.toDouble()))?.div(10815.15)
        val lamb = (getLd?.toDouble()?.times(getLd.toDouble()))?.times(getPb!!.toDouble())?.div(10840)

        val arjo =((getLd?.toDouble()?.times(getLd.toDouble()))?.times(getPb!!.toDouble()))?.div(10000)



        val lambToKg =  lamb?.div(2.205)
        binding.schoorlvalue.text = "${df.format(sch)} Kg"
        binding.schoorlIDvalue.text = "${df.format(schID)} Kg"
        binding.wintervalue.text = "${df.format(wEuropeToKg)} Kg"
        binding.winterIDvalue.text = "${df.format(wID)} Kg"
        binding.lambournevalue.text = "${df.format(arjo)} Kg"
        return view

    }


}