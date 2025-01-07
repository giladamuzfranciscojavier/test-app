package com.example.testapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.isVisible
import core.test.Answer

class AnswerSolverView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val text: TextView
    private val checkbox: CheckBox
    val radiobutton: RadioButton
    private var list = mutableListOf<RadioButton>()
    var answer : Answer? = null

    init{
        val view = LayoutInflater.from(context).inflate(R.layout.view_answer_solver, this, true)

        text = view.findViewById(R.id.text)
        checkbox = view.findViewById(R.id.checkbox)
        radiobutton = view.findViewById(R.id.radiobutton)
        radiobutton.setOnClickListener {clickEvent()}
    }

    constructor(context: Context, answerSolver:AnswerSolver, attrs: AttributeSet? = null) : this(context, attrs) {
        answer = answerSolver.answer
        if(answerSolver.multiple){
            radiobutton.visibility = View.GONE
            checkbox.visibility = View.VISIBLE
        }
        else{
            radiobutton.visibility=View.VISIBLE
            checkbox.visibility=View.GONE
        }


        list=answerSolver.group
        list.add(radiobutton)
    }

    fun getActiveButton() : CompoundButton {
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