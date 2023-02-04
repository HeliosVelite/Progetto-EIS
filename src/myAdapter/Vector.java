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

 * @author Zuech
 */
public class Vector {
		
		/**
		 * Current-version vector
		 */
	    java.util.Vector<Object> vector;
	    
	    /**
	     * Constructs an empty vector.
	     */
	    public Vector() {
	        vector = new java.util.Vector<Object>();
	    }
	    /**
	     * Constructs an empty vector with the specified initial capacity.
	     * @param initialCapacity
	     * 
	     * @throws IllegalArgumentException if the specified initial capacity is negative
	     */
	    public Vector(int initialCapacity) throws IllegalArgumentException {
	        vector = new java.util.Vector<Object>(initialCapacity);
	    }
	    /**
	     * Constructs an empty vector with the specified initial capacity and capacity increment.
	     * @param initialCapacity the initial capacity of the vector.
	     * @param capacityIncrement the amount by which the capacity is increased when the vector overflows.
	     * 
	     * @throws IllegalArgumentException if the specified initial capacity is negative
	     */
	    public Vector(int initialCapacity, int capacityIncrement) throws IllegalArgumentException {
	        vector = new java.util.Vector<Object>(initialCapacity, capacityIncrement);
	    }
	    /**
	     * Adds the specified component to the end of this vector, increasing its size by one. The capacity of this vector is increased if its size becomes greater 
	     * than its capacity.
	     * @param obj the component to be added.
	     */
	    public void addElement(Object obj){
	        vector.addElement(obj);
	    }
	    /**
	     * Returns the current capacity of this vector.
	     * @return the current capacity of this vector.
	     */
	    public int capacity(){
	        return vector.capacity();
	    }
	    /**
	     * Tests if the specified object is a component in this vector.
	     * @param elem an object
	     * @return true if the specified object is a component in this vector; false otherwise.
	     */
	    public boolean contains(Object elem){
	        return vector.contains(elem);
	    }
	    /**
	     * Copies the components of this vector into the specified array. The array must be big enough to hold all the objects in this vector.
	     * @param anArray the array into which the components get copied.
	     */
	    public void copyInto(Object[] anArray){
	        vector.copyInto(anArray);
	    }
	    /**
	     * Returns the component at the specified index.
	     * @param index an index into this vector.
	     * @return the component at the specified index.
	     * @throws ArrayIndexOutOfBoundsException if an invalid index was given
	     */
	    public Object elementAt(int index) throws ArrayIndexOutOfBoundsException{
	        return vector.elementAt(index);
	    }
	    /**
	     * Returns an enumeration of the components of this vector.
	     * @return an enumeration of the components of this vector.
	     * @see Enumeration
	     */
	    public Enumeration<Object> elements(){
	        return vector.elements();
	    }
	    /**
	     * Increases the capacity of this vector, if necessary, to ensure that it can hold at least the number of components specified by the minimum capacity argument.
	     * @param minCapacity the desired minimum capacity.
	     */
	    public void ensureCapacity(int minCapacity){
	        vector.ensureCapacity(minCapacity);
	    }
	    /**
	     * Returns the first component of this vector.
	     * @return the first component of this vector.
	     * @throws NoSuchElementException if this vector has no components
	     */
	    public Object firstElement() throws NoSuchElementException{
	        return vector.firstElement();
	    }
	    /**
	     * Searches for the first occurrence of the given argument, testing for equality using the equals method.
	     * @param elem an object
	     * @return the index of the first occurrence of the argument in this vector; returns -1 if the object is not found.
	     * @see Object.equals(java.lang.Object
	     */
	    public int indexOf(Object elem){
	        return vector.indexOf(elem);
	    }
	    /**
	     * Searches for the first occurrence of the given argument, beginning the search at index, and testing for equality using the equals method.
	     * @param elem an object
	     * @param index the index to start searching from.
	     * @return the index of the first occurrence of the object argument in this vector at position index or later in the vector; returns -1 if the object is not found.
	     * @throws IndexOutOfBoundsException if index is out of bounds.
	     * @see Object.equals(java.lang.Object
	     */
	    public int indexOf(Object elem, int index) throws IndexOutOfBoundsException {
	        return vector.indexOf(elem, index);
	    }
	    /**
	     * Inserts the specified object as a component in this vector at the specified index. Each component in this vector with an index greater or equal to the specified index is shifted upward to have an index one greater than the value it had previously.
	     * <br>
	     * The index must be a value greater than or equal to 0 and less than or equal to the current size of the vector.
	     * @param obj the object to insert.
	     * @param index where to insert the new component.
	     * @throws ArrayIndexOutOfBoundsException if the index was invalid.
	     * @see #size()
	     */
	    public void insertElementAt(Object obj, int index) throws ArrayIndexOutOfBoundsException {
	        vector.insertElementAt(obj, index);
	    }
	    /**
	     * Tests if this vector has no components.
	     * @return true if this vector has no components; false otherwise.
	     */
	    public boolean isEmpty(){
	        return vector.isEmpty();
	    }
	    /**
	     * Returns the last component of the vector
	     * @return the last component of the vector, i.e., the component at index size() - 1.
	     * @throws NoSuchElementException if this vector is emtpy
	     */
	    public Object lastElement() throws NoSuchElementException{
	        return vector.lastElement();
	    }
	    /**
	     * Returns the index of the last occurrence of the specified object in this vector.
	     * @param elem the desired element
	     * @return the index of the last occurrence of the specified object in this vector; returns -1 if the object is not found.
	     */
	    public int lastIndexOf(Object elem){
	        return vector.lastIndexOf(elem);
	    }
	    /**
	     * Searches backwards for the specified object, starting from the specified index, and returns an index to it.
	     * @param elem the desired element
	     * @param index the index to start searching from.
	     * @return the index of the last occurrence of the specified object in this vector at position less than index in the vector; -1 if the object is not found.
	     * @throws IndexOutOfBoundsException if index is greater than or equal to the current size of this vector.
	     */
	    public int lastIndexOf(Object elem, int index) throws IndexOutOfBoundsException {
	        return vector.lastIndexOf(elem, index);
	    }
	    /**
	     * Removes all components from this vector and sets its size to zero.
	     */
	    public void removeAllElements(){
	        vector.removeAllElements();
	    }
	    /**
	     * Removes the first occurrence of the argument from this vector. If the object is found in this vector, each component in 
	     * the vector with an index greater or equal to the object's index is shifted downward to have an index one smaller than the value it had previously.
	     * @param obj the component to be removed.
	     * @return true if the argument was a component of this vector; false otherwise.
	     */
	    public boolean removeElement(Object obj){
	        return vector.removeElement(obj);
	    }
	    /**
	     * Deletes the component at the specified index. Each component in this vector with an index greater or equal to the specified index is shifted downward to have an index one smaller than the value it had previously.
	     * <br>
	     * The index must be a value greater than or equal to 0 and less than the current size of the vector.
	     * @param index the index of the object to remove.
	     * @throws ArrayIndexOutOfBoundsException if the index was invalid.
	     * @see #size()
	     */
	    public void removeElementAt(int index) throws ArrayIndexOutOfBoundsException{
	        vector.removeElementAt(index);
	    }
	    /**
	     * Sets the component at the specified index of this vector to be the specified object. The previous component at that position is discarded.
	     * <br>
	     * The index must be a value greater than or equal to 0 and less than the current size of the vector.
	     * @param obj what the component is to be set to.
	     * @param index the specified index.
	     * @throws ArrayIndexOutOfBoundsException if the index was invalid.
	     * @see #size()
	     */
	    public void setElementAt(Object obj, int index) throws ArrayIndexOutOfBoundsException{
	        vector.setElementAt(obj, index);
	    }
	    /**
	     * Sets the size of this vector. If the new size is greater than the current size, new null items are added to the end of the vector. 
	     * If the new size is less than the current size, all components at index newSize and greater are discarded.
	     * @param newSize the new size of this vector.
	     * @throws ArrayIndexOutOfBoundsException if new size is negative.
	     */
	    public void setSize(int newSize){
	        vector.setSize(newSize);
	    }
	    /**
	     * Returns the number of components in this vector.
	     * @return the number of components in this vector.
	     */
	    public int size(){
	        return vector.size();
	    }
	    /**
	     * Returns a string representation of this vector.
	     * @return a string representation of this vector.
	     */
	    @Override
	    public String toString(){
	        return vector.toString();
	    }
	    /**
	     * Trims the capacity of this vector to be the vector's current size. An application can use this operation to minimize the storage of a vector.
	     */
	    public void trimToSize(){
	        vector.trimToSize();
	    }
}