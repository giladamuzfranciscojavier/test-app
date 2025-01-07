package com.example.testapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.isVisible
import core.test.Answer

class AnswerCreatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val text: EditText
    private val checkbox: CheckBox
    private val radiobutton: RadioButton
    private var list = mutableListOf<RadioButton>()
    var answer : Answer? = null

    init{
        val view = LayoutInflater.from(context).inflate(R.layout.view_answer_creator, this, true)

        text = view.findViewById(R.id.text)
        checkbox = view.findViewById(R.id.checkbox)
        radiobutton = view.findViewById(R.id.radiobutton)
        radiobutton.setOnClickListener { clickEvent() }
    }

    constructor(context: Context, answerCreator:AnswerCreator, attrs: AttributeSet? = null) : this(context, attrs) {
        answer = answerCreator.answer
        if(answerCreator.multiple){
            radiobutton.visibility = View.GONE
            checkbox.visibility = View.VISIBLE
        }
        else{
            radiobutton.visibility=View.VISIBLE
            checkbox.visibility=View.GONE
        }
        list=answerCreator.group
        list.add(radiobutton)
    }

    //Guarda la respuesta
    fun saveAnswer() : Answer{
        answer!!.text = text.text.toString()
        if(checkbox.isChecked){
            answer!!.isCorrect = checkbox.isChecked
        }
        else {
            answer!!.isCorrect = radiobutton.isChecked
        }

        return answer!!
    }

    //Gestionan qué componente se muestra
    fun showCheckbox() {
        checkbox.isEnabled = true
        checkbox.visibility=VISIBLE
        radiobutton.isEnabled = false
        radiobutton.visibility=GONE
    }

    fun showRadioButton() {
        checkbox.isEnabled = false
        checkbox.visibility=GONE
        radiobutton.isEnabled = true
        radiobutton.visibility=VISIBLE
    }

    fun getActiveButton() : CompoundButton{
        return if(radiobutton.isVisible){
            radiobutton
        } else {
            checkbox
        }
    }

    private fun clickEvent(){
        for(item in list){
            if(item.isChecked){
                item.isChecked=false
            }
        }
        radiobutton.isChecked=true
    }

}