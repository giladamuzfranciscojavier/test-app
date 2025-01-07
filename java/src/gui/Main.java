package gui;

import com.formdev.flatlaf.FlatLightLaf;
import gui.config.ConfigLoad;
import gui.config.Settings;
import core.saveLoad.db.DBManager;
import core.saveLoad.testProcessor.TestLoader;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.HashMap;

public class Main extends javax.swing.JFrame {

    private static boolean dbConnected;
    private static boolean isAdmin;
    
    private static DBManager dbmanager;
    
    private static Settings settings;
    
    
    public Main(String[] args) {
        
        //Carga las opciones de configuración
        settings = ConfigLoad.load();        
        initComponents();
        
        //Inicia en modo normal
        setNormalUser();
        
        setLocationRelativeTo(null);
        
        //Si recibe un fichero válido como argumento lo abre directamente
        if(args.length>0){
            try{
                new SolverGUI(this, true, new TestLoader().loadFromFile(new File(args[0]))).setVisible(true);
                return;
            }     
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Solver = new javax.swing.JButton();
        Creator = new javax.swing.JButton();
        labelConnStatus = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Test App");
        setResizable(false);

        Solver.setText("Resolver");
        Solver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolverActionPerformed(evt);
            }
        });

        Creator.setText("Crear");
        Creator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreatorActionPerformed(evt);
            }
        });

        labelConnStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelConnStatus.setText("Estado de Conexión: Desconectado ");

        jButton1.setText("Conectar a Base de Datos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("Opciones");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Admin Panel");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Creator, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(Solver, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelConnStatus)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelConnStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Creator)
                    .addComponent(Solver))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CreatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreatorActionPerformed
        new CreatorGUI(this, true).setVisible(true);
        //this.setVisible(false);
    }//GEN-LAST:event_CreatorActionPerformed

    private void SolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolverActionPerformed
        
        int option;
        
        //Si hay conexión con la base de datos se pregunta como se quiere guardar el test. De lo contrario se selecciona automáticamente el guardado local
        if(Main.isDbConnected()){
            option = JOptionPane.showOptionDialog(rootPane, "Selecciona de donde quieres cargar el test", "Cargar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Cancelar", "Base de Datos", "Local"}, null);
        }
        
        else{
            option=2;
        }
        
        switch(option){
            
            case 1:
                
                HashMap<String, Integer> testList = TestLoader.getTestsFromDB(dbmanager);
                
                if(testList.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "No hay tests en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String selection = (String)JOptionPane.showInputDialog(this, "Selecciona el test", "Cargar", JOptionPane.PLAIN_MESSAGE, null, testList.keySet().toArray(), null);
                if(selection==null){
                    return;
                }
                int id = testList.get(selection);
                new SolverGUI(this, true, new TestLoader().loadFromDB(dbmanager, id)).setVisible(true);
                //this.setVisible(false);            
                break;

            
            case 2:
                JFileChooser loadFile = new JFileChooser();
                loadFile.setFileFilter(new FileNameExtensionFilter("xml", "xml"));
                 if(loadFile.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){            
                    new SolverGUI(this, true, new TestLoader().loadFromFile(loadFile.getSelectedFile())).setVisible(true);
                    //this.setVisible(false); 
                }  

                //new SolverGUI(new TestLoader().loadFromFile(new File("prueba.xml"))).setVisible(true);
                break;
                
            default:
                break;
        }        
    }//GEN-LAST:event_SolverActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new DBMenu(this, true).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        OptionsGUI options = new OptionsGUI(this, true);
        options.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        HashMap<String, Integer> testList = TestLoader.getTestsFromDB(dbmanager);   
        
        if(testList.isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "No hay tests en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String selection = (String)JOptionPane.showInputDialog(this, "Selecciona el test a eliminar", "Eliminar", JOptionPane.PLAIN_MESSAGE, null, testList.keySet().toArray(), null);
        if(selection==null){
            return;
        }
        int id = testList.get(selection);
        dbmanager.deleteFromDB(id);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SolverGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main(args).setVisible(true);
            }
        });
    }
    
    //Actualiza el estado de la interfaz a modo admin (NOTA: sin estar conectado un usuario admin no sirve de nada)
    protected void setAdmin(){
        isAdmin=true;
        jMenu1.setText("ADMIN MODE");
        jMenuItem2.setVisible(true);
    }
    
    protected void setNormalUser(){
        isAdmin=false;
        jMenu1.setText("Archivo");
        jMenu1.setForeground(jMenuItem1.getForeground());
        jMenuItem2.setVisible(false);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Creator;
    private javax.swing.JButton Solver;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JLabel labelConnStatus;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the dbConnected
     */
    public static boolean isDbConnected() {
        return dbConnected;
    }

    /**
     * @param aDbConnected the dbConnected to set
     */
    public static void setDbConnected(boolean aDbConnected) {
        dbConnected = aDbConnected;        
    }

    /**
     * @return the dbmanager
     */
    public static DBManager getDbmanager() {
        return dbmanager;
    }

    /**
     * @param adbmanager the dbmanager to set
     */
    public static void setDbmanager(DBManager adbmanager) {
        dbmanager = adbmanager;
    }

    /**
     * @return the labelConnStatus
     */
    public javax.swing.JLabel getLabelConnStatus() {
        return labelConnStatus;
    }

    /**
     * @return the settings
     */
    public static Settings getSettings() {
        return settings;
    }
}
