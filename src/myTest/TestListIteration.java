package myTest;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.ListAdapter;

/**
 * <p><b>Summary: </b> test case for the iterator of the ListAdapter class implementing the
 * HListIterator interface</p>
 * <div style="display:grid; grid-template-columns: 20% 1fr; align-items: center;"> 
 * <b>Test Case Design</b>
 * <p> An empty list and pokedex-like list are used to test iteration and check edge-cases.</p>
 * <b>Test Description & Expected Results</b>
 * <p>See test methods</p>
 * <b>Pre-Condition</b>
 * <p>ListAdapter methods must work as specified in the HList interface. The void and HCollection
 * constructor must work as intended.</p>
 * <b>Post-Condition</b>
 * <p>All tests are passed with the expected results.</p>
 * </div>
 */
public class TestListIteration {
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
	 * <b>Description: </b> checks if an iterator on an empty list works as expected
	 * <br>
	 * <b>Expected Results: </b> there should be no next or previous elements; previous index should return -1 
	 * and next index 0.
	 */
	@Test
	public void testEmptyListIterator() {
		HListIterator i = emptylist.listIterator();
		assertFalse(i.hasNext());
		assertFalse(i.hasPrevious());
		boolean correctIndex = i.previousIndex() == -1;
		assertTrue(correctIndex);
		correctIndex = i.nextIndex() == 0;
		assertTrue(correctIndex);
	}
	
	/**
	 * <b>Description: </b> checks if the specified index corresponds to the element returned by the first call
	 * to next. Checks for IndexOutOfBoundsException if the index is out of bounds.
	 * <br>
	 * <b>Expected Results: </b>
	 */
	@Test
	public void testIteratorFromIndex() {
		boolean exceptionThrown = false;
		try {
			list.listIterator(-4);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		exceptionThrown = false;
		try {
			list.listIterator(list.size() + 1);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		for(int i = 0; i <= list.size(); i++) {
			if(i < list.size())
				assertEquals(list.get(i), list.listIterator(i).next());
			if(i > 0)
				assertEquals(list.get(i - 1), list.listIterator(i).previous());
		}
	}
	
	/**
	 * <b>Description: </b> checks insertion of the provided element at the current position of the index.
	 * <br>
	 * <b>Expected Results: </b> the element should be inserted immediately before the next element that
	 * would be returned by next and after the next element that would be returned by previous. A subsequent
	 * call to next should be unaffected and a call to previous should return the added element
	 */
	@Test
	public void testAdd() {
		HListIterator i = list.listIterator(4);
		Object n = i.next();
		Object p = i.previous();
		i.add("Pikachu");
		assertEquals(n, i.next());
		i.previous();
		assertNotEquals(p, i.previous());
		assertEquals(list.get(4), "Pikachu");
		boolean sizeGrew = list.size() > pokedex.length;
		assertTrue(sizeGrew);
	}
	
	/**
	 * <b>Description: </b> checks hasNext when there is a next element and where there isn't
	 * <br>
	 * <b>Expected Results: </b> if hasNext return false, then the next call to next should throw an exception
	 */
	@Test
	public void testHasNext() {
		HListIterator i = list.listIterator();
		boolean exceptionThrown = false;
		while(i.hasNext()) {
			try {
				i.next();
			}
			catch(NoSuchElementException e) {
				exceptionThrown = true;
			}
			assertFalse(exceptionThrown);
		}
		exceptionThrown = false;
		try {
			i.next();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	/**
	 * <b>Description: </b> checks hasPrevious when there is a previous element and where there isn't
	 * <br>
	 * <b>Expected Results: </b> if hasPrevious return false, then the next call to previous should throw
	 * an exception
	 */
	@Test
	public void testHasPrevious() {
		HListIterator i = list.listIterator(list.size());
		boolean exceptionThrown = false;
		while(i.hasPrevious()) {
			try {
				i.previous();
			}
			catch(NoSuchElementException e) {
				exceptionThrown = true;
			}
			assertFalse(exceptionThrown);
		}
		exceptionThrown = false;
		try {
			i.previous();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	/**
	 * <b>Description: </b> checks that the element returned is the next in the list. Also checks that calling
	 * next and previous back and forth return the same elements. Checks for NoSuchElementException if there's
	 * no next element
	 * <br>
	 * <b>Expected Results: </b> the returned element is the next, NoSuchElementException is thrown when
	 * hasNext returns false and the same elements are returned by calling next and previous back and forth
	 */
	@Test
	public void testNext() {
		HListIterator i = list.listIterator();
		int x = 0;
		while(i.hasNext()) {
			assertEquals(list.get(x), i.next());
			x++;
		}
		assertFalse(i.hasNext());
		boolean exceptionThrown = false;
		try {
			i.next();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		Object p = i.previous();
		Object n = i.next();
		assertEquals(p, i.previous());
		assertEquals(n, i.next());
	}
	
	/**
	 * <b>Description: </b> checks that the returned index corresponds to the index of the element returned by
	 * next. Also checks that the returned index when hasNext is false is the list size
	 * <br>
	 * <b>Expected Results: </b> the returned index and the next element's index in the list are equals, when
	 * hasNext is false the returned index is equal to the list size
	 */
	@Test
	public void testNextIndex() {
		HListIterator i = list.listIterator();
		while(i.hasNext()) {
			int x = i.nextIndex();
			boolean sameIndex = x == list.subList(0, x + 1).lastIndexOf(i.next());
			assertTrue(sameIndex);
		}
		assertFalse(i.hasNext());
		boolean indexEqualsSize = list.size() == i.nextIndex();
		assertTrue(indexEqualsSize);
	}
	
	/**
	 * <b>Description: </b> checks that the element returned is the previous in the list. Also checks that calling
	 * next and previous back and forth return the same elements. Checks for NoSuchElementException if there's
	 * no previous element
	 * <br>
	 * <b>Expected Results: </b> the returned element is the previous, NoSuchElementException is thrown when
	 * hasPrevious returns false and the same elements are returned by calling next and previous back and forth
	 */
	@Test
	public void testPrevious() {
		HListIterator i = list.listIterator(list.size());
		int x = list.size() - 1;
		while(i.hasPrevious()) {
			assertEquals(list.get(x), i.previous());
			x--;
		}
		assertFalse(i.hasPrevious());
		boolean exceptionThrown = false;
		try {
			i.previous();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		Object n = i.next();
		Object p = i.previous();
		assertEquals(n, i.next());
		assertEquals(p, i.previous());
	}
	
	/**
	 * <b>Description: </b> checks that the returned index corresponds to the index of the element returned by
	 * previous. Also checks that the returned index when hasPrevious is false is -1
	 * <br>
	 * <b>Expected Results: </b> the returned index and the previous element's index in the list are equals,
	 * when hasPrevious is false the returned index is equal -1
	 */
	@Test
	public void testPreviousIndex() {
		HListIterator i = list.listIterator(list.size());
		while(i.hasPrevious()) {
			int x = i.previousIndex();
			boolean sameIndex = x == list.subList(0, x + 1).lastIndexOf(i.previous());
			assertTrue(sameIndex);
		}
		assertFalse(i.hasPrevious());
		boolean indexEqualsSize = -1 == i.previousIndex();
		assertTrue(indexEqualsSize);
	}
	
	/**
	 * <b>Description: </b> checks removal of the last element returned by previous/next. Also checks that
	 * remove can be called once per call of previous/next and only if add has not been called in the meanwhile
	 * <br>
	 * <b>Expected Results: </b> the element returned by the last call to next/previous had been successfully
	 * removed from the list. Calling remove without calling next/previous at least once since iterator
	 *  initialisation or last add call, throws IllegalStateException
	 */
	@Test
	public void testRemove() {
		HListIterator i = list.listIterator(3);
		boolean exceptionThrown = false;
		try {
			i.remove();
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		Object o = i.next();
		assertTrue(list.contains(o));
		i.remove();
		assertFalse(list.contains(o));
		exceptionThrown = false;
		try {
			i.remove();
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		o = i.previous();
		assertTrue(list.contains(o));
		i.remove();
		assertFalse(list.contains(o));
		exceptionThrown = false;
		try {
			i.next();
			i.add("Alcremie");
			i.remove();
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	/**
	 * <b>Description: </b> checks the correct setting of the last element returned by previous/next to the
	 * provided Object. Also checks that set can be called once per call of previous/next and only if add 
	 * or remove have not been called in the meanwhile
	 * <br>
	 * <b>Expected Results: </b> the element returned by the last call to next/previous had been successfully
	 * set to the provided Object. Calling set without calling next/previous at least once since iterator
	 * initialisation or last add/remove call, throws IllegalStateException
	 */
	@Test
	public void testSet() {
		HListIterator i = list.listIterator(3);
		boolean exceptionThrown = false;
		try {
			i.set("Talonflame");
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		int x = i.nextIndex();
		Object o = i.next();
		i.set("Porygon");
		assertNotEquals(list.get(x), o);
		i.set(o);
		assertEquals(list.get(x), o);
		x = i.previousIndex();
		o = i.previous();
		i.set("Porygon");
		assertNotEquals(list.get(x), o);
		i.set(o);
		assertEquals(list.get(x), o);
		exceptionThrown = false;
		try {
			i.add("Alcremie");
			i.set("Arcanine");
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		exceptionThrown = false;
		try {
			i.next();
			i.remove();
			i.set("Arcanine");
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
}
