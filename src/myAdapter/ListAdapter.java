package myAdapter;

import java.util.NoSuchElementException;

/**
 * ListAdapter adapts the {@link Vector} class from Java CLDC1.1 to the {@link HList} interface of Java J2SE1.4.2.
 * This HList implementation has no restriction on the acceptable values, so null is an acceptable value for an item.
 * <p>This is an application of the Adapter design pattern</p>
 * @author Zuech Riccardo
 * @see HList
 * @see Vector
 */
public class ListAdapter implements HList{
	/**
	 * The vector working as adaptee for the HList interface
	 */
	private Vector v;
	/**
	 * A reference to the mother list/sublist of this sublist.
	 * If this list is not a sublist, then this attribute is set to null
	 */
	private final ListAdapter motherList;
	/**
	 * Indexes of this sublist.
	 * If this list is not a sublist, then they are set to 0 and this.size() respectively
	 */
	private int from, to;
	
	/**
	 * Default constructor, creates an empty list.
	 */
	public ListAdapter() {
		v = new Vector();
		motherList = null;
		from = 0;
		to = 0;
	}
	/**
	 * Private constructor used in the subList method.
	 * @param mother the list from which the subList is to be derived
	 * @param fromIndex low endpoint of the subList
	 * @param toIndex high endpoint of the subList
	 * @throws IndexOutOfBoundsException if the endpoints indexes are out of bounds
	 */
	private ListAdapter(ListAdapter mother, int fromIndex, int toIndex) throws IndexOutOfBoundsException {
		if(fromIndex<0) throw new IndexOutOfBoundsException("A negative start index has been passed as argument!");
		if(toIndex > mother.size()) throw new IndexOutOfBoundsException("The high endpoint index is greater than the list size!"); 
		v = mother.v;
		motherList = mother;
		from = fromIndex + mother.from;
		to = toIndex + mother.from;
	}
	
	/**
     * Constructor used to create a list with the same elements as the provided collection
     * @param c collection whose elements populate the list
     */
	public ListAdapter(HCollection c) {
		this();
		HIterator i = c.iterator();
		while(i.hasNext()) {
			add(i.next());
		}
	}
	/**
     * Returns the number of elements in this list. If this list contains more than <i>Integer.MAX_VALUE</i> elements, returns <i>Integer.MAX_VALUE</i>.
     *
     * @return the number of elements in this list.
     */
	public int size() {
		return to - from;
	}
	/**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements.
     */
	public boolean isEmpty() {
		return to == from;
	}
	/**
     * Returns true if this list contains the specified element. More formally, 
     * returns true if and only if this list contains at least one element e such that (o==null ? e==null : o.equals(e)).
     *
     * @param obj element whose presence in this list is to be tested.
     * 
     * @return true if this list contains the specified element.
     */
	public boolean contains(Object o) {
		if(o == null) {
			for(int i = from; i < to; i++) {
				if(v.elementAt(i) == null) return true;
			}
			return false;
		}
		else {
			int i = v.indexOf(o);
			return i >= from && i < to;
		}
	}
	/**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
	public HIterator iterator() {
		return new ListAdapterIterator();
	}
	/**
     * Returns an array containing all of the elements in this list in proper sequence.
     *
     * @return an array containing all of the elements in this list in proper
     * sequence.
     */
	public Object[] toArray() {
		Object[] a = new Object[size()];
		for(int i = 0; i < size(); i++) {
			a[i] = get(i);
		}
		return a;
	}
	/**
     * Returns an array containing all of the elements in this list in proper sequence.
     *
     * @param arrayTarget the array into which the elements of this list are to be
     * stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
     * 
     * @return an array containing the elements of this list.
     * 
     * @throws NullPointerException if the specified array is null.
     */
	public Object[] toArray(Object[] a) throws NullPointerException {
		if(a == null) throw new NullPointerException();
		if(a.length < size()) return toArray();
		for(int i = 0; i < a.length; i++) {
			a[i] = i < size() ? get(i) : null;
		}
		return a;
	}
	
	private void updateRange(int amount) {
		to += amount;
		if(motherList != null) motherList.updateRange(amount);
	}
	/**
     * Inserts the specified element at the specified position in this list
     *. Shifts the element currently at that position (if any)
     * and any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     *
     * @throws IndexOutOfBoundsException - if the index is out of range (index &lt; 0 || index &lt; size()).
     */
	public void add(int index, Object element) throws IndexOutOfBoundsException {
		v.insertElementAt(element, index + from);
		updateRange(1);
	}
	/**
     * Appends the specified element to the end of this list.
     *
     * @param obj element to be appended to this list.
     * @return true (as per the general contract of the Collection.add method).
     */
	public boolean add(Object o) {
		add(size(), o);
		return true;
	}
	/**
     * Removes the first occurrence in this list of the specified element.
     * If this list does not contain the element, it is unchanged. More formally,
     * removes the element with the lowest index i such that (o==null ? get(i)==null : o.equals(get(i)))
     * (if such an element exists).
     *
     * @param obj element to be removed from this list, if present.
     * @return true if this list contained the specified element.
     * 
     */
	public boolean remove(Object o) {
		int index = v.indexOf(o);
		if(index >= from && index < to) {
			v.removeElementAt(index);
			updateRange(-1);
			return true;
		}
		return false;
	}
	/**
     * Returns true if this list contains all of the elements of the specified collection.
     *
     * @param coll collection to be checked for containment in this list.
     * @return true if this list contains all of the elements of the specified collection.
     * @throws NullPointerException if the specified collection is null.
     */
	public boolean containsAll(HCollection c) throws NullPointerException {
		if(c == null) throw new NullPointerException();
		HIterator x = c.iterator();
		while(x.hasNext()) {
			int index = v.indexOf(x.next());
			if(index < from || index >= to) return false;
		}
		return true;
	}
	/**
     * Appends all of the elements in the specified collection to the end of this
     * list, in the order that they are returned by the specified collection's
     * iterator. The behavior of this operation is unspecified
     * if the specified collection is modified while the operation is in progress.
     * (Note that this will occur if the specified collection is this list, and it's nonempty.)
     *
     * @param coll collection whose elements are to be added to this list.
     * @return true if this list changed as a result of the call.
     *
     * @throws NullPointerException if the specified collection contains one or more null elements and this list does not support null elements, or if the specified collection is null.
     */
	public boolean addAll(HCollection c) throws NullPointerException {
		if(c == null) throw new NullPointerException();
		HIterator x = c.iterator();
		while(x.hasNext())
			add(x.next());
		return true;
	}
	/**
     * Inserts all of the elements in the specified collection into this list at the
     * specified position. Shifts the element currently at that
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
     * @throws NullPointerException if the specified collection contains one or more null elements and this list does not support null elements, or if the specified collection is null.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &lt; size()).

     */
	public boolean addAll(int index, HCollection c) throws NullPointerException, IndexOutOfBoundsException {
		if((index+from >= to && !isEmpty()) || index < 0) throw new IndexOutOfBoundsException();
		if(c == null) throw new NullPointerException();
		HIterator x = c.iterator();
		while(x.hasNext()) {
			add(index, x.next());
			index++;
		}
		return true;
	}
	/**
     * Removes from this list all the elements that are contained in the specified collection.
     *
     * @param coll collection that defines which elements will be removed from this list.
     * @return true if this list changed as a result of the call.
     *
     * @throws NullPointerException  if the specified collection is null.
     */
	public boolean removeAll(HCollection c) throws NullPointerException {
		if(c == null) throw new NullPointerException();
		HIterator x = c.iterator();
		boolean changed = false;
		while(x.hasNext()) {
			Object o = x.next();
			while(contains(o)) {
				changed = remove(o);
			}
		}
		return changed;
	}
	/**
     * Retains only the elements in this list that are contained in the specified
     * collection. In other words, removes from this list all
     * the elements that are not contained in the specified collection.
     *
     * @param coll collection that defines which elements this set will retain.
     *
     * @return true if this list changed as a result of the call.
     *
     * @throws NullPointerException - if the specified collection is null.
     */
	public boolean retainAll(HCollection c) throws NullPointerException {
		if(c == null) throw new NullPointerException();
		HIterator x = iterator();
		boolean changed = false;
		while(x.hasNext()) {
			Object o = x.next();
			if(!c.contains(o)) {
				x.remove();
				changed = true;
			}
		}
		return changed;
	}
	/**
     * Removes all of the elements from this list. This list will be empty after this call returns (unless it throws an exception).
     *
     */
	public void clear() {
		int i = 0;
		while(i + from < to) {
			v.removeElementAt(from);
			i++;
		}
		updateRange(i*-1);
	}
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
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		try {
			ListAdapter otherList = (ListAdapter) o;
			if(otherList.size() != size()) return false;
			HIterator l1 = iterator(), l2 = otherList.iterator();
			while(l1.hasNext() && l2.hasNext()) {
				Object n1 = l1.next(), n2 =l2.next();
				if((n1 == null && n2 != null) || (n1 != null && n2 == null)) return false;
				else if(n1 != null && !n1.equals(n2)) return false;
			}
			return true;
		}
		catch(ClassCastException c) {
			return false;
		}
	}
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
     * This ensures that {@code list1.equals(list2)} implies that {@code list1.hashCode() == list2.hashCode()} for any two lists, list1
     * and list2, as required by the general contract of {@link Object.hashCode}.
     *
     * @return the hash code value for this list.
     */
	@Override
	public int hashCode() {
		int hashCode = 1;
		HIterator i = iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
		}
		return hashCode;
	}
	/**
     * Returns the element at the specified position in this list.
     *
     * @param index index of element to return.
     * @return the element at the specified position in this list.
     *
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
	public Object get(int index) throws IndexOutOfBoundsException {
		return v.elementAt(from + index);		
	}
	/**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     *
     * @throws IndexOutOfBoundsException - if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
	public Object set(int index, Object element) throws IndexOutOfBoundsException {
		if(index+from >= to || index < 0) throw new IndexOutOfBoundsException();
		Object prev = v.elementAt(index + from);
		v.setElementAt(element, index + from);
		return prev;
	}
	/**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to removed.
     * @return the element previously at the specified position.
     * 
     * @throws IndexOutOfBoundsException - if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
	public Object remove(int index) throws IndexOutOfBoundsException { 
		if(index+from >= to || index < 0) throw new IndexOutOfBoundsException();
		Object prev = v.elementAt(index + from);
		v.removeElementAt(index + from);
		updateRange(-1);
		return prev;
	}
	/**
     * Returns the index in this list of the first occurrence of the specified
     * element, or -1 if this list does not contain this element. More formally,
     * returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
     *
     * @param obj element to search for.
     * @return the index in this list of the first occurrence of the specified
     * element, or -1 if this list does not contain this element.
     * 
     */
	public int indexOf(Object o) {
		int index = v.indexOf(o, from);
		if(index < 0 || index >= to) return -1;
		return index - from;
	}
	/**
     * Returns the index in this list of the last occurrence of the specified
     * element, or -1 if this list does not contain this element. More formally,
     * returns the highest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
     *
     * @param obj element to search for.
     * @return the index in this list of the last occurrence of the specified element, or -1 if this list does not contain this element.
     */
	public int lastIndexOf(Object o) {
		int index = v.lastIndexOf(o, to - 1);
		if(index < from) return -1;
		return index - from;
	}
	/**
     * Returns a list iterator of the elements in this list (in proper sequence).
     *
     * @return a list iterator of the elements in this list (in proper sequence).
     */
	public HListIterator listIterator() {
		return new ListAdapterIterator();
	}
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
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &lt; size()).
     */
	public HListIterator listIterator(int index) throws IndexOutOfBoundsException {
		if(index > size() || index < 0) throw new IndexOutOfBoundsException();
		return new ListAdapterIterator(index);
	}
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
     * (fromIndex &lt; 0 || toIndex &lt; size || fromIndex &lt; toIndex).
     */
	public ListAdapter subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
		if(toIndex > size() || fromIndex < 0) throw new IndexOutOfBoundsException();
		return new ListAdapter(this, fromIndex, toIndex);
	}
	/**
	 * An iterator for lists that allows the programmer to traverse the list in either direction, modify 
	 * the list during iteration, and obtain the iterator's current position in the list. A <i>ListIterator</i> 
	 * has no current element; its <i>cursor position</i> always 
	 * lies between the element that would be returned by a call to <i>previous()</i> and the element that would be 
	 * returned by a call to <i>next()</i>. In a list of length <i>n</i>, there are <i>n+1</i> valid index values,
	 * from <i>0</i> to <i>n</i>, inclusive.
	 *
	 * <pre>
	          Element(0)   Element(1)   Element(2)   ... Element(n)   
	        ^            ^            ^            ^               ^
	 Index: 0            1            2            3               n+1
	
	 </pre>
	 * <p> Note that the <a href="ListIterator.html#remove()"><code>remove()</code></a> and
	 * <a href="ListIterator.html#set(java.lang.Object)"><code>set(Object)</code></a> methods are
	 * <i>not</i> defined in terms of the cursor position;  they are defined to operate on the last element returned
	 * by a call to <a href="ListIterator.html#next()"><code>next()</code></a> or
	 * <a href="ListIterator.html#previous()"><code>previous()</code></a>.</p>
	
	 * @see HCollection
	 * @see HList
	 * @see HIterator
	 * 
	 * @author Zuech
	 */
	public class ListAdapterIterator implements HListIterator {
		private int cursor;
		/**
		 * Attribute used to keep memory of the cursor value at last set/next call. It's set to -1 if next or set
		 * have not been called yet.
		 */
		private int lastCall;
		/**
		 * Default constructor: creates an iterator whose cursor is placed at the beginning of the list, before the first
		 * element.
		 */
		public ListAdapterIterator() {
			this(0);
		}
		/**
		 * Default constructor: creates an iterator whose cursor is placed at the specified index.
		 */
		public ListAdapterIterator(int index) {
			cursor = index;
			lastCall = -1;
		}
		/**
	     * Returns true if this list iterator has more elements when traversing
	     * the list in the forward direction. (In other words, returns true if
	     * next would return an element rather than throwing an exception.)
	     *
	     * @return true if the list iterator has more elements when traversing
	     * the list in the forward direction.
	     */
		public boolean hasNext() {
			return cursor < size();
		}
		/**
	     * Returns the next element in the list. This method may be called repeatedly to
	     * iterate through the list, or intermixed with calls to previous to go
	     * back and forth. (Note that alternating calls to next and
	     * previous will return the same element repeatedly.)
	     *
	     * @return the next element in the list.
	     * @throws NoSuchElementException - if the iteration has no next element.
	     */
		public Object next() throws NoSuchElementException {
			if(!hasNext()) throw new NoSuchElementException();
			lastCall = cursor;
			Object e = get(cursor);
			cursor++;
			return e;
		}
		/**
	     * Returns true if this list iterator has more elements when traversing
	     * the list in the reverse direction. (In other words, returns true if
	     * previous would return an element rather than throwing an exception.)
	     *
	     * @return true if the list iterator has more elements when traversing
	     *         the list in the reverse direction.
	     */
		public boolean hasPrevious() {
			return cursor > 0; 
		}
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
		public Object previous() throws NoSuchElementException {
			if(!hasPrevious()) throw new NoSuchElementException();
			cursor -= 1;
			lastCall = cursor;
			return get(cursor);
		}
		/**
	     * Returns the index of the element that would be returned by a subsequent call
	     * to next. (Returns list size if the list iterator is at the end of
	     * the list.)
	     *
	     * @return the index of the element that would be returned by a subsequent call
	     * to next, or list size if list iterator is at end of list.
	     */
		public int nextIndex() {
			if(hasNext())
				return cursor;
			return size();
		}
		/**
	     * Returns the index of the element that would be returned by a subsequent call
	     * to previous. (Returns -1 if the list iterator is at the beginning of
	     * the list.)
	     *
	     * @return the index of the element that would be returned by a subsequent call
	     * to previous, or -1 if list iterator is at beginning of list.
	     */
		public int previousIndex() {
			if(hasPrevious())
				return cursor - 1;
			return -1;
		}
		/**
	     * Removes from the list the last element that was returned by next or
	     * previous (optional operation). This call can only be made once per
	     * call to next or previous. It can be made only if
	     * ListIterator.add has not been called after the last call to
	     * next or previous.
	     *
	     * @throws IllegalStateException neither next nor previous have been called, or remove or add have been called after the last call to * next or previous.
	     */
		public void remove() throws IllegalStateException {
			if(lastCall < 0) throw new IllegalStateException();
			ListAdapter.this.remove(lastCall);
			if(cursor > lastCall) cursor--;
			lastCall = -1;
		}
		/**
	     * Replaces the last element returned by next or previous with
	     * the specified element (optional operation). This call can be made only if
	     * neither ListIterator.remove nor ListIterator.add have been
	     * called after the last call to next or previous.
	     *
	     * @param obj the element with which to replace the last element returned by
	     * next or previous.
	     *
	     * @throws IllegalStateException neither next nor previous have been called, or remove or add have been called after the last call to * next or previous.
	     */
		public void set(Object o) throws IllegalStateException {
			if(lastCall <0) throw new IllegalStateException();
			ListAdapter.this
			.set(lastCall, o);
		}
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
	     */
		public void add(Object o) {
			ListAdapter.this.add(cursor, o);
			cursor++;
			lastCall = -1;
		}
	}
}
