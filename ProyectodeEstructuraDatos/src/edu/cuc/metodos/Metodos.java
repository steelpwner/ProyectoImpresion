package edu.cuc.metodos;


import edu.cuc.ventanas.Dialogo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.Timer;
/**
 *
 * @author dmolina
 */
public class Metodos {

    public static Cola cargarDatos(String ruta) {
        Cola<String> colaNueva = new Cola();
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            try {
                while (lector.ready()) {
                    colaNueva.enqueue(lector.readLine());
                }
            } catch (IOException ex) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colaNueva;
    }
  
      public static void colaALista(Cola<String> cola, JList lista) {
    Iterator<String> iterador=cola.iterator();
    DefaultListModel<String> modeloLista = new DefaultListModel();
        while (iterador.hasNext()) {
            String next = iterador.next();
             String nombreArchivo = "";
            int j = 0;
            while (next.charAt(j) != ',') {
                nombreArchivo += next.charAt(j);
                j++;
            }
            modeloLista.addElement(nombreArchivo);
        }
        lista.setModel(modeloLista);
    }
     
       public static void iniciarSimulacion(String next,Cola impresiones, Dialogo dialogo) {

            String nombreArchivo = sacarNombreArchivo(next);
            dialogo.setNombreLabel(nombreArchivo);
            Timer temporizador = new Timer(1000, new ActionListener() {
                long tiempo = sacarSegundosArchivo(next) * 1000;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tiempo > 0) {
                        dialogo.setLblEspera("Espere " + tiempo / 1000 + " segundos");
                        tiempo -= 1000;
                    } else {
                        dialogo.setVisible(false);
                    }
                }
            });
            boolean continuar = true;
            dialogo.setLblEspera("Espere...");
            dialogo.setVisible(true);
            while (continuar) {
                temporizador.start();
                if (!dialogo.isVisible()) {
                    temporizador.stop();
                    continuar = false;
                }
            }
    }
    
    public static String sacarNombreArchivo(String next) {
        int i=0;
        String nombreArchivo = "";
        while (next.charAt(i) != ',') {
            nombreArchivo += next.charAt(i);
            i++;
        }
        i++;
        return nombreArchivo;
    }
    
    public static long sacarSegundosArchivo(String next) {
        int i=0;
        while (next.charAt(i) != ',') {
            i++;
        }
        i++;
        String cadenaTiempo = "";
        while (i < next.length()) {
            cadenaTiempo += next.charAt(i);
            i++;
        }
        return Long.parseLong(cadenaTiempo);
    }
   
   public static void actualizadorLista (JList lista, Cola<String> impresiones) {
   DefaultListModel modeloLista= new DefaultListModel();
   impresiones.dequeue();
   Iterator<String> iterador= impresiones.iterator();
    int i=0;
       while (iterador.hasNext()) {
           String next = iterador.next();
            String nombreArchivo = "";
            int j = 0;
            while (next.charAt(j) != ',') {
                nombreArchivo += next.charAt(j);
                j++;
            }
            modeloLista.addElement(nombreArchivo);
       }
   lista.setModel(modeloLista);
   }
   
    public static void main(String[] args) {
 
    }

}
