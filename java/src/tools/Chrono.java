package tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.Timer;

//Clase cronómetro básica
public class Chrono extends Thread {

    int h;
    int m;
    int s;
    JLabel label;
    Timer t;

    public Chrono(JLabel label) {
        h = 0;
        m = 0;
        s = 0;
        this.label = label;
    }

    @Override
    public void run() {

        t = new Timer(1000, new ActionListener() {

            //Actualiza cada segundo
            @Override
            public void actionPerformed(ActionEvent e) {
                //Incrementa un segundo
                s++;
                //Al llegar al minuto se incrementa el contador de los mismos y se resetea el de segundos
                if (s > 59) {
                    m++;
                    s = 0;

                    //Se hace lo mismo con las horas
                    if (m > 59) {
                        h++;
                        m = 0;
                    }
                }

                //Se actualiza el label con el nuevo valor
                String time = (h > 9 ? "" + h : ("0" + h)) + ":" + (m > 9 ? "" + m : ("0" + m)) + ":" + (s > 9 ? "" + s : ("0" + s));
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
