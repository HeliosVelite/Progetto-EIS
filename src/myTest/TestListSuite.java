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
		assertArrayEquals("The istantiated list doesn't have the same elements of the provided collection.", list.toArray(), newList.toArray());
		for(int i = 0; i < list.size(); i++)
			assertEquals("The istantiated list doesn't have the same elements of the provided collection.", newList.get(i), list.get(i));
		boolean sameSize = list.size() == newList.size();
		assertTrue("The istantiated list doesn't have the same size of the provided collection.", sameSize);
		newList = new ListAdapter(emptylist);
		assertArrayEquals("The istantiated list doesn't have the same elements of the provided collection.", emptylist.toArray(), newList.toArray());
		sameSize = emptylist.size() == newList.size();
		assertTrue("The istantiated list doesn't have the same size of the provided collection.", sameSize && newList.isEmpty());
	}

    /**
     * <b>Summary</b>: test the contains() method on a populated list
     * <br>
     * <b>Test Case Design</b>: test the contains() method by using a pokedex list and checking for notable elements.
     * <br>
     * <b>Test Description</b>:  test that each pokemon in the pokedex is contained in the list and that pokemon outside of the pokedex are not contained.
     * Then test null before and after adding it to the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex and null
     * <br>
     * <b>Expected Results</b>: pokemon in the pokedex are contained, pokemon outside of the pokedex are not. Null is contained only after adding it
     */
	@Test
	public void testContains() {
		for(String mon: pokedex)
			assertTrue("The list does not contain all the elements from its initialisation collection.", list.contains(mon));
		assertFalse(list.contains("Zamazenta"));
		assertFalse("Null is contained even though it's outside of the intended collection.", list.contains(null));
		list.add(null);
		assertTrue("Null is not contained after adding it.",list.contains(null));
	}
	/**
     * <b>Summary</b>: test the containsAll(HCollection c) method on a populated list
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
     * <b>Summary</b>: test the equals() method on an empty and a populated list
     * <br>
     * <b>Test Case Design</b>: test the equals() method on an empty and a pokedex list
     * <br>
     * <b>Test Description</b>: test a list for equality with null, itself and its clone. Then test equality after adding a pokemon and null the the list and its clone
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex, plus null and Rayquaza
     * <br>
     * <b>Expected Results</b>: a populated list is not equal to null, but it's equal to itself and its clone. Adding elements makes two lists different unless the
     * elements are equals
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
