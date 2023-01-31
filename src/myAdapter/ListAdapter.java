package myAdapter;

import static org.junit.Assert.assertNotNull;

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
	private final int from, to;
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
		if(fromIndex<0) throw new IndexOutOfBoundsException("A negative start index has been passed as argument!");v = mother.v;
		motherList = mother;
		from = fromIndex + mother.from;
		to = toIndex + from;
		if(to > v.size()) throw new IndexOutOfBoundsException("The high endpoint index is greater than the list size!"); 
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
		Object[] a = new Object[to - from];
		for(int i = from; i < to; i++)
			a[i - from] = v.elementAt(i);
		return a;
	}
	
	public Object[] toArray(Object[] a) throws NullPointerException {
		if(a == null) throw new NullPointerException();
		if(a.length < to - from) return toArray();
		int i = from;
		while(i < to) {
			a[i - from] = v.elementAt(i);
			i++;
		}
		if(a.length > to - from) {
			i -= from;
			while(i < a.length) {
				a[i] = null;
				i++;
			}
		}
		return a;
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
			return cursor < to;
		}
		public Object next() throws NoSuchElementException {
			if(!hasNext()) throw new NoSuchElementException();
			cursor += 1;
			lastCall = cursor;
			return v.elementAt(cursor);
		}
		public boolean hasPrevious() {
			return cursor >= from; 
		}
		public Object previous() throws NoSuchElementException {
			if(!hasPrevious()) throw new NoSuchElementException();
			cursor -= 1;
			lastCall = cursor;
			return v.elementAt(cursor);
		}
		public int nextIndex() {
			return hasNext() ? cursor + 1 : size();
		}
		public int previousIndex() {
			return hasPrevious() ? cursor - 1 : -1;
		}
		public void remove() throws IllegalStateException {
			if(lastCall <0) throw new IllegalStateException();
			ListAdapter.this.remove(lastCall);
			if(cursor >= lastCall) cursor--;
			lastCall = -1;
		}
		public void set(Object o) throws IllegalStateException {
			if(lastCall <0) throw new IllegalStateException();
			ListAdapter.this.set(lastCall, o);
			lastCall = -1;
		}
		public void add(Object o) {
			ListAdapter.this.add(cursor, o);
			cursor++;
		}
	}
}
