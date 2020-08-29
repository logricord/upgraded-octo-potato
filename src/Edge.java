package hw5;

/**
 * <b>Edge</b> represents an <b>immutable</b> object with a label and a destination to 
 * a <b>Node</b>.
 * @specfield label : E // the name of the edge
 * @specfield node  : T // a point; has a name
 * 
 * @author Logan Ricord
 */

public class Edge<E, T> {

	/**the name of this Edge*/
	private final E label;
	
	/**the destination node of this Edge*/
	private final T destination;
		
	// Abstraction Function:
	//		AF(r) = Edge e with label given by this.label such that
	//			destination = node that e points toward
	//
	// Representation Invariant for all Edges e:
	// 		label != null &&
	//		destination != null
	
	/**
	 * Constructs an Edge with given label and given destination node.
	 * 
	 * @param label The label for this Edge object
	 * @param destination The node that this points to
	 * @requires label != null && destination != null
	 * @effects constructs a new Edge with given label and given destination node
	 */
	public Edge(E label, T destination) {
		assert label != null : "label is null";
		assert destination != null : "destination is null";
		this.label = label;
		this.destination = destination;
		checkRep();
	}
	
	/**
	 * Returns this.label.
	 * 
	 * @return this.label
	 */
	public E getLabel() {
		return label;
	}
	
	/**
	 * Returns this.destination.
	 * 
	 * @return this.destination
	 */
	public T getDestination() {
		return destination;
	}
	
	/**
	 * Returns true if this.label and other.label are equal and this.destination 
	 * and other.destination are equal.
	 * 
	 * @return true iff this.label and other.label are equal and this.destination 
	 * and other.destination are equal
	 */
	@Override
	public boolean equals(Object other) {
		if(! (other instanceof Edge<?, ?>)) {
			return false;
		}
		Edge<?, ?> e = (Edge<?, ?>) other;
		return this.getLabel().equals(e.getLabel()) && 
				this.getDestination().equals(e.getDestination());
	}
	
	/**
	  * Standard hashCode function.
	  *
	  * @return an int that all objects equal to this will also return.
	  */
	@Override
	public int hashCode() {
		return 29 * label.hashCode() * destination.hashCode();
	}
	
	/**
	 * Returns a String representation of this taking the form of "label -&gt; destination".
	 * 
	 * @return a String representation of this
	 */
	@Override
	public String toString() {
		return "" + destination + "(" + label + ")";
	}
	
	/**
	 * Checks if this representation invariant holds.
	 */
	private void checkRep() {
		assert label != null : "label is null";
		assert destination != null : "destination is null";
	}
}
