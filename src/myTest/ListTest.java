package myTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * <div style="display:grid; grid-template-columns: 20% 1fr;">
 * <b>Summary</b>
 * <p></p>
 * <b>Test Suite Design</b>
 * <p></p>
 * <b>Pre-Condition</b>
 * <p></p>
 * <b>Post-Condition</b>
 * <p></p>
 * <b>Test Cases</b>
 * <p></p>
 * <b>Test Suite Execution Records</b>
 * <p></p>
 * <b>Execution Variables</b>
 * <p></p>
 * </div>
 * @author Zuech
 *
 */
public class ListTest {
	private HList list;
	private HList emptyList;
	private static String[] pokedex = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon",
			"Charizard", "Squirtle", "Wartortle", "Blastoise"};
	
	@Before
	public void setUp() throws Exception {
		list = new ListAdapter();
		emptyList = new ListAdapter();
		for(String mon: pokedex) {
			list.add(mon);
		}
	}
	
	/**
	 * <div style="display:grid; grid-template-columns: 20% 1fr;">
	 * <b>Summary</b>
	 * <p></p>
	 * <b>Test Case Design</b>
	 * <p></p>
	 * <b>Test Description</b>
	 * <p></p>
	 * <b>Pre-Condition</b>
	 * <p></p>
	 * <b>Post-Condition</b>
	 * <p></p>
	 * <b>Expected results</b>
	 * <p></p>
	 * </div>
	 */
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}