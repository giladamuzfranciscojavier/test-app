package com.example.testapp

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.MainActivity.Companion.dbConnected
import com.example.testapp.MainActivity.Companion.dbManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import core.saveLoad.testProcessor.TestLoader
import core.test.Test
import java.io.File
import java.io.FileOutputStream

//Menú principal. Lo primero que ve el usuario
class MainMenu : Fragment(), TestSelect.dialogListener {

    private var creatorButton : Button?=null
    private var solverButton : Button?=null
    private var settings : FloatingActionButton?=null

    private var canDelete=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Oculta el botón para volver atrás
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowCustomEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        creatorButton = view.findViewById(R.id.CreatorMode)
        creatorButton!!.setOnClickListener{openCreator()}

        solverButton = view.findViewById(R.id.SolverMode)
        solverButton!!.setOnClickListener{solverClick()}

        settings = view.findViewById(R.id.settings)
        settings!!.setOnClickListener {settingsClick()}

        if(dbManager!=null){
            Thread{
                canDelete = dbManager!!.checkPrivileges()
            }.start()
        }

        return view
    }



    private fun openCreator(){
        findNavController().navigate(R.id.action_mainmenu_to_creator)
    }


    private fun settingsClick(){
        findNavController().navigate(R.id.action_mainmenu_to_settings)
    }


    private fun solverClick(){
        if(dbConnected){
            val builder = AlertDialog.Builder(requireContext()).setTitle("Como cargar el test")
            builder.setPositiveButton("Base de Datos") { dialog: DialogInterface, _: Int ->
                openSolver(1)
                dialog.dismiss()
            }
            builder.setNeutralButton("Fichero Local")   { dialog: DialogInterface, _: Int ->
                openSolver(2)
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar"){ dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
            builder.show()
        }

        else{
            openSolver(2)
        }

    }

    private fun openSolver(res:Int){
        when(res){
            1 -> {
                var ok = true
                val t = Thread{
                    val testList = TestLoader.getTestsFromDB(dbManager)
                    if(testList.isEmpty()){
                        ok=false
                        return@Thread
                    }
                    TestSelect(testList, canDelete, this).show(childFragmentManager, "test")
                }
                t.start()
                while (t.isAlive){}
                if(!ok){
                    val builder = AlertDialog.Builder(requireContext()).setTitle("No hay tests en la base de datos")

                    builder.setNeutralButton("Vale")   { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
            }
            2 -> {

                val fileintent = Intent()
                fileintent.setAction(Intent.ACTION_GET_CONTENT)
                fileintent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                fileintent.setType("text/xml")
                fileintent.addCategory(Intent.CATEGORY_OPENABLE);
                getFilesLauncher.launch(fileintent)

            }
            else -> {}
        }
    }

    private val getFilesLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { it2 ->
        if (it2.resultCode == Activity.RESULT_OK) {
            val data: Intent = it2.data!!
            val instream = requireActivity().contentResolver.openInputStream(data.data!!)
            val file = File(requireContext().cacheDir, "temp")
            file.delete()
            file.createNewFile()
            val outstream = FileOutputStream(file)
            val buffer = ByteArray(4 * 1024) // or other buffer size
            var read: Int

            while ((instream!!.read(buffer).also { it.also { read = it } }) != -1) {
                outstream.write(buffer, 0, read)
            }
            outstream.close()
            passData(TestLoader().loadFromFile(file))
            file.delete()
        }
    }

    override fun passData(test: Test) {
        val bundle = bundleOf("test" to test)
        findNavController().navigate(R.id.action_mainmenu_to_solver, bundle)
    }
}