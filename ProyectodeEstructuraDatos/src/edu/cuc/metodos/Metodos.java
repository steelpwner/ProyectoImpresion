package edu.cuc.metodos;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
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
