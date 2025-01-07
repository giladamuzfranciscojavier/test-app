package tools;

import gui.SolverGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;


//Clase temporizador
public class Countdown extends Thread {

    int h;
    int m;
    int s;
    JLabel label;
    SolverGUI gui;
    Timer t;

    public Countdown(JLabel label, int h, int m, SolverGUI gui){
        this.h=h;
        this.m=m;
        s=0;
        this.label=label;
        this.gui=gui;
        
        label.setText((h>9?""+h:("0"+h)) + ":" + (m>9?""+m:("0"+m)) + ":" + (s>9?""+s:("0"+s)));
    }
    
    
    @Override
    public void run() {
        
        t = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                s--;
                if(s<0){
                    m--;
                    if(m<0){
                        h--;
                        if(h<0){
                            gui.finish();
                            return;
                        }
                        m=59;
                    }            
                    s=59;
                }
                String time = (h>9?""+h:("0"+h)) + ":" + (m>9?""+m:("0"+m)) + ":" + (s>9?""+s:("0"+s));                
                label.setText(time);
            }
        });
        
        t.start();        
    
    } 
    
    @Override
    public void interrupt(){
        t.stop();
        super.interrupt();
    }
    
}
