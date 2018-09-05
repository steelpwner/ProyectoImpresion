/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import edu.cuc.clases.Cola;
import edu.cuc.clases.Metodos;
import edu.cuc.ventanas.Dialogo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JList;
import org.junit.Assert;

/**
 *
 * @author Diego
 */
public class TestMetodos {

    public TestMetodos() {
   
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMetodoCola() {
        Assert.assertEquals(6, Metodos.cargarDatos("spool.txt").getSize());
        Cola<String> cola = new Cola();
        cola.enqueue("testing");
        Assert.assertEquals(cola.getEnd(), cola.peek());
    }

    @Test
    public void testIterator() {
        Cola<String> cola = new Cola();
        cola.enqueue("testingiterator");
        Iterator<String> iterador = cola.iterator();
        String next = iterador.next();
        Assert.assertEquals(next, cola.peek().getValor());
    }

    @Test
    public void testSize() {
        Cola<String> cola = new Cola();
        Assert.assertEquals(cola.isEmpty(), cola.getSize() == 0);
        cola.enqueue("testingsize");
        Assert.assertEquals(cola.isEmpty(), cola.getSize() == 0);
    }
    
    @Test
    public void testSimulacion() {
    String next="documento1.txt,6";
    Assert.assertEquals("documento1.txt", Metodos.sacarNombreArchivo(next));
    Assert.assertEquals(6, Metodos.sacarSegundosArchivo(next));
    Cola<String> cola= new Cola();
    cola.enqueue("hola.html,2");
    cola.enqueue("mundo.txt,6");
    cola.enqueue("testing.docx,8");
    cola.enqueue("testing.xlss,8");
    Assert.assertNotSame(3, cola.getSize());
    Metodos.iniciarSimulacion(cola.peek().getValor(), new Dialogo(new JFrame(),false));
    Metodos.actualizadorLista(new JList(), cola);
    Assert.assertEquals(3, cola.getSize());
    Assert.assertFalse(cola.isEmpty());
    Assert.assertNotNull(cola.peek());
    Assert.assertEquals("mundo.txt,6", cola.peek().getValor());
    }
}
