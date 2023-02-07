package myTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HIterator;
import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.ListAdapter;

/**
 * <b>Summary</b>: The test suite TestListSuite collects test cases on methods of the ListAdapter class.
 * This collection of test cases covers every method of the ListAdapter class and test their intended behaviour and edge-cases.
 * 
 * The {@link ListIterator} test cases are collected in a dedicated test suite. 
 * <br>
 * <b>Test Suite Design</b>: The test suite contains test cases designed to test the intended behaviour of the ListAdapter class' methods and their
 * edge-cases. In order to do so the test suite uses two instances of ListAdapter: an empty list and a generic list populated by an immutable collection of pokemon
 * (referred to as pokedex).
 * <br>
 * <b>Pre-Condition</b>: none
 * <br>
 * <b>Post-Condition</b>: none
 * <br>
 * <b>Test Cases</b>:
 * <li>
 * 		{@link testContains}
 * </li>
 * <li>
 * 		{@link testContainsAll}
 * </li>
 * <li>
 * 		{@link testEquals}
 * </li>
 * <li>
 * 		{@link testGet}
 * </li>
 * <li>
 * 		{@link testIndexOf}
 * </li>
 * <li>
 * 		{@link testIsEmpty}
 * </li>
 * <li>
 * 		{@link testLastIndexOf}
 * </li>
 * <li>
 * 		{@link testSize}
 * </li>
 * <li>
 * 		{@link testToArray}
 * </li>
 * <li>
 * 		{@link testToArrayWithTarget}
 * </li>
 * <li>
 * 		{@link testHashCode}
 * </li>
 * <li>
 * 		{@link testAdd}
 * </li>
 * <li>
 * 		{@link testAddAtIndex}
 * </li>
 * <li>
 * 		{@link testAddAll}
 * </li>
 * <li>
 * 		{@link testAddAllAtIndex}
 * </li>
 * <li>
 * 		{@link testClear}
 * </li>
 * <li>
 * 		{@link testRemoveIndex}
 * </li>
 * <li>
 * 		{@link testRemoveObject}
 * </li>
 * <li>
 * 		{@link testRemoveAll}
 * </li>
 * <li>
 * 		{@link testRetainAll}
 * </li>
 * <li>
 * 		{@link testSet}
 * </li>
 * <li>
 * 		{@link testSublist}
 * </li>
 * <br>
 * <b>Test suite execution records</b>: <ul>
 * <li>JVM from JME CLDC 1.1
 * </li>
 * <li>JUnit 4.13
 * </li>
 * <li>Hamcrest 1.3
 * </li>
 * </ul>
 * <br>
 * <b>Execution Variables</b>: <ul>
 * <li>{@code String[] pokedex = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon",
			"Charizard", "Squirtle", "Wartortle", "Blastoise"};}: An immutable array of pokemon from which the list istance of ListAdapter is populated.</li>
 * <li>{@code HList list}: an instance of ListAdapter populated by every pokemon in the pokedex.</li>
 * <li>{@code HList emptylist}: an empty instance of ListAdapter.</li>
 * </ul>
 * 
 * @see TestListIteratorSuite
 * @see ListAdapter
 * @author Zuech Riccardo
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
     * <b>Summary</b>: test case for the initialisation of an empty list through the default constructor
     * <br>
     * <b>Test Case Design</b>: test the emptiness of a list created from the default constructor
     * <br>
     * <p><b>Test Description</b>: a list is instantiated through the default constructor and it's asserted to be empty through the use of isEmpty(), checking
     * that its size is 0 and that calling get(0) throws IndexOutOfBounds exception
     * <br>
     * <b>Pre-Condition</b>:  none
     * <br>
     * <b>Post-Condition</b>:  the instantiated list is empty
     * <br>
     * </p><b>Expected Results</b>:  isEmpty() returns true, the list size is 0 and get(0) throws IndexOutOfBoundsException
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
     * <b>Summary</b>: test case for the initialisation of a list through the collection constructor
     * <br>
     * <b>Test Case Design</b>: test that a list instantiated by passing a collection to its constructor contains each element of the provided collection
     * <br>
     * <p><b>Test Description</b>: a list is instantiated by passing a pokedex list as its constructor's argument, then it's checked element by element to see
     * if each pokemon in the pokedex list is contained. Also it's checked to be equal by size and through the toArray() method. Then another list is
     * Instantiated by passing an empty list as argument of its constructor and the same tests are repeated on it, plus a check on its emptiness.
     * <br>
     * <b>Pre-Condition</b>:  none
     * <br>
     * <b>Post-Condition</b>:  the instantiated list is equal to the pokedex list and then to the empty list
     * <br>
     * </p><b>Expected Results</b>:  the list is equals element per element to the pokedex list, they have the same size and toArray() returns the same array.
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
     * <b>Summary</b>: test case for the contains(Object o) method
     * <br>
     * <b>Test Case Design</b>: test the contains(Object o) method a pokedex list and an empty list checking for notable elements.
     * <br>
     * <p><b>Test Description</b>:  test that each pokemon in the pokedex is contained in the list but not in the empty list, 
     * and test that pokemon outside of the pokedex are not contained in both. Then test null before and after adding it to the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex, and the empty list is empty
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex and null, the empty list contains just null
     * <br>
     * </p><b>Expected Results</b>: pokemon in the pokedex are contained in the pokedex list but not in the empty list, pokemon outside of the
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
     * <b>Summary</b>: test case for the containsAll(HCollection c)
     * <br>
     * <b>Test Case Design</b>: test the containsAll(HCollection c) method on a pokedex list using notable collections as argument
     * <br>
     * <p><b>Test Description</b>: test the containsAll(HCollection c) method on a pokedex list using as argument an empty list and an equal list. Then test the 
     * method adding and removing a pokemon  from the argument collection and adding null to both the list and the collection.
     * Then test that the list size is greater or equal to the collection size.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: an empty list is always contained in any list, a list is always contained in itself. Adding the same element to both the collection
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
     * <b>Summary</b>: test case for the equals() method
     * <br>
     * <b>Test Case Design</b>: test the equals() method on an empty and a pokedex list, two lists are defined to be equal if they contain the
     * same elements in the same order
     * <br>
     * <p><b>Test Description</b>: test a list for equality with null, itself and its clone. Then test equality after adding a pokemon and null the the list and its clone
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex, plus null and Rayquaza
     * <br>
     * </p><b>Expected Results</b>: a populated list is not equal to null, but it's equal to itself and its clone. Adding elements makes two lists different unless the
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
     * <p><b>Test Description</b>: invokes get through a pokedex list to check that each returned element is equal to the pokemon
     * contained in the pokedex array at the current index, then test for matching size. Then test for indexes out of bounds.
     * <br>
     * <b>Pre-Condition</b>: the list is populated only with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated only with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: invoking get through the pokedex list returns the same elements in the same order of the list-generating pokedex array, therefore
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
     * <b>Summary</b>: test case for indexOf(Object o) method
     * <br>
     * <b>Test Case Design</b>: test the indexOf(Object o) method on non-repetitive pokedex list and on a repetitive pokedex list.
     * <br>
     * <p><b>Test Description</b>: test the returned index for each pokemon in a non-repetitive pokedex list and test the first occurrence by adding an
     * already contained pokemon to the pokedex list. Also test the method on not contained pokemon.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex, but with an extra Charmander at the end.
     * <br>
     * </p><b>Expected Results</b>: each element's first occurrence in the list should be the index occupied by the element in the pokedex array. Not contained
     * elements return -1 and adding an already contained pokemon doesn't change the value returned by the method, in other words only the index of the first
     * occurrence is returned.
     */
	@Test
	public void testIndexOf() {
		for(int i = 0; i < pokedex.length; i++) {
			boolean sameMon = i == list.indexOf(pokedex[i]);
			assertTrue("An unmodified non-repetitive list should have the first occurance of each element in the same position as its initialisation colelction.", sameMon);
		}
		boolean unlisted = list.indexOf("Magearna") == -1;
		assertTrue("The first occurance of an uncontained element should be returned as -1 (invalid index).", unlisted);
		list.add("Charmander");
		boolean isLastIndex = list.indexOf("Charmander") == (list.size() - 1);
		assertFalse("A repetitive list should return as index the first occurrance of a repetitive element.", isLastIndex);
	}
    /**
     * <b>Summary</b>: test case for isEmpty() method
     * <br>
     * <b>Test Case Design</b>: test the isEmpty() method on an empty list and a pokedex list
     * <br>
     * <p><b>Test Description</b>: test emptiness of an empty list (emptiness corresponds to zero size) and a pokedex list
     * <br>
     * <b>Pre-Condition</b>: emptylist is empty and the pokedex list is populated with every pokemon in the pokedex.
     * <br>
     * <b>Post-Condition</b>: emptylist is still empty and the pokedex list is still populated with every pokemon in the pokedex.
     * <br>
     * </p><b>Expected Results</b>: the "empty list" is empty and zero sized, while the pokedex list is not
     */
	@Test
	public void testIsEmpty() {
		boolean zeroSize = emptylist.size() == 0;
		assertTrue("An empty list should be zero sized and isEmpty() should return true.", emptylist.isEmpty() && zeroSize);
		zeroSize = list.size() == 0;
		assertTrue("A populated list should not be zero sized and isEmpty() should not return true.", !list.isEmpty() && !zeroSize);
	}
	 /**
     * <b>Summary</b>: test case for lastIndexOf(Object o) method
     * <br>
     * <b>Test Case Design</b>: test the lastIndexOf(Object o) method on non-repetitive pokedex list and on a repetitive pokedex list.
     * <br>
     * <p><b>Test Description</b>: test the returned index for each pokemon in a non-repetitive pokedex list and test the last occurrence by adding an
     * already contained pokemon to the pokedex list. Also test the method on not contained pokemon.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex, but with an extra Charmander at the end.
     * <br>
     * </p><b>Expected Results</b>: each element's last occurrence in the non-modified list should be the index occupied by the element in the pokedex array.
     * Not contained elements return -1 and adding an already contained element changes the value returned by the method, in other words only the index of 
     * the last occurrence is returned.
     */
	@Test
	public void testLastIndexOf() {
		for(int i = 0; i < pokedex.length; i++) {
			boolean sameMon = i == list.lastIndexOf(pokedex[i]);
			assertTrue("An unmodified non-repetitive list should have the last occurance of each element in the same position as its initialisation colelction.", sameMon);
			
		}
		boolean unlisted = list.lastIndexOf("Magearna") == -1;
		assertTrue("The last occurance of an uncontained element should be returned as -1 (invalid index).", unlisted);
		list.add("Charmander");
		boolean isLastIndex = list.lastIndexOf("Charmander") == (list.size() - 1);
		assertTrue("After adding an already contained element, a list should have the element's last occurrence at its end.", isLastIndex);
	}
    /**
     * <b>Summary</b>: test case for size() method
     * <br>
     * <b>Test Case Design</b>: test the size() method on a pokedex list and an empty list.
     * <br>
     * <p><b>Test Description</b>: test the size of the pokedex list, then the size of the empty list
     * <br>
     * <b>Pre-Condition</b>: one list is populated with every pokemon in the pokedex and the other is empty
     * <br>
     * <b>Post-Condition</b>: one list is still populated with every pokemon in the pokedex and the other is still empty
     * <br>
     * </p><b>Expected Results</b>: the pokedex list's size is the same as the pokedex array; the empty list size is zero
     */
	@Test
	public void testSize() {
		boolean sameSize = pokedex.length == list.size();
		assertTrue("An un-modified list should have the same size as its initialisation collection.",  sameSize);
		sameSize = 0 == (new ListAdapter()).size();
		assertTrue("An empty list size should be zero.", sameSize);
	}
    /**
     * <b>Summary</b>: test case for the toArray() method
     * <br>
     * <b>Test Case Design</b>: test the toArray() method on a pokedex list and an empty list
     * <br>
     * <p><b>Test Description</b>: test correspondence between returned array items and list items for the populated pokedex list
	 * list and the empty list
     * <br>
    * <b>Pre-Condition</b>: one list is populated with every pokemon in the pokedex and the other is empty
     * <br>
     * <b>Post-Condition</b>: one list is still populated with every pokemon in the pokedex and the other is still empty
     * <br>
     * </p><b>Expected Results</b>: the pokedex list returns a copy of the pokedex array, while the empty list returns an empty array
     */
	@Test
	public void testToArray() {
		assertArrayEquals("The array returned by an un-modified list should be the same as the array it is initialized from.", pokedex, list.toArray());
		boolean zeroSize = emptylist.toArray().length == 0;
		assertTrue("An empty list should return an empty array.", zeroSize && emptylist.isEmpty());
	}
	 /**
     * <b>Summary</b>: test case for the toArray(Object[] arrayTarget) method
     * <br>
     * <b>Test Case Design</b>: test the toArray(Object[] arrayTarget) method on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test the presence of each element of the pokedex list in arrayTarget.
     * Notable test edge-cases are using null, an array smaller than the pokedex list's size, and an array bigger as arrayTarget.
     * <br>
    * <b>Pre-Condition</b>: one list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: one list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: each element contained in the list is present in the returned arrayTarget. Using null as arrayTarget 
     * throws NullPointerException. Using a smaller array returns a new array and using a bigger array fills it with null after the last element of the list.
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
		assertTrue("NullPointerException should be thrown when using null as arrayTarget.", exceptionThrown);
		
		Object[] a = new Object[list.size() - 1];
		Object[] b = list.toArray(a);
		assertFalse("The toArray(Object[] arrayTarget) method should return a different array from arrayTarget if it has not enough storing space.", a.equals(b));
		for(int i = 0; i < b.length; i++) {
			boolean sameIndex = i == list.indexOf(b[i]);
			assertTrue("The returned arrayTarget should contain each element of the list in the correct position.", sameIndex);
		}
		a = new Object[list.size() + 100];
		b = list.toArray(a);
		assertTrue("The toArray(Object[] arrayTarget) method should return the same arrayTarget if it has enough storing space.", a.equals(b));
		for(int i = 0; i < b.length; i++) {
			if(i >= list.size()) {
				assertEquals("The returned array should be filled with null after the last element of the list.", b[i], null);
			}
			else {
				boolean sameIndex = i == list.indexOf(b[i]);
				assertTrue("The returned arrayTarget should contain each element of the list in the correct position.", sameIndex);
			}
		}
	}
	/**
     * <b>Summary</b>: test case for the hashCode() method
     * <br>
     * <b>Test Case Design</b>: test the hashCode() method on a pokedex list and an empty list
     * <br>
     * <p><b>Test Description</b>: test the hassCode() method on the pokedex list and an empty list by comparing its return value with the hashCode algorithm 
     * value for lists.
     * <br>
     * <b>Pre-Condition</b>: one list is populated with every pokemon in the pokedex and the other is empty
     * <br>
     * <b>Post-Condition</b>: one list is still populated with every pokemon in the pokedex and the other is still empty
     * <br>
     * </p><b>Expected Results</b>: the algorithm-generated and method-returned values are the same for both the pokedex list and the empty list
     */
	@Test
	public void testHashCode() {
		int hashCode = 1;
		HIterator i = list.iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
		}
		boolean equalCodes = hashCode == list.hashCode();
		assertTrue("The hashCode() method should return the same value as the hash code algorithm for lists.", equalCodes);
		hashCode = 1;
		i = emptylist.iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
		}
		equalCodes = hashCode == emptylist.hashCode();
		assertTrue("The hashCode() method should return the same value as the hash code algorithm for lists.", equalCodes);
	}
    /**
     * <b>Summary</b>: test case for the add(Object o) method
     * <br>
     * <b>Test Case Design</b>: test the add(Object o) method using a pokedex list
     * <br>
     * <p><b>Test Description</b>:  test the addition of a new pokemon at the end of the pokedex list, checking in particular the difference in size of the list
     * and the position of the added element
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex plus the one added
     * <br>
     * </p><b>Expected Results</b>: the new pokemon is added at the end of the list, making it grow by 1 in size
     */
	@Test
	public void testAdd() {
		int pSize = list.size();
		assertTrue("A call to the add(Object o) method of a list should always be successful.", list.add("Arcanine"));
		boolean grew = pSize < list.size();
		assertTrue("The call to add(Objecy o) should add the object o, making the list grow in size by 1.", grew);
		boolean lastElement = list.lastIndexOf("Arcanine") == (list.size() - 1);
		assertTrue("The last occurrence of an element just added to a list by add(Object o) should be at the end of the list.", lastElement);
	}
    /**
     * <b>Summary</b>: test case for the add(int index, Object o) method
     * <br>
     * <b>Test Case Design</b>: test the add(int index, Object o) method using a pokedex list
     * <br>
     * <p><b>Test Description</b>:  test the addition of a new pokemon at the specified index of the pokedex list, making the list grow
	 * in size and shifting already contained items to the right. Test indexes out of bounds.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex plus Arcanine
     * <br>
     * </p><b>Expected Results</b>: the new pokemon is added at the provided index, making it grow by 1 in size and shifting the other items to the right. Calling
     * the method with an index out of bounds throws IndexOutOfBoundsException
     */
	@Test
	public void testAddAtIndex() {
		boolean exceptionThrown = false;
		try {
			list.add(-1, "Arcanine");
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using a negative index should throw IndexOutOfBoundsException.", exceptionThrown);
		exceptionThrown = false;
		try {
			list.add(list.size() + 1, "Arcanine");
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using an index greater than the list's size should throw IndexOutOfBoundsException.", exceptionThrown);
		int pSize = list.size();
		list.add(3, "Arcanine");
		boolean grew = pSize < list.size();
		assertTrue("The call to add(int index, Objecy o) should add the object o at the provided index, making the list grow in size by 1.", grew);
		assertEquals("The element should be added at the provided index.", "Arcanine", list.get(3));
		for(int i = 4; i < pokedex.length; i++) {
			boolean hasShifted = i < list.indexOf(pokedex[i]);
			assertTrue("Elements from the provided index should have been shifted to the right.", hasShifted);
		}
	}
	/**
     * <b>Summary</b>: test case for the addAll(HCollection c) method
     * <br>
     * <b>Test Case Design</b>: test the addAll(HCollection c) method using a pokedex list and an empty list
     * <br>
     * <p><b>Test Description</b>:  test the addition of a second pokedex collection at the end of the pokedex list and the empty list, making the lists grow
	 * in size by the size of the pokedex collection. Test the addition of null as c.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex and the other list is empty
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex, but a second pokedex is appended at the end; the empty list is now
     * populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: the new pokedex is added at the end of the lists, making them grow by the pokedex collection in size. Calling
     * the method with null as the collection throws NullPointerException.
     */
	@Test
	public void testAddAll() {
		boolean exceptionThrown = false;
		try {
			list.addAll(null);
		}
		catch(NullPointerException e) {
			exceptionThrown = true;
		}
		assertTrue("Using null as the collection to add should throw NullPointerException..", exceptionThrown);
		HList pokedexList = new ListAdapter(list);
		int pSize = emptylist.size();
		assertTrue("The addAll(HCollection c) method should always change the list.", emptylist.addAll(pokedexList));
		boolean grew = pSize < emptylist.size();
		assertTrue("The addAll(HCollection c) method should make the list grow.", grew);
		boolean same = emptylist.size() == pokedexList.size();
		assertTrue("The addAll(HCollection c) method should increase the size of the list by the size of the collection.", same);
		assertEquals("Adding a list to an empty list should make the two lists equals.", list, emptylist);
		pSize = list.size();
		assertTrue("The addAll(HCollection c) method should always change the list.", list.addAll(pokedexList));
		grew = pSize < list.size();
		assertTrue("The addAll(HCollection c) method should make the list grow.", grew);
		same = list.size() == pSize + pokedexList.size();
		assertTrue("The addAll(HCollection c) method should increase the size of the list by the size of the collection.", same);
		HListIterator i = pokedexList.listIterator();
		while(i.hasNext())
			assertEquals("The collection should have been added at the end of the list.", list.get(i.nextIndex() + pSize), i.next());
		
	}
	/**
     * <b>Summary</b>: test case for the add(int index, HCollection c) method
     * <br>
     * <b>Test Case Design</b>: test the add(int index, HCollection c) method using a pokedex list and an empty list
     * <br>
     * <p><b>Test Description</b>:  test the addition of a new collection of pokemon at the specified index of the pokedex list and the empty list, making them grow
	 * in size and shifting already contained items to the right. Test indexes out of bounds.
     * <br>
     * <b>Pre-Condition</b>: one list is populated with every pokemon in the pokedex 
     * <br>
     * <b>Post-Condition</b>: the list is populated with every pokemon in the pokedex, but a second pokedex is appended at the specified index;
     * the empty list is now populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: the new pokedex collection is added at the provided indexes, making the lists grow by the collection's size 
     * and shifting the other items to the right. Calling the method with an index out of bounds throws IndexOutOfBoundsException
     */
	@Test
	public void testAddAllAtIndex() {
		boolean exceptionThrown = false;
		try {
			list.add(-1, list);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using a negative index should throw IndexOutOfBoundsException.", exceptionThrown);
		exceptionThrown = false;
		try {
			list.add(list.size() + 1, list);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using an index greater than the list's size should throw IndexOutOfBoundsException.", exceptionThrown);
		assertTrue("The addAll(int index, HCollection c) method should always change the list.", emptylist.addAll(0, list));
		boolean grew = emptylist.size() == list.size();
		assertTrue("The addAll(HCollection c) method should make the list grow.", grew);
		assertEquals("Adding a list to an empty list should make it equal to that list.", list, emptylist);
		
		assertTrue("The addAll(int index, HCollection c) method should always change the list.", list.addAll(3, emptylist));
		for(int i = 0; i < list.size(); i++) {
			if(i < 3)
				assertEquals("Elements before the index in the list should be the same as before the call to addAll(int index, HCollection c)", pokedex[i], list.get(i));
			else if (i >= 3 + emptylist.size())
				assertEquals("Elements after the index + size of the collection in the list should be shifted to the right by the size of the added collection.", pokedex[i - emptylist.size()], list.get(i));
			else {
				assertEquals("Elements between the index and the index + size of the collection in the list should be the same as te elements in the colelction.", emptylist.get(i - 3), list.get(i));
			}
		}
	}
    /**
     * <b>Summary</b>: test case for the clear() method
     * <br>
     * <b>Test Case Design</b>: test the clear() method on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test emptiness of the pokedex list after calling clear() on the list
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is now empty
     * <br>
     * </p><b>Expected Results</b>: the list is empty after the call to clear()
     */
	@Test
	public void testClear() {
		list.clear();
		assertTrue("A cleared list should be empty.", list.isEmpty());
	}
    /**
     * <b>Summary</b>: test case for the remove(int index) method
     * <br>
     * <b>Test Case Design</b>: test the remove(int index) method on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test indexes out of bounds, then test removal of an element at a valid index. Then tests removal of every element in the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is now empty
     * <br>
     * </p><b>Expected Results</b>: calling the method with an index out of bounds throws IndexOutOfBoundsException, while calling it with a valid index removes
     * the element at that index, shifting on the left every element at its right and returning the removed element. Removal of every element makes the list empty.
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
		assertTrue("Using a negative index should throw IndexOutOfBoundsException.", exceptionThrown);
		exceptionThrown = false;
		try {
			list.remove(list.size());
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using an index greater than the list's size should throw IndexOutOfBoundsException.", exceptionThrown);
		Object mon = list.get(3);
		assertEquals("The removed element should be the element at the provided index.", mon, list.remove(3));
		for(int i = 3; i < list.size(); i++)
			assertEquals("After calling remove(int index) the elements after the provided index should have been shifted by 1 to the left.", pokedex[i + 1], list.get(i));
		int size = list.size();
		while(size > 0) {
			mon = list.get(0);
			assertEquals("The removed element should be the element at the provided index.", mon, list.remove(0));
			size--;
		}
		assertTrue("After removing every element the list should be empty.", list.isEmpty());
	}
    /**
     * <b>Summary</b>: test case for the remove(Object o) method
     * <br>
     * <b>Test Case Design</b>: test the remove(Object o) method on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test removal of the first occurrence in this list of the specified element and shifting of any subsequent elements to the left;
     * test immutability in removal of not contained items; test removal of duplicate elements. test removal of every element contained in the list
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is now empty
     * <br>
     * </p><b>Expected Results</b>: removal of a not contained element returns false and doesn't change the list, while removal of a contained element returns
     * true and shifts any subsequent element to the left. Removal of a duplicated element removes only the first occurrence. Removal of every element contained
     * in the list makes it empty.
     */
	@Test
	public void testRemoveObject() {
		boolean contained = list.contains(null);
		assertFalse("Remove(Object o) should return false if the obejct is not contained in the list and therefore the list is not changed.", list.remove(null) || contained);
		assertArrayEquals("Remove(Object o) should not change the list if the object is not contained.", pokedex, list.toArray());
		
		contained = list.contains("Arcanine");
		assertFalse("Remove(Object o) should return false if the obejct is not contained in the list and therefore the list is not changed.", list.remove("Arcanine") || contained);
		assertArrayEquals("Remove(Object o) should not change the list if the object is not contained.", pokedex, list.toArray());
		
		int k = list.indexOf("Charizard");
		contained = list.contains("Charizard");
		assertTrue("Remove(Object o) should return true if the object is contained in the list.", list.remove("Charizard") && contained);
		for(int i = 0; i < k; i++)
			assertEquals("Remove(Object o) should not shift or change elements before the removed object.", pokedex[i], list.get(i));
		for(int i = k; i < list.size(); i++)
			assertEquals("Remove(Object o) should shift to the left elements after thee removed object.", pokedex[i + 1], list.get(i));
		
		int i = list.indexOf("Venusaur");
		list.add("Venusaur");
		contained = list.contains("Venusaur");
		boolean duplicated = list.indexOf("Venusaur") != list.lastIndexOf("Venusaur");
		assertTrue("Remove(Object o) should return true for a duplicated object.", list.remove("Venusaur") && contained && duplicated);
		duplicated = list.indexOf("Venusaur") != list.lastIndexOf("Venusaur");
		assertFalse("Remove(Object o) should remove one istance of a duplicated object.", duplicated);
		assertNotEquals("Remove(Object o) should remove the first occurrence of the object.", "Venusaur", list.get(i));
		
		int size = list.size();
		while(size > 0) {
			assertTrue("Remove(Object o) should return true when object is the first element of a not-empty list.", list.remove(list.get(0)));
			size--;
		}
		assertTrue("After removing every element the list should be empty.", list.isEmpty());
	}
    /**
     * <b>Summary</b>: test case for the removeAll(HCollection c) method
     * <br>
     * <b>Test Case Design</b>: test the removeAll(HCollection c) method using a pokedex list and an empty list
     * <br>
     * <p><b>Test Description</b>: test removal of an empty list from the pokedex list, then test removal of a clone of the pokedex list. Then test the method
     * after adding a shared and a not shared element to the lists. Test the method with null as collection.
     * <br>
     * <b>Pre-Condition</b>: one list is populated with every pokemon in the pokedex and the other is empty
     * <br>
     * <b>Post-Condition</b>: one list is populated with two pokemon, while the other with just one. They share one element.
     * <br>
     * </p><b>Expected Results</b>: calling the method with null as a collection throws NullPointerException. Removal of an empty collection doesn't change the list
     * and therefore returns false, while removal of the list itself returns true as every element is removed and the list is now empty. The method returns true
     * if the list shares at least one element with the collection and the common element are removed.
     */
	@Test
	public void testRemoveAll() {
		boolean exceptionThrown = false;
		try {
			list.removeAll(null);
		}
		catch(NullPointerException e) {
			exceptionThrown = true;
		}
		assertTrue("Using null as the collection to add should throw NullPointerException..", exceptionThrown);
		
		assertFalse("Calling removeAll(HCollection c) when c is empty should not remove any element from the list.", list.removeAll(emptylist));
		assertArrayEquals("Calling removeAll(HCollection c) when c is empty should have not changed the list.", pokedex, list.toArray());
		
		assertTrue("Calling removeAll(HCollection c) when c is the list itself should have removed all element from the list.", list.removeAll(new ListAdapter(list)));
		assertTrue("Calling removeAll(HCollection c) when c is the list itself should have made the list empty.", list.isEmpty());
		
		list.add("Chespin");
		list.add("Frokie");
		emptylist.add("Frokie");
		assertTrue("Calling removeAll(HCollection c) when c and the list share at least one item should remove the shared elements.", list.removeAll(emptylist));
		String[] a = {"Chespin"};
		assertArrayEquals("The shared elements should have been removed..", a, list.toArray());
	}
    /**
     * <b>Summary</b>: test case for the retainAll(HCollection c) method
     * <br>
     * <b>Test Case Design</b>: test the retainAll(HCollection c) method on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test removal of all the elements not in the provided collection, then test immutability when called with a
     * collection of every item in the list. Then test the method after adding a shared and a not shared element to the lists. Test when null is provided as the collection.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is populated with one pokemon not in the pokedex
     * <br>
     * </p><b>Expected Results</b>: Using null as the collection to add throws NullPointerException; the method called with a clone of the list as collection
     * returns false and therefore the list is unchanged, while using an empty list as collection returns true and every element in the list has been removed.
     * Calling the method using a collection with only one element shared with the list, returns true and keeps just that element in the list.
     */
	@Test
	public void testRetainAll() {
		boolean exceptionThrown = false;
		try {
			list.removeAll(null);
		}
		catch(NullPointerException e) {
			exceptionThrown = true;
		}
		assertTrue("Using null as the collection to add should throw NullPointerException..", exceptionThrown);
		
		assertFalse("Calling retainAll(HCollection c) when c is a clone of the list should return false as the list should be unchanged.", list.retainAll(new ListAdapter(list)));
		assertArrayEquals("Calling retainAll(HCollection c) when c is a clone of the list should not change the list", pokedex, list.toArray());
		
		assertTrue("Calling retainAll(HCollection c) when c is an empty list should return true as every element in the list should have been removed.", list.retainAll(emptylist));
		assertArrayEquals("Calling retainAll(HCollection c) when c is an empty list should have removed every element from the list..", emptylist.toArray(), list.toArray());
		
		list.add("Chespin");
		list.add("Frokie");
		emptylist.add("Frokie");
		assertTrue("Calling the method using a collection with only one element shared with the list, should return true and keep just that element in the list.", list.retainAll(emptylist));
		String[] a = {"Frokie"};
		assertArrayEquals("Calling the method using a collection with only one element shared with the list, should keep just that element in the list.", a, list.toArray());
	}

    /**
     * <b>Summary</b>: test case for the method set(int index, Object o)
     * <br>
     * <b>Test Case Design</b>: test the method set(int index, Object o) on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test the method when called with an index out of bounds. Then test the method on the pokedex list with a valid index, setting
     * the pokemon in that position to a different one outside the pokedex.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated by the same number of pokemon, but now one of them are outside of the pokedex
     * <br>
     * </p><b>Expected Results</b>: calling the method with an index out of bounds throws IndexOutOfBoundsException. Calling the method with a valid index and
     * providing a pokemon outside the pokedex changes the element at the index with the provided pokemon and returns the pokemon previously in that position
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
		assertTrue("Using an index greater than the list's size should throw IndexOutOfBoundsException.", exceptionThrown);
		exceptionThrown = false;
		try {
			list.set(list.size(), null);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using an index greater than the list's size should throw IndexOutOfBoundsException.", exceptionThrown);
		
		Object o1 = list.get(4);
		Object o2 = list.set(4, "Sandile");
		assertEquals("Calling get(int index, Object o) should have returned the element previously at the provided index.", o1, o2);
		assertNotEquals("Calling get(int index, Object o) should have changed the element at the provided index.", o1, list.get(4));
		assertEquals("Calling get(int index, Object o) should have set the element at the provided index as the provided object.", list.get(4), "Sandile");
	}
	 /**
     * <b>Summary</b>: test case for the subList(int fromIndex, int toIndex) method
     * <br>
     * <b>Test Case Design</b>: test the subList(int fromIndex, int toIndex) method on a pokedex list.
     * <br>
     * <p><b>Test Description</b>: test creating a subList of the pokedex list from the middle to its end. Test creating a sublist of the pokedex from start to the last
     * element excluded and then test reflection of changes made in in the sublist to the pokedex list (for example clearing the sublist or
     * adding an element to it). Test creating a sublist with indexes out of bounds.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is with just two pokemon, one from outside the pokedex and one from it, in this order.
     * <br>
     * </p><b>Expected Results</b>: a sublist contains every element of the list from fromIndex to toIndex (excluded). Changes to the sublist are reflected to the
     * original list, so clearing the sublist clears the original list in that range and adding an element to the sublist adds the same elements to the list relative to the
     * range of the sublist. Creating a sublist with indexes out of bounds throws IndexOutOfBoundsException.
     */
	@Test
	public void testSublist() {
		HList pList = list.subList(5, list.size());
		for(int i = 5; i < list.size(); i++) {
			assertEquals("A sublist should contain every element of the list in its range.", list.get(i), pList.get(i - 5));
		}
		
		pList = list.subList(0, list.size() - 1);
		for(int i = 0; i < list.size(); i++) {
			assertEquals("A sublist should contain every element of the list in its range.", list.get(i), pList.get(i - 0));
		}
		assertNotEquals("A sublist toIndex should be excluded.", pList.get(pList.size() - 1), list.get(list.size() - 1));
		
		pList.clear();
		assertTrue("Clearing a sublist should not clear all the original list, but just the portion covered by the sublist.", pList.isEmpty() && !list.isEmpty());
		boolean lSizeIs1 = list.size() == 1;
		assertTrue("Clearing a sublist should shrink the original list by the size of the sublist.", lSizeIs1);
		
		pList.add("Aegislash");
		assertEquals("Changes to the sublist should be reflected in the original list, relative to the ranges of the sublist.", pList.get(0), list.get(0));
		
		boolean exceptionThrown = false;
		try {
			list.subList(-4, 3);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using an index greater than the list's size should throw IndexOutOfBoundsException.", exceptionThrown);
		
		exceptionThrown = false;
		try {
			list.subList(0, list.size() + 1);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("Using an index greater than the list's size should throw IndexOutOfBoundsException.", exceptionThrown);
	}
}
