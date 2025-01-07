package gui.config;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class ConfigLoad {
    
    public static Settings load(){
        File f = new File("settings.xml");
        Settings s = new Settings();
        
        //Si no existe el fichero carga la configuración por defecto
        if(!f.exists()){
            return s;
        }        
        
        
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(f);
            
            Element colorcorrect = (Element)doc.getElementsByTagName("correct").item(0);
            Element colorincorrect = (Element)doc.getElementsByTagName("incorrect").item(0);
            Element colorpartial = (Element)doc.getElementsByTagName("partial").item(0);
            Element font = (Element)doc.getElementsByTagName("Font").item(0);
            
            s.setFont(font.getTextContent());
            s.setCorrectColor(new Color(Integer.parseInt(colorcorrect.getTextContent()))); 
            s.setIncorrectColor(new Color(Integer.parseInt(colorincorrect.getTextContent()))); 
            s.setPartialColor(new Color(Integer.parseInt(colorpartial.getTextContent()))); 
            
            return s;
            
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ConfigLoad.class.getName()).log(Level.SEVERE, null, ex);
            return new Settings();
        }
        
    }    
    
}
