package myAdapter;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * Class Vector of Java Micro Edition CLDC1.1
 * <br>
 * <p>
 * The Vector class implements a growable array of objects.
 * Like an array, it contains components that can be accessed using an integer index. However, the size of a Vector can grow or shrink as needed to accommodate adding and removing items after the Vector has been created.
 * Each vector tries to optimize storage management by maintaining a capacity and a capacityIncrement. The capacity is always at least as large as the vector size; it is usually larger because as components are added to the vector, the vector's storage increases in chunks the size of capacityIncrement. An application can increase the capacity of a vector before inserting a large number of components; this reduces the amount of incremental reallocation.
 * </p>
 * <b>This class purpose is to force Java Micro Edition CLDC1.1 version of the Vector class into a work environment with the last version of Java</b>
 * @see <a href="https://docs.oracle.com/javame/config/cldc/ref-impl/cldc1.1/jsr139/index.html">CLDC1.1</a>

 * @author Zuech Riccardo
 */
public class Vector {

    java.util.Vector<Object> vector;

    public Vector() {
        vector = new java.util.Vector<Object>();
    }

    public Vector(int initialCapacity) {
        vector = new java.util.Vector<Object>(initialCapacity);
    }

    public Vector(int initialCapacity, int capacityIncrement) {
        vector = new java.util.Vector<Object>(initialCapacity, capacityIncrement);
    }

    public void addElement(Object obj, Object o){
        vector.addElement(obj);
    }

    public int capacity(){
        return vector.capacity();
    }

    public boolean contains(Object elem){
        return vector.contains(elem);
    }

    public void copyInto(Object[] anArray){
        vector.copyInto(anArray);
    }

    public Object elementAt(int index) throws ArrayIndexOutOfBoundsException{
        return vector.elementAt(index);
    }

    public Enumeration<Object> elements(){
        return vector.elements();
    }

    public void ensureCapacity(int minCapacity){
        vector.ensureCapacity(minCapacity);
    }

    public Object firstElement() throws NoSuchElementException{
        return vector.firstElement();
    }

    public int indexOf(Object elem){
        return vector.indexOf(elem);
    }

    public int indexOf(Object elem, int index){
        return vector.indexOf(elem, index);
    }

    public void insertElementAt(Object obj, int index){
        vector.insertElementAt(obj, index);
    }

    public boolean isEmpty(){
        return vector.isEmpty();
    }

    public Object lastElement() throws NoSuchElementException{
        return vector.lastElement();
    }

    public int lastIndexOf(Object elem){
        return vector.lastIndexOf(elem);
    }

    public int lastIndexOf(Object elem, int index){
        return vector.lastIndexOf(elem, index);
    }

    public void removeAllElements(){
        vector.removeAllElements();
    }

    public boolean removeElement(Object obj){
        return vector.removeElement(obj);
    }

    public void removeElementAt(int index) throws ArrayIndexOutOfBoundsException{
        vector.removeElementAt(index);
    }

    public void setElementAt(Object obj, int index) throws ArrayIndexOutOfBoundsException{
        vector.setElementAt(obj, index);
    }

    public void setSize(int newSize){
        vector.setSize(newSize);
    }

    public int size(){
        return vector.size();
    }

    public String toString(){
        return vector.toString();
    }

    public void trimToSize(){
        vector.trimToSize();
    }
}