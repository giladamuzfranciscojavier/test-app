package core.saveLoad.testProcessor;

import core.saveLoad.db.DBManager;
import core.test.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.Blob;

/**
 * Test export operations
 */
public class TestCreator {

    static Test test;

    /**
     * Class constructor
     * @param test the test to export
     */
    public TestCreator(Test test) {
        this.test = test;
    }

    //Operación principal para guardar el test
    private DOMSource createTest() {
        try {
            //Declaración del DOM
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            Document doc = dom.createDocument(null, "Test", null);

            //Se crea el elemento raíz y se insertan sus atributos
            Element root = doc.getDocumentElement();

            root.setAttribute("title", test.getTitle());
            root.setAttribute("correctScore", String.valueOf(test.getCorrectAnsScore()));
            root.setAttribute("incorrectScore", String.valueOf(test.getIncorrectAnsScore()));
            root.setAttribute("partialScore", String.valueOf(test.getPartialAnsScore()));     
            root.setAttribute("canCheckAnswer", String.valueOf(test.canCheckAnswer()));
            
            //Si hay puntuación de aprobado se incluye tambien , de lo contrario se omite
            if(test.hasMinScore()){
                root.setAttribute("hasMinScore","true");
                root.setAttribute("minScore", String.valueOf(test.getPassScore()));
            }
            
            else{
                root.setAttribute("hasMinScore","false");
            }        
            

            //Si hay límite de tiempo se incluyen también las horas y los minutos. De lo contrario se omiten
            if (test.hasTimeLimit()) {
                root.setAttribute("hasTimeLimit", "true");
                root.setAttribute("hours", String.valueOf(test.getHours()));
                root.setAttribute("mins", String.valueOf(test.getMins()));
            } else {
                root.setAttribute("hasTimeLimit", "false");
            }

            ArrayList<Question> questions = test.getQuestions();

            for (Question q : questions) {
                Element question = doc.createElement("question");
                root.appendChild(question);
                question.setAttribute("title", q.getTitle());
                question.setAttribute("multiAnswer", String.valueOf(q.isMultAnswer()));

                ArrayList<Answer> answers = q.getAnswers();

                for (Answer a : answers) {
                    Element answer = doc.createElement("answer");
                    question.appendChild(answer);
                    answer.setAttribute("text", a.getText());
                    answer.setAttribute("correct", String.valueOf(a.isCorrect()));
                }
            }

            return new DOMSource(doc);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Exports test as a local file
     * @param f the output file for the test
     * @return whether the operation is successful or not
     */
    public boolean saveAsFile(File f) {
        DOMSource src = createTest();

        try {
            StreamResult result = new StreamResult(f);
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            t.transform(src, result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    
    /**
     * Exports test to database
     * @param dbmanager the database connection
     * @param author the test author
     */
    public void saveToDB(DBManager dbmanager, String author){
        
        try {
            
            DOMSource src = createTest();
            
            Connection conn = dbmanager.getConnection(); 
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(bos);            
            Transformer t = TransformerFactory.newInstance().newTransformer();            
            t.transform(src, result);     
            
            Blob b = conn.createBlob();
            b.setBytes(1, bos.toByteArray());  
                        
            PreparedStatement ps = conn.prepareStatement("INSERT INTO test (testName, testAuthor, testFile) VALUES (?,?,?)");
                       
            ps.setString(1, test.getTitle());
            ps.setString(2, author);     
            ps.setBlob(3, b);
            
            ps.executeUpdate();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    /**
     * Exports test to database with anonymous author
     * @param dbmanager the database connection
     */
    public void saveToDB(DBManager dbmanager){
        saveToDB(dbmanager, "anon");
    }

    /**
     * Main method for testing purposes
     * @param args 
     */
    public static void main(String[] args) {

        Test test = new Test("prueba", false, 0, 0, false, 0, 1, 0, 0, true);
        Question question = new Question("pregunta");
        Answer answer = new Answer("si", true);
        question.addAnswer(answer);
        answer = new Answer("no", false);
        question.addAnswer(answer);
        test.addQuestion(question);

        TestCreator tc = new TestCreator(test);

        tc.saveAsFile(new File("prueba.xml"));

        //DBManager db = new DBManager();        
        //tc.saveToDB(db);
        
    }

}
