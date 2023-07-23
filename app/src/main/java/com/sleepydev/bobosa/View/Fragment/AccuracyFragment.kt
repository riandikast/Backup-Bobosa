package com.sleepydev.bobosa.View.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sleepydev.bobosa.Adapter.AdapterAccuracy
import com.sleepydev.bobosa.Adapter.AdapterBatch
import com.sleepydev.bobosa.Database.Accuracy
import com.sleepydev.bobosa.Database.History
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.DeleteDialogBinding
import com.sleepydev.bobosa.databinding.FragmentAccuracyBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.exp


class AccuracyFragment : Fragment() {
    private var _binding: FragmentAccuracyBinding? = null
    private val binding get() = _binding!!
    lateinit var output2 : String
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
        getAcc()
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
                    db?.AccuracyDao()?.deleteAcc()

                    getAcc()
                }
                a.dismiss()
            }
            a.show()
        }

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
                val df2 = DecimalFormat("#.####")
                df2.roundingMode = RoundingMode.CEILING
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
                val djagra =
                    (ldData?.toDouble()?.times(ldData.toDouble()))?.times(pbData!!.toDouble())?.div(11045)
                val re = Regex("-")

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


                val schAcc = ((sch?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val schIDAcc = ((schID?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val wEurAcc = ((wEuropeToKg?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val wIDAcc = ((wID?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val arjoAcc = ((arjo?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val lambAcc = ((lamb?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val djagraAcc = ((djagra?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)
                val neuralAcc = ((dnrOutput1?.minus(bAktual.toDouble()))?.div(bAktual.toDouble()))?.times(100)

                binding.schoorlvalue.text = "${re.replace("${df2.format(schAcc)}", "")}%"
                binding.schoorlIDvalue.text = "${re.replace("${df2.format(schIDAcc)}", "")}%"
                binding.wintervalue.text = "${re.replace("${df2.format(wEurAcc)}", "")}%"
                binding.winterIDvalue.text = "${re.replace("${df2.format(wIDAcc)}", "")}%"
                binding.lambournevalue.text = "${re.replace("${df2.format(lambAcc)}", "")}%"
                binding.djagravalue.text = "${re.replace("${df2.format(djagraAcc)}", "")}%"
                binding.neuralvalue.text = "${re.replace("${df2.format(neuralAcc)}", "")}%"

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val formatted = current.format(formatter)

                it.hideKeyboard()
                val db = HistoryDB.getInstance(requireContext())
                GlobalScope.async {
                    db?.AccuracyDao()?.insertAcc(
                        Accuracy(
                            null,
                            "${ldData} Cm",
                            "${pbData} Cm",
                            "${bobot} Kg",
                            "${re.replace("${df.format(schAcc)}", "")}%",
                            "${re.replace("${df.format(schIDAcc)}", "")}%",
                            "${re.replace("${df.format(wEurAcc)}", "")}%",
                            "${re.replace("${df.format(wIDAcc)}", "")}%",
                            "${re.replace("${df.format(lambAcc)}", "")}%",
                            "${re.replace("${df.format(djagraAcc)}", "")}%",
                            "${re.replace("${df2.format(neuralAcc)}", "")}%",
                            "${formatted}",

                        )
                    )
                    getAcc()
                }

            }









        }
        return view
    }

    fun getAcc() {
        GlobalScope.launch {
            val db = HistoryDB.getInstance(requireContext())
            val listdata = db?.AccuracyDao()?.getAllAccy()
            requireActivity().runOnUiThread {
                listdata.let {
                    if (listdata?.size == 0) {
                        binding.checkdata.visibility = View.VISIBLE
                        binding.checkdata.text = "Belum ada data"
                    } else {
                        binding.checkdata.text = ""
                        binding.checkdata.visibility = View.GONE
                    }
                    binding.listAcc.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                    val sorted = it?.sortedByDescending { it.waktu }
                    val adapt = AdapterAccuracy(sorted!!)

                    binding.listAcc.adapter = adapt


                }
            }
        }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


}