package myTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * <div style="display:grid; grid-template-columns: 20% 1fr;">
 * <b>Summary</b>
 * <p>Test case aimed at testing inspection methods</p>
 * <b>Test Case Design</b>
 * <p>Test case aimed at testing inspection methods for the ListAdapter implementation of the HList interface.
 * A collection of pokemon is used as list.</p>
 * <b>Test Description</b>
 * <p>contains: each pokemon in the pokedex must be contained in the list; contains must work as specified
 * with null.</p>
 * <p>containsAll: empty list must be contained; it also must contain itself; it should work as specified
 * with null elements. List size must be greater than or equal to collection size</p>
 * <p>equals: two lists are defined to be equal if they contain the same elements in the same order</p>
 * <p>get: each pokemon must be in the correct position; IndexOutOfBoundsException must be thrown</p>
 * <p>indexOf:</p>
 * <p>isEmpty:</p>
 * <p>lastIndexOf:</p>
 * <p>size:</p>
 * <p>toArray:</p>
 * <p>toArrayWithTarget:</p>
 * <b>Pre-Condition</b>
 * <p>Add, size methods must work as intended. The void and HCollection constructor must work as intended.</p>
 * <b>Post-Condition</b>
 * <p>Each item in the list is in its correct position.</p>
 * <b>Expected results</b>
 * <p>All tests are passed.</p>
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
	@Test
	public void testContains() {
		for(String mon: pokedex)
			assertTrue(list.contains(mon));
		assertFalse(list.contains("Zamazenta"));
		assertFalse(list.contains(null));
		list.add(null);
		assertTrue(list.contains(null));
	}
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
	@Test
	public void testIndexOf() {
		fail("Not yet implemented");
	}
	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}
	@Test
	public void testLastIndexOf() {
		fail("Not yet implemented");
	}
	@Test
	public void testSize() {
		fail("Not yet implemented");
	}
	@Test
	public void testToArray() {
		fail("Not yet implemented");
	}
	@Test
	public void testToArrayWithTarget() {
		fail("Not yet implemented");
	}
}
