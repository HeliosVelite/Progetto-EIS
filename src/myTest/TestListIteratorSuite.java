package myTest;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.ListAdapter;

/**
 * 
 */
public class TestListIteratorSuite {
	
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
     * <b>Summary</b>: test case for the listIterator() method of on an empty instance of the ListAdapter class
     * <br>
     * <b>Test Case Design</b>: test the list iterator on an empty list
     * <br>
     * <p><b>Test Description</b>: creates an iterator on the empty list and test the next and previous elements and their indexes.
     * <br>
     * <b>Pre-Condition</b>: the list is empty
     * <br>
     * <b>Post-Condition</b>: the list is still empty
     * <br>
     * </p><b>Expected Results</b>: an iterator on an empty list has no previous or next element, therefore previousIndex() returns -1 and next() returns 0 as the iterator
     * is always at the beginning of the list
     */
    @Test
	public void testEmptyListIterator() {
		HListIterator i = emptylist.listIterator();
		assertFalse("An iterator on an empty list should have no next element.", i.hasNext());
		assertFalse("An iterator on an empty list should have no previous element.", i.hasPrevious());
		boolean correctIndex = i.previousIndex() == -1;
		assertTrue("An iterator on an empty list should always be at the beginning of the list and therefore previousIndex() should always return -1.", correctIndex);
		correctIndex = i.nextIndex() == 0;
		assertTrue("An iterator on an empty list should always be at the beginning of the list and therefore nextIndex() should always return 0.", correctIndex);
	}
    /**
     * <b>Summary</b>: test case for the listIterator(int index) method of the ListAdapter class
     * <br>
     * <b>Test Case Design</b>: test list iterators starting from every valid index of a pokedex list.
     * <br>
     * <p><b>Test Description</b>: test the method when index is out of bounds. Then test that the iterator is placed before the provided index (for every valid index), 
     * so a subsequent call so next() returns the element in the list at the provided index, while previous() returns the element at index - 1.
     * <br>
     * <b>Pre-Condition</b>: the pokedex list is populated with every pokemon in the pokedex.
     * <br>
     * <b>Post-Condition</b>: the pokedex list is still populated with every pokemon in the pokedex.
     * <br>
     * </p><b>Expected Results</b>: calling the method with an index out of bounds throws IndexOutOfBoundsException. An iterator just created from the index i, is placed
     * before i, so that a subsequent call to next() returns the element at the position i in the list, while a call to previous() returns the element at the position i - 1
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
		assertTrue("IndexOutOfBoundsException should be thrown when creating an iterator with a negative index.",exceptionThrown);
		exceptionThrown = false;
		try {
			list.listIterator(list.size() + 1);
		}
		catch(IndexOutOfBoundsException e) {
			exceptionThrown = true;
		}
		assertTrue("IndexOutOfBoundsException should be thrown when invoking creating an iterator with an index greater or equal to the list size.",exceptionThrown);
		for(int i = 0; i <= list.size(); i++) {
			if(i < list.size())
				assertEquals("Calling next() on an interator just created at the index i, should return the element at the position i in the list.", list.get(i), list.listIterator(i).next());
			if(i > 0)
				assertEquals("Calling previous() on an interator just created at the index i, should return the element at the position i -1 in the list.", list.get(i - 1), list.listIterator(i).previous());
		}
	}
    /**
     * <b>Summary</b>: test case for the add(Object o) method of the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test the add(Object o) method on a pokedex list using an iterator starting from the middle of the list
     * <br>
     * <p><b>Test Description</b>: create the list iterator starting from a valid position and test the insertion of the provided object before the implicit cursor, so
     * that a subsequent call to next() would be unaffected but a subsequent call to previous() returns the added object.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: list is still populated with every pokemon in the pokedex plus a new one outside the pokedex in the middle of the list
     * <br>
     * </p><b>Expected Results</b>:  the object has been added to the list (making it grow) and it's returned by a subsequent call to previous(), while a subsequent call to next() returns
     * is unaffected. Implicitly the cursor has been moved by one on the right (as an element has been added before it) and therefore a subsequent call to 
     * nextIndex() returns its previous value + 1.
     */
	@Test
	public void testAdd() {
		HListIterator i = list.listIterator(4);
		Object n = i.next();
		Object p = i.previous();
		int pNextIndex = i.nextIndex();
		i.add("Pikachu");
		boolean cursorMoved = i.nextIndex() == pNextIndex + 1;
		assertEquals("A subsequent call to next() should be unaffected by a call to add(Object o)", n, i.next());
		assertTrue("A call to add(Object o) moved the cursor by one, as the object o has been added to the list before the cursor.", cursorMoved);
		i.previous();
		assertNotEquals("A subsequent call to previous() should return the element just added by add(Object o).", p, i.previous());
		assertEquals("A call to add(Object o) should have added the object o right before the implicit cursor.", list.get(4), "Pikachu");
		boolean sizeGrew = list.size() == pokedex.length + 1;
		assertTrue("A call to add(Object o) should have made the list grow in size by 1.", sizeGrew);
	}
    /**
     * <b>Summary</b>: test case for the hasNext() method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test the hasNext() method on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test the hasNext() method of an iterator starting from the beginning of the pokemon list by repeatedly calling next() while hasNext()
     * returns true and then test calling next(). Test the value of nextIndex() when hasNext() is false.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: while hasNext() returns true, next() doesn't throw NoSuchElementException, but when hasNext() returns false a call to next() throws
     * NoSuchElementException (as the iterator's cursor is at the end of the list). When hasNext() is false the iterator is at the end of the list so nextIndex() returns 
     * the size of the list.
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
			assertFalse("While hasNext() returns true, a call to next() should not throw NoSuchElementException.", exceptionThrown);
		}
		exceptionThrown = false;
		boolean ended = !i.hasNext();
		try {
			i.next();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue("When hasNext() returns false, a call to next() should throw NoSuchElementException.", exceptionThrown && ended);
		boolean correctIndex = i.nextIndex() == list.size();
		assertTrue("When hasNext() returns false, a call to nextIndex() should return the list size as the iterator is at the end of the list.", correctIndex && ended);
	}
    /**
     * <b>Summary</b>: test case for the hasPrevious() method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test the hasNext() method on a pokedex list
     * <br>
     * <p><b>Test Description</b>: test the hasNext() method of an iterator starting from the end of the pokemon list by repeatedly calling previous() while hasPrevious()
     * returns true and then test calling previous(). Test the value previousIndex() when hasPrevious() is false.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: while hasPrevious() returns true, previous() doesn't throw NoSuchElementException, but when hasPrevious() returns false a call to
     * previous() throws NoSuchElementException (as the iterator's cursor is at the beginning of the list). When hasPrevious() is false the iterator is at the beginning
     * of the list so previousIndex() returns -1.
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
			assertFalse("While hasPrevious() returns true, a call to previous() should not throw NoSuchElementException.", exceptionThrown);
		}
		boolean ended = !i.hasPrevious();
		exceptionThrown = false;
		try {
			i.previous();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue("When hasPrevious() returns false, a call to previous() should throw NoSuchElementException.", exceptionThrown && ended);
		boolean correctIndex = i.previousIndex() == -1;
		assertTrue("When hasPrevious() returns false, a call to previousIndex() should return -1 as the iterator is at the beginning of the list.", correctIndex && ended);
	}
    /**
     * <b>Summary</b>: test case for the interaction between the next() and previous() methods of the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: call previous and next back and forth on iterators from notable positions of a pokedex list.
     * <br>
     * <p><b>Test Description</b>: test previous and next back and forth on an iterator from the end of the pokedex list, then test next and previous back and forth
     * on an iterator from the beginning of the pokedex list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: When the iterator is at the end of the list, let p be the value returned by previous() and n the value returned by next() called in
     * this order, p is equal to the value of a subsequent call to previous() and n is equal to the value of a subsequent() call to next(). When the iterator is at the 
     * beginning of the list, let n be the value returned by next() and p the value returned by previous() called in
     * this order, n is equal to the value of the subsequent call to next() and p is equal to the value of the subsequent call to previous(). 
     */
	@Test
	public void testNextPrevious() {
		HListIterator i = list.listIterator(list.size());
		Object p = i.previous();
		Object n = i.next();
		assertEquals("When the iterator is at the end of the list, let p be the value returned by previous() and n the value returned by next() called in this order, p should be equal to the value of a subsequent call to previous() and n is equal to the value of a subsequent() call to next().", p, i.previous());
		assertEquals("When the iterator is at the end of the list, let p be the value returned by previous() and n the value returned by next() called in this order, p is equal to the value of a subsequent call to previous() and n should be equal to the value of a subsequent() call to next().", n, i.next());
		
		i = list.listIterator();
		n = i.next();
		p = i.previous();
		assertEquals("When the iterator is at the beginning of the list, let n be the value returned by next() and p the value returned by previous() called in this order, n should be equal to the value of the subsequent call to next() and p is equal to the value of the subsequent call to previous(). ", n, i.next());
		assertEquals("When the iterator is at the beginning of the list, let n be the value returned by next() and p the value returned by previous() called in this order, n is equal to the value of the subsequent call to next() and p should be equal to the value of the subsequent call to previous(). ", p, i.previous());
	}
    /**
     * <b>Summary</b>: test case for the next() method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test next() on an iterator from the beginning of a pokedex list
     * <br>
     * <p><b>Test Description</b>: Create a list iterator at the beginning of the pokedex list and test next() through the list. Test calling next() when the iterator is at the
     *  end of the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: calling next() through the list returns the correct sequence of elements in the list. Calling next() when the iterator is at the end of 
     * the list throws NoSuchElementException.
     */
	@Test
	public void testNext() {
		HListIterator i = list.listIterator();
		int x = 0;
		while(i.hasNext()) {
			assertEquals("Calling next() through the list should return the correct sequence of elements in the list", list.get(x), i.next());
			x++;
		}
		boolean ended = !i.hasNext();
		boolean exceptionThrown = false;
		try {
			i.next();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue("Calling next() when the iterator is at the end of the list should throw NoSuchElementException.", exceptionThrown && ended);
	}
    /**
     * <b>Summary</b>: test case for the nextIndex() method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test nextIndex() on an iterator from the beginning of a pokedex list
     * <br>
     * <p><b>Test Description</b>: Create a list iterator at the beginning of the pokedex list and test nextIndex() through the list. Test calling nextIndex() when
     * the iterator is at the end of the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: calling nextIndex() through the list returns a sequence of numbers from 0 to the size of the list (excluded). Calling nextIndex() 
     * when the iterator is at the end of the list returns the size of the list.
     */
	@Test
	public void testNextIndex() {
		HListIterator i = list.listIterator();
		while(i.hasNext()) {
			int x = i.nextIndex();
			boolean sameIndex = x == list.subList(0, x + 1).lastIndexOf(i.next());
			assertTrue("Calling nextIndex() through the list should return a sequence of numbers from 0 to the size of the list (exclusive)", sameIndex);
		}
		boolean ended = !i.hasNext();
		boolean indexEqualsSize = list.size() == i.nextIndex();
		assertTrue("Calling nextIndex()  when the iterator is at the end of the list should return the size of the list.", indexEqualsSize && ended);
	}
	/**
     * <b>Summary</b>: test case for the previous() method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test previous() on an iterator from the end of a pokedex list
     * <br>
     * <p><b>Test Description</b>: Create a list iterator at the end of the pokedex list and test previous() through the list.
     * Test calling previous() when the iterator is at the beginning of the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: calling previous() through the list returns the inverted sequence of elements in the list. Calling previous() when the iterator is at the
     * beginning of the list throws NoSuchElementException.
     */
	@Test
	public void testPrevious() {
		HListIterator i = list.listIterator(list.size());
		int x = list.size() - 1;
		while(i.hasPrevious()) {
			assertEquals("Calling previous() through the list should return the inverted sequence of elements in the list",list.get(x), i.previous());
			x--;
		}
		boolean ended = !i.hasPrevious();
		boolean exceptionThrown = false;
		try {
			i.previous();
		}
		catch(NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue("Calling next() when the iterator is at the end of the list should throw NoSuchElementException.", exceptionThrown && ended);
	}
    /**
     * <b>Summary</b>: test case for the previousIndex() method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test previousIndex() on an iterator from the end of a pokedex list
     * <br>
     * <p><b>Test Description</b>: Create a list iterator at the end of the pokedex list and test previousIndex() through the list. Test calling previousIndex() when
     * the iterator is at the ebginning of the list.
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: calling previousIndex() through the list returns a sequence of numbers from the size of the list (excluded) to -1. Calling previousIndex() 
     * when the iterator is at the beginning of the list returns -1.
     */
	@Test
	public void testPreviousIndex() {
		HListIterator i = list.listIterator(list.size());
		while(i.hasPrevious()) {
			int x = i.previousIndex();
			boolean sameIndex = x == list.subList(0, x + 1).lastIndexOf(i.previous());
			assertTrue("Calling previousIndex() through the list should return a sequence of numbers from the size of the list - 1 to 0", sameIndex);
		}
		boolean ended = !i.hasPrevious();
		boolean indexEqualsSize = -1 == i.previousIndex();
		assertTrue("Calling previousIndex()  when the iterator is at the beginning of the list should return -1.", indexEqualsSize && ended);
	}
    /**
     * <b>Summary</b>: test case for the remove() method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test the remove() method on a pokedex list using an iterator starting from the middle of the list
     * <br>
     * <p><b>Test Description</b>: create an iterator in the middle of the pokedex list and test remotion before and after calling previous()
     * or next(). Then test calling the method after calling ListAdapterIterator.add(Object o) and at last test calling the method without calling previous() or next()
     * since the last call to remove().
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with every pokemon in the pokedex
     * <br>
     * </p><b>Expected Results</b>: Calling remove() removed the element returned by the last call to previous() or next(). If previous() or next() have not been called
     * since the iterator creation or since the last call to remove(), or if ListAdapterIterator.add(Object o) has been called in the meanwhile, then remove()
     * throws IllegalStateException
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
		assertTrue("If previous() or next() have not been called since the iterator creation, then remove() should throw IllegalStateException", exceptionThrown);
		
		Object o = i.next();
		boolean contained = list.contains(o);
		i.remove();
		assertFalse("Calling remove() should have removed the element returned by the last call to next().", list.contains(o) || !contained);
		
		exceptionThrown = false;
		try {
			i.remove();
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue("If previous() or next() have not been called since the last call to remove(), then a calling remove() should throw IllegalStateException", exceptionThrown);
		
		o = i.previous();
		contained = list.contains(o);
		i.remove();
		assertFalse("Calling remove() should have removed the element returned by the last call to previous().", list.contains(o) || !contained);
		
		exceptionThrown = false;
		try {
			i.next();
			i.add("Alcremie");
			i.remove();
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue("If ListAdapterIterator.add(Object o) has been called since the last call to next() or previous(), then calling remove() should throw IllegalStateException", exceptionThrown);
	}
    /**
     * <b>Summary</b>: test case for the set(Object o) method for the ListAdapterIterator class
     * <br>
     * <b>Test Case Design</b>: test the set(Object o) method on a pokedex list using an iterator starting from the middle of the list
     * <br>
     * <p><b>Test Description</b>: create an iterator in the middle of the pokedex list and test set(Object o) before and after calling previous()
     * and next(). Then test calling the method when ListAdapterIterator.add(Object o) and ListAdapterIterator.remove() have been called
     * between set(Object o) and the last call to previous()/next().
     * <br>
     * <b>Pre-Condition</b>: the list is populated with every pokemon in the pokedex
     * <br>
     * <b>Post-Condition</b>: the list is still populated with pokemon from the pokedex
     * <br>
     * </p><b>Expected Results</b>: Calling set(Object o) set to o the element of the list returned by the last call to previous() and next(). If previous() or next() have not
     * been called since the iterator creation or if ListAdapterIterator.add(Object o) or ListAdapterIterator.remove() have been called
     * in the meanwhile and previous() or next() have not been called again, then set(Object o) throws IllegalStateException
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
		assertTrue("Calling set(Object o) without calling previous() or next() at least once since the iterator initialization should throw IllegaStateException.", exceptionThrown);
		
		int x = i.nextIndex();
		Object o = i.next();
		i.set("Porygon");
		assertNotEquals("The element of the list previously returned by next() before calling set(Object o) should have been changed.", list.get(x), o);
		
		i.set(o);
		assertEquals("Calling set(Object o) with the element returned by the last call to next() as o should reset the element of the list in that position.", list.get(x), o);
		
		x = i.previousIndex();
		o = i.previous();
		i.set("Porygon");
		assertNotEquals("The element of the list previously returned by previous() before calling set(Object o) should have been changed.", list.get(x), o);
		
		i.set(o);
		assertEquals("Calling set(Object o) with the element returned by the last call to previous() as o should reset the element of the list in that position.", list.get(x), o);
		
		exceptionThrown = false;
		try {
			i.add("Alcremie");
			i.set("Arcanine");
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue("Calling set(Object o) after calling add(Object o) without calling next()/previous() in the meanwhile should throw IllegalStateException.", exceptionThrown);
		exceptionThrown = false;
		try {
			i.previous();
			i.remove();
			i.set("Arcanine");
		}
		catch(IllegalStateException e) {
			exceptionThrown = true;
		}
		assertTrue("Calling set(Object o) after calling remove() without calling next()/previous() in the meanwhile should throw IllegalStateException.", exceptionThrown);
	}
}
