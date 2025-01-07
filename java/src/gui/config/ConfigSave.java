package gui.config;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;




public class ConfigSave {
    public static void save(Settings settings){
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            Document doc = dom.createDocument(null, "Settings", null);
            
            Element root = doc.getDocumentElement();
            
            Element font = doc.createElement("Font");
            font.setTextContent(settings.getFont());
            root.appendChild(font);
            
            Element colors = doc.createElement("Colors");
            root.appendChild(colors);
            
            Element correct = doc.createElement("correct");
            correct.setTextContent(String.valueOf(settings.getCorrectColor().getRGB()));
            colors.appendChild(correct);
            
            Element incorrect = doc.createElement("incorrect");
            incorrect.setTextContent(String.valueOf(settings.getIncorrectColor().getRGB()));
            colors.appendChild(incorrect);
            
            Element partial = doc.createElement("partial");
            partial.setTextContent(String.valueOf(settings.getPartialColor().getRGB()));
            colors.appendChild(partial);
            
            File f = new File("settings.xml");
            
            StreamResult result = new StreamResult(f);
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            t.transform(new DOMSource(doc), result);
            
            
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(ConfigSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
