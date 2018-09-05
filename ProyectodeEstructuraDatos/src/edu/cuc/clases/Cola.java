package edu.cuc.clases;

import java.util.Iterator;

public class Cola<E> implements Iterable {

    private Nodo top, end;
    private int size;

    public Cola() {
        top = end = null;
        size = 0;
    }

    public void enqueue(E valor) {
        Nodo<E> nodo = new Nodo(valor);
        if (top == null) {
            top = end = nodo;

        } else {
            this.end.setLink(nodo);
            this.end = nodo;
        }
        this.size++;
    }

    public Nodo<E> dequeue() {
        if (isEmpty()) {
            return null;
        } else {
            Nodo<E> nodo = this.top;
            this.top = this.top.getLink();
            this.size--;
            return nodo;
        }
    }

    public Nodo<E> peek() {
        return this.top;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public Nodo<E> getEnd() {
        return this.end;
    }

    public void display() {
        Iterator<E> iterador = this.iterator();
        System.out.println("La Cola de tamaño " + this.getSize() + " es la siguiente:");
        while (iterador.hasNext()) {
            E next = iterador.next();
            System.out.println(next);
        }
    }

    @Override
    public Iterator iterator() {
        Iterator<E> iterador = new Iterator() {
            Nodo<E> nodoActual = top;

            @Override
            public boolean hasNext() {
                return nodoActual != null;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    E dato = nodoActual.getValor();
                    nodoActual = nodoActual.getLink();
                    return dato;
                } else {
                    throw new NullPointerException("No hay siguiente");
                }
            }
        };
        return iterador;
    }

}
