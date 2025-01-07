package gui.config;

import java.awt.Color;
import java.awt.Font;
import java.util.Set;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;




public class Settings {
    private Color correctColor;
    private Color incorrectColor;
    private Color partialColor;
    
    private String font;
    
    
    //Configuración por defecto
    public Settings(){
        correctColor = Color.GREEN;
        incorrectColor = Color.RED;
        partialColor = Color.YELLOW;
        font = "Segoe UI";
    }
    

    /**
     * @return the correctColor
     */
    public Color getCorrectColor() {
        return correctColor;
    }

    /**
     * @param correctColor the correctColor to set
     */
    public void setCorrectColor(Color correctColor) {
        this.correctColor = correctColor;
    }

    /**
     * @return the incorrectColor
     */
    public Color getIncorrectColor() {
        return incorrectColor;
    }

    /**
     * @param incorrectColor the incorrectColor to set
     */
    public void setIncorrectColor(Color incorrectColor) {
        this.incorrectColor = incorrectColor;
    }

    /**
     * @return the partialColor
     */
    public Color getPartialColor() {
        return partialColor;
    }

    /**
     * @param partialColor the partialColor to set
     */
    public void setPartialColor(Color partialColor) {
        this.partialColor = partialColor;
    }

    /**
     * @return the font
     */
    public String getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(String font) {
        this.font = font;
                
        Set<Object> keys = UIManager.getDefaults().keySet();
        for(Object o : keys){       
            if(UIManager.get(o) instanceof FontUIResource){
                FontUIResource resource = (FontUIResource)UIManager.get(o);
                UIManager.put(o, new FontUIResource(font, resource.getStyle(), resource.getSize()));
            }
        }
        
    }
    
}
