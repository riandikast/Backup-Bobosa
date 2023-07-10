package com.sleepydev.bobosa.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.FragmentAccuracyBinding

import java.math.RoundingMode
import java.text.DecimalFormat


class AccuracyFragment : Fragment() {
    private var _binding: FragmentAccuracyBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccuracyBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as MainActivity).refreshTitle()

        binding.btnhitung.setOnClickListener {
            val bundle = Bundle()
            val ld = binding.inputlingkardada.text.toString()
            val pb = binding.inputpanjangbadan.text.toString()
            val bAktual = binding.inputbobotaktual.text.toString()


            val ldData = binding.inputlingkardada.text.toString().trim()
            val pbData = binding.inputpanjangbadan.text.toString().trim()
            val bobot = binding.inputbobotaktual.text.toString().trim()


            if (ldData.isEmpty() ){
                binding.inputlingkardada.error = "Harus Dilengkapi"
            }
            if (pbData.isEmpty() ){
                binding.inputpanjangbadan.error = "Harus Dilengkapi"
            }

            if (bobot.isEmpty()){
                binding.inputbobotaktual.error = "Harus Dilengkapi"
            }

            if (ldData.isNotEmpty() && pbData.isNotEmpty() && bobot.isNotEmpty()){
                bundle.putString("ld", ld)
                bundle.putString("pb", pb)
                bundle.putString("sel",bobot)

                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.CEILING

                val ldInch = ldData?.toDouble()?.div(2.54)
                val pbInch = pbData?.toDouble()?.div(2.54)
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

                val re = Regex("-")


                val schAcc = ((sch?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val schIDAcc = ((schID?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val wEurAcc = ((wEuropeToKg?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val wIDAcc = ((wID?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val arjoAcc = ((arjo?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)

                binding.schoorlvalue.text = "${re.replace("${df.format(schAcc)}", "")}%"
                binding.schoorlIDvalue.text = "${re.replace("${df.format(schIDAcc)}", "")}%"
                binding.wintervalue.text = "${re.replace("${df.format(wEurAcc)}", "")}%"
                binding.winterIDvalue.text = "${re.replace("${df.format(wIDAcc)}", "")}%"
                binding.lambournevalue.text = "${re.replace("${df.format(arjoAcc)}", "")}%"

            }







        }
        return view
    }


}