package com.example.testapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import core.saveLoad.db.DBManager

//Actividad principal. Se limita a cargar (o crear) las preferencias y cargar el fragmento de navegación
class MainActivity : AppCompatActivity() {

    companion object{
        var dbConnected = false
        var dbManager : DBManager ?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Carga las preferencias
        val preferences = PreferenceManager.getDefaultSharedPreferences(baseContext)

        //Si no están inicializadas se generan con los valores por defecto
        if(!preferences.contains("init")){
            preferences.edit().clear().apply()
            preferences.edit().putString("init","init")
                .putString("colorCorrect", "#00FF0A")
                .putString("colorIncorrect", "#FF1400")
                .putString("colorPartial", "#FFCC00").apply()
        }

        setContentView(R.layout.activity_main)


        //Configura la barra superior personalizada
        val toolbar : Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }

    }
}