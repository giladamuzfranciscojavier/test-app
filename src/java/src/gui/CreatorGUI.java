package gui;

import core.saveLoad.testProcessor.TestCreator;
import core.test.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CreatorGUI extends javax.swing.JDialog {

    ArrayList<CreatorAnswer> answers;

    //Lista ordenada para gestionar el índice de preguntas
    LinkedList<Question> questions;

    ButtonGroup buttonGroupAnswers;

    public CreatorGUI(javax.swing.JFrame parent, boolean modal) {
        super(parent,modal);
        initComponents();
        this.setLocationRelativeTo(null);
        buttonGroupAnswers = new ButtonGroup();

        //Ajuste de velocidad de scroll
        jScrollPane.getVerticalScrollBar().setUnitIncrement(30);

        questions = new LinkedList<>();
        answers = new ArrayList<>();

        addQuestion();

        //dynamicAnswerAddTest();    
                
        addNavigationListeners(this.getRootPane());
        
        
        //Listener para filtrar caracteres que no sean números o si ya hay 2 cifras
        KeyListener klistener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                JFormattedTextField src = (JFormattedTextField) e.getSource();
                if (c < '0' || c > '9' || src.getText().length() > 1) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        //Versión sin filtro por longitud
        KeyListener klistener2 = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c < '0' || c > '9') {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        //Listener para mantener el valor por debajo de 59 (para que funcione el valor máximo del componente está en 99), 
        //puesto que de lo contrario no lo procesaría ni saltaría evento
        ChangeListener clistener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SpinnerModel model = (SpinnerModel) e.getSource();
                if ((int) model.getValue() > 59) {
                    model.setValue(59);
                }
            }

        };

        ((JSpinner.DefaultEditor) spinnerHours.getEditor()).getTextField().addKeyListener(klistener);
        ((JSpinner.DefaultEditor) spinnerMins.getEditor()).getTextField().addKeyListener(klistener);

        ((JSpinner.DefaultEditor) spinnerCorrect.getEditor()).getTextField().addKeyListener(klistener2);
        ((JSpinner.DefaultEditor) spinnerIncorrect.getEditor()).getTextField().addKeyListener(klistener2);
        ((JSpinner.DefaultEditor) spinnerPartial.getEditor()).getTextField().addKeyListener(klistener2);
        ((JSpinner.DefaultEditor) spinnerMinScore.getEditor()).getTextField().addKeyListener(klistener2);

        spinnerMins.getModel().addChangeListener(clistener);

        refresh();
    }

    //Prueba de la creación dinámica de componentes
    private void dynamicAnswerAddTest() {
        for (int i = 0; i < 100; i++) {
            CreatorAnswer cans = new CreatorAnswer(true);
            cans.getAnswer().setText("instancia " + (i + 1));
            cans.getTextArea().setText(cans.getAnswer().getText());
            cans.setSize(cans.getPreferredSize());
            cans.setLocation(0, 100 * answers.size());
            answers.add(cans);
        }
    }
    
    
    //Método para la creación dinámica de respuestas. Versión con respuesta ya creada
    public void addAnswer(Answer ans) {
        saveQuestion(questions.get(comboxIndex.getSelectedIndex()));
        CreatorAnswer answer = new CreatorAnswer(rbuttonMultAnswer.isSelected(), ans);
        answer.setSize(answer.getPreferredSize());
        answer.setLocation(0, 100 * answers.size());
        buttonGroupAnswers.add(answer.getRadioButton());
        answers.add(answer);

        refresh();
    }

    //Método para la creación dinámica de respuestas. Versión que crea una nueva respuesta
    public void addAnswer() {
        addAnswer(new Answer());
    }

    //Añade una pregunta y la coloca al final
    public void addQuestion() {
        questions.add(new Question());
        comboxIndex.addItem(String.valueOf(comboxIndex.getModel().getSize() + 1));
        comboxIndex.setSelectedIndex(comboxIndex.getModel().getSize() - 1);
    }

    //Refresca el contenedor de respuestas para que se visualicen los cambios
    public void refresh() {
        answerContainer.removeAll();
        for (CreatorAnswer ca : answers) {
            answerContainer.add(ca);
            ca.getTextArea().setText(ca.getAnswer().getText());
            ca.getActiveButton().setSelected(ca.getAnswer().isCorrect());
        }
        answerContainer.setPreferredSize(new Dimension(answerContainer.getWidth(), 100 * answers.size()));
        answerContainer.revalidate();
        answerContainer.repaint();
    }

    //Carga una pregunta
    public void loadQuestion(Question q) {

        qTitle.setText(q.getTitle());

        rbuttonOnlyAnswer.setSelected(!q.isMultAnswer());
        rbuttonMultAnswer.setSelected(q.isMultAnswer());

        answers = new ArrayList<>();

        for (Answer answer : q.getAnswers()) {
            addAnswer(answer);
        }

        //Resetea las respuestas para evitar duplicados
        q.resetAnswers();

        if (answers.isEmpty()) {
            addAnswer();
        }

    }

    //Guarda la pregunta
    public void saveQuestion(Question q) {
        q.setTitle(qTitle.getText());

        q.resetAnswers();

        for (CreatorAnswer ca : answers) {
            q.addAnswer(ca.saveAnswer());
        }

    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupAnswerType = new javax.swing.ButtonGroup();
        jScrollPane = new javax.swing.JScrollPane();
        answerContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        buttonNext = new javax.swing.JButton();
        buttonPrevious = new javax.swing.JButton();
        comboxIndex = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        spinnerCorrect = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        spinnerIncorrect = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        spinnerPartial = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        buttonAddQuestion = new javax.swing.JButton();
        spinnerHours = new javax.swing.JSpinner();
        cboxTimeLimit = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        spinnerMins = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        cboxCheckAnswer = new javax.swing.JCheckBox();
        buttonFinish = new javax.swing.JButton();
        cboxminScore = new javax.swing.JCheckBox();
        spinnerMinScore = new javax.swing.JSpinner();
        cleanQuestions = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        rbuttonOnlyAnswer = new javax.swing.JRadioButton();
        rbuttonMultAnswer = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        qTitle = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        buttonAddAnswer = new javax.swing.JButton();
        cleanAnswers = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear Test");
        setResizable(false);

        jScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answerContainer.setPreferredSize(new java.awt.Dimension(404, 0));

        javax.swing.GroupLayout answerContainerLayout = new javax.swing.GroupLayout(answerContainer);
        answerContainer.setLayout(answerContainerLayout);
        answerContainerLayout.setHorizontalGroup(
            answerContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );
        answerContainerLayout.setVerticalGroup(
            answerContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        jScrollPane.setViewportView(answerContainer);

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

        spinnerCorrect.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        jLabel2.setText("Correcta");

        spinnerIncorrect.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        jLabel3.setText("Incorrecta");

        spinnerPartial.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        jLabel4.setText("Parcial");

        buttonAddQuestion.setText("Nueva Pregunta");
        buttonAddQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddQuestionActionPerformed(evt);
            }
        });

        spinnerHours.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));
        spinnerHours.setEnabled(false);

        cboxTimeLimit.setText("Tiempo máximo");
        cboxTimeLimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxTimeLimitActionPerformed(evt);
            }
        });

        jLabel5.setText("Horas");

        spinnerMins.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));
        spinnerMins.setEnabled(false);

        jLabel6.setText("Minutos");

        cboxCheckAnswer.setText("Permitir comprobar respuesta");

        buttonFinish.setText("Finalizar");
        buttonFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFinishActionPerformed(evt);
            }
        });

        cboxminScore.setText("Puntuación mínima de aprobado");
        cboxminScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxminScoreActionPerformed(evt);
            }
        });

        spinnerMinScore.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spinnerMinScore.setEnabled(false);

        cleanQuestions.setText("Limpiar Preguntas Vacías");
        cleanQuestions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanQuestionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(comboxIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addComponent(cboxCheckAnswer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(spinnerHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(spinnerMins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cboxTimeLimit)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cleanQuestions)
                        .addGap(18, 18, 18)
                        .addComponent(buttonAddQuestion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonFinish))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerCorrect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerIncorrect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerPartial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spinnerMinScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboxminScore)
                .addGap(58, 58, 58))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonNext)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(buttonPrevious)
                                .addComponent(comboxIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerMins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxCheckAnswer)
                    .addComponent(cboxTimeLimit))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboxminScore)
                    .addComponent(spinnerMinScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinnerIncorrect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinnerPartial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinnerCorrect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonFinish)
                    .addComponent(buttonAddQuestion)
                    .addComponent(cleanQuestions))
                .addContainerGap())
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(0, 60));

        buttonGroupAnswerType.add(rbuttonOnlyAnswer);
        rbuttonOnlyAnswer.setText("Respuesta Única");
        rbuttonOnlyAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbuttonOnlyAnswerActionPerformed(evt);
            }
        });

        buttonGroupAnswerType.add(rbuttonMultAnswer);
        rbuttonMultAnswer.setSelected(true);
        rbuttonMultAnswer.setText("Respuesta Múltiple");
        rbuttonMultAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbuttonMultAnswerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbuttonOnlyAnswer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbuttonMultAnswer)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbuttonMultAnswer)
                    .addComponent(rbuttonOnlyAnswer))
                .addContainerGap())
        );

        qTitle.setColumns(20);
        qTitle.setRows(1);
        jScrollPane1.setViewportView(qTitle);

        jLabel7.setText("Enunciado");

        buttonAddAnswer.setText("Nueva Respuesta");
        buttonAddAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddAnswerActionPerformed(evt);
            }
        });

        cleanAnswers.setText("Limpiar Respuestas Vacías");
        cleanAnswers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanAnswersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cleanAnswers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonAddAnswer))
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAddAnswer)
                    .addComponent(cleanAnswers))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFinishActionPerformed

        if (cboxTimeLimit.isSelected()
                && Integer.parseInt(spinnerHours.getModel().getValue().toString()) == 0
                && Integer.parseInt(spinnerMins.getModel().getValue().toString()) == 0) {
            JOptionPane.showMessageDialog(rootPane, "El tiempo límite indicado es 0. Aumenta el tiempo o desmarca la opción de tiempo límite", "ERROR", JOptionPane.WARNING_MESSAGE);
            return;
        }

        cleanQuestions();

        if (questions.size() < 1) {
            JOptionPane.showMessageDialog(rootPane, "El test está vacío", "ERROR", JOptionPane.WARNING_MESSAGE);
            return;
        }

        saveQuestion(questions.get(0));
        Test test = new Test();

        for (Question q : questions) {
            test.addQuestion(q);
        }

        test.setHasTimeLimit(cboxTimeLimit.isSelected());
        test.setHasMinScore(cboxminScore.isSelected());
        test.setMinScore(Double.parseDouble(spinnerMinScore.getModel().getValue().toString()));
        test.setCanCheckAnswer(cboxCheckAnswer.isSelected());
        test.setHours(Integer.parseInt(spinnerHours.getModel().getValue().toString()));
        test.setMins(Integer.parseInt(spinnerMins.getModel().getValue().toString()));
        test.setCorrectAnsScore(Double.parseDouble(spinnerCorrect.getModel().getValue().toString()));
        test.setIncorrectAnsScore(Double.parseDouble(spinnerIncorrect.getModel().getValue().toString()));
        test.setPartialAnsScore(Double.parseDouble(spinnerPartial.getModel().getValue().toString()));

        String title = JOptionPane.showInputDialog("Introduce el nombre del test");
        if (title == null || title.isBlank()) {
            JOptionPane.showMessageDialog(rootPane, "Debes introducir un nombre");
            return;
        } else {
            test.setTitle(title);
        }

        int option;

        //Si hay conexión con la base de datos se pregunta como se quiere guardar el test. De lo contrario se selecciona automáticamente el guardado local
        if (Main.isDbConnected()) {
            option = JOptionPane.showOptionDialog(rootPane, "Selecciona como quieres guardar el test", "Guardar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Cancelar", "Base de Datos", "Local"}, null);
        } else {
            option = 2;
        }

        switch (option) {

            case 1:
                new TestCreator(test).saveToDB(Main.getDbmanager(), Main.getDbmanager().getUser());
                this.dispose();
                break;

            case 2:
                JFileChooser saveFile = new JFileChooser();
                saveFile.setSelectedFile(new File(title+".xml"));
                saveFile.setFileFilter(new FileNameExtensionFilter("xml", "xml"));

                if (saveFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File f = saveFile.getSelectedFile();

                    //Se añade la extensión si aún no la tiene
                    if (f.length() < 5 || !f.getName().substring(f.getName().length() - 4).equals(".xml")) {
                        System.out.println(f.getName().substring(f.getName().length() - 4));
                        f = new File(f + ".xml");
                    }
                    if (new TestCreator(test).saveAsFile(f)) {
                        JOptionPane.showMessageDialog(this, "Test guardado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }
                //new TestCreator(test).saveAsFile(new File("prueba.xml"));
                break;

            default:
                return;
        }
    }//GEN-LAST:event_buttonFinishActionPerformed

    private void buttonAddQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddQuestionActionPerformed
        
        //Guarda la pregunta para que la comprobación refleje su estado actual
        saveQuestion(questions.get(comboxIndex.getSelectedIndex()));
        
        //Comprueba si hay preguntas vacías
        for(int i=0;i<questions.size();i++){
            Question q = questions.get(i);
                        
            //Si una pregunta no tiene título comprueba las respuestas de la misma
            if(q.getTitle().isBlank()){
                boolean empty=true;
                
                for(Answer a : q.getAnswers()){
                    //Si encuentra una respuesta que no esté vacía deja de buscar
                    if(!a.getText().isBlank()){
                        empty=false;
                        break;
                    }
                }
                //Si todas las respuestas están vacías carga la pregunta y termina la ejecución del método
                if(empty){
                    comboxIndex.setSelectedIndex(i);
                    return;
                }                
            }
        }
        
        addQuestion();
    }//GEN-LAST:event_buttonAddQuestionActionPerformed

    private void buttonAddAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddAnswerActionPerformed
        addAnswer();
    }//GEN-LAST:event_buttonAddAnswerActionPerformed

    private void comboxIndexItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboxIndexItemStateChanged
        try {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                loadQuestion(questions.get(comboxIndex.getSelectedIndex()));
                if (comboxIndex.getSelectedIndex() > 0) {
                    buttonPrevious.setEnabled(true);
                } else {
                    buttonPrevious.setEnabled(false);
                }

                if (comboxIndex.getSelectedIndex() < comboxIndex.getModel().getSize() - 1) {
                    buttonNext.setEnabled(true);
                } else {
                    buttonNext.setEnabled(false);
                }

            } else if (evt.getStateChange() == ItemEvent.DESELECTED && Integer.parseInt(evt.getItem().toString()) > -1) {
                saveQuestion(questions.get(Integer.parseInt(evt.getItem().toString()) - 1));
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_comboxIndexItemStateChanged

    private void rbuttonMultAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbuttonMultAnswerActionPerformed
        questions.get(comboxIndex.getSelectedIndex()).setMultiAnswer(true);
        for (CreatorAnswer ca : answers) {            
            ca.showCheckbox();
        }
    }//GEN-LAST:event_rbuttonMultAnswerActionPerformed

    private void buttonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreviousActionPerformed
        if (comboxIndex.getSelectedIndex() - 1 < 1) {
            buttonPrevious.setEnabled(false);

            if (comboxIndex.getModel().getSize() > 1) {
                buttonNext.setEnabled(true);
            }
        }
        comboxIndex.setSelectedIndex(comboxIndex.getSelectedIndex() - 1);
    }//GEN-LAST:event_buttonPreviousActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        if (comboxIndex.getSelectedIndex() + 1 == comboxIndex.getModel().getSize() - 1) {
            buttonNext.setEnabled(false);

            if (comboxIndex.getModel().getSize() > 1) {
                buttonPrevious.setEnabled(true);
            }
        }
        comboxIndex.setSelectedIndex(comboxIndex.getSelectedIndex() + 1);
    }//GEN-LAST:event_buttonNextActionPerformed

    private void rbuttonOnlyAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbuttonOnlyAnswerActionPerformed
        questions.get(comboxIndex.getSelectedIndex()).setMultiAnswer(false);
        for (CreatorAnswer ca : answers) {
            ca.showRadioButton();            
        }
    }//GEN-LAST:event_rbuttonOnlyAnswerActionPerformed

    private void cboxTimeLimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxTimeLimitActionPerformed
        if (cboxTimeLimit.isSelected()) {
            spinnerHours.setEnabled(true);
            spinnerMins.setEnabled(true);
        } else {
            spinnerHours.setEnabled(false);
            spinnerMins.setEnabled(false);
        }
    }//GEN-LAST:event_cboxTimeLimitActionPerformed

    private void cleanQuestionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanQuestionsActionPerformed

        cleanQuestions.setEnabled(false);
        cleanQuestions();
        cleanQuestions.setEnabled(true);
    }//GEN-LAST:event_cleanQuestionsActionPerformed

    //Método para limpiar respuestas vacías de una pregunta
    private void cleanAnswersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanAnswersActionPerformed

        //Guarda la pregunta para que tenga en cuenta los valores actuales
        saveQuestion(questions.get(comboxIndex.getSelectedIndex()));

        //Itera sobre las preguntas y borra aquellas con texto en blanco
        Iterator<CreatorAnswer> it = answers.iterator();
        while (it.hasNext()) {
            if (it.next().getAnswer().getText().isBlank()) {
                it.remove();
            }
        }


        if (answers.isEmpty()) {
            addAnswer();
        } 
        
        //Vuelve a guardar la pregunta para que procese la limpieza
        saveQuestion(questions.get(comboxIndex.getSelectedIndex()));
        
        //Carga la pregunta para que se reflejen los cambios en la interfaz
        loadQuestion(questions.get(comboxIndex.getSelectedIndex()));
        
    }//GEN-LAST:event_cleanAnswersActionPerformed

    private void cboxminScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxminScoreActionPerformed
        if(cboxminScore.isSelected()){
            spinnerMinScore.setEnabled(true);
        }
        else{
            spinnerMinScore.setEnabled(false);
        }
    }//GEN-LAST:event_cboxminScoreActionPerformed

    //Método para limpiar las preguntas vacías
    public void cleanQuestions() {
        //Guarda la pregunta para que tenga en cuenta los valores actuales
        saveQuestion(questions.get(comboxIndex.getSelectedIndex()));

        Iterator<Question> it = questions.iterator();
        //Crea una variable para almacenar las preguntas sobre las que se itera
        Question temp;
        while (it.hasNext()) {
            //Si el enunciado está vacío sigue con la comprobación
            if ((temp = it.next()).getTitle().isBlank()) {
                //Itera sobre las respuestas de la pregunta
                Iterator<Answer> it2 = temp.getAnswers().iterator();
                while (it2.hasNext()) {
                    //Limpia las respuestas vacías
                    if (it2.next().getText().isBlank()) {
                        it2.remove();
                    }
                }
                //Si después de limpiar no quedan respuestas se borra la pregunta
                if (temp.getAnswers().isEmpty()) {
                    it.remove();
                }
            }
        }
        populateComboxIndex();
        refresh();
    }

    //Actualiza el contenido del combo box de las preguntas
    public void populateComboxIndex() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (int i = 0; i < questions.size(); i++) {
            model.addElement(i + 1);
        }

        comboxIndex.setModel(model);

        if (model.getSize() < 1) {
            addQuestion();
        }
                
        //Carga la primera pregunta
        loadQuestion(questions.get(0));
        
        //La única forma fiable de gestionar los botones de navegación es disparar un evento "simulando" una selección
        
        //Al no haber un evento de deselección es necesario guardar la pregunta manualmente para evitar que se sobrescriban las respuestas
        saveQuestion(questions.get(0));
        
        //Dispara el evento
        comboxIndexItemStateChanged(new ItemEvent(comboxIndex, 0, ItemEvent.ITEM_FIRST, ItemEvent.SELECTED));
    }
    
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
    private javax.swing.JButton buttonAddAnswer;
    private javax.swing.JButton buttonAddQuestion;
    private javax.swing.JButton buttonFinish;
    private javax.swing.ButtonGroup buttonGroupAnswerType;
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonPrevious;
    private javax.swing.JCheckBox cboxCheckAnswer;
    private javax.swing.JCheckBox cboxTimeLimit;
    private javax.swing.JCheckBox cboxminScore;
    private javax.swing.JToggleButton cleanAnswers;
    private javax.swing.JButton cleanQuestions;
    private javax.swing.JComboBox<String> comboxIndex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea qTitle;
    private javax.swing.JRadioButton rbuttonMultAnswer;
    private javax.swing.JRadioButton rbuttonOnlyAnswer;
    private javax.swing.JSpinner spinnerCorrect;
    private javax.swing.JSpinner spinnerHours;
    private javax.swing.JSpinner spinnerIncorrect;
    private javax.swing.JSpinner spinnerMinScore;
    private javax.swing.JSpinner spinnerMins;
    private javax.swing.JSpinner spinnerPartial;
    // End of variables declaration//GEN-END:variables
}
