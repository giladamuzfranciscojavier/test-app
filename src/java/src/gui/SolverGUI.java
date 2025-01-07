package gui;
import core.test.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import tools.*;

public class SolverGUI extends javax.swing.JDialog {

    
    Test test;
    
    LinkedList<Question> questions;
    ButtonGroup buttonGroupAnswers;
    
    Thread clock;
    
    ArrayList<SolverAnswer> answers;
    
    public SolverGUI(javax.swing.JFrame parent, boolean modal, Test test) {
        super(parent,modal);
        initComponents();
        setLocationRelativeTo(null);   
        
        
        addNavigationListeners(this.getRootPane());
        
        
        this.test=test;        
        //Activa o desactiva el botón según venga determinado en el test
        buttonCheckAnswer.setEnabled(test.canCheckAnswer());
        
        //Carga las preguntas en una lista ordenada para el índice
        questions=new LinkedList<>(test.getQuestions());   
        
        //Añade un item en el combo box por cada pregunta
        for(int i=0;i<questions.size();i++){
            comboxIndex.addItem(String.valueOf(comboxIndex.getModel().getSize()+1));
        }
        
        //Se ajusta el índice a la primera pregunta para inicializarla
        comboxIndex.setSelectedIndex(0);
        
        //Si hay más de 2 preguntas se activa el botón de siguiente pregunta
        if(questions.size()>1){
            buttonNext.setEnabled(true);
        }        
        
        if(test.hasTimeLimit()){
            clock = new Countdown(labelTime, test.getHours(), test.getMins(), this);
        }
        
        else{
            clock = new Chrono(labelTime);
        }
        clock.start();
    }

    
    public void addAnswer(Answer ans){
        SolverAnswer answer = new SolverAnswer(ans, questions.get(comboxIndex.getSelectedIndex()).isMultAnswer());
        answer.setSize(answer.getPreferredSize());
        answer.setLocation(0, 100*answers.size());
        if(!(questions.get(comboxIndex.getSelectedIndex())).isMultAnswer()){
            buttonGroupAnswers.add(answer.getRadioButton());
        }
        
        answers.add(answer);
        
        refresh();
    }
    
    
    
    public void loadQuestion(Question q){
        
        buttonGroupAnswers = new ButtonGroup();
        
        qTitle.setText(q.getTitle());
        
        if(!q.isLocked()){
            if(test.canCheckAnswer()){
                buttonCheckAnswer.setEnabled(true);
            }            
            buttonClearAnswer.setEnabled(true);
        }
        
        else{
            buttonCheckAnswer.setEnabled(false);
            buttonClearAnswer.setEnabled(false);
        }
        
        answers = new ArrayList<>();   
        
        for(Answer answer : q.getAnswers()){
            addAnswer(answer);
        }
                
    }
            
    
    public void saveQuestionState(){

        if(answers==null){
            return;
        }
        for(SolverAnswer sa : answers){
            sa.getAnswer().setUserAnswer(sa.getActiveButton().isSelected());
        }
    }
            
            
    //Refresca el contenedor de respuestas para que se visualicen los cambios
    public void refresh(){
        answerContainer.removeAll();
        for(SolverAnswer sa : answers){
            answerContainer.add(sa);
            sa.getTextArea().setText(sa.getAnswer().getText());
            sa.getActiveButton().setSelected(sa.getAnswer().getUserAnswer());
            
            if(questions.get(comboxIndex.getSelectedIndex()).isLocked()){
                sa.getActiveButton().setEnabled(false);
            }            
        }        
        
        if(questions.get(comboxIndex.getSelectedIndex()).isLocked()){
            check();
        }
        
        answerContainer.setPreferredSize(new Dimension(answerContainer.getWidth(), 100*answers.size()));
        answerContainer.revalidate();
        answerContainer.repaint();
    }
    
    
    
    //Comprueba si las respuestas son correctas
    public void check(){
        saveQuestionState();
        for(SolverAnswer sa : answers){            
            
            sa.getActiveButton().setEnabled(false);
            
            switch (questions.get(comboxIndex.getSelectedIndex()).questionStatus()) {
                case Question.INCORRECT:
                    sa.getTextArea().setForeground(Main.getSettings().getIncorrectColor());
                    break;
                case Question.NO_ANSWER:
                    sa.getTextArea().setForeground(Main.getSettings().getPartialColor());
                    break;
                case Question.CORRECT:
                    sa.getTextArea().setForeground(Main.getSettings().getCorrectColor());
                    break;
                default:
                    if(sa.getAnswer().checkUserAnswer()==Answer.CORRECT){
                        sa.getTextArea().setForeground(Main.getSettings().getCorrectColor());
                    }
                    else{
                        sa.getTextArea().setForeground(Main.getSettings().getPartialColor());
                    }   break;
            }
        }
        buttonCheckAnswer.setEnabled(false);
        buttonClearAnswer.setEnabled(false);
        questions.get(comboxIndex.getSelectedIndex()).setLocked(true);
    }
    
    
    public void finish(){

        clock.interrupt();
        check();        
        
        String msg = "Puntuación obtenida: "+test.getScore()+" de "+test.getMaxScore()
                +"\n\nPuntuaciones: \nCorrectas: "+test.getCorrectAnsScore()
                +" puntos\nIncorrectas: "+test.getIncorrectAnsScore()
                +" puntos\nMáximo por parcialmente correctas: "+test.getPartialAnsScore()+" puntos";
        
        if(test.hasMinScore()){
            if(test.hasPassed()){
                msg=msg.concat("\n\nEnhorabuena! Has aprobado!");
            }
            else{
                msg=msg.concat("\n\nQue pena, no has aprobado... Vuelve a intentarlo!");
            }
        }
        
        JOptionPane.showMessageDialog(rootPane, msg, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        answerContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        buttonNext = new javax.swing.JButton();
        buttonPrevious = new javax.swing.JButton();
        comboxIndex = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        buttonCheckAnswer = new javax.swing.JButton();
        buttonFinish = new javax.swing.JButton();
        labelTime = new javax.swing.JLabel();
        buttonClearAnswer = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        qTitle = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Resolver Test");
        setResizable(false);

        javax.swing.GroupLayout answerContainerLayout = new javax.swing.GroupLayout(answerContainer);
        answerContainer.setLayout(answerContainerLayout);
        answerContainerLayout.setHorizontalGroup(
            answerContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        answerContainerLayout.setVerticalGroup(
            answerContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(answerContainer);

        buttonNext.setText("->");
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        buttonPrevious.setText("<-");
        buttonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });

        comboxIndex.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboxIndexItemStateChanged(evt);
            }
        });

        jLabel1.setText("Pregunta");

        buttonCheckAnswer.setText("Comprobar Respuesta");
        buttonCheckAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCheckAnswerActionPerformed(evt);
            }
        });

        buttonFinish.setText("Finalizar");
        buttonFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFinishActionPerformed(evt);
            }
        });

        labelTime.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        labelTime.setText("00:00:00");

        buttonClearAnswer.setText("Borrar Respuesta");
        buttonClearAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearAnswerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(buttonCheckAnswer)
                        .addGap(18, 18, 18)
                        .addComponent(buttonClearAnswer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonFinish))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(comboxIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelTime)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonNext)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonPrevious)
                        .addComponent(comboxIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonFinish)
                    .addComponent(buttonCheckAnswer)
                    .addComponent(buttonClearAnswer))
                .addContainerGap())
        );

        jLabel7.setText("Enunciado");

        qTitle.setEditable(false);
        qTitle.setColumns(20);
        qTitle.setRows(1);
        jScrollPane2.setViewportView(qTitle);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFinishActionPerformed
        finish();
    }//GEN-LAST:event_buttonFinishActionPerformed

    private void buttonCheckAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCheckAnswerActionPerformed
        check();
    }//GEN-LAST:event_buttonCheckAnswerActionPerformed

    private void comboxIndexItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboxIndexItemStateChanged
        try{
            if(evt.getStateChange()==ItemEvent.SELECTED){    
                saveQuestionState();
                loadQuestion(questions.get(comboxIndex.getSelectedIndex()));
                if(comboxIndex.getSelectedIndex()>0){
                    buttonPrevious.setEnabled(true);
                }
                else{
                    buttonPrevious.setEnabled(false);
                }
                
                if(comboxIndex.getSelectedIndex()<comboxIndex.getModel().getSize()-1){
                    buttonNext.setEnabled(true);
                }
                
                else{
                    buttonNext.setEnabled(false);
                }
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_comboxIndexItemStateChanged

    private void buttonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreviousActionPerformed
        if(comboxIndex.getSelectedIndex()-1<1){
            buttonPrevious.setEnabled(false);
            
            if(comboxIndex.getModel().getSize()>1){
                buttonNext.setEnabled(true);
            }
        }
        comboxIndex.setSelectedIndex(comboxIndex.getSelectedIndex()-1);
    }//GEN-LAST:event_buttonPreviousActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        if(comboxIndex.getSelectedIndex()+1 == comboxIndex.getModel().getSize()-1){
            buttonNext.setEnabled(false);
            
            if(comboxIndex.getModel().getSize()>1){
                buttonPrevious.setEnabled(true);
            }
        }
        comboxIndex.setSelectedIndex(comboxIndex.getSelectedIndex()+1);
    }//GEN-LAST:event_buttonNextActionPerformed

    private void buttonClearAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearAnswerActionPerformed
        for(SolverAnswer sa:answers){
            sa.getActiveButton().setSelected(false);
        }
        buttonGroupAnswers.clearSelection();
    }//GEN-LAST:event_buttonClearAnswerActionPerformed

    
    //Añade listeners recursivamente a todos los componentes salvo aquellos de determinado tipo para permitir la navegación con teclado
    public void addNavigationListeners(Container cont){
        for(Component c : cont.getComponents()){
            
            //Si es un contenedor ejecuta el método de forma recursiva
            if(c instanceof Container){
                addNavigationListeners((Container) c);
            }
            
            //Si es un botón de radio o un componente con caja de texto lo omite para evitar un comportamiento potencialmente no deseado
            if(c instanceof JTextField || c instanceof JTextArea || c instanceof JSpinner || c instanceof JRadioButton){
                continue;
            }
            
            c.addKeyListener(new KeyListener(){
                
                @Override
                public void keyTyped(KeyEvent e) {
                }     
                @Override
                public void keyReleased(KeyEvent e) {
                }               
                
                @Override
                public void keyPressed(KeyEvent e) {
                    //Si se presiona la tecla derecha y está habilitado el botón siguiente lanza su evento de acción
                    if(e.getKeyCode()==KeyEvent.VK_RIGHT && buttonNext.isEnabled()){
                        buttonNextActionPerformed(null);
                    }

                    //Si se presiona la tecla izquierda y está habilitado el botón anterior lanza su evento de acción
                    else if(e.getKeyCode()==KeyEvent.VK_LEFT && buttonPrevious.isEnabled()){                        
                        buttonPreviousActionPerformed(null);
                    }
                }
            });
        }
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel answerContainer;
    private javax.swing.JButton buttonCheckAnswer;
    private javax.swing.JButton buttonClearAnswer;
    private javax.swing.JButton buttonFinish;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonPrevious;
    private javax.swing.JComboBox<String> comboxIndex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JLabel labelTime;
    private javax.swing.JTextArea qTitle;
    // End of variables declaration//GEN-END:variables
}
