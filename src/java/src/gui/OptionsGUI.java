package gui;

import com.formdev.flatlaf.FlatLightLaf;
import gui.config.ConfigLoad;
import gui.config.ConfigSave;
import gui.config.Settings;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class OptionsGUI extends javax.swing.JDialog {
    
    Color corcolor;
    Color incolor;
    Color parcolor;

    /** Creates new form OptionsGUI */
    public OptionsGUI(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Settings s = ConfigLoad.load();
        
        labelSample.setFont(new Font(s.getFont(), Font.PLAIN, 12));
        
        labelcorrect.setForeground(s.getCorrectColor());
        labelincorrect.setForeground(s.getIncorrectColor());
        labelpartial.setForeground(s.getPartialColor());
        
        setLocationRelativeTo(parent);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        Font[] fonts = ge.getAllFonts();
        
        //Para prevenir duplicados
        HashSet<String> reps = new HashSet();
        
        for(Font f : fonts){
            if(!reps.contains(f.getFontName())){
                cboxFonts.addItem(f.getFontName());
                reps.add(f.getFontName());
            }            
        }
        
        cboxFonts.setSelectedItem(s.getFont());
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cboxFonts = new javax.swing.JComboBox<>();
        colorCorrect = new javax.swing.JButton();
        colorIncorrect = new javax.swing.JButton();
        colorPartial = new javax.swing.JButton();
        labelSample = new javax.swing.JLabel();
        labelcorrect = new javax.swing.JLabel();
        labelincorrect = new javax.swing.JLabel();
        labelpartial = new javax.swing.JLabel();
        cancel = new javax.swing.JButton();
        accept = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opciones");

        jLabel1.setText("Tipografías");

        cboxFonts.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboxFontsItemStateChanged(evt);
            }
        });

        colorCorrect.setText("Color Respuestas Correctas");
        colorCorrect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorCorrectActionPerformed(evt);
            }
        });

        colorIncorrect.setText("Color Respuestas Incorrectas");
        colorIncorrect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorIncorrectActionPerformed(evt);
            }
        });

        colorPartial.setText("Color Respuestas Parciales");
        colorPartial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorPartialActionPerformed(evt);
            }
        });

        labelSample.setText("Texto de ejemplo");

        labelcorrect.setBackground(java.awt.Color.black);
        labelcorrect.setForeground(java.awt.Color.green);
        labelcorrect.setText("Correcta");

        labelincorrect.setBackground(java.awt.Color.black);
        labelincorrect.setForeground(java.awt.Color.red);
        labelincorrect.setText("Incorrecta");

        labelpartial.setBackground(java.awt.Color.black);
        labelpartial.setForeground(java.awt.Color.orange);
        labelpartial.setText("Parcial");

        cancel.setText("Cancelar");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        accept.setText("Aceptar");
        accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(accept))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboxFonts, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(colorCorrect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(colorPartial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(colorIncorrect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelcorrect)
                                    .addComponent(labelincorrect)
                                    .addComponent(labelpartial)))
                            .addComponent(labelSample))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboxFonts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelSample)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorCorrect)
                    .addComponent(labelcorrect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorIncorrect)
                    .addComponent(labelincorrect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorPartial)
                    .addComponent(labelpartial))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(accept))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void colorCorrectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorCorrectActionPerformed
        corcolor = chooseColor();   
        labelcorrect.setForeground(corcolor);
    }//GEN-LAST:event_colorCorrectActionPerformed

    private void colorIncorrectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorIncorrectActionPerformed
        incolor = chooseColor(); 
        labelincorrect.setForeground(incolor);
    }//GEN-LAST:event_colorIncorrectActionPerformed

    private void colorPartialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorPartialActionPerformed
        parcolor = chooseColor();
        labelpartial.setForeground(parcolor);
    }//GEN-LAST:event_colorPartialActionPerformed

    private void cboxFontsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboxFontsItemStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED){
            labelSample.setFont(new Font(evt.getItem().toString(),Font.PLAIN,15));
        }
    }//GEN-LAST:event_cboxFontsItemStateChanged

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void acceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptActionPerformed
        Settings s = Main.getSettings();
        s.setCorrectColor(labelcorrect.getForeground());
        s.setIncorrectColor(labelincorrect.getForeground());
        s.setPartialColor(labelpartial.getForeground());
        s.setFont(labelSample.getFont().getFontName());
        SwingUtilities.updateComponentTreeUI(getParent());
        ConfigSave.save(s);
        dispose();
    }//GEN-LAST:event_acceptActionPerformed

    public Color chooseColor(){
        JColorChooser cchooser = new JColorChooser();
        int res = JOptionPane.showConfirmDialog(this, cchooser, "Selecciona un color", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if(res==JOptionPane.OK_OPTION){
            return cchooser.getColor();
        }
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OptionsGUI(null, false).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept;
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> cboxFonts;
    private javax.swing.JButton colorCorrect;
    private javax.swing.JButton colorIncorrect;
    private javax.swing.JButton colorPartial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelSample;
    private javax.swing.JLabel labelcorrect;
    private javax.swing.JLabel labelincorrect;
    private javax.swing.JLabel labelpartial;
    // End of variables declaration//GEN-END:variables

}
