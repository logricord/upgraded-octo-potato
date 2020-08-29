package hw5;

import java.util.Set;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * <b>Graph</b> represents a mutable directed labeled multi-graph.
 * @specfield node  : T 		 // a point in the graph; has a name
 * @specfield nodes : map    	 // the nodes in the graph, mapped to the edges that node 
 * 									is a source of
 * @specfield edge  : Edge<E, T> // connects two nodes in a graph; has a label
 * @specfield edges : set	 	 // the edges in the graph
 * 
 * @author Logan Ricord
 */

public class Graph<T, E> implements Iterable<T> {

	/** Set of node objects in this.*/
	private Map<T, Set<Edge<E, T>>> nodes;
	
	private static final boolean DEBUG_FLAG = false;
	
	// Abstraction Function:
	//		AF(r) = Graph such that
	// 			nodes = collection of connected node objects t on the graph
	//					mapped to the set of edges that that node is a source of,
	//					[t1, t2, ..., tn] where n is the number of nodes in the 
	//					graph; an empty graph is represented by []
	//
	// Representation Invariant:
	//		foreach T t in nodes, [t1, t2, ..., tn], t != null
	
	/**
	 * Creates a Graph.
	 * 
	 * @effects constructs a new Graph with no nodes
	 */
	public Graph() {
		nodes = new HashMap<T, Set<Edge<E, T>>>();
		checkRep();
	}
	
	/**
	 * Creates a Graph with given node.
	 * 
	 * @param node The node object to be added to this
	 * @requires node != null
	 * @effects constructs a new Graph with the given node
	 */
	public Graph(T node) {
		assert node != null : "node is null";
		nodes = new HashMap<T, Set<Edge<E, T>>>();
		nodes.put(node, new HashSet<Edge<E, T>>());
		checkRep();
	}
	
	/**
	 * Creates a Graph with given set of nodes.
	 * 
	 * @param nodes The set of nodes to become this.nodes
	 * @requires !nodes.contains(null);
	 * @effects constructs a new Graph with the given set of nodes
	 */
	public Graph(Set<T> nodes) {
		for(T node : nodes) {
			assert node != null : "nodes contains a null";
			this.nodes.put(node, new HashSet<Edge<E, T>>());
		}
		if(DEBUG_FLAG)
			checkRep();
	}
	
	/**
	 * Adds node to this.nodes, returning true if successful.
	 *
	 * @param node The node to be added to this.nodes
	 * @requires node != null
	 * @effects adds node to this.nodes
	 * @returns true iff node is added to this.nodes
	 */
	public void addNode(T node) {
		assert node != null : "node is null";
		nodes.putIfAbsent(node, new HashSet<Edge<E, T>>());
	}
	
	/**
	 * Adds given set of nodes to this.nodes, returning true if each node in nodes is 
	 * successfully added to this.nodes.
	 * 
	 * @param newNodes The set of nodes to be added to this.nodes
	 * @requires !nodes.contains(null)
	 * @effects adds given set of nodes to this.nodes
	 * @return true iff each node in nodes is added to this.nodes
	 * 
	 * Note that if some, but not all, nodes in newNodes are added to this.nodes,
	 * the method will return false and the added nodes will <b>remain</b> in 
	 * this.nodes.
	 */
	public boolean addNode(Set<T> newNodes) {
		assert !newNodes.contains(null) : "nodes contains a null";
		int initSize = nodes.size();
		for(T name : newNodes) {
			nodes.putIfAbsent(name, new HashSet<Edge<E, T>>());
		}
		checkRep();
		
		if(initSize + newNodes.size() != nodes.size())
			return false;
		else
			return true;
	}
	
	/**
	 * Creates an Edge with label edgeLabel with given parent name as the node source 
	 * and given child name as the node destination.
	 * 
	 * @param parent The name of the parent (or source) node
	 * @param child The name of the child (or destination) node
	 * @param edgeLabel The label for the new Edge
	 * @requires this.contains(parent) && this.contains(child)
	 * @return true iff an Edge with label edgeLabel was successfully added from
	 *		   node with name parent to node with name child
	 */
	public boolean addEdge(T parent, T child, E edgeLabel) {
		assert this.contains(parent) : "graph does not contain parent";
		assert this.contains(child) : "graph does not contain child";
		
		Set<Edge<E, T>> set = nodes.get(parent);
		boolean success = set.add(new Edge<E, T>(edgeLabel, child));
		nodes.put(parent, set);
		return success;
	}
	
	/**
	 * Returns set of edges associated with given node, or null if node is not in this.nodes.
	 * 
	 * @param node The node to be searched for in this
	 * @return the set of edges associated with given node, or null if node is not in 
	 * 		   this.nodes
	 */
	public Set<Edge<E, T>> getEdges(T node) {
		return nodes.get(node);
	}
	
	/**
	 * Returns set of nodes in this.nodes.
	 * 
	 * @return set of nodes in this.nodes
	 */
	public Set<T> getNodes() {
		return nodes.keySet();
	}
	
	/**
	 * Returns set of all child nodes of given node (i.e. all nodes that the given node
	 * is the source of).
	 * 
	 * @param node The node whose children will be returned
	 * @return set of all child nodes of given node
	 */
	public Set<T> getChildren(T node) {
		Set<T> children = new HashSet<T>();
		if(node != null && this.contains(node)) {
			for(Edge<E, T> edge : this.getEdges(node)) {
				children.add(edge.getDestination());
			}
		}
		return children;
	}
	
	/**
	 * Removes given node from this.nodes, returning set of edges previously associated 
	 * with that node if successful.
	 * 
	 * @param node The node to be removed from this.nodes
	 * @effects if this contains node, removes node from this.nodes
	 * @return set of edges previously associated with given node (null if node is not
	 * 		   in this.nodes)
	 */
	public Set<Edge<E, T>> remove(T node) {
		return nodes.remove(node);
	}
	
	/**
	 * Returns true if node is contained in this.nodes.
	 * 
	 * @param node The node to be searched for in this.nodes
	 * @return true iff node is contained in this.nodes
	 */
	public boolean contains(T node) {
		return nodes.containsKey(node);
	}
	
	/**
	 * Returns number of nodes in this.nodes (or the size of this.nodes).
	 * 
	 * @return number of node objects in this
	 */
	public int nodeCount() {
		return nodes.size();
	}
	
	/**
	 * Returns number of edges contained in this.nodes.
	 * 
	 * @return number of edges in this
	 */
	public int edgeCount() {
		int count = 0;
		for(Set<Edge<E, T>> set : nodes.values()) {
			count = count + set.size();
		}
		return count;
	}
	
	/**
	 * Returns number of edges that the given node is a source of.
	 * 
	 * @param node The node whose edges will be counted
	 * @return the number of edges that the given node is a source of
	 */
	public int edgeCount(T node) {
		return getEdges(node).size();
	}
	
	/**
	 * Returns number of edges from one node to another node. 
	 * 
	 * @param from The source node
	 * @param to The destination node
	 * @throws IllegalArgumentException if from or to is null or if from or to have no
	 * 		   mapping in this.nodes 
	 * @return the number of edges from the source node to the destination node in this
	 */
	public int edgesBetween(T from, T to) {
		if(from == null || to == null)
			throw new IllegalArgumentException("from and/or to is null");
		
		if(nodes.get(from) == null || nodes.get(to) == null)
			throw new IllegalArgumentException("from and/or to has no mapping in this.nodes");
		
		int count = 0;
		for(Edge<E, T> edge : nodes.get(from)) {
			if(edge.getDestination().equals(to))
				count = count + 1;
		}
		return count;
	}
	
	/**
	 * Returns true if nodes is empty (i.e. this has no nodes).
	 * 
	 * @return true iff nodes is empty
	 */
	public boolean isEmpty() {
		return nodes.isEmpty();
	}
	
	/**
	 * Returns true if given node has any edges.
	 * 
	 * @return true iff given node has any edges
	 */
	public boolean hasEdges(T node) {
		if(contains(node) && !getEdges(node).isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Removes all nodes in this.nodes.
	 * 
	 * @effects removes all nodes in this.nodes.
	 */
	public void clear() {
		nodes.clear();
		checkRep();
	}
	
	/**
	 * Returns an Iterator for the nodes contained in this.nodes.
	 * 
	 * @return an Iterator for the nodes contained in this.nodes
	 */
	public Iterator<T> iterator() {
		return Collections.unmodifiableSet(nodes.keySet()).iterator();
	}
	
	/**
	 * Checks if this representation invariant holds.
	 */
	private void checkRep() {
		if(DEBUG_FLAG) {
			for(T node : nodes.keySet()) {
				assert node != null : "graph contains a null node";
			}
			for(Set<Edge<E, T>> set : nodes.values()) {
				for(Edge<E, T> edge : set) {
					assert edge != null : "graph contains a null edge";
				}
			}
		}
	}
}
