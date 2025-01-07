package core.saveLoad.testProcessor;

import core.saveLoad.db.DBManager;
import core.test.*;
import java.io.File;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test import operations
 */
public class TestLoader {

    //Carga el test a partir de un documento DOM
    private Test load(Document doc) {

        Test test = new Test();

        //Mapea los atributos del elemento raíz al objeto test
        NamedNodeMap attr = doc.getDocumentElement().getAttributes();

        test.setTitle(attr.getNamedItem("title").getTextContent());
        test.setCorrectAnsScore(getDoubleAttr(attr, "correctScore"));
        test.setIncorrectAnsScore(getDoubleAttr(attr, "incorrectScore"));
        test.setPartialAnsScore(getDoubleAttr(attr, "partialScore"));
        test.setHasMinScore(getBoolAttr(attr, "hasMinScore"));
        test.setHasTimeLimit(getBoolAttr(attr, "hasTimeLimit"));
        test.setCanCheckAnswer(getBoolAttr(attr, "canCheckAnswer"));

        if (test.hasMinScore()) {
            test.setMinScore(getDoubleAttr(attr, "minScore"));
        }

        if (test.hasTimeLimit()) {
            test.setHours(Integer.parseInt(attr.getNamedItem("hours").getTextContent()));
            test.setMins(Integer.parseInt(attr.getNamedItem("mins").getTextContent()));
        }

        NodeList questions = doc.getElementsByTagName("question");

        for (int i = 0; i < questions.getLength(); i++) {
            Question q = new Question();
            test.addQuestion(q);

            Element question = (Element) questions.item(i);
            q.setTitle(question.getAttribute("title"));
            q.setMultiAnswer(Boolean.parseBoolean(question.getAttribute("multiAnswer")));

            NodeList answers = question.getElementsByTagName("answer");

            for (int j = 0; j < answers.getLength(); j++) {
                Answer a = new Answer();
                q.addAnswer(a);

                Element answer = (Element) answers.item(j);

                a.setText(answer.getAttribute("text"));
                a.setCorrect(Boolean.parseBoolean(answer.getAttribute("correct")));
            }

        }
        return test;
    }

    /**
     * Loads a test from a local file
     *
     * @param f the file from which the test will be loaded
     * @return the loaded test
     */
    public Test loadFromFile(File f) {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(f);
            return load(doc);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads a test from a database
     *
     * @param dbmanager the database connection manager
     * @param id the test id
     * @return the loaded test
     */
    public Test loadFromDB(DBManager dbmanager, int id) {

        try {

            Connection conn = dbmanager.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT testFile FROM test WHERE testID = ?");
            ps.setInt(1, id);

            ResultSet rset = ps.executeQuery();

            Blob b = null;

            if (rset.next()) {
                b = rset.getBlob(1);
            } else {
                return null;
            }

            InputStream in = b.getBinaryStream();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);

            return load(doc);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the tests available from the database
     * @param dbmanager the database connection manager
     * @return a map containing the test names and their respective ids
     */
    public static HashMap<String,Integer> getTestsFromDB(DBManager dbmanager) {
        HashMap<String, Integer> testList = new HashMap<>();
        try {
            Connection conn = dbmanager.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT testName, testID from test");
            ResultSet rs = ps.executeQuery();            
            
            
            while (rs.next()) {
                testList.put(rs.getString("testName"), rs.getInt("testID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return testList;
    }

    //Obtienen atributos double y booleano
    private static double getDoubleAttr(NamedNodeMap attr, String name) {
        return Double.parseDouble(attr.getNamedItem(name).getTextContent());
    }

    private static boolean getBoolAttr(NamedNodeMap attr, String name) {
        return Boolean.parseBoolean(attr.getNamedItem(name).getTextContent());
    }

    /**
     * Main method for testing purposes
     * @param args 
     */
    public static void main(String[] args) {
        Test test = new TestLoader().loadFromFile(new File("prueba.xml"));
        //Test test = new TestLoader().loadFromDB(new DBManager(),1);
        System.out.println(test);
    }

}
