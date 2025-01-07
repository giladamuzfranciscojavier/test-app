package com.example.testapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import core.test.Answer
import core.test.Question
import core.test.Test
import java.util.Timer
import java.util.TimerTask

//Panel de resolución de test
class SolverFragment : Fragment() {

    private var test:Test?=null

    private var questions : ArrayList<Question>?=null
    private var answers = ArrayList<AnswerSolverView>()
    private var answerGroup = ArrayList<RadioButton>()
    private var preferenceManager : SharedPreferences?=null

    private val t : Timer = Timer()
    private var cd : CountDownTimer ?= null
    private var finished = false

    var index=0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Muestra el botón para volver atrás
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowCustomEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val view = inflater.inflate(R.layout.fragment_solver, container, false)

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //Carga el test del bundle recibido
        test = arguments?.get("test") as Test?
        questions = test!!.questions

//region View binding
        answerLayout = view.findViewById(R.id.answerLayout)
        comboxIndex = view.findViewById(R.id.comboxIndex)
        clock = view.findViewById(R.id.clock)
        qTitle = view.findViewById(R.id.qTitle)
        check = view.findViewById(R.id.check)
        check!!.setOnClickListener {checkClick()}


        finish = view.findViewById(R.id.finish)
        finish!!.setOnClickListener {finishClick()}

        clear = view.findViewById(R.id.clear)
        clear!!.setOnClickListener {clearClick()}
//endregion

        check!!.isEnabled=test!!.canCheckAnswer()
        val gestureDetector = GestureDetector(this.context, gestureListener)

        val touchListener = View.OnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        view.allViews.forEach { x-> x.setOnTouchListener(touchListener) }

        comboxIndex!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                loadQuestion(questions!![0])
                index=0
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position<0){
                    return
                }
                saveQuestionState()
                index=position
                loadQuestion(questions!![position])
            }
        }

        //Al presionar el botón atrás salta un diálogo preguntando al usuario si quiere salir
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Volver al menú principal?")
            builder.setPositiveButton("Si") { _: DialogInterface, _: Int ->
                findNavController().popBackStack()
            }

            builder.setNegativeButton("No"){ dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
            builder.show()
        }

        populateComboxIndex()

        //Cuenta atrás
        if(test!!.hasTimeLimit()){
            var h = test!!.hours
            var m = test!!.mins
            var s = 1

            cd = object : CountDownTimer((h*3600000+m*60000+1000).toLong(), 1000){
                override fun onTick(p0: Long) {
                    s--
                    if (s < 0) {
                        m--
                        if (m < 0) {
                            h--
                            if (h < 0) {
                                this.onFinish()
                            }
                            m = 59
                        }
                        s = 59
                    }
                    val time =
                        ((if (h > 9) "" + h else ("0$h")) + ":" + (if (m > 9) "" + m else ("0$m"))) + ":" + (if (s > 9) "" + s else ("0$s"))
                    clock!!.text = time
                }

                override fun onFinish() {
                    finishClick()
                }
            }
            cd!!.start()
        }

        //Cronómetro
        else{
            var h=0
            var m=0
            var s=0
            val task = object : TimerTask() {
                override fun run() {
                    //Incrementa un segundo
                    s++
                    //Al llegar al minuto se incrementa el contador de los mismos y se resetea el de segundos
                    if (s > 59) {
                        m++
                        s = 0

                        //Se hace lo mismo con las horas
                        if (m > 59) {
                            h++
                            m = 0
                        }
                    }
                    val time =
                        ((if (h > 9) "" + h else ("0$h")) + ":" + (if (m > 9) "" + m else ("0$m"))) + ":" + (if (s > 9) "" + s else ("0$s"))

                    try{
                        activity!!.runOnUiThread {
                            clock!!.text=time
                        }
                    }
                    catch (e:NullPointerException){

                    }
                }
            }
            t.schedule(task, 1000, 1000)
        }


        return view
    }

    fun saveQuestionState(){
        for (sa in answers) {
            sa.answer!!.userAnswer = sa.getActiveButton().isChecked
        }
    }

    private fun addAnswer(ans: Answer?) {
        val answer = AnswerSolverView(requireContext(), AnswerSolver(questions!![comboxIndex!!.selectedItemPosition].isMultAnswer,answerGroup,ans!!))

        if(!(questions!![comboxIndex!!.selectedItemPosition]).isMultAnswer){
            answerGroup.add(answer.radiobutton)
        }

        answers.add(answer)

        refresh()
    }

    fun loadQuestion(q: Question) {

        if(comboxIndex!!.selectedItemPosition<0){
            return
        }

        qTitle!!.text = q.title

        check!!.isEnabled = test!!.canCheckAnswer() && !q.isLocked
        clear!!.isEnabled = !q.isLocked


        answers = ArrayList()

        for (answer in q.answers) {
            addAnswer(answer)
        }
    }


    private fun populateComboxIndex(){
        val list = mutableListOf<Int>()
        for(i in 0 until questions!!.size){
            list.add(i+1)
        }
        index=0
        comboxIndex!!.setSelection(index)
        comboxIndex!!.adapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item, list)

    }


    private fun refresh(){
        answerLayout!!.removeAllViewsInLayout()
        for (sa in answers) {
            answerLayout!!.addView(sa)
            sa.text.text = sa.answer!!.text
            sa.getActiveButton().isChecked = sa.answer!!.userAnswer

            sa.getActiveButton().isEnabled = !questions!![comboxIndex!!.selectedItemPosition].isLocked

            if(!sa.getActiveButton().isEnabled){
                colorUpdate(sa)
            }
        }
    }

    private fun checkClick(){
        saveQuestionState()
        for (sa in answers) {
            sa.getActiveButton().isEnabled = false
            colorUpdate(sa)
        }
        check!!.isEnabled = false
        clear!!.isEnabled = false
        questions!![comboxIndex!!.selectedItemPosition].isLocked = true
    }

    //Cambia los colores del texto de las respuestas al correspondiente según su estado
    private fun colorUpdate(sa: AnswerSolverView) {
        when (questions!![comboxIndex!!.selectedItemPosition].questionStatus()) {
            Question.INCORRECT -> sa.text.setTextColor(
                preferenceManager!!.getString(
                    "colorIncorrect",
                    "#FF1400"
                )!!.toColorInt()
            )

            Question.NO_ANSWER -> sa.text.setTextColor(
                preferenceManager!!.getString(
                    "colorPartial",
                    "#FFCC00"
                )!!.toColorInt()
            )

            Question.CORRECT -> sa.text.setTextColor(
                preferenceManager!!.getString(
                    "colorCorrect",
                    "#00FF0A"
                )!!.toColorInt()
            )

            else -> if (sa.answer!!.checkUserAnswer() == Answer.CORRECT) {
                sa.text.setTextColor(
                    preferenceManager!!.getString(
                        "colorCorrect",
                        Color.GREEN.toString()
                    )!!.toColorInt()
                )
            } else {
                sa.text.setTextColor(
                    preferenceManager!!.getString(
                        "colorPartial",
                        Color.YELLOW.toString()
                    )!!.toColorInt()
                )
            }
        }
    }


    private fun finishClick(){
        //Para evitar crasheos si se llama más de una vez (por ejemplo al clicar el botón y que termine una cuenta atrás al mismo tiempo)
        synchronized(this){
            if(finished){
                return
            }
            finished=true

            t.cancel()
            if(cd!=null){
                cd!!.cancel()
            }
            checkClick()

            var msg = "Puntuaciones: \n"+
                    "Correctas: ${test!!.correctAnsScore} puntos\n"+
                    "Incorrectas: ${test!!.incorrectAnsScore} puntos\n"+
                    "Máximo por parcialmente correctas: ${test!!.partialAnsScore} puntos"

            if (test!!.hasMinScore()) {
                msg = if (test!!.hasPassed()) {
                    "$msg\n\nEnhorabuena! Has aprobado!"
                } else {
                    "$msg\n\nQue pena, no has aprobado... Vuelve a intentarlo!"
                }
            }

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(msg).setPositiveButton("Aceptar"){ dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }

            builder.setTitle("Puntuación obtenida: ${test!!.score} de ${test!!.maxScore}")
            builder.setMessage(msg)

            builder.setOnDismissListener { _ ->
                run {
                    findNavController().popBackStack()
                }
            }

            val dialog = builder.create()

            dialog.show()
        }
    }

    private fun clearClick(){
        for(answer in answers){
            answer.getActiveButton().isChecked = false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        t.cancel()
        cd?.cancel()
    }


    //Listener de gestos para cambiar de pregunta
    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {

            //println(e1!!.getX())
            //println(e2.getX());


            //Si el diferencial de X es menor que -50 (para evitar cambios accidentales)
            // y no es la primera pregunta cambia a la pregunta anterior
            if(e1!!.x -e2.x <-50 && comboxIndex!!.selectedItemPosition>0){
                comboxIndex!!.setSelection(comboxIndex!!.selectedItemPosition-1)
            }

            //Si el diferencial de X es mayor que 50 y no es la última pregunta cambia a la pregunta siguiente
            else if(e1.x -e2.x >50 && comboxIndex!!.selectedItemPosition<comboxIndex!!.adapter.count-1){
                comboxIndex!!.setSelection(comboxIndex!!.selectedItemPosition+1)
            }


            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }


    var comboxIndex : Spinner?=null
    var clock : TextView?=null
    private var check : Button?=null
    private var finish : Button?=null
    private var clear : Button?=null
    private var qTitle : TextView?=null
    private var answerLayout : LinearLayout?=null
}