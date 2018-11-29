package hw5.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hw5.Edge;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Edge<String, String> class.
 * <p>
 */

public class EdgeTest {

	private static String destNode1;
	private static String destNode2;
	private static Set<Edge<String, String>> set;
	private static Edge<String, String> e1;
	private static Edge<String, String> e2;
	
	private static final int NUM_EDGES_TO_TEST = 6;
	
	@Before
	public void setUp() throws Exception {
		destNode1 = "Node destination 1";
		destNode2 = "Node destination 2";
		e1 = new Edge<String, String>("Edge 1", destNode1);
		e2 = new Edge<String, String>("Edge 2", destNode2);
		
		set = new HashSet<Edge<String, String>>();
		for(int i = 1; i <= NUM_EDGES_TO_TEST; i++) {
			Edge<String, String> e = new Edge<String, String>("Edge " + i, "Node destination " + i);
			set.add(e);
		}
	}

	@Test
	public void testEdgeGetLabel() {
		String expected1 = "Edge 1";
		assertEquals("e1 getLabel does not equal expected", expected1, e1.getLabel());
		
		String expected2 = "Edge 2";
		assertEquals("e2 getLabel does not equal expected", expected2, e2.getLabel());
	}
	
	@Test
	public void testEdgeGetDestination() {
		String expected1 = destNode1;
		assertEquals("e1 getDestination does not equal expected", 
				expected1, e1.getDestination());
		
		String expected2 = destNode2;
		assertEquals("e2 getDestination does not equal expected",
				expected2, e2.getDestination());
	}
	
	@Test
	public void testEdgeToString() {
		String expected1 = "Node destination 1(Edge 1)";
		assertEquals("e1 toString does not equal expected", expected1, e1.toString());
		
		String expected2 = "Node destination 2(Edge 2)";
		assertEquals("e2 toString does not equal expected", expected2, e2.toString());
	}
	
	@Test
	public void testEdgeEqualsTransitive() {
		Edge<String, String> edge1 = new Edge<String, String>("Edge 1", destNode1);
		Edge<String, String> edge2 = new Edge<String, String>("Edge 1", destNode1);
		Edge<String, String> edge3 = new Edge<String, String>("Edge 1", destNode1);
		assertTrue("edge fails transitive equality test", 
				edge1.equals(edge2) && edge2.equals(edge3) && edge1.equals(edge3));
	}
	
	@Test
	public void testEdgeEqualsReflexive() {
		assertTrue("edge fails reflexive equality test", e1.equals(e1));
	}
	
	@Test
	public void testEdgeEqualsSymmetric() {
		Edge<String, String> edge1 = new Edge<String, String>("Edge 1", destNode1);
		Edge<String, String> other = new Edge<String, String>("Edge 1", destNode1);
		assertTrue("node fails symmetric equality test", 
				edge1.equals(other) && other.equals(edge1));
	}
	
	@Test
	public void testEdgeEqualsDifferentLabel() {
		Edge<String, String> edge1 = new Edge<String, String>("Edge 1", destNode1);
		Edge<String, String> edge2 = new Edge<String, String>("Edge 2", destNode1);
		assertFalse("edge is equal to different-label edge", edge1.equals(edge2));
		assertFalse("edge is equal to different-label edge", edge2.equals(edge1));
	}
	
	@Test
	public void testEdgeEqualsDifferentDest() {
		Edge<String, String> edge1 = new Edge<String, String>("Edge 1", destNode1);
		Edge<String, String> edge2 = new Edge<String, String>("Edge 1", destNode2);
		assertFalse("edge is equal to different-dest edge", edge1.equals(edge2));
		assertFalse("edge is equal to different-dest edge", edge2.equals(edge1));
	}
	
	@Test
	public void testEdgeHashCodeSame() {
		Edge<String, String> edge1 = new Edge<String, String>("Edge 1", destNode1);
		Edge<String, String> other = new Edge<String, String>("Edge 1", destNode1);
		assertTrue("edge hash code does not equal other edge hash code with same name",
				edge1.hashCode() == other.hashCode());
	}
	
	@Test
	public void testEdgeHashCodeDifferent() {
		Edge<String, String> edge1 = new Edge<String, String>("Edge 1", destNode1);
		Edge<String, String> edge2 = new Edge<String, String>("Edge 2", destNode2);
		Edge<String, String> edge10 = new Edge<String, String>("Edge 10", destNode1);
		assertFalse("edge hash code equals other edge hash code with different name",
				edge1.hashCode() == edge2.hashCode());
		assertFalse("edge hash code equals other edge hash code with different name",
				edge2.hashCode() == edge10.hashCode());
		assertFalse("edge hash code equals other edge hash code with different name",
				edge1.hashCode() == edge10.hashCode());
	}
}
