package myTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * <p><b>Summary: </b>
 * Test case aimed at testing inspection methods</p>
 * <div style="display:grid; grid-template-columns: 20% 1fr; align-items: center;">
 * <b>Test Case Design</b>
 * <p>Test case aimed at testing inspection methods for the ListAdapter implementation of the HList interface.
 * A collection of pokemon is used as a list to inspect.</p>
 * <b>Test Description & Expected results</b>
 * <p>See test methods.</p>
 * <b>Pre-Condition</b>
 * <p>Add method must work as intended. The void and HCollection constructor must work as intended.</p>
 * <b>Post-Condition</b>
 * <p>Each item in the list is in its correct position.</p>
 * </div>
 */

public class TestListInspection {
	private HList list;
	private static String[] pokedex = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon",
			"Charizard", "Squirtle", "Wartortle", "Blastoise"};
	@Before
	public void setUp() {
		list = new ListAdapter();
		for(String mon: pokedex)
			list.add(mon);
	}
	/**
	 * <b>Description: </b> checks if notable items are contained
	 * <br>
	 * <b>Expected results: </b> Each pokemon in the pokedex must be contained in the list; contains must work as specified
	 * with null.
	 */
	@Test
	public void testContains() {
		for(String mon: pokedex)
			assertTrue(list.contains(mon));
		assertFalse(list.contains("Zamazenta"));
		assertFalse(list.contains(null));
		list.add(null);
		assertTrue(list.contains(null));
	}
	/**
	 * <b>Description: </b> checks for notable item collections and mix-ups
	 * <br>
	 * <b>Expected results: </b> Empty list must be contained; a list also must contain itself; it should work as specified
	 * with null elements. List size must be greater than or equal to collection size.
	 */
	@Test
	public void testContainsAll() {
		HCollection c = new ListAdapter();
		assertTrue(list.containsAll(c));
		c = new ListAdapter(list);
		assertTrue(list.containsAll(c));
		c.add("Garganacl");
		assertFalse(list.containsAll(c));
		list.add(null);
		c.remove("Garganacl");
		c.add(null);
		assertTrue(list.containsAll(c));
		boolean gtSize = list.size() >= c.size();
		assertTrue(gtSize);
	}
	/**
	 * <b>Description: </b> Two lists are defined to be equal if they contain the same elements in the same order
	 * <br>
	 * <b>Expected results: </b> null returns false; a list is equal to itself and its copy; two list with
	 * same items but different order are not equals
	 */
	@Test
	public void testEquals() {
		HList list2 = new ListAdapter(list);
		assertFalse(list.equals(null));
		assertTrue(list.equals(list));
		assertTrue(list.equals(list2));
		list2.add(null);
		list2.add("Rayquaza");
		list.add("Rayquaza");
		assertFalse(list.equals(list2));
		list.add(null);
		assertFalse(list.equals(list2));
	}
	/**
	 * <b>Description: </b> check for the correct position of each item
	 * <br>
	 * <b>Expected results: </b> Each pokemon must be in the correct position; IndexOutOfBoundsException must be thrown.
	 */
	@Test
	public void testGet() {
		for(int i = 0; i < pokedex.length; i++)
			assertEquals(pokedex[i], list.get(i));
		assertEquals(pokedex[pokedex.length - 1], list.get(list.size() - 1));
		boolean exceptionThrown = false;
		try {
			list.get(1000);
			list.get(-1);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	/**
	 * <b>Description: </b> checks the correct index for each item in a non-repetitive list and check for
	 * the first occurrence in a repetitive list
	 * <br>
	 * <b>Expected results: </b> Each pokemon in the pokedex must be in the correct position in the list;
	 * unlisted pokemons return -1; only the index of the first occurrence is returned
	 */
	@Test
	public void testIndexOf() {
		for(int i = 0; i < pokedex.length; i++) {
			boolean sameMon = i == list.indexOf(pokedex[i]);
			assertTrue(sameMon);
		}
		boolean unlisted = list.indexOf("Magearna") == -1;
		assertTrue(unlisted);
		list.add("Charmander");
		boolean isLastIndex = list.indexOf("Charmander") == (list.size() - 1);
		assertFalse(isLastIndex);
	}
	/**
	 * <b>Description: </b> checks list emptiness of a new list, populated list and 0-sized list
	 * <br>
	 * <b>Expected results: </b> A new list must be empty; an empty list is a zero-sized list; a populated list is not empty
	 */
	@Test
	public void testIsEmpty() {
		HList emptylist = new ListAdapter();
		assertTrue(emptylist.isEmpty());
		boolean zeroSize = emptylist.size() == 0;
		assertTrue(zeroSize);
		assertFalse(list.isEmpty());
	}
	/**
	 * <b>Description: </b> checks the correct index for each item in a non-repetitive list and check for
	 * the last occurrence in a repetitive list
	 * <br>
	 * <b>Expected results: </b> Each pokemon in the pokedex must be in the correct position in the list;
	 * unlisted pokemons return -1; only the index of the last occurrence is returned
	 */
	@Test
	public void testLastIndexOf() {
		for(int i = 0; i < pokedex.length; i++) {
			boolean sameMon = i == list.lastIndexOf(pokedex[i]);
			assertTrue(sameMon);
		}
		boolean unlisted = list.lastIndexOf("Magearna") == -1;
		assertTrue(unlisted);
		list.add("Charmander");
		boolean isLastIndex = list.lastIndexOf("Charmander") == (list.size() - 1);
		assertTrue(isLastIndex);
	}
	/**
	 * <b>Description: </b> checks size of populated list with known items and new list
	 * <br>
	 * <b>Expected results: </b> A new list must be 0-sized; populated list size must be the number of elements contained;
	 */
	@Test
	public void testSize() {
		boolean sameSize = pokedex.length == list.size();
		assertTrue(sameSize);
		sameSize = 0 == (new ListAdapter()).size();
		assertTrue(sameSize);
	}
	/**
	 * <b>Description: </b> checks correspondence between returned array items and list items for populated
	 * lists and empty lists
	 * <br>
	 * <b>Expected results: </b> The returned array must contain all elements of the list, in the correct order. An empty list returns
	 * an empty array.
	 */
	@Test
	public void testToArray() {
		Object[] a = list.toArray();
		boolean sameSize = a.length == list.size();
		assertTrue(sameSize);
		for(int i = 0; i < a.length; i++) {
			boolean sameIndex = i == list.indexOf(a[i]);
			assertTrue(sameIndex);
		}
		sameSize = (new ListAdapter()).toArray().length == 0;
		assertTrue(sameSize);
	}
	/**
	 * <b>Description: </b> same as toArray() but target array is populated with null if bigger than list
	 * <br>
	 * <b>Expected results: </b> The returned array must contain all elements of the list, in the correct order. An empty list returns
	 * an empty array. A bigger target array than list is populated by items of the list and null.
	 * A smaller target array is redefined to contain all items of the list. A null target array must
	 * throw NullPointerException
	 */
	@Test
	public void testToArrayWithTarget() {
		boolean exceptionThrown = false;
		try {
			list.toArray(null);
		}
		catch(NullPointerException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);

		boolean sameSize = (new ListAdapter()).toArray().length == 0;
		assertTrue(sameSize);
		
		Object[] a = new Object[0];
		a = list.toArray(a);
		for(int i = 0; i < a.length; i++) {
			boolean sameIndex = i == list.indexOf(a[i]);
			assertTrue(sameIndex);
		}
		a = new Object[100];
		a = list.toArray(a);
		for(int i = 0; i < a.length; i++) {
			if(i >= list.size()) {
				assertEquals(a[i], null);
			}
			else {
				boolean sameIndex = i == list.indexOf(a[i]);
				assertTrue(sameIndex);
			}
		}
	}
}
