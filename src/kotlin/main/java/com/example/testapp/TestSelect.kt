package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import core.saveLoad.testProcessor.TestLoader
import core.test.Test

//Diálogo para seleccionar (o borrar) un test de la base de datos
class TestSelect(private val tests:HashMap<String, Int>, private val canDelete:Boolean, private val listener:dialogListener) : DialogFragment() {

    private var testList : Spinner?=null
    private var delete : Button?=null
    private var accept : Button?=null
    private var cancel : Button?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_test_select, container, false)

        testList = view.findViewById(R.id.testList)
        loadList()

        delete = view.findViewById(R.id.delete)
        delete!!.setOnClickListener {deleteClick()}
        if(!canDelete){
            delete!!.visibility=GONE
        }

        cancel = view.findViewById(R.id.cancel)
        cancel!!.setOnClickListener {cancelClick()}

        accept = view.findViewById(R.id.accept)
        accept!!.setOnClickListener {acceptClick()}

        return view
    }

    private fun loadList() {
        val list = tests.keys.toTypedArray()
        testList!!.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
    }

    interface dialogListener{
        fun passData(test: Test)
    }


    private fun deleteClick(){
        var success = false
        val t = Thread{
            success = MainActivity.dbManager!!.deleteFromDB(tests[testList!!.selectedItem]!!)
        }
        t.start()
        while(t.isAlive){}
        if(success){
            tests.remove(testList!!.selectedItem)
            loadList()
        }

    }

    private fun cancelClick(){
        dismiss()
    }

    private fun acceptClick(){
        var res:Test?=null
        val t = Thread{
            res = TestLoader().loadFromDB(MainActivity.dbManager, tests[testList!!.selectedItem]!!)
        }
        t.start()
        while (t.isAlive){}
        sendData(res!!)
        dismiss()
    }

    private fun sendData(res:Test){
        listener.passData(res)
    }

}