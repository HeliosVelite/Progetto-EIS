package myAdapter;

/**
 *
 * <p>
An iterator for lists that allows the programmer 
 to traverse the list in either direction, modify 
 the list during iteration, and obtain the iterator's 
 current position in the list. A <tt>ListIterator</tt> 
 has no current element; its <i>cursor position</i> always 
 lies between the element that would be returned by a call 
 to <tt>previous()</tt> and the element that would be 
 returned by a call to <tt>next()</tt>. In a list of 
 length <tt>n</tt>, there are <tt>n+1</tt> valid 
 index values, from <tt>0</tt> to <tt>n</tt>, inclusive. 
 </p>
 *
 * <pre>
          Element(0)   Element(1)   Element(2)   ... Element(n)   
        ^            ^            ^            ^               ^
 Index: 0            1            2            3               n+1

 </pre>
 * <p>
 Note that the <a href="ListIterator.html#remove()"><code>remove()</code></a> and <a href="ListIterator.html#set(java.lang.Object)"><code>set(Object)</code></a> methods are
 <i>not</i> defined in terms of the cursor position;  they are defined to
 operate on the last element returned by a call to <a href="ListIterator.html#next()"><code>next()</code></a> or <a href="ListIterator.html#previous()"><code>previous()</code></a>.
 </p>

 * @see HCollection
 * @see HList
 * @see HIterator
 */

public interface HListIterator extends HIterator {

    /**
     * Returns true if this list iterator has more elements when traversing
     * the list in the forward direction. (In other words, returns true if
     * next would return an element rather than throwing an exception.)
     *
     * @return true if the list iterator has more elements when traversing
     * the list in the forward direction.
     */
    boolean hasNext();

    /**
     * Returns the next element in the list. This method may be called repeatedly to
     * iterate through the list, or intermixed with calls to previous to go
     * back and forth. (Note that alternating calls to next and
     * previous will return the same element repeatedly.)
     *
     * @return the next element in the list.
     * @throws NoSuchElementException - if the iteration has no next element.
     */
    Object next();

    /**
     * Returns true if this list iterator has more elements when traversing
     * the list in the reverse direction. (In other words, returns true if
     * previous would return an element rather than throwing an exception.)
     *
     * @return true if the list iterator has more elements when traversing
     *         the list in the reverse direction.
     */
    boolean hasPrevious();

    /**
     * Returns the previous element in the list. This method may be called
     * repeatedly to iterate through the list backwards, or intermixed with calls to
     * next to go back and forth. (Note that alternating calls to
     * next and previous will return the same element repeatedly.)
     *
     * @return the previous element in the list.
     *
     * @throws NoSuchElementException - if the iteration has no next element.
     */
    Object previous();

    /**
     * Returns the index of the element that would be returned by a subsequent call
     * to next. (Returns list size if the list iterator is at the end of
     * the list.)
     *
     * @return the index of the element that would be returned by a subsequent call
     * to next, or list size if list iterator is at end of list.
     */
    int nextIndex();

    /**
     * Returns the index of the element that would be returned by a subsequent call
     * to previous. (Returns -1 if the list iterator is at the beginning of
     * the list.)
     *
     * @return the index of the element that would be returned by a subsequent call
     *         to previous, or -1 if list iterator is at beginning of list.
     */
    int previousIndex();

    /**
     * Removes from the list the last element that was returned by next or
     * previous (optional operation). This call can only be made once per
     * call to next or previous. It can be made only if
     * ListIterator.add has not been called after the last call to
     * next or previous.
     *
     *@throws UnsupportedOperationException if the remove operation is not supported by this list iterator.
     *@throws IllegalStateException neither next nor previous have been called, or remove or add have been called after the last call to * next or previous.
     */
    void remove();

    /**
     * Replaces the last element returned by next or previous with
     * the specified element (optional operation). This call can be made only if
     * neither ListIterator.remove nor ListIterator.add have been
     * called after the last call to next or previous.
     *
     * @param obj the element with which to replace the last element returned by
     * next or previous.
     *
     *@throws UnsupportedOperationException if the set operation is not supported by this list iterator.
     *@throws IllegalStateException neither next nor previous have been called, or remove or add have been called after the last call to * next or previous.
     *@throws ClassCastException if the class of the specified element prevents it from being added to this list.
     *@throws IllegalArgumentException if some aspect of the specified element prevents it from being added to this list.
     */
    void set(Object obj);

    /**
     * Inserts the specified element into the list (optional operation). The element
     * is inserted immediately before the next element that would be returned by
     * next, if any, and after the next element that would be returned by
     * previous, if any. (If the list contains no elements, the new element
     * becomes the sole element on the list.) The new element is inserted before the
     * implicit cursor: a subsequent call to next would be unaffected, and
     * a subsequent call to previous would return the new element. (This
     * call increases by one the value that would be returned by a call to
     * nextIndex or previousIndex.)
     *
     * @param obj the element to insert.
     * 
     * @throws UnsupportedOperationException if the add method is not supported by this list iterator.
     * @throws ClassCastException if the class of the specified element prevents it from being added to this list.
     * @throws IllegalArgumentException if some aspect of this element prevents it from being added to this list.
     */
    void add(Object obj);
}