package gui;

import core.test.*;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class CreatorAnswer extends javax.swing.JPanel {

    private Answer answer;
        
    //Además de los componentes, inicializa un objeto de tipo respuesta (código pasado al otro constructor por limpieza)
    public CreatorAnswer(boolean mult){
        this(mult, new Answer());
    }  
    
    //Carga una respuesta ya creada al navegar entre las preguntas creadas
    public CreatorAnswer(boolean mult, Answer answer){
        initComponents();
        this.answer=answer; 
        if(mult){
            showCheckbox();
        }
        else{
            showRadioButton();
        }        
    }
       
    //Guarda la respuesta
    public Answer saveAnswer(){
        answer.setText(textArea.getText());
        //Obtiene la información del componente que esté activo
        if(cboxAnswer.isEnabled()){
            answer.setCorrect(cboxAnswer.isSelected());
        }        
        else {
            answer.setCorrect(rbuttonAnswer.isSelected());
        }        
        
        return answer;
    }    
    
    //Gestionan qué componente se muestra
    public void showCheckbox(){
        cboxAnswer.setEnabled(true);
        cboxAnswer.setVisible(true);
        rbuttonAnswer.setEnabled(false);
        rbuttonAnswer.setVisible(false);
    }    
    public void showRadioButton(){
        cboxAnswer.setEnabled(false);
        cboxAnswer.setVisible(false);
        rbuttonAnswer.setEnabled(true);
        rbuttonAnswer.setVisible(true);
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
        if(rbuttonAnswer.isEnabled()){
            return rbuttonAnswer;
        }
        else return cboxAnswer;
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

        textArea.setColumns(20);
        textArea.setRows(1);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboxAnswer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbuttonAnswer;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables

    
    
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
}
