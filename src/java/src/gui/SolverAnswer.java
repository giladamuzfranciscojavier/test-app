package gui;

import core.test.*;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class SolverAnswer extends javax.swing.JPanel {

    Answer answer;
        
    //Inicializa los componentes, asocia la respuesta correspondiente y determina si se muestra checkbox o radiobutton
    public SolverAnswer(Answer answer, boolean mult){
        initComponents();
        this.answer=answer;        
        rbuttonAnswer.setEnabled(!mult);
        rbuttonAnswer.setVisible(!mult);
        cboxAnswer.setEnabled(mult);
        cboxAnswer.setVisible(mult);
        loadAnswer();
    }
    
    /**
     * @return the answer
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }    
    
    public void loadAnswer(){
        textArea.setText(answer.getText());        
    }


    //Devuelve el textarea
    public JTextArea getTextArea(){
        return textArea;
    }
    
    //Devuelve el radiobutton para añadir al grupo
    public JRadioButton getRadioButton(){
        return rbuttonAnswer;
    }
    
    //Devuelve el botón que está activado
    public JToggleButton getActiveButton(){
        if(rbuttonAnswer.isVisible()){
            return rbuttonAnswer;
        }        
        else {
            return cboxAnswer;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        cboxAnswer = new javax.swing.JCheckBox();
        rbuttonAnswer = new javax.swing.JRadioButton();

        setMaximumSize(new java.awt.Dimension(392, 76));
        setMinimumSize(new java.awt.Dimension(392, 76));
        setPreferredSize(new java.awt.Dimension(392, 76));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(1);
        textArea.setBorder(null);
        textArea.setPreferredSize(new java.awt.Dimension(232, 76));
        textArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(textArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboxAnswer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbuttonAnswer)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rbuttonAnswer)
                    .addComponent(cboxAnswer))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        getActiveButton().doClick();
    }//GEN-LAST:event_formMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboxAnswer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbuttonAnswer;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
    
}
