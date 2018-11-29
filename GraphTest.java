package hw5.test;

import java.util.Set;
import java.util.HashSet;

import hw5.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Graph class.
 * <p>
 */

public final class GraphTest {
	
	private static Graph<String, String> graph;
	private static Set<String> set;
	private static String[] array;
	private static String n1;
	private static String n2;
	
	private static final int NUM_TO_TEST = 6;
	
	@Before
	public void setUp() throws Exception {
		n1 = "Node 1";
		n2 = "Node 2";
		graph = new Graph<String, String>();
		
		set = new HashSet<String>();
		array = new String[NUM_TO_TEST];
		for(int i = 1; i <= NUM_TO_TEST; i++) {
			String node = "Node " + i;
			set.add(node);
			array[i-1] = node;
		}
	}
	
	private void set(String n1) {
		graph.clear();
		graph.addNode(n1);
	}
	
	private void set(String n1, String n2) {
		graph.clear();
		graph.addNode(n1);
		graph.addNode(n2);
	}
	
	private void set(Set<String> set) {
		graph.clear();
		graph.addNode(set);
	}
	
	private void giveChildren(String node, int n) {
		graph.clear();
		for(int i = 1; i <= n + 1; i++) {
			graph.addNode("Node " + i);
		}
		for(int i = 1; i <= n; i++) {
			graph.addEdge(node, "Node " + (i + 1), "Edge " + i);
		}
	}
	
	@Test
	public void testAddOneNode() {
		set(n1);
		assertTrue("graph does not contain n1 after n1 add", graph.contains(n1));
	}
	
	@Test
	public void testAddTwoNodes() {
		set(n1, n2);
		assertTrue("graph does not contain n1 after n1, n2 add", graph.contains(n1));
		assertTrue("graph does not contain n2 after n1, n2 add", graph.contains(n2));
	}

	@Test
	public void testAddSetOnEmptyGraph() {
		set(set);
		assertEquals("node count after set add on empty graph: " + 
				graph.nodeCount() + "; should be: " + NUM_TO_TEST, 
				NUM_TO_TEST, graph.nodeCount());
	}
	
	@Test
	public void testAddSetOnNonEmptyGraph() {
		set(n1, n2);
		graph.addNode(set);
		assertEquals("node count after set add on non-empty graph: " + 
				graph.nodeCount() + "; should be " + NUM_TO_TEST, 
				NUM_TO_TEST, graph.nodeCount());
	}
	
	@Test
	public void testGetEdgesNonExistentNode() {
		graph.clear();
		assertEquals("getEdges on non-existent node is non-null", 
				null, graph.getEdges(n1));
	}
	
	@Test
	public void testGetEdgesEmptyNode() {
		set(n1);
		assertEquals("getEdges on zero-edge node is not 0", 0, graph.getEdges(n1).size());
	}
	
	@Test
	public void testGetEdges1EdgeNode() {
		giveChildren(n1, 1);
		assertEquals("getEdges on one-edge node is not 1", 1, graph.getEdges(n1).size());
	}
	
	@Test
	public void testGetEdgesTwoEdgeNode() {
		giveChildren(n1, 2);
		assertEquals("getEdges on two-edge node is not 2", 2, graph.getEdges(n1).size());
	}
	
	@Test
	public void testGetEdgesManyEdgeNode() {
		giveChildren(n1, NUM_TO_TEST);
		assertEquals("getEdges on many-edge node is not correct", 
				NUM_TO_TEST, graph.getEdges(n1).size());
	}
	
	@Test
	public void testGetNodesEmptyGraph() {
		graph.clear();
		assertTrue("getNodes on empty graph is not correct", 
				graph.getNodes().size() == 0);
	}
	
	@Test
	public void testGetNodes1NodeGraph() {
		set(n1);
		assertTrue("getNodes on one-node graph is not correct", 
				graph.getNodes().size() == 1);
	}
	
	@Test
	public void testGetNodes2NodeGraph() {
		set(n1, n2);
		assertTrue("getNodes on two-node graph is not correct", 
				graph.getNodes().size() == 2);
	}
	
	@Test
	public void testGetNodesManyNodeGraph() {
		set(set);
		assertTrue("getNodes on many-node graph is not correct", 
				graph.getNodes().size() == NUM_TO_TEST);
	}
	
	@Test
	public void testGetChildrenNonExistentNode() {
		graph.clear();
		assertTrue("getChildren from non-existent node is not empty", 
				graph.getChildren(n1).isEmpty());
	}
	
	@Test
	public void testGetChildrenNoChildren() {
		set(n1);
		assertTrue("getChildren from 0-children node is not empty", 
				graph.getChildren(n1).isEmpty());
	}
	
	@Test
	public void testGetChildrenOneChild() {
		giveChildren(n1, 1);
		assertEquals("getChildren from 1-child node is not of size 1",
				1, graph.getChildren(n1).size());
	}
	
	@Test
	public void testGetChildrenTwoChildren() {
		giveChildren(n1, 2);
		assertEquals("getChildren from 2-children node is not size of 2",
				2, graph.getChildren(n1).size());
	}
	
	@Test
	public void testGetChildrenManyChildren() {
		giveChildren(n1, NUM_TO_TEST);
		assertEquals("getChildren from " + NUM_TO_TEST + "-children node is"
				+ " not size of " + NUM_TO_TEST, 
				NUM_TO_TEST, graph.getChildren(n1).size());
	}
	
	@Test
	public void testAddEmptySet() {
		set(new HashSet<String>());
		assertTrue("graph is not empty after empty set add on empty graph", 
				graph.isEmpty());
	}
	
	@Test
	public void testRemoveOnEmptyGraph() {
		graph.clear();
		assertEquals("remove n1 returned non-null on empty graph", null, graph.remove(n1));
	}
	
	@Test
	public void testRemoveOnOneNodeGraph() {
		set(n1);
		Set<Edge<String, String>> s = graph.getEdges(n1);
		assertEquals("remove n2 returned non-null on n1-only graph", null, graph.remove(n2));
		assertEquals("remove n1 returned wrong set of edges on n1-only graph", 
				s, graph.remove(n1));
	}
	
	@Test
	public void testRemoveOnMultiNodeGraph() {
		set(set);
		if(NUM_TO_TEST > 1) {
			Set<Edge<String, String>> s1 = graph.getEdges(n1);
			assertEquals("remove n1 returned wrong set of edges on set graph", 
					s1, graph.remove(n1));
			
			Set<Edge<String, String>> s2 = graph.getEdges(n2);
			assertEquals("remove n2 returned wrong set of edges on set graph", 
					s2, graph.remove(n2));
		} else if (NUM_TO_TEST > 0) {
			Set<Edge<String, String>> s1 = graph.getEdges(n1);
			assertEquals("remove n1 returned wrong set of edges on set graph", 
					s1, graph.remove(n1));
		}
	}
	
	@Test
	public void testContainsWhileEmpty() {
		graph.clear();
		assertFalse("graph contains node after clear", 
				graph.contains(n1) || graph.contains(n2));
	}
	
	@Test
	public void testContainsWith1() {
		set(n1);
		assertTrue("graph does not contain n1 after n1 add", graph.contains(n1));
		assertFalse("graph contains n2 after n1-only add", graph.contains(n2));
	}
	
	@Test
	public void testContainsWith2() {
		set(n1, n2);
		assertTrue("graph does not contain n1 after n1, n2 add", graph.contains(n1));
		assertTrue("graph does not contain n2 after n1, n2 add", graph.contains(n2));
	}
	
	@Test
	public void testContainsWithMany() {
		set(set);
		if(NUM_TO_TEST > 1) {
			assertTrue("graph does not contain n1 after set add", graph.contains(n1));
			assertTrue("graph does not contain n2 after set add", graph.contains(n2));
		} else if(NUM_TO_TEST > 0) {
			assertTrue("graph does not contain n1 after set add", graph.contains(n1));
		}
	}
	
	@Test
	public void testContainsAfterRemove() {
		set(n1, n2);
		
		graph.remove(n1);
		assertFalse("graph contains n1 after n1 remove", graph.contains(n1));
		assertTrue("graph does not contain n2 after n1 remove", graph.contains(n2));
		
		graph.remove(n2);
		assertFalse("graph contains n1 after n1, n2 remove", graph.contains(n1));
		assertFalse("graph contains n2 after n1, n2 remove", graph.contains(n2));
	}
	
	@Test
	public void testNodeCountWith0() {
		graph.clear();
		assertEquals("node count after clear: " + graph.nodeCount() + "; should be 0", 
				0, graph.nodeCount());
	}
	
	@Test
	public void testNodeCountWith1() {
		set(n1);
		assertEquals("node count after n1 add: " + graph.nodeCount() + "; should be 1", 
				1, graph.nodeCount());
	}
	
	@Test
	public void testNodeCountWith2() {
		set(n1, n2);
		assertEquals("node count after n1, n2 add: " + graph.nodeCount() + "; should be 2", 
				2, graph.nodeCount());
	}
	
	@Test
	public void testNodeCountWithSet() {
		set(set);
		assertEquals("node count after set add: " + graph.nodeCount() + "; should be " +
				NUM_TO_TEST, NUM_TO_TEST, graph.nodeCount());
	}
	
	@Test
	public void testEdgeCountEmptyGraph() {
		graph.clear();
		assertEquals("edgeCount on empty graph is non-zero", 0, graph.edgeCount());
	}
	
	@Test
	public void testEdgeCountOneEdgeGraph() {
		giveChildren(n1, 1);
		assertEquals("edgeCount on one-edge graph is not 1", 1, graph.edgeCount());
	}
	
	@Test
	public void testEdgeCountTwoEdgeGraph() {
		giveChildren(n1, 2);
		assertEquals("edgeCount on one-edge graph is not 2", 2, graph.edgeCount());
	}
	
	@Test
	public void testEdgeCountManyEdgeGraph() {
		giveChildren(n1, NUM_TO_TEST);
		assertEquals("edgeCount on many-edge graph is not correct", 
				NUM_TO_TEST, graph.edgeCount());
	}
	
	@Test
	public void testEdgeCountNoEdgeNode() {
		set(n1);
		assertEquals("edgeCount on empty graph is non-zero", 0, graph.edgeCount(n1));
	}
	
	@Test
	public void testEdgeCountOneEdgeNode() {
		giveChildren(n1, 1);
		assertEquals("edgeCount on one-edge node is not 1", 1, graph.edgeCount(n1));
	}
	
	@Test
	public void testEdgeCountTwoEdgeNode() {
		giveChildren(n1, 2);
		assertEquals("edgeCount on one-edge node is not 2", 2, graph.edgeCount(n1));
	}
	
	@Test
	public void testEdgeCountManyEdgeNode() {
		giveChildren(n1, NUM_TO_TEST);
		assertEquals("edgeCount on many-edge node is not correct", 
				NUM_TO_TEST, graph.edgeCount(n1));
	}
	
	@Test
	public void testEdgesBetween0Edges() {
		set(n1, n2);
		assertEquals("edgesBetween edgeless nodes is not 0", 0, graph.edgesBetween(n1, n2));
		assertEquals("edgesBetween edgeless nodes is not 0", 0, graph.edgesBetween(n1, n2));
	}
	
	@Test
	public void testEdgesBetween1Edge() {
		set(n1, n2);
		graph.addEdge(n1, n2, "edge");
		assertEquals("edgesBetween 1-edged nodes is not 1", 1, graph.edgesBetween(n1, n2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEdgesBetweenNonExistentNode() {
		graph.clear();
		graph.edgesBetween(n1, n2);
	}
	
	@Test
	public void testIsEmptyOnEmpty() {
		graph.clear();
		assertTrue("graph is not empty after clear", graph.isEmpty());
	}
	
	@Test
	public void testIsEmptyWith1() {
		set(n1);
		assertFalse("graph is empty after n1 add", graph.isEmpty());
	}
	
	@Test
	public void testIsEmptyWith2() {
		set(n1, n2);
		assertFalse("graph is empty after n2 add", graph.isEmpty());
	}
	
	@Test
	public void testIsEmptyWithSet() {
		set(set);
		assertFalse("graph is empty after set add", graph.isEmpty());
	}
	
	@Test
	public void testHasEdgesNonExistentNode() {
		graph.clear();
		assertFalse("hasEdges returns true on non-existent node", graph.hasEdges(n1));
	}
	
	@Test
	public void testHasEdgesEmptyNode() {
		set(n1);
		assertFalse("hasEdges returns true on empty node", graph.hasEdges(n1));
	}
	
	@Test
	public void testHasEdgesOneEdgeNode() {
		giveChildren(n1, 1);
		assertTrue("hasEdges returns false on one-edge node", graph.hasEdges(n1));
	}
	
	@Test
	public void testHasEdgesTwoEdgeNode() {
		giveChildren(n1, 2);
		assertTrue("hasEdges returns false on two-edge node", graph.hasEdges(n1));
	}
	
	@Test
	public void testHasEdgesManyEdgeNode() {
		giveChildren(n1, NUM_TO_TEST);
		assertTrue("hasEdges returns false on many-edge node", graph.hasEdges(n1));
	}
	
	@Test
	public void testInitialClear() {
		graph.clear();
		assertTrue("graph is not empty after initial clear", graph.isEmpty());
	}
	
	@Test
	public void testClearWith1() {
		set(n1);
		graph.clear();
		assertTrue("graph is not empty after one node clear", graph.isEmpty());
	}
	
	@Test
	public void testClearWith2() {
		set(n1, n2);
		graph.clear();
		assertTrue("graph is not empty after two node clear", graph.isEmpty());
	}
	
	@Test
	public void testClearWithSet() {
		set(set);
		graph.clear();
		assertTrue("graph is not empty after set add and " + NUM_TO_TEST + 
				" node clear", graph.isEmpty());
	}
	
	@Test
	public void testAddEdgeBetweenNodes() {
		set(n1, n2);
		graph.addEdge(n1, n2, "Edge 1");
		assertTrue("n1 does not have edge after n1 -> n2 edge add", graph.hasEdges(n1));
		assertEquals("n1 does not have one edge after n1 -> n2 edge add", 1, graph.edgeCount(n1));
	}
	
	@Test
	public void testIterator() {
		graph.clear();
		Set<String> allNodes = new HashSet<String>();
		Set<String> seenNodes = new HashSet<String>();
		for(String node : array) {
			allNodes.add(node);
			graph.addNode(node);
		}
		int count = 0;
		for(String node : graph) {
			assertTrue("iterator returned node not in graph", graph.contains(node));
			assertFalse("iterator returned same node twice", seenNodes.contains(node));
			seenNodes.add(node);
			count = count + 1;
		}
		assertEquals("iterator did not return enough nodes", array.length, count);
	}
}
