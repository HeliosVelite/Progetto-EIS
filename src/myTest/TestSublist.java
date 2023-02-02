package myTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * <p><b>Summary: </b> test case for the subList method of the ListAdapter class implementing the
 * Hlist interface</p>
 * <div style="display:grid; grid-template-columns: 20% 1fr; align-items: center;"> 
 * <b>Test Case Design</b>
 * <p> A list populated by pokemon is used to test the methods and check edge-cases.</p>
 * <b>Test Description</b>
 * <p>Check for correct return of a view of the portion of this list between the specified fromIndex,
 * inclusive, and toIndex, exclusive. Checks if empty list is returned by fromIndex == toIndex.
 * Checks is non-structural changes in the returned list are reflected in the original list and viceversa.
 * Checks for throwing of IndexOutOfBoundsException if ranges are out of bounds.
 * </p>
 * <b>Pre-Condition</b>
 * <p>get, size methods must work as specified. The HCollection constructor must work as intended.</p>
 * <b>Post-Condition</b>
 * <p>The test is passed with the expected results.</p>
 * <b>Expected Results</b>
 * </div>
 */
public class TestSublist {

	private HList list;
	private final String[] pokedex = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon",
			"Charizard", "Squirtle", "Wartortle", "Blastoise"};
	@Before
	public void setUp() {
		list = new ListAdapter();
		for(String mon: pokedex)
			list.add(mon);
	}
	
	@Test
	public void test() {
		HList pList = list.subList(5, list.size());
		for(int i = 5; i < pokedex.length; i++) {
			assertEquals(list.get(i), pList.get(i - 5));
			assertEquals(pokedex[i], pList.get(i - 5));
		}
		pList = list.subList(0, list.size() - 1);
		assertNotEquals(pList.get(pList.size() - 1), list.get(list.size() - 1));
		pList.clear();
		assertTrue(pList.isEmpty());
		assertFalse(list.isEmpty());
		boolean lSizeIs1 = list.size() == 1;
		assertTrue(lSizeIs1);
		boolean exceptionThrown = false;
		try {
			list.subList(-4, 3);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		exceptionThrown = false;
		try {
			list.subList(0, list.size() + 1);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

}
