package myTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * <p><b>Summary: </b> test case for manipulation methods of the ListAdapter class implementing the
 * Hlist interface</p>
 * <div style="display:grid; grid-template-columns: 20% 1fr; align-items: center;"> 
 * <b>Test Case Design</b>
 * <p> An empty list and pokedex-like list are used to test list manipulation methods and check edge-cases.</p>
 * <b>Test Description & Expected Results</b>
 * <p>See test methods</p>
 * <b>Pre-Condition</b>
 * <p>size, lastIndexOf, isEmpty, get, toArray methods must work as specified. The void and HCollection
 * constructor must work as intended.</p>
 * <b>Post-Condition</b>
 * <p>All tests are passed with the expected results.</p>
 * </div>
 */
public class TestListManipulation {
	
	private HList list, emptylist;
	private final String[] pokedex = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon",
			"Charizard", "Squirtle", "Wartortle", "Blastoise"};
	@Before
	public void setUp() {
		emptylist = new ListAdapter();
		list = new ListAdapter();
		for(String mon: pokedex)
			list.add(mon);
	}
	
	/**
	 * <b>Description: </b> checks if the item is added at the end of a populated and empty list, making
	 * the list grow in size
	 * <br>
	 * <b>Expected Results: </b> items are correctly added at the end
	 */
	@Test
	public void testAdd() {
		int pSize = list.size();
		assertTrue(list.add("Arcanine"));
		boolean grew = pSize < list.size();
		assertTrue(grew);
		boolean lastElement = list.lastIndexOf("Arcanine") == (list.size() - 1);
		assertTrue(lastElement);
	}
	
	/**
	 * <b>Description: </b> checks if the item is added at the specified index of the list, making it grow
	 * in size and shifting already contained items to the right. Checks for IndexOutOfBoundsException
	 * <br>
	 * <b>Expected Results: </b> item has been correctly added and subsequent items are correctly shifted;
	 * IndexOutOfBounds has been correctly thrown
	 */
	@Test
	public void testAddAtIndex() {
		boolean exceptionThrown = false;
		try {
			emptylist.add(-1, "Arcanine");
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		exceptionThrown = false;
		try {
			list.add(list.size() + 1, "Arcanine");
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		int pSize = list.size();
		list.add(3, "Arcanine");
		boolean grew = pSize < list.size();
		assertTrue(grew);
		boolean lastElement = list.lastIndexOf("Arcanine") == 3;
		assertTrue(lastElement);
		for(int i = 4; i < pokedex.length; i++) {
			boolean hasShifted = i < list.indexOf(pokedex[i]);
			assertTrue(hasShifted);
		}
	}
	
	/**
	 * <b>Description: </b> checks if items are added at the specified index of the list, making it grow in
	 * size and shifting already contained items to the right. Checks for IndexOutOfBoundsException
	 * <br>
	 * <b>Expected Results: </b> item has been correctly added and subsequent items are correctly shifted;
	 * IndexOutOfBounds has been correctly thrown
	 */
	@Test
	public void testAddAll() {
		boolean exceptionThrown = false;
		try {
			emptylist.add(-1, list);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		assertTrue(emptylist.addAll(0, list));
		boolean grew = emptylist.size() == list.size();
		assertTrue(grew);
		assertTrue(emptylist.addAll(3, list));
		for(int i = 0; i < emptylist.size(); i++) {
			if(i < 3)
				assertEquals(pokedex[i], emptylist.get(i));
			else if (i >= 3 + list.size())
				assertEquals(pokedex[i - list.size()], emptylist.get(i));
			else {
				assertEquals(pokedex[i - 3], emptylist.get(i));
			}
		}
	}
	
	/**
	 * <b>Description: checks for emptiness after clear call </b>
	 * <br>
	 * <b>Expected Results: the list is empty </b>
	 */
	@Test
	public void testClear() {
		list.clear();
		assertTrue(list.isEmpty());
	}
	
	/**
	 * <b>Description: </b> checks removal of the element at the specified position in this list and
	 * shifting of any subsequent elements to the left; checks for return of the element removed; checks for
	 * IndexOutOfBoundsException
	 * <br>
	 * <b>Expected Results: </b> the element has been removed, any subsequent element has been shifted and the
	 * removed element has been returned. IndexOutOfBoundsException is thrown for out of bound indexes
	 */
	@Test
	public void testRemoveIndex() {
		boolean exceptionThrown = false;
		try {
			list.remove(-1);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		exceptionThrown = false;
		try {
			list.remove(list.size());
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		Object mon = list.get(3);
		assertEquals(mon, list.remove(3));
		for(int i = 3; i < list.size(); i++)
			assertEquals(pokedex[i + 1], list.get(i));
	}
	
	/**
	 * <b>Description: </b> checks removal of the first occurrence in this list of the specified element 
	 * and shifting of any subsequent elements to the left; checks immutability in removal of 
	 * not contained items; 
	 * <br>
	 * <b>Expected Results: </b> the element has been removed, if contained in the list, otherwise the list is
	 * unchanged; items are shifted if the removal has been successful.
	 */
	@Test
	public void testRemoveObject() {
		assertFalse(list.remove(null));
		assertArrayEquals(pokedex, list.toArray());
		assertFalse(list.remove("Arcanine"));
		assertArrayEquals(pokedex, list.toArray());
		int k = list.indexOf("Charizard");
		assertTrue(list.remove("Charizard"));
		for(int i = 0; i < k; i++)
			assertEquals(pokedex[i], list.get(i));
		for(int i = k; i < list.size(); i++)
			assertEquals(pokedex[i + 1], list.get(i));
	}
	/**
	 * <b>Description: </b> checks removal of all the elements in the specified collection (even empty
	 * collection); checks immutability in removal of not contained items; 
	 * <br>
	 * <b>Expected Results: </b> all the common elements have been removed
	 */
	@Test
	public void testRemoveAll() {
		assertFalse(list.removeAll(emptylist));
		assertArrayEquals(pokedex, list.toArray());
		assertTrue(list.removeAll(new ListAdapter(list)));
		assertTrue(list.isEmpty());
		list.add("Chespin");
		list.add("Frokie");
		emptylist.add("Frokie");
		assertTrue(list.removeAll(emptylist));
		String[] a = {"Chespin"};
		assertArrayEquals(a, list.toArray());
	}
	
	/**
	 * <b>Description: </b> checks removal of all the elements not in the specified collection (even empty
	 * collection); checks immutability in retention of all contained items; 
	 * <br>
	 * <b>Expected Results: </b> all the common elements have been removed
	 */
	@Test
	public void testRetainAll() {
		assertFalse(list.retainAll(new ListAdapter(list)));
		assertArrayEquals(pokedex, list.toArray());
		assertTrue(list.retainAll(emptylist));
		assertArrayEquals(emptylist.toArray(), list.toArray());
		list.add("Chespin");
		list.add("Frokie");
		emptylist.add("Frokie");
		assertTrue(list.retainAll(emptylist));
		String[] a = {"Frokie"};
		assertArrayEquals(a, list.toArray());
	}
	
	/**
	 * <b>Description: </b> checks for the correct setting of an element at a specified index; check also for
	 * IndexOutOfBoundsException if index is out of bounds
	 * <br>
	 * <b>Expected results: </b> the element at the specified index has been changed
	 */
	@Test
	public void testSet() {
		boolean exceptionThrown = false;
		try {
			list.set(-1, null);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		exceptionThrown = false;
		try {
			list.set(list.size(), null);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		Object o1 = list.get(4);
		Object o2 = list.set(4, "Sandile");
		assertEquals(o1, o2);
		assertNotEquals(o1, list.get(4));
		assertEquals(list.get(4), "Sandile");
	}

}
