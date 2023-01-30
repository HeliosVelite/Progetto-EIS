package myTest;
import org.junit.Test;
import static org.junit.Assert.*;
import myAdapter.Vector;

public class VectorEqualityTest {

	@Test
	public void vectorEqualityIsObjectEquality() {
		Vector vec1 = new Vector();
		Vector vec2 = new Vector();
		Vector vec3 = vec1;
		assertTrue(!vec1.equals(vec2) && vec1.equals(vec3));
	}

}
