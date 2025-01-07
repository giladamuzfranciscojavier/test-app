package com.example.testapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Spinner
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import core.saveLoad.testProcessor.TestCreator
import core.test.Answer
import core.test.Question
import core.test.Test
import java.io.File


class CreatorFragment : Fragment() {

    val questions = ArrayList<Question>()
    private var answers = ArrayList<AnswerCreatorView>()
    private var answerGroup = ArrayList<RadioButton>()

    private var height:Int?=null

    //Como no hay forma de obtener un item deseleccionado es necesario almacenarlo manualmente
    var index = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Muestra el botón para volver atrás
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowCustomEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//region View binding
        val view = inflater.inflate(R.layout.fragment_creator, container, false)
        answerLayout = view.findViewById(R.id.answerLayout)
        testLayout = view.findViewById(R.id.testNavLayout)
        enunciado = view.findViewById(R.id.enunciado)

        radioButtonOnly = view.findViewById(R.id.radioButtonOnly)

        radioButtonMultiple = view.findViewById(R.id.radioButtonMultiple)

        comboxIndex = view.findViewById(R.id.comboxIndex)

        answerGroup = ArrayList()

        buttonAddQuestion = view.findViewById(R.id.buttonAddQuestion)
        buttonFinish = view.findViewById(R.id.buttonFinish)
        buttonAddAnswer = view.findViewById(R.id.buttonAddAnswer)
        buttonCleanAnswers = view.findViewById(R.id.buttonCleanAnswers)
        buttonCleanQuestions = view.findViewById(R.id.buttonCleanQuestions)

        checkBoxTimeLimit = view.findViewById(R.id.checkBoxTimeLimit)
        checkBoxComprobar = view.findViewById(R.id.checkBoxComprobar)
        checkBoxMinScore = view.findViewById(R.id.checkBoxMinScore)
        correctScore = view.findViewById(R.id.correctScore)
        incorrectScore = view.findViewById(R.id.incorrectScore)
        partialScore = view.findViewById(R.id.partialScore)

        hours = view.findViewById(R.id.hours)
        minutes = view.findViewById(R.id.minutes)
        minScore = view.findViewById(R.id.minScore)
//endregion

        val gestureDetector = GestureDetector(this.context, gestureListener)

        val touchListener = OnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        view.allViews.forEach { x-> x.setOnTouchListener(touchListener) }

        radioButtonOnly!!.setOnClickListener { radioButtonOnlyClick() }
        radioButtonMultiple!!.setOnClickListener {radioButtonMultipleClick()}

        buttonAddQuestion!!.setOnClickListener {addQuestion()}
        buttonAddAnswer!!.setOnClickListener {addAnswer()}
        buttonCleanQuestions!!.setOnClickListener {cleanQuestions()}
        buttonCleanAnswers!!.setOnClickListener {cleanAnswers()}
        buttonFinish!!.setOnClickListener {buttonFinishClick()}

        checkBoxMinScore!!.setOnClickListener {cboxMinScoreClick()}

        comboxIndex!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                loadQuestion(questions[0])
                index=0
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveQuestion(questions[index])
                index=position
                loadQuestion(questions[position])
            }
        }

        checkBoxTimeLimit!!.setOnClickListener {checkBoxTimeLimitClick()}

        hours!!.addTextChangedListener(textWatcherHours)
        minutes!!.addTextChangedListener(textWatcherMins)

        hours!!.onFocusChangeListener=focusChangeListener
        minutes!!.onFocusChangeListener=focusChangeListener
        correctScore!!.onFocusChangeListener=focusChangeListener
        incorrectScore!!.onFocusChangeListener=focusChangeListener
        partialScore!!.onFocusChangeListener=focusChangeListener
        minScore!!.onFocusChangeListener=focusChangeListener


        addQuestion()

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


        //Gestiona la visibilidad del panel de navegación con el teclado virtual
        view.viewTreeObserver.addOnGlobalLayoutListener {

            //Si no hay altura de pantalla registrada se toma la actual
            if(height==null){
                height = view!!.height
            }

            //Si la altura actual es menor que la registrada (teclado visible) se esconde el panel de navegación
            if(view.height<height!!){
                buttonCleanQuestions!!.visibility=GONE
                buttonAddQuestion!!.visibility=GONE
                buttonFinish!!.visibility=GONE
                radioButtonMultiple!!.visibility=GONE
                radioButtonOnly!!.visibility=GONE
            }
            //En caso contrario se muestra
            else{
                buttonCleanQuestions!!.visibility=VISIBLE
                buttonAddQuestion!!.visibility=VISIBLE
                buttonFinish!!.visibility=VISIBLE
                radioButtonMultiple!!.visibility=VISIBLE
                radioButtonOnly!!.visibility=VISIBLE
            }
        }
        return view
    }

    private fun cboxMinScoreClick(){
        minScore!!.isEnabled = checkBoxMinScore!!.isChecked
    }

    private fun addAnswer(ans:Answer){
        if(comboxIndex!!.adapter.count>0 && index<comboxIndex!!.adapter.count){
            saveQuestion(questions[index])
        }

        val answer = AnswerCreatorView(this.requireContext(), AnswerCreator(radioButtonMultiple!!.isChecked, answerGroup, ans))
        answers.add(answer)
        refresh()
    }

    private fun addAnswer(){
        addAnswer(Answer())
    }

    //Añade una pregunta y la coloca al final
    private fun addQuestion() {

        if(questions.isNotEmpty()){
            saveQuestion(questions[index])
        }


        for(i in 0 until questions.size){
            val q = questions[i]

            if(q.title.isBlank()){
                var empty = true

                for(answer in q.answers){
                    if(answer.text.isNotBlank()){
                        empty=false
                        break
                    }
                }
                if(empty){
                    comboxIndex!!.setSelection(i)
                    return
                }
            }
        }

        questions.add(Question())
        val list = mutableListOf<Int>()
        for(i in 0..comboxIndex!!.adapter.count){
            list.add(i+1)
        }
        comboxIndex!!.adapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item, list)

        comboxIndex!!.setSelection(comboxIndex!!.count-1)
    }

    private fun saveQuestion(q:Question){
        q.title = enunciado!!.text.toString()

        q.resetAnswers()

        for(answer:AnswerCreatorView in answers){
            q.addAnswer(answer.saveAnswer())
        }
    }


    //Carga una pregunta
    fun loadQuestion(q: Question) {
        enunciado!!.setText(q.title)

        radioButtonOnly!!.isChecked=!q.isMultAnswer
        radioButtonMultiple!!.isChecked=q.isMultAnswer

        answers = ArrayList()

        for (answer in q.answers) {
            addAnswer(answer!!)
        }

        //Resetea las respuestas para evitar duplicados
        q.resetAnswers()

        if (answers.isEmpty()) {
            addAnswer()
        }


    }

    //Refresca el contenedor de respuestas para que se visualicen los cambios
    private fun refresh() {
        answerLayout!!.removeAllViewsInLayout()
        for (ca in answers) {
            answerLayout!!.addView(ca)
            ca.text.setText(ca.answer!!.text)
            ca.getActiveButton().isChecked = ca.answer!!.isCorrect
        }
    }


    //Función para limpiar las preguntas vacías
    private fun cleanQuestions() {
        //Guarda la pregunta para que tenga en cuenta los valores actuales
        saveQuestion(questions[index])

        val it = questions.iterator()
        //Crea una variable para almacenar las preguntas sobre las que se itera
        var temp: Question
        while (it.hasNext()) {
            //Si el enunciado está vacío sigue con la comprobación
            if (it.next().also { temp = it }.title.isBlank()) {
                //Itera sobre las respuestas de la pregunta
                val it2 = temp.answers.iterator()
                while (it2.hasNext()) {
                    //Limpia las respuestas vacías
                    if (it2.next().text.isBlank()) {
                        it2.remove()
                    }
                }
                //Si después de limpiar no quedan respuestas se borra la pregunta
                if (temp.answers.isEmpty()) {
                    it.remove()
                }
            }
        }
        populateComboxIndex()
        saveQuestion(questions[index])
        refresh()
    }


    private fun populateComboxIndex(){
        val list = mutableListOf<Int>()
        for(i in 0 until questions.size){
            list.add(i+1)
        }
        index=0
        comboxIndex!!.setSelection(index)
        comboxIndex!!.adapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item, list)
        if(comboxIndex!!.adapter.count<1){
            addQuestion()
        }
        loadQuestion(questions[0])
    }

    private fun cleanAnswers(){

        //Guarda la pregunta para que tenga en cuenta los valores actuales
        saveQuestion(questions[index])


        //Itera sobre las preguntas y borra aquellas con texto en blanco
        val it: MutableIterator<AnswerCreatorView> = answers.iterator()
        while (it.hasNext()) {
            if (it.next().answer!!.text.isBlank()) {
                it.remove()
            }
        }


        if (answers.isEmpty()) {
            addAnswer()
        }


        //Vuelve a guardar la pregunta para que procese la limpieza
        saveQuestion(questions[index])


        //Carga la pregunta para que se reflejen los cambios en la interfaz
        loadQuestion(questions[index])
    }


    //Click event handlers
    private fun radioButtonOnlyClick(){
        questions[index].setMultiAnswer(false)
        for (answer in answers) {
            answer.showRadioButton()
        }
    }

    private fun radioButtonMultipleClick(){
        questions[index].setMultiAnswer(true)
        for (answer in answers) {
            answer.showCheckbox()
        }
    }

    private fun checkBoxTimeLimitClick(){
        if(checkBoxTimeLimit!!.isChecked){
            hours!!.isEnabled=true
            minutes!!.isEnabled=true
        }
        else{
            hours!!.isEnabled=false
            minutes!!.isEnabled=false
        }
    }


    //Por peculiaridades de Android la función está "separada" en 3 partes, siendo esta la primera
    //Se comprueba si se ha indicado límite de tiempo y este es 0
    private fun buttonFinishClick(){

        saveQuestion(questions[comboxIndex!!.selectedItemPosition])

        val h = hours!!.text.toString().toInt()
        val m = minutes!!.text.toString().toInt()
        if(checkBoxTimeLimit!!.isChecked && h == 0 && m == 0){
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("El tiempo límite indicado es 0")
            builder.setMessage("Aumenta el tiempo o desmarca la opción de tiempo límite")
            builder.setNeutralButton("Aceptar") { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
            //Muestra el diálogo de error y se cancela el proceso de guardado
            builder.show()
        }
        else{
            //Continúa a la siguiente parte
            finishAfterCheckTime()
        }
    }

    //Segunda parte de la función para guardar test
    //Una vez hecha la comprobación anterior se pide el nombre del test y se comprueba que no está vacío
    private fun finishAfterCheckTime(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Introduce el nombre del test")

        val input = EditText(requireContext())

        builder.setView(input)

        var name: String

        //Crea un diálogo para introducir el nombre del test
        builder.setPositiveButton("Aceptar") { _: DialogInterface, _: Int ->
            name = input.text.toString()
            //Si el nombre está vacío muestra un mensaje de error y se cancela el proceso de guardado
            if(name.isEmpty()){
                val builder2 = AlertDialog.Builder(requireContext()).setTitle("Debes introducir un nombre")
                    .setNeutralButton("Aceptar") { dialog:DialogInterface, _:Int->
                        dialog.cancel()
                    }
                builder2.show()
            }
            else{
                //Continúa con la siguiente parte
                finishAfterName(name)
            }

        }

        //Proceso de guardado cancelado por parte del usuario
        builder.setNegativeButton("Cancelar") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }

        builder.show()
    }


    //Tercera parte de la función para guardar test
    //Pregunta al usuario como quiere guardar el test si está conectado a la base de datos
    private fun finishAfterName(name:String){
        //Si está conectado a la base de datos muestra un diálogo con las opciones de guardado
        if(MainActivity.dbConnected){
            val builder = AlertDialog.Builder(requireContext()).setTitle("Como guardar el test")
            builder.setPositiveButton("Base de Datos") { _: DialogInterface, _: Int ->
                finishSave(name, 1)
            }
            builder.setNeutralButton("Fichero Local")   { _: DialogInterface, _: Int ->
                finishSave(name, 2)
            }

            builder.setNegativeButton("Cancelar"){ dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
            builder.show()
        }

        else{
            finishSave(name, 2)
        }
    }

    //Última parte de la función para guardar test
    //Realiza el guardado propiamente dicho
    private fun finishSave(name:String, result:Int){

        cleanQuestions()

        val h = hours!!.text.toString().toInt()
        val m = minutes!!.text.toString().toInt()
        val ms = minScore!!.text.toString().toDouble()
        val correct = correctScore!!.text.toString().toDouble()
        val incorrect = incorrectScore!!.text.toString().toDouble()
        val partial = partialScore!!.text.toString().toDouble()

        val test = Test(name, checkBoxTimeLimit!!.isChecked,h,m,checkBoxMinScore!!.isChecked,ms, correct,incorrect,partial,checkBoxComprobar!!.isChecked)

        for(question in questions){
            test.addQuestion(question)
        }

        val creator = TestCreator(test)

        try {
            when (result) {
                //Guarda en base de datos
                1 -> {
                    Thread { creator.saveToDB(MainActivity.dbManager) }.start()
                }
                //Guarda en fichero local
                2 -> {

                    var n = name

                    if(name.length < 5 || name.substring(name.length-4) != ".xml"){
                        n+=".xml"
                    }
                    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + n)
                    if(creator.saveAsFile(file)){
                        AlertDialog.Builder(requireContext()).setTitle("Test guardado con éxito en la carpeta Descargas")
                            .setNeutralButton("Aceptar"){dialog,_->dialog.dismiss()}.show()

                    }
                    else{
                        AlertDialog.Builder(requireContext()).setTitle("Error").setMessage("Ya existe un documento con ese nombre")
                            .setNeutralButton("Aceptar"){dialog,_->dialog.dismiss()}.show()
                        return
                    }
                }

                else -> {}
            }
            findNavController().popBackStack()
        }
        catch (e:Exception){
            e.run { printStackTrace() }
        }

    }


    //TextWatcher para el campo de texto de los minutos
    private val textWatcherMins = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable) {

            //Si el nuevo estado está vacío no hace nada
            if(p0.toString().isEmpty()){
                return
            }

            //Si el valor numérico del campo es superior a 59 se ajusta a dicho valor
            if(p0.toString().toInt()>59){
                val ab: Editable = SpannableStringBuilder(p0.toString().replace(p0.toString(), "59"))
                p0.replace(0, p0.length, ab)
            }
        }
    }

    //TextWatcher para el campo de texto de las horas
    private val textWatcherHours = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable) {
            if(p0.toString().isEmpty()){
                return
            }
            //Si el valor numérico del campo es superior a 99 se ajusta a dicho valor
            if(p0.toString().toInt()>99){
                val ab: Editable = SpannableStringBuilder(p0.toString().replace(p0.toString(), "99"))
                p0.replace(0, p0.length, ab)
            }
        }
    }


    //Listener para cambio de foco en campos numéricos que ajusta el valor a 0 si este ha quedado vacío
    private val focusChangeListener = OnFocusChangeListener { view, hasFocus ->
        if(!hasFocus){
            val src = view as EditText
            if(src.text.toString().isEmpty()){
                src.setText("0")
            }
        }
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


    private var answerLayout : LinearLayout?=null
    private var testLayout:ConstraintLayout?=null
    private var enunciado : EditText?=null
    private var radioButtonMultiple : RadioButton?=null
    private var radioButtonOnly : RadioButton?=null
    private var comboxIndex : Spinner?=null
    private var buttonAddQuestion : Button?=null
    private var buttonFinish : Button?=null
    private var buttonAddAnswer : Button?=null
    private var buttonCleanAnswers : Button?=null
    private var buttonCleanQuestions : Button?=null
    private var checkBoxTimeLimit : CheckBox?=null
    private var checkBoxComprobar : CheckBox?=null
    private var checkBoxMinScore : CheckBox?=null
    private var correctScore : EditText?=null
    private var incorrectScore : EditText?=null
    private var partialScore : EditText?=null
    private var hours : EditText?=null
    private var minutes : EditText?=null
    private var minScore : EditText?=null
}