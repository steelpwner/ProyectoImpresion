package edu.cuc.clases;


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

    /**
     * Este metodo recibe una ruta y carga a una cola los datos que estén en el archivo específicado
     * @param ruta La ruta de la que viene el archivo
     * @return La cola con los datos ya cargados
     */
    public static Cola cargarDatos(String ruta) {
        Cola<String> colaNueva = new Cola(); //Creación de la cola que será devuelta
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta)); //Creación del lector
            try {
                while (lector.ready()) { //Se verifica que el lector esté listo (Que hayan más lineas que leer)
                    colaNueva.enqueue(lector.readLine());  //Se mete en la cola la siguiente linea del archivo
                }
            } catch (IOException ex) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colaNueva;
    }
  
    /**
     * Este método recibe una Cola y una JList, la cola es usada para llenar la JList en la ventana
     * @param cola La cola que se va a usar para pasar a la lista
     * @param lista La lista que va a ser modificada con un DefaultListModel
     */
    public static void colaALista(Cola<String> cola, JList lista) {
    Iterator<String> iterador=cola.iterator(); //Se crea el iterador que servirá para recorrer la cola
    DefaultListModel<String> modeloLista = new DefaultListModel();  //Se crea el modelo que sirve para llenar la lista
        while (iterador.hasNext()) { //Se hará hasta que no hayan más elementos
            String next = iterador.next(); //Se va avanzando de elemento en elemento
            String nombreArchivo = sacarNombreArchivo(next); //Se obtiene el nombre del archivo de la cadena
            modeloLista.addElement(nombreArchivo); //Se añade al modelo el nombre del archivo para mostrar en la ventana
        }
        lista.setModel(modeloLista); //Ya teniendo el modelo lleno se pasa a la lista
    }
     
    /**
     * Este método sirve para con una cadena y una ventana iniciar la simulación de impresión de un archivo
     * @param next La cadena que se va a usar en esta simulación
     * @param dialogo La ventana que se usará para la simulación
     */
    public static void iniciarSimulacion(String next, Dialogo dialogo) {
            String nombreArchivo = sacarNombreArchivo(next); //Se obtiene el nombre del archivo
            dialogo.setNombreLabel(nombreArchivo); //Teniendo el nombre del archivo se pega en la ventana a mostrar
            Timer temporizador = new Timer(1000, new ActionListener() { //Se crea un temporizador que contará cada segundo
                long tiempo = sacarSegundosArchivo(next) * 1000; //Se saca el tiempo en segundos para luego pasar a milisegundos (ya que el timer trabaja con milisegundos)
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tiempo > 0) { //Se verifica que el tiempo sea mayor a cero
                        dialogo.setLblEspera("Espere " + tiempo / 1000 + " segundos"); //Se muestra en la ventana el tiempo restante
                        tiempo -= 1000; //Se resta el tiempo cada segundo que pasa
                    } else {
                        dialogo.dispose(); //Cuando ya el tiempo es cero se cierra la ventana
                    }
                }
            });
            boolean continuar = true; //Se crea una variable que servirá para que el método no se acabe hasta que se cierre la ventana
            dialogo.setLblEspera("Espere..."); //Como es la misma ventana la que se usará para todas (para ahorrar memoria) se hace un setText de "Espere..."
            dialogo.setVisible(true); //Se vuelve visible a la ventana para mostrar el contador
            while (continuar) {
                temporizador.start(); // Cada pasada del while se hace el iniciar del temporizador para que vaya contando segundo a segundo
                if (!dialogo.isVisible()) { //Se verifica que la ventana se cerró
                    temporizador.stop(); //Si se cumple esta condición se para el temporizador
                    continuar = false; //Se vuelve la variable falsa para que ya no se tengan que hacer más pasadas del while
                }
            }
    }
    
    /**
     * Este método recibe una cadena con el formato "Nombre.extension,tiempo", el cual se usa en este proyecto, sirve para sacar el nombre del archivo.
     * @param next La cadena a la cual se le sacará el nombre
     * @return Devuelve el nombre del archivo ya sacado de la cadena
     * @throws NullPointerException si la cadena no tiene ninguna coma
     */
    public static String sacarNombreArchivo(String next) {
        if (next.contains(",")) { //Se verifica que la cadena tenga coma
        int i=0; //Se inicia un contador en cero para ir subiendo a la longitud de la cadena
        String nombreArchivo = ""; //Se inicializa el nombre del archivo
        while (next.charAt(i) != ',') { //Se hace un mientras qué hasta que haya una coma
            nombreArchivo += next.charAt(i); //Se añade cada caracter para formar el nombre del archivo
            i++; //Se va sumando de uno en uno para ir cogiendo de caracter en caracter de la cadena
        }
        return nombreArchivo;
        } else { //De otra manera no hay comas en la cadena, por lo que no se puede realizar la obtención del nombre
        throw new NullPointerException("No había ninguna coma en la cadena");
        }
    }
    
    /**
     * Este método recibe una cadena con el formato "Nombre.extension,tiempo", el cual se usa en este proyecto, sirve para sacar los segundos que durará el archivo en imprimirse.
     * @param next La cadena a la cual se le sacará el tiempo
     * @return Devuelve el tiempo a imprimir que tendrá el archivo
     * @throws NullPointerException si la cadena no tiene ninguna coma
     */
    public static long sacarSegundosArchivo(String next) {
        if (next.contains(",")) { //Se verifica que la cadena tenga coma
        int i=0; //Se inicia un contador en cero para ir subiendo a la longitud de la cadena
        while (next.charAt(i) != ',') { //Se hace un contador hasta que haya una coma en la cadena
            i++; //Se aumenta el valor del contador hasta llegar a la coma
        }
        i++; //Se aumenta en uno el valor del contador para pasar la coma
        String cadenaTiempo = ""; //Se crea la cadena de tiempo
        while (i < next.length()) { //Como lo que sigue son los segundos de hace desde el valor actual del contador hasta la longitud de la cadena menos uno
            cadenaTiempo += next.charAt(i); //Se añade a la cadena de tiempo cada valor siguiente a la coma
            i++; //Se aumenta el valor del contador hasta llegar a la longitud de la cadena menos uno
        }
        return Long.parseLong(cadenaTiempo); //Se retorna la cadena de tiempo convertida ya a Long
        } else { //De otra manera no hay comas en la cadena, por lo que no se puede realizar la obtención de los segundos
        throw new NullPointerException("No había ninguna coma en la cadena");
        }
    }
   
    /**
     * Este método recibe una JList y una Cola, elimina el primer archivo de la cola para después hacer la respectiva actualización, utiliza el método colaALista
     * @param lista La lista que será actualizada
     * @param impresiones La cola que será usada en la actualización
     */
   public static void actualizadorLista (JList lista, Cola<String> impresiones) {
   impresiones.dequeue(); //Se saca el primer valor de la cola, ya que se completó el ciclo de su impresión
   colaALista(impresiones, lista); //Se actualiza la lista de acuerdo a los valores que quedan en la cola
   }
   
    public static void main(String[] args) {
    }

}
