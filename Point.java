package hw8;

/**
 * Point represents a point with the ability to also represent a building. A Point object
 * has x and y coordinates and, if it is a building, also has an abbreviated name and a
 * full name.
 * 
 * @author Logan Ricord
 */
public class Point {

	/** The x coordinate of this */
	private final Double xCoord;
	
	/** The y coordinate of this */
	private final Double yCoord;

	/** Denotes whether this is a building */
	private final boolean isBuilding;
	
	/** The abbreviated name of this, if this is a building */
	private final String abbrName;
	
	/** The full name of this, if this is a building */
	private final String fullName;
	
	// Representation invariant for all Point objects p:
	//		if p does not represent a building:
	//			p.xCoord and p.yCoord != null
	//
	//		if p represents a building:
	//			p.xCoord, p.yCoord, p.abbrName, and p.fullName != null
	//			p.isBuilding = true
	//
	// Abstraction function:
	//		Each Point p has: 
	//			coordinates (x, y) given by p.xCoord and p.yCoord;
	//			its state as a building or an ordinary point denoted by isBuilding, where
	//				if p represents a building, isBuilding = true
	//
	//			And if p represents a building, p also has:
	//				an abbreviated name given by p.abbrName, and
	//				a full name given by p.fullName
	
	/** 
	 * Constructs a Point object with the given x and y coordinates.
	 * 
	 * @param x The x coordinate of the constructed Point
	 * @param y The y coordinate of the constructed Point
	 * @effects Constructs a Point object
	 */
	public Point(double x, double y) {
		xCoord = x;
		yCoord = y;
		abbrName = null;
		fullName = null;
		isBuilding = false;
		checkRep();
	}
	
	/**
	 * Constructs a Point object that represents a building with the given abbreviated and
	 * full names and x and y coordinates.
	 * 
	 * @param abbr The abbreviated building name of the constructed Point object
	 * @param full The full building name of the constructed Point object
	 * @param x The x coordinate of the constructed Point
	 * @param y The y coordinate of the constructed Point
	 * @effects constructs a Point object
	 * 
	 * Note that if abbr or full are null or empty strings, the constructed Point will <b>not</b>
	 * represent a building (the constructed Point will only represent an ordinary point).
	 */
	public Point(String abbr, String full, double x, double y) {
		xCoord = x;
		yCoord = y;
		abbrName = abbr;
		fullName = full;
		if(abbr != null && full != null && abbr.length() != 0 && full.length() != 0)
			isBuilding = true;
		else
			isBuilding = false;
		checkRep();
	}
	
	/**
	 * Returns the x coordinate of this.
	 * 
	 * @return the x coordinate of this
	 */
	public Double getX() {
		checkRep();
		return xCoord;
	}
	
	/**
	 * Returns the y coordinate of this.
	 * 
	 * @return the y coordinate of this
	 */
	public Double getY() {
		checkRep();
		return yCoord;
	}
	
	/**
	 * If this represents a building, returns the abbreviated building name. Otherwise, returns
	 * null.
	 * 
	 * @return abbreviated name of this if this represents a building, null otherwise
	 */
	public String getAbbr() {
		checkRep();
		if(isBuilding)
			return abbrName;
		else
			return null;
	}
	
	/**
	 * If this represents a building, returns the full building name. Otherwise, returns
	 * null.
	 * 
	 * @return full name of this if this represents a building, null otherwise
	 */
	public String getName() {
		checkRep();
		if(isBuilding)
			return fullName;
		else
			return null;
	}
	
	/**
	 * Returns true if this represents a building, false otherwise.
	 * 
	 * @return true iff this represents a building
	 */
	public boolean isBuilding() {
		return isBuilding;
	}
	
	/**
	 * Returns a string representation of this.
	 * 
	 * @return a string representation of this in the form of "(x, y)" where x is the
	 * 		   x coordinate of this and y is the y coordinate of this
	 */
	@Override
	public String toString() {
		checkRep();
		return "(" + Math.round(xCoord) + ", " + Math.round(yCoord) + ")";
	}
	
	/**
	 * Returns true if this is equal to other, false otherwise.
	 * 
	 * @param other The object to be compared to this
	 * @return true iff this is equal to other
	 * 
	 * Note that the equality of two points depends only on their x and y coordinates.
	 */
	@Override
	public boolean equals(Object other) {
		checkRep();
		if(! (other instanceof Point)) {
			return false;
		}
		Point p = (Point) other;
		return (p.getX().equals(this.getX()) && p.getY().equals(this.getY()));
	}
	
	/**
	  * Standard hashCode function.
	  *
	  * @return an int that all objects equal to this will also return.
	  */
	@Override
	public int hashCode() {
		checkRep();
		return xCoord.hashCode() +  13 * yCoord.hashCode();
	}
	
	/**
	 * Checks if this representation invariant holds.
	 */
	private void checkRep() {
		assert xCoord != null : "x coordinate is null";
		assert yCoord != null : "y coordinate is null";
		if(isBuilding) {
			assert abbrName != null : "abbreviated name is null";
			assert fullName != null : "full name is null";
		}
	}
}
