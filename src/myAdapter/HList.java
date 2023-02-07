package myAdapter;

/**
 * <p> An ordered collection (also known as a <i>sequence</i>).  The user of this
 * interface has precise control over where in the list each element is
 * inserted.  The user can access elements by their integer index (position in
 * the list), and search for elements in the list.</p>
 *
 * <p> Unlike sets, lists typically allow duplicate elements.  More formally,
 * lists typically allow pairs of elements <i>e1</i> and <i>e2</i>
 * such that <i>e1.equals(e2)</i>, and they typically allow multiple
 * null elements if they allow null elements at all.  It is not inconceivable
 * that someone might wish to implement a list that prohibits duplicates, by
 * throwing runtime exceptions when the user attempts to insert them, but we
 * expect this usage to be rare.</p>
 * 
 * <p> The <i>List</i> interface places additional stipulations, beyond those
 * specified in the <i>Collection</i> interface, on the contracts of the
 * <i>iterator</i>, <i>add</i>, <i>remove</i>, <i>equals</i>, and
 * <i>hashCode</i> methods.  Declarations for other inherited methods are
 * also included here for convenience.</p>
 * 
 * <p> The <i>List</i> interface provides four methods for positional (indexed)
 * access to list elements.  Lists (like Java arrays) are zero based.  Note
 * that these operations may execute in time proportional to the index value
 * for some implementations (the <i>LinkedList</i> class, for
 * example). Thus, iterating over the elements in a list is typically
 * preferable to indexing through it if the caller does not know the
 * implementation.</p>
 * 
 * <p> The <i>List</i> interface provides a special iterator, called a
 * <i>ListIterator</i>, that allows element insertion and replacement, and
 * bidirectional access in addition to the normal operations that the
 * <i>Iterator</i> interface provides.  A method is provided to obtain a
 * list iterator that starts at a specified position in the list.</p>
 *
 * <p> The <i>List</i> interface provides two methods to search for a specified
 * object.  From a performance standpoint, these methods should be used with
 * caution.  In many implementations they will perform costly linear
 * searches.</p>
 * 
 * <p> The <i>List</i> interface provides two methods to efficiently insert and
 * remove multiple elements at an arbitrary point in the list.</p>
 *
 * <p> Note: While it is permissible for lists to contain themselves as elements,
 * extreme caution is advised: the <i>equals</i> and <i>hashCode</i>
 * methods are no longer well defined on a such a list.
 *</p>
 * 
 * <p> Some list implementations have restrictions on the elements that
 * they may contain.  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * <i>NullPointerException</i> or <i>ClassCastException</i>.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the list may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface. 
 * </p>
 *
 * @see HCollection
 */

public interface HList extends HCollection {

    /**
     * Returns the number of elements in this list. If this list contains more than <i>Integer.MAX_VALUE</i> elements, returns <i>Integer.MAX_VALUE</i>.
     *
     * @return the number of elements in this list.
     */
    int size();

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements.
     */
    boolean isEmpty();

    /**
     * Returns true if this list contains the specified element. More formally, 
     * returns true if and only if this list contains at least one element e such that (o==null ? e==null : o.equals(e)).
     *
     * @param obj element whose presence in this list is to be tested.
     * @return true if this list contains the specified element.
     * @throws ClassCastException if the type of the specified element is incompatible with this list (optional).
     * @throws NullPointerException if the specified element is null and this list does not support null elements (optional).
     */
    boolean contains(Object obj);

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
    HIterator iterator();

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * Obeys the general contract of the Collection.toArray method.
     *
     * @return an array containing all of the elements in this list in proper
     * sequence.
     */
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * Obeys the general contract of the Collection.toArray(Object[]) method.
     *
     * @param arrayTarget the array into which the elements of this list are to be
     * stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
     * 
     * @return an array containing the elements of this list.
     *
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of every element in this list.
     * @throws NullPointerException if the specified array is null.
     */
    Object[] toArray(Object arrayTarget[]);

    /**
     * Appends the specified element to the end of this list (optional operation).
     *
     * @param obj element to be appended to this list.
     * @return true (as per the general contract of the Collection.add method).
     * @throws UnsupportedOperationException if the add method is not supported by this list.
     * @throws ClassCastException if the class of the specified element prevents it from being added to this list.
     * @throws NullPointerException if the specified element is null and this list does not support null elements.
     * @throws IllegalArgumentException if some aspect of this element prevents it from being added to this list.
     */
    boolean add(Object obj);

    /**
     * Removes the first occurrence in this list of the specified element (optional operation).
     * If this list does not contain the element, it is unchanged. More formally,
     * removes the element with the lowest index i such that (o==null ? get(i)==null : o.equals(get(i)))
     * (if such an element exists).
     *
     * @param obj element to be removed from this list, if present.
     * @return true if this list contained the specified element.
     * 
     * @throws ClassCastException if the type of the specified element is incompatible with this list (optional).
     * @throws NullPointerException if the specified element is null and this list does not support null elements (optional).
     * @throws UnsupportedOperationException if the remove method is not supported by this list.
     */
    boolean remove(Object obj);

    /**
     * Returns true if this list contains all of the elements of the specified collection.
     *
     * @param coll collection to be checked for containment in this list.
     * @return true if this list contains all of the elements of the specified collection.
     * 
     * @throws ClassCastException if the types of one or more elements in the specified collection are incompatible with this list (optional).
     * @throws NullPointerException if the specified collection contains one or more null elements and this list does not support null elements (optional).
     * @throws NullPointerException if the specified collection is null.
     */
    boolean containsAll(HCollection coll);

    /**
     * Appends all of the elements in the specified collection to the end of this
     * list, in the order that they are returned by the specified collection's
     * iterator (optional operation). The behavior of this operation is unspecified
     * if the specified collection is modified while the operation is in progress.
     * (Note that this will occur if the specified collection is this list, and it's nonempty.)
     *
     * @param coll collection whose elements are to be added to this list.
     * @return true if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException - if the addAll method is not supported by this list.
     * @throws ClassCastException - if the class of an element in the specified collection prevents it from being added to this list.
     * @throws NullPointerException - if the specified collection contains one or more null elements and this list does not support null elements, or if the specified collection is null.
     * @throws IllegalArgumentException - if some aspect of an element in the specified collection prevents it from being added to this list.
     */
    boolean addAll(HCollection coll);

    /**
     * Inserts all of the elements in the specified collection into this list at the
     * specified position (optional operation). Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (increases their
     * indices). The new elements will appear in this list in the order that they
     * are returned by the specified collection's iterator. The behavior of this
     * operation is unspecified if the specified collection is modified while the
     * operation is in progress. (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * @param index index at which to insert first element from the specified collection.
     * @param coll elements to be inserted into this list.
     * @return true if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException if the addAll method is not supported by this list.
     * @throws ClassCastException if the class of one of elements of the specified collection prevents it from being added to this list.
     * @throws NullPointerException if the specified collection contains one or more null elements and this list does not support null elements, or if the specified collection is null.
     * @throws IllegalArgumentException if some aspect of one of elements of the specified collection prevents it from being added to this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).

     */
    boolean addAll(int index, HCollection coll);

    /**
     * Removes from this list all the elements that are contained in the specified collection (optional operation).
     *
     * @param coll collection that defines which elements will be removed from this list.
     * @return true if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException - if the removeAll method is not supported by this list.
     * @throws ClassCastException - if the types of one or more elements in this list are incompatible with the specified collection (optional).
     * @throws NullPointerException - if this list contains one or more null elements and the specified collection does not support null elements (optional).
     * @throws NullPointerException - if the specified collection is null.
     */
    boolean removeAll(HCollection coll);

    /**
     * Retains only the elements in this list that are contained in the specified
     * collection (optional operation). In other words, removes from this list all
     * the elements that are not contained in the specified collection.
     *
     * @param coll collection that defines which elements this set will retain.
     *
     * @return true if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException - if the retainAll method is not supported by this list.
     * @throws ClassCastException - if the types of one or more elements in this list are incompatible with the specified collection (optional).
     * @throws NullPointerException - if this list contains one or more null elements and the specified collection does not support null elements (optional).
     * @throws NullPointerException - if the specified collection is null.
     */
    boolean retainAll(HCollection coll);

    /**
     * Removes all of the elements from this list (optional operation). This list will be empty after this call returns (unless it throws an exception).
     *
     * @throws UnsupportedOperationException - if the clear method is not supported by this list.
     */
    void clear();


    /**
     * Compares the specified object with this list for equality. Returns true if
     * and only if the specified object is also a list, both lists have the same
     * size, and all corresponding pairs of elements in the two lists are
     * <i>equal</i>. (Two elements e1 and e2 are <i>equal</i> if (e1==null ?
     * e2==null : e1.equals(e2)).) In other words, two lists are defined to be equal
     * if they contain the same elements in the same order. This definition ensures
     * that the equals method works properly across different implementations of the List interface.
     *
     * @param obj the object to be compared for equality with this list.
     * @return true if the specified object is equal to this list.
     */
    boolean equals(Object obj);

    /**
     * Returns the hash code value for this list. The hash code of a list is defined
     * to be the result of the following calculation:
     *
     * <pre>
     * hashCode = 1;
     * Iterator i = list.iterator();
     * while (i.hasNext()) {
     *     Object obj = i.next();
     *     hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
     * }
     * </pre>
     *
     * This ensures that list1.equals(list2) implies that list1.hashCode()==list2.hashCode() for any two lists, list1
     * and list2, as required by the general contract of Object.hashCode.
     *
     * @return the hash code value for this list.
     */
    int hashCode();

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of element to return.
     * @return the element at the specified position in this list.
     *
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0|| index &gt;= size()).
     */
    Object get(int index);

    /**
     * Replaces the element at the specified position in this list with the specified element (optional operation).
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     *
     * @throws UnsupportedOperationException - if the set method is not supported by this list.
     * @throws ClassCastException - if the class of the specified element prevents it from being added to this list.
     * @throws NullPointerException - if the specified element is null and this list does not support null elements.
     * @throws IllegalArgumentException - if some aspect of the specified element prevents it from being added to this list.
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index &gt;= size()).
     */
    Object set(int index, Object element);

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation). Shifts the element currently at that position (if any)
     * and any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     *
     * @throws UnsupportedOperationException - if the add method is not supported by this list.
     * @throws ClassCastException - if the class of the specified element prevents it from being added to this list.
     * @throws NullPointerException - if the specified element is null and this list does not support null elements.
     * @throws IllegalArgumentException - if some aspect of the specified element prevents it from being added to this list.
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index &lt; size()).
     */
    void add(int index, Object element);

    /**
     * Removes the element at the specified position in this list (optional operation).
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to removed.
     * @return the element previously at the specified position.
     *
     * @throws UnsupportedOperationException - if the remove method is not supported by this list.
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index &gt;= size()).
     */
    Object remove(int index);


    /**
     * Returns the index in this list of the first occurrence of the specified
     * element, or -1 if this list does not contain this element. More formally,
     * returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
     *
     * @param obj element to search for.
     * @return the index in this list of the first occurrence of the specified
     * element, or -1 if this list does not contain this element.
     * 
     * @throws ClassCastException - if the type of the specified element is incompatible with this list (optional).
     * @throws NullPointerException - if the specified element is null and this list does not support null elements (optional).
     */
    int indexOf(Object obj);

    /**
     * Returns the index in this list of the last occurrence of the specified
     * element, or -1 if this list does not contain this element. More formally,
     * returns the highest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
     *
     * @param obj element to search for.
     * @return the index in this list of the last occurrence of the specified element, or -1 if this list does not contain this element.
     * 
     * @throws ClassCastException - if the type of the specified element is incompatible with this list (optional).
     * @throws NullPointerException - if the specified element is null and this list does not support null elements (optional).
     */
    int lastIndexOf(Object obj);

    /**
     * Returns a list iterator of the elements in this list (in proper sequence).
     *
     * @return a list iterator of the elements in this list (in proper sequence).
     */
    HListIterator listIterator();

    /**
     * Returns a list iterator of the elements in this list (in proper sequence),
     * starting at the specified position in this list. The specified index
     * indicates the first element that would be returned by an initial call to the
     * next method. An initial call to the previous method would return the element
     * with the specified index minus one.
     *
     * @param index index of first element to be returned from the list iterator (by a call to the next method).
     * @return a list iterator of the elements in this list (in proper sequence), starting at the specified position in this list.
     * 
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index &lt; size()).
     */
    HListIterator listIterator(int index);

    /**
     * Returns a view of the portion of this list between the specified fromIndex,
     * inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the
     * returned list is empty.) The returned list is backed by this list, so
     * non-structural changes in the returned list are reflected in this list, and
     * vice-versa. The returned list supports all of the optional list operations
     * supported by this list.
     * <br>
     * This method eliminates the need for explicit range operations (of the sort
     * that commonly exist for arrays). Any operation that expects a list can be
     * used as a range operation by passing a subList view instead of a whole list.
     * For example, the following idiom removes a range of elements from a list:
     *
     * <pre>
     * list.subList(from, to).clear();
     * </pre>
     *
     * Similar idioms may be constructed for indexOf and lastIndexOf, and all of the
     * algorithms in the Collections class can be applied to a subList.
     * <br>
     * The semantics of the list returned by this method become undefined if the
     * backing list (i.e., this list) is <i>structurally modified</i> in any way
     * other than via the returned list. (Structural modifications are those that
     * change the size of this list, or otherwise perturb it in such a fashion that
     * iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList.
     * @param toIndex high endpoint (exclusive) of the subList.
     * @return a view of the specified range within this list.
     *
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     * (fromIndex < 0 || toIndex &lt; size ||fromIndex &lt; toIndex).
     */
    HList subList(int fromIndex, int toIndex);
}