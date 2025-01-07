package com.example.testapp

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.testapp.MainActivity.Companion.dbConnected
import com.example.testapp.MainActivity.Companion.dbManager
import core.saveLoad.db.DBManager

//Panel de opciones
class SettingsFragment : PreferenceFragmentCompat() {

    private var correct : ListPreference?=null
    private var incorrect : ListPreference?=null
    private var partial : ListPreference?=null
    private var restore : CheckBoxPreference?=null
    private var url : EditTextPreference?=null
    private var user : EditTextPreference?=null
    private var password : EditTextPreference?=null
    private var connect : SwitchPreference?=null
    private val colorValues = R.array.colorValues
    private val colorNames = R.array.colorNames

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        //Muestra el botón para volver atrás
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowCustomEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//region View binding
        correct = findPreference("colorCorrect")
        partial = findPreference("colorPartial")
        incorrect = findPreference("colorIncorrect")
        restore = findPreference("restore")

        restore!!.setOnPreferenceClickListener { restoreClick() }

        url = findPreference("url")
        user = findPreference("user")
        password = findPreference("password")


        password!!.setOnBindEditTextListener { it.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD }


        connect = findPreference("connect")
        connect!!.isChecked = dbConnected
        connect!!.setOnPreferenceClickListener { connectToDB() }
//endregion

        checkAnonUser(user!!.text.toString())

        user!!.setOnPreferenceChangeListener {_, newValue -> checkAnonUser(newValue as String)}

        //Si se introduce una contraseña se "muestra" enmascarada. De lo contrario se muestra el texto por defecto
        password!!.setOnPreferenceChangeListener { _, newValue ->
            if(newValue.toString().isNotEmpty()){
                var txt = ""
                for(i in 1..newValue.toString().length){
                    txt= "$txt*"
                }
                password!!.summary = txt
            }
            else{
                password!!.summary = "Introduce la contraseña"
            }
            true
        }

        //Actualiza las listas de colores si estas están vacías
        if(correct!!.entries==null){
            updateList(correct!!)
            correct!!.setValueIndex(correct!!.findIndexOfValue("#00FF0A"))
        }

        if(partial!!.entries==null){
            updateList(partial!!)
            partial!!.setValueIndex(partial!!.findIndexOfValue("#FFCC00"))
        }

        if(incorrect!!.entries==null){
            updateList(incorrect!!)
            incorrect!!.setValueIndex(incorrect!!.findIndexOfValue("#FF1400"))
        }
    }

    private fun checkAnonUser(txt:String) : Boolean {
        if (txt == "Anon") {
            password!!.text = "abc123.."
            password!!.summary = "********"
        }
        return true
    }

    //Restablece las opciones por defecto
    private fun restoreClick() : Boolean{

        correct!!.setValueIndex(correct!!.findIndexOfValue("#00FF0A"))

        incorrect!!.setValueIndex(incorrect!!.findIndexOfValue("#FF1400"))

        partial!!.setValueIndex(partial!!.findIndexOfValue("#FFCC00"))

        restore!!.isChecked=false

        return true
    }

    //Conecta a la base de datos de acuerdo a la información proporcionada por el usuario
    private fun connectToDB() : Boolean{

        dbManager=null
        dbConnected=false

        if(connect!!.isChecked){

            val t = Thread {
                dbManager = DBManager(url!!.text.toString(), user!!.text.toString(), password!!.text.toString())
                dbConnected=dbManager!!.connection!=null
                requireActivity().runOnUiThread {
                    connect!!.isChecked = dbConnected
                }
            }
            t.start()
        }
        connect!!.isChecked = dbConnected
        return dbConnected
    }

    //Carga la lista con los colores disponibles
    private fun updateList(list:ListPreference){
        list.entries = requireContext().resources.getStringArray(colorNames)
        list.entryValues = requireContext().resources.getStringArray(colorValues)
    }
}