package myAdapter;

import java.util.NoSuchElementException;

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
	 * Constructor for an empty list.
	 */
	public ListAdapter() {
		v = new Vector();
		motherList = null;
		from = 0;
		to = 0;
	}
	/**
	 * Private constructor used for the subList method
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
	
	public ListAdapter(HCollection c) {
		this();
		HIterator i = c.iterator();
		while(i.hasNext()) {
			add(i.next());
		}
	}
	
	public int size() {
		return to - from;
	}
	
	public boolean isEmpty() {
		return to == from;
	}
	
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
	
	public HIterator iterator() {
		return new ListAdapterIterator();
	}
	
	public Object[] toArray() {
		Object[] a = new Object[size()];
		for(int i = 0; i < size(); i++) {
			a[i] = get(i);
		}
		return a;
	}
	
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
	
	public void add(int index, Object element) {
		v.insertElementAt(element, index + from);
		updateRange(1);
	}
	
	public boolean add(Object o) {
		add(size(), o);
		return true;
	}
	
	public boolean remove(Object o) {
		int index = v.indexOf(o);
		if(index >= from && index < to) {
			v.removeElementAt(index);
			updateRange(-1);
			return true;
		}
		return false;
	}
	
	public boolean containsAll(HCollection c) throws NullPointerException {
		if(c == null) throw new NullPointerException();
		HIterator x = c.iterator();
		while(x.hasNext()) {
			int index = v.indexOf(x.next());
			if(index < from || index >= to) return false;
		}
		return true;
	}
	
	public boolean addAll(HCollection c) throws NullPointerException {
		if(c == null) throw new NullPointerException();
		HIterator x = c.iterator();
		while(x.hasNext())
			add(x.next());
		return true;
	}
	
	public boolean addAll(int index, HCollection c) throws NullPointerException, IndexOutOfBoundsException {
		if(index+from >= to || index < 0) throw new IndexOutOfBoundsException();
		if(c == null) throw new NullPointerException();
		HIterator x = c.iterator();
		while(x.hasNext()) {
			add(index, x.next());
			index++;
		}
		return true;
	}
	
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
	
	public void clear() {
		int i = 0;
		while(i + from < to) {
			v.removeElementAt(from);
			i++;
		}
		updateRange(i*-1);
	}
	
	@Override
	public boolean equals(Object o) {
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
	
	public Object get(int index) throws IndexOutOfBoundsException {
		return v.elementAt(from + index);		
	}
	
	public Object set(int index, Object element) throws IndexOutOfBoundsException {
		if(index+from >= to || index < 0) throw new IndexOutOfBoundsException();
		Object prev = v.elementAt(index + from);
		v.setElementAt(element, index + from);
		return prev;
	}
	
	public Object remove(int index) throws IndexOutOfBoundsException { 
		if(index+from >= to || index < 0) throw new IndexOutOfBoundsException();
		Object prev = v.elementAt(index + from);
		v.removeElementAt(index + from);
		updateRange(-1);
		return prev;
	}
	
	public int indexOf(Object o) {
		int index = v.indexOf(o, from);
		if(index < 0 || index >= to) return -1;
		return index - from;
	}
	
	public int lastIndexOf(Object o) {
		int index = v.lastIndexOf(o, to - 1);
		if(index < from) return -1;
		return index - from;
	}
	
	public HListIterator listIterator() {
		return new ListAdapterIterator();
	}
	
	public HListIterator listIterator(int index) throws IndexOutOfBoundsException {
		if(index > size() || index < 0) throw new IndexOutOfBoundsException();
		return new ListAdapterIterator(index);
	}
	
	public ListAdapter subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
		if(toIndex > size() || fromIndex < 0) throw new IndexOutOfBoundsException();
		return new ListAdapter(this, fromIndex, toIndex);
	}
	
	public class ListAdapterIterator implements HListIterator {
		private int cursor;
		private int lastCall;
		public ListAdapterIterator() {
			this(0);
		}
		public ListAdapterIterator(int index) {
			cursor = index;
			lastCall = -1;
		}
		public boolean hasNext() {
			return cursor < size();
		}
		public Object next() throws NoSuchElementException {
			if(!hasNext()) throw new NoSuchElementException();
			lastCall = cursor;
			Object e = get(cursor);
			cursor++;
			return e;
		}
		public boolean hasPrevious() {
			return cursor > 0; 
		}
		public Object previous() throws NoSuchElementException {
			if(!hasPrevious()) throw new NoSuchElementException();
			cursor -= 1;
			lastCall = cursor;
			return get(cursor);
		}
		public int nextIndex() {
			if(hasNext())
				return cursor;
			return size();
		}
		public int previousIndex() {
			if(hasPrevious())
				return cursor - 1;
			return -1;
		}
		public void remove() throws IllegalStateException {
			if(lastCall < 0) throw new IllegalStateException();
			ListAdapter.this.remove(lastCall);
			if(cursor > lastCall) cursor--;
			lastCall = -1;
		}
		public void set(Object o) throws IllegalStateException {
			if(lastCall <0) throw new IllegalStateException();
			ListAdapter.this.set(lastCall, o);
		}
		public void add(Object o) {
			ListAdapter.this.add(cursor, o);
			cursor++;
			lastCall = -1;
		}
	}
}
