package myTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * 
 */
public class TestListSuite {

	final String[] pokedex = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon",
			"Charizard", "Squirtle", "Wartortle", "Blastoise"};
	static long timeStart = 0;
	HList list, emptylist;
	/**
     * BeforeClass JUnit static method. Announces the the test begin
     * and starts its timer.
     */
	@BeforeClass
	public static void beforeClassMethod() {
		System.out.println("TestListSuite started.");
		timeStart = System.currentTimeMillis();
	}
	/**
	 * Before JUnit Method. It initialises the instances of ListAdapter used in this test suite.
	 */
	@Before
	public void setUp() {
		emptylist = new ListAdapter();
		list = new ListAdapter();
		for(String mon: pokedex)
			list.add(mon);
	}
	/**
     * AfterClass JUnit static method. Announces the the test end
     * and stops its timer. Prints milliseconds elapsed from the beginning.
     */
    @AfterClass
    public static void afterClassMethod(){
        System.out.println("TestListSuite ended. Time elapsed " + (System.currentTimeMillis() - timeStart)  + "ms.");
    }
    /**
     * <b>Summary</b>: test the initialisation of an empty list through the default constructor
     * <br>
     * <b>Test Case Design</b>: test the emptiness of a list created from the default constructor
     * <br>
     * <b>Test Description</b>: a list is instantiated through the default constructor and it's asserted to be empty through the use of isEmpty(), checking
     * that its size is 0 and that calling get(0) throws IndexOutOfBounds exception
     * <br>
     * <b>Pre-Condition</b>:  none
     * <br>
     * <b>Post-Condition</b>:  the instantiated list is empty
     * <br>
     * <b>Expected Results</b>:  isEmpty() returns true, the list size is 0 and get(0) throws IndexOutOfBoundsException
     */
	@Test
	public void testDefaultConstructor() {
		HList newList = new ListAdapter();
		assertTrue(newList.isEmpty());
		boolean exceptionThrown = false;
		try {
			newList.get(0);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		boolean zeroSize = newList.size() == 0;
		assertTrue(zeroSize);
	}
	/**
     * <b>Summary</b>: test the initialisation of a list through the collection constructor
     * <br>
     * <b>Test Case Design</b>: test that a list instantiated by passing a collection to its constructor contains each element of the provided collection
     * <br>
     * <b>Test Description</b>: a list is instantiated by passing a pokedex list as its constructor's argument, then it's checked element by element to see
     * if each pokemon in the pokedex list is contained. Also it's checked to be equal by size and through the toArray() method. Then another list is
     * Instantiated by passing an empty list as argument of its constructor and the same tests are repeated on it, plus a check on its emptiness.
     * <br>
     * <b>Pre-Condition</b>:  none
     * <br>
     * <b>Post-Condition</b>:  the instantiated list is equal to the pokedex list and then to the empty list
     * <br>
     * <b>Expected Results</b>:  the list is equals element per element to the pokedex list, they have the same size and toArray() returns the same array.
     * Same for the list instantiated by passing an empty list, plus it's empty.
     */
	@Test
	public void testCollectionConstructor() {
		HList newList = new ListAdapter(list);
		assertArrayEquals("The istantiated list should have the same elements of the provided collection.", list.toArray(), newList.toArray());
		for(int i = 0; i < list.size(); i++)
			assertEquals("The istantiated list shoul have the same elements of the provided collection.", newList.get(i), list.get(i));
		boolean sameSize = list.size() == newList.size();
		assertTrue("The istantiated list should have the same size of the provided collection.", sameSize);
		newList = new ListAdapter(emptylist);
		assertArrayEquals("The istantiated list should have the same elements of the provided collection.", emptylist.toArray(), newList.toArray());
		sameSize = emptylist.size() == newList.size();
		assertTrue("The istantiated list should have the same size of the provided collection.", sameSize && newList.isEmpty());
	}

    /**
     * <b>Summary</b>: test the contains(Object o) method
     * <br>
     * <b>Test Case Design</b>: test the contains(Object o) method a pokedex list and an empty list checking for notable elements.
     * <br>
     * <b>Test Description</b>:  test that each pokemon in the pokedex is contained in the list but not in the empty list, 
     * and test that pokemon outside of the pokedex are not contained in both. Then test null before and after adding it to the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex, and the empty list is empty
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex and null, the empty list contains just null
     * <br>
     * <b>Expected Results</b>: pokemon in the pokedex are contained in the pokedex list but not in the empty list, pokemon outside of the
     * pokedex are not contained in both. Null is contained only after adding it
     */
	@Test
	public void testContains() {
		for(String mon: pokedex) {
			assertTrue("The list should contain all the elements from its initialisation collection.", list.contains(mon));
			assertFalse("The empty list should noytcontain the elements from any collection.", emptylist.contains(mon));
		}
		assertFalse("The list should not contain elements outside its initialisation collection.", list.contains("Zamazenta"));
		assertFalse("The empty list should not contain any elements.", emptylist.contains("Zamazenta"));
		assertFalse("Null should not be contained as it's outside of the intended collection.", list.contains(null));
		assertFalse("The empty list should not contain any elements.", emptylist.contains(null));
		list.add(null);
		emptylist.add(null);
		assertTrue("Null should be contained after adding it.", list.contains(null) && emptylist.contains(null));
	}
	/**
     * <b>Summary</b>: test the containsAll(HCollection c)
     * <br>
     * <b>Test Case Design</b>: test the containsAll(HCollection c) method on a pokedex list using notable collections as argument
     * <br>
     * <b>Test Description</b>: test the containsAll(HCollection c) method on a pokedex list using as argument an empty list and an equal list. Then test the 
     * method adding and removing a pokemon  from the argument collection and adding null to both the list and the collection.
     * Then test that the list size is greater or equal to the collection size.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Expected Results</b>: an empty list is always contained in any list, a list is always contained in itself. Adding the same element to both the collection
     * and the list still returns true, while adding a new element not contained in the list to the collection returns false.
     */
	@Test
	public void testContainsAll() {
		assertTrue("A list should contain an emptylist.", list.containsAll(emptylist));
		assertTrue("A list should contain itself.", list.containsAll(list));
		HCollection c = new ListAdapter(list);
		c.add("Garganacl");
		assertFalse("A list should not contain foreign elements.", list.containsAll(c));
		list.add(null);
		c.remove("Garganacl");
		c.add(null);
		assertTrue("If a collection was contained in a list, then after adding the same element to both, the collection should still be contained in the list.", list.containsAll(c));
		boolean gtSize = list.size() >= c.size();
		assertTrue("A list should not contain a collection of size greater than itself.", gtSize);
	}
    /**
     * <b>Summary</b>: test the equals() method
     * <br>
     * <b>Test Case Design</b>: test the equals() method on an empty and a pokedex list, two lists are defined to be equal if they contain the
     * same elements in the same order
     * <br>
     * <b>Test Description</b>: test a list for equality with null, itself and its clone. Then test equality after adding a pokemon and null the the list and its clone
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex, plus null and Rayquaza
     * <br>
     * <b>Expected Results</b>: a populated list is not equal to null, but it's equal to itself and its clone. Adding elements makes two lists different unless the
     * elements are equals and placed in the same order.
     */
	@Test
	public void testEquals() {
		HList list2 = new ListAdapter(list);
		assertFalse("An initialised list should not be equal to null.",list.equals(null));
		assertTrue("A list should be equal to itself.", list.equals(list));
		assertTrue("A list should be equal to another list with the same elements in the same order.", list.equals(list2));
		list2.add(null);
		list2.add("Rayquaza");
		list.add("Rayquaza");
		assertFalse("A list should not be equal to another list with different elements.", list.equals(list2));
		list.add(null);
		assertFalse("A list should not be equal to another list with the same elements but in different order.", list.equals(list2));
	}
    /**
     * <b>Summary</b>: get(int index) method test case
     * <br>
     * <b>Test Case Design</b>: test the get(int index) method on a pokedex list populated only with every pokemon in the pokedex array (in the same order)
     * <br>
     * <b>Test Description</b>: invokes get through a pokedex list to check that each returned element is equal to the pokemon
     * contained in the pokedex array at the current index, then test for matching size. Then test for indexes out of bounds.
     * <br>
     * <b>Pre-Condition</b>: the list is populated only with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated only with every pokemon in the pokedex
     * <br>
     * <b>Expected Results</b>: invoking get through the pokedex list returns the same elements in the same order of the list-generating pokedex array, therefore
     * they have the same size. Also calling get with indexes out of bounds throws IndexOutOfBoundsException.
     */
	@Test
	public void testGet() {
		for(int i = 0; i < pokedex.length; i++)
			assertEquals("An unmodified list should contain the same elements in the same position as its initialisation collection.", pokedex[i], list.get(i));
		assertEquals("An unmodified list should have the same size as its initialisation collection.", pokedex[pokedex.length - 1], list.get(list.size() - 1));
		boolean exceptionThrown = false;
		try {
			list.get(-1);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("IndexOutOfBoundsException should be thrown when invoking get with a negative index.",exceptionThrown);
		exceptionThrown = false;
		try {
			list.get(list.size());
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("IndexOutOfBoundsException should be thrown when invoking get with an index greater or equal to the list size.",exceptionThrown);
	}
    /**
     * <b>Summary</b>:
     * <br>
     * <b>Test Case Design</b>: 
     * <br>
     * <b>Test Description</b>: 
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: 
     * <br>
     * <b>Expected Results</b>: 
     */

}
