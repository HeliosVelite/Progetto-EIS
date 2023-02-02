package myTest;

import static org.junit.Assert.*;

import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * <p><b>Summary: </b> Test case used to test that the ListAdapter implementation of the List interface
 * constructors are working properly.<br> This test case covers the void constructor and the HCollection
 * constructor.</p>
 * <div style="display:grid; grid-template-columns: 20% 1fr; align-items: center;">
 * <b>Test Case Design</b>
 * <p>A collection of Pokemon is being used to test the correct functionality of the constructors.
 * The size and content of the constructed list are going to be tested.</p>
 * <b>Test Description & Expected results</b>
 * <p>See test methods</p>
 * <b>Pre-Condition</b>
 * <p>isEmpty, size, toArray and get methods must work as intended. Add method must work in order to
 * build the collection for testing the collection constructor.</p>
 * <b>Post-Condition</b>
 * <p>All expected results are met.</p>
 * </div>
 */
public class TestListConstructor {
	
	private final String[] pokedex = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon",
			"Charizard", "Squirtle", "Wartortle", "Blastoise"};
	/**
	 * <b>Description: </b> the generated list is checked to be empty through the use of isEmpty,
	 * size and get.
	 * <br>
	 * <b>Expected results: </b> the list is empty and 0-sized
	 */
	@Test
	public void testVoidConstructor() {
		HList list = new ListAdapter();
		assertTrue(list.isEmpty());
		// assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		boolean exceptionThrown = false;
		try {
			list.get(0);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		boolean zeroSize = list.size() == 0;
		assertTrue(zeroSize);
	}
	/**
	 * <b>Description: </b>  method isEmpty must be false, the size must be correct and each item must be
	 * at the expected index.
	 * <br>
	 * <b>Expected results: </b> list size and content are equal to the pokedex length; also it's not empty
	 */
	@Test
	public void testCollectionConstructor() {
		HCollection listDex = new ListAdapter();
		for(String mon: pokedex)
			listDex.add(mon);
		HList list = new ListAdapter(listDex);
		assertArrayEquals(pokedex, list.toArray());
		// repetitive test, but useful in case toArray doesn't work properly
		for(int i = 0; i < pokedex.length; i++)
			assertEquals(pokedex[i], list.get(i));
		assertFalse(list.isEmpty());
	}

}
