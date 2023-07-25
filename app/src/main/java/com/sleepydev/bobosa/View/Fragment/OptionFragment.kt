package com.sleepydev.bobosa.View.Fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.sleepydev.bobosa.Database.BackupTime
import com.sleepydev.bobosa.Database.HistoryDB
import com.sleepydev.bobosa.Datastore.StateManager
import com.sleepydev.bobosa.View.Activity.MainActivity
import com.sleepydev.bobosa.databinding.DatabaseDialogBinding
import com.sleepydev.bobosa.databinding.FragmentOptionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class OptionFragment : Fragment() {

    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!
    var checkFile = 0
    var valid = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentOptionBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as MainActivity).refreshTitle()
        val dataManager = StateManager(requireContext())
        dataManager.tempState.asLiveData().observe(requireActivity()) {
           binding.switch1.isChecked = it
        }

        binding.switch1.setOnCheckedChangeListener{ _, isChecked ->

            if (binding.switch1.isChecked) {
                GlobalScope.launch {
                    dataManager.saveTemporaryState(true)
                }

            } else {
                GlobalScope.launch {
                    dataManager.saveTemporaryState(false)
                }
            }

        }

        binding.backupdb.setOnClickListener {
            val backupBinding = DatabaseDialogBinding.inflate(inflater, container, false)
            val inputView = backupBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()

            GlobalScope.async {
                val database = HistoryDB.getInstance(requireContext())
                val getBackup = database?.HistoryDao()?.getBackupTime()
                if (getBackup?.time == null) {
                    backupBinding.backuptime.text = "Last Backup: \n None"
                } else {
                    backupBinding.backuptime.text = "Last Backup: \n ${getBackup?.time}"
                }
            }

            backupBinding.batal.setOnClickListener {
                a.dismiss()
            }
            backupBinding.lanjutkan.setOnClickListener {
                setupPermissions()
                GlobalScope.async {
                    val database = HistoryDB.getInstance(requireContext())
                    val getBackup = database?.HistoryDao()?.getBackupTime()
                    if (getBackup?.time == null) {
                        backupBinding.backuptime.text = "Last Backup: \n None"
                    } else {
                        backupBinding.backuptime.text = "Last Backup: \n ${getBackup?.time}"
                    }
                }
            }

            a.show()
        }


        var launcherForResult =  registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            if (it!=null){
                val db = context?.getDatabasePath("History.db")?.absolutePath
                val wal = context?.getDatabasePath("History.db-wal")?.absolutePath
                val shm = context?.getDatabasePath("History.db-shm")?.absolutePath
                val dir = context?.getExternalFilesDir("")
                val filenamesToDocumentFile = mutableMapOf<String, DocumentFile>()
                val documentsTree = DocumentFile.fromTreeUri(requireContext(), it!!)
                val childDocuments = documentsTree?.listFiles()
                if (childDocuments != null) {
                    for (childDocument in childDocuments) {
                        childDocuments[0].name?.let {
                            filenamesToDocumentFile[it] = childDocument
                            val inputStream: InputStream =
                                requireActivity().contentResolver.openInputStream(childDocument.uri)!!
                            if (childDocument.name == "History.db" || childDocument.name == "History.db-wal" || childDocument.name == "History.db-shm") {
                                val myFolder = File(
                                    requireContext().getExternalFilesDir(""),
                                    childDocument.name!!
                                )


                                Log.d("dfds", childDocument.name.toString())

                                val outputStream: OutputStream = FileOutputStream(myFolder, false)
                                val buf = ByteArray(1024)
                                var len: Int
                                while (inputStream.read(buf).also { len = it } > 0) {

                                    outputStream.write(buf, 0, len)
                                }

                                outputStream.close()
                                inputStream.close()
                            }

                            if(childDocument.name == "History.db"){
                                checkFile +=1
                            }

                            if(childDocument.name == "History.db-wal"){
                                checkFile +=1
                            }

                            if(childDocument.name == "History.db-shm"){
                                checkFile +=1
                            }
                            Log.d("fg", checkFile.toString())
                            if (checkFile == 3) {


                                val dbExternal = "$dir/History.db"
                                val walExternal = "$dir/History.db-wal"
                                val shmExternal = "$dir/History.db-shm"
                                File(dbExternal).copyTo(File(db!!), true)
                                File(walExternal).copyTo(File(wal!!), true)
                                File(shmExternal).copyTo(File(shm!!), true)
                                Toast.makeText(requireContext(), "Restore Successfully", Toast.LENGTH_LONG).show()
                                Handler(Looper.getMainLooper()).postDelayed({
                                      restartApp()

                                }, 1500)
                                valid = true
                            }else{

                                valid = false

                            }
                        }
                    }
                }


                if (!valid){
                    checkFile = 0
                    Toast.makeText(requireContext(), "Cant Find All Required File on This Folder", Toast.LENGTH_SHORT).show()
                }


            }

        }
        binding.restoredb.setOnClickListener {
            val restoreBinding = DatabaseDialogBinding.inflate(inflater, container, false)
            val inputView = restoreBinding.root
            val a = AlertDialog.Builder(requireContext())
                .setView(inputView)
                .create()

            restoreBinding.dbtitle.text = "Restore Database?"
            restoreBinding.timeLayout.visibility = View.GONE
            restoreBinding.titlewarning.text = "Pilih folder yang terdapat file: \"History.db\" \n \"History.db-wal\" \n \"History.db-shm\""

            restoreBinding.batal.setOnClickListener {
                a.dismiss()
            }


            restoreBinding.lanjutkan.setOnClickListener {
                launcherForResult.launch(null)
            }

            a.show()
        }
        return  view
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }else{
            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)


            Toast.makeText(requireContext(), "Backup Successfully", Toast.LENGTH_LONG).show()
            val database = HistoryDB.getInstance(requireContext())

            GlobalScope.launch {
                database?.HistoryDao()?.deleteBackupTime()
                database?.HistoryDao()?.insertBackupTime(BackupTime("$formatted"))
            }

            val documentFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() +"/" + "Backup Bobosa" + "/" + formatted.toString().replace(":", ".")

            val db = context?.getDatabasePath("History.db")?.absolutePath
            Log.d("asdpaosd", db.toString())
            val wal = context?.getDatabasePath("History.db-wal")?.absolutePath
            val shm = context?.getDatabasePath("History.db-shm")?.absolutePath
            File(db!!).copyTo(File(documentFolder, "History.db"), true)
            File(wal!!).copyTo(File(documentFolder, "History.db-wal"), true)
            File(shm!!).copyTo(File(documentFolder, "History.db-shm"), true)

        }
    }

    private fun makeRequest() {
        requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }


    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val formatted = current.format(formatter)
                Toast.makeText(requireContext(), "Backup Successfully", Toast.LENGTH_LONG).show()
                val database = HistoryDB.getInstance(requireContext())
                GlobalScope.launch {
                    database?.HistoryDao()?.deleteBackupTime()
                    database?.HistoryDao()?.insertBackupTime(BackupTime("$formatted"))
                }

                val documentFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() +"/" + "Backup Bobosa" + "/" + "#" + formatted.toString().replace(":", ".")
                val db = context?.getDatabasePath("History.db")?.absolutePath
                val wal = context?.getDatabasePath("History.db-wal")?.absolutePath
                val shm = context?.getDatabasePath("History.db-shm")?.absolutePath
                File(db!!).copyTo(File(documentFolder, "History.db"), true)
                File(wal!!).copyTo(File(documentFolder, "History.db-wal"), true)
                File(shm!!).copyTo(File(documentFolder, "History.db-shm"), true)

            }else{
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val formatted = current.format(formatter)

                Toast.makeText(requireContext(), "Backup Successfully", Toast.LENGTH_LONG).show()
                val database = HistoryDB.getInstance(requireContext())
                GlobalScope.launch {
                    database?.HistoryDao()?.deleteBackupTime()
                    database?.HistoryDao()?.insertBackupTime(BackupTime("$formatted"))
                }

                val documentFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() +"/" + "Backup Bobosa" + "/" + "#" + formatted.toString().replace(":", ".")
                val db = context?.getDatabasePath("History.db")?.absolutePath
                val wal = context?.getDatabasePath("History.db-wal")?.absolutePath
                val shm = context?.getDatabasePath("History.db-shm")?.absolutePath
                File(db!!).copyTo(File(documentFolder, "History.db"), true)
                File(wal!!).copyTo(File(documentFolder, "History.db-wal"), true)
                File(shm!!).copyTo(File(documentFolder, "History.db-shm"), true)

            }

        } else {
            // PERMISSION NOT GRANTED
            Toast.makeText(requireContext(), "Permissions Denied", Toast.LENGTH_LONG).show()

        }
    }

    private fun restartApp(){
        val ctx: Context = requireActivity()
        val pm = ctx.packageManager
        val intent = pm.getLaunchIntentForPackage(ctx.packageName)
        val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
        ctx.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}