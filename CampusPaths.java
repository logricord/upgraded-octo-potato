package hw8;

import hw5.*;
import hw7.MarvelPaths2;
import hw8.CampusParser.MalformedDataException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * CampusPaths represents a map of a campus with points read from data files
 * and the ability to find paths and directions between points.
 *  
 * @specfield point    : Point // a point on campus
 * @specfield building : Point // a point on campus that corresponds to a building
 * 								  (note: not all points are buildings)
 * @specfield edge     : Edge<Double, Point> // connects two points on campus, labeled by a 
 * 											 // distance between them
 * @specfield graph    : Graph<Point, Double> // stores points
 * 
 * @author Logan Ricord
 */

public class CampusPaths implements Iterable<CampusPoint> {

	// Abstraction Function:
	// each CampusPaths object has:
	//		- a graph of the campus with points connected by edges labeled
	// 		  with the distance between the points
	//		- a list containing all of the buildings on the campus
	//
	// Representation Invariant:
	// foreach CampusPaths c:
	//		c.graph != null && c.buildings != null
	
	private Graph<CampusPoint, Double> graph;
	private List<CampusPoint> buildings;
	
	public CampusPaths() {
		graph = new Graph<CampusPoint, Double>();
		buildings = new ArrayList<CampusPoint>();
		checkRep();
	}
	
	/**
	 * Creates a new CampusPaths object from the given files.
	 * 
	 * @param pathsFile The file with campus path data to be read
	 * @param buildingsFile The file with campus building data to be read
	 * @throws NullPointerException if pathsFile = null | buildingsFile = null
	 * 		   MalformedDataException if either given file is not well-formed
	 * 		   (further documented in the CampusParser class)
	 */
	public CampusPaths(String pathsFile, String buildingsFile) 
			throws NullPointerException, MalformedDataException {
		graph = new Graph<CampusPoint, Double>();
		buildings = new ArrayList<CampusPoint>();
		this.buildGraph(pathsFile, buildingsFile);
		checkRep();
	}
	
	/**
	 * Builds a graph with campus data from the given files.
	 * 
	 * @param campus The graph to hold campus building and path data
	 * @param pathsFile The file with campus path data
	 * @param buildingsFile The file with campus building data
	 * @throws MalformedDataException if either given file is not well-formed
	 * 		   (further documented in the CampusParser class)
	 */
	public void buildGraph(String pathsFile, String buildingsFile) 
			throws MalformedDataException {
		CampusParser.parseBuildings(buildingsFile, buildings);
		CampusParser.parsePaths(pathsFile, graph, buildings);
	}
	
	/**
	 * Finds the shortest path between two points in a graph in terms of distance. If
	 * no path found, returns null.
	 * 
	 * @param graph The graph to be searched in
	 * @param start The starting point of the path
	 * @param dest The destination point of the path
	 * @return A list of edges leading from start to dest, null if no path found
	 * @throws IllegalArgumentException if graph == null || start == null || 
	 * 		   dest == null || !graph.contains(start) || !graph.contains(dest)
	 */
	public List<Edge<Double, CampusPoint>> findPath(CampusPoint start, 
			CampusPoint dest) throws IllegalArgumentException {
		checkRep();
		return MarvelPaths2.findShortestPath(this.graph, start, dest);
	}
	
	/**
	 * Returns building with given abbreviated name, null if not found.
	 * 
	 * @param abbrName Abbreviated name of building to be searched for
	 * @return building with given abbreviated name, null if not found
	 */
	public CampusPoint getBuilding(String abbrName) {
		for(CampusPoint p : buildings) {
			if(p.getAbbr().equals(abbrName))
				return p;
		}
		return null;
	}
	
	/**
	 * Returns this list of buildings.
	 * 
	 * @return this list of buildings
	 */
	public List<CampusPoint> getBuildings() {
		return Collections.unmodifiableList(buildings);
	}
	
	/**
	 * Finds and returns the direction between two points.
	 * 
	 * @param start The starting point
	 * @param end The ending point
	 * @return a string depicting the direction (N, W, S, E, NW, NE, SW, or SE) 
	 * 		   between start and end
	 * @throws IllegalArgumentException if start or end is null
	 */
	public String getDirection(CampusPoint start, CampusPoint end) {
		checkRep();
		
		if(start == null)
			throw new IllegalArgumentException("start is null");
		
		if(end == null)
			throw new IllegalArgumentException("end is null");
		
		// Declare variables, including pi/npi for ease of use
		String direc = "";
		double dx = end.getX() - start.getX();
		double dy = start.getY() - end.getY();
		double theta = Math.atan2(dy, dx);
		double pi = Math.PI;
		double npi = -1 * Math.PI;
		
		// Use theta to find direction between points
		if(theta >= npi / 8 && theta <= pi / 8) {
			direc = "E";
		} else if(theta > pi / 8 && theta < 3 * pi / 8) {
			direc = "NE";
		} else if(theta >= 3 * pi / 8 && theta <= 5 * pi / 8) {
			direc = "N";
		} else if(theta > 5 * pi / 8 && theta < 7 * pi / 8) {
			direc = "NW";
		} else if(theta >= 7 * pi / 8 || theta <= 7 * npi / 8) {
			direc = "W";
		} else if(theta > 7 * npi / 8 && theta < 5 * npi / 8) {
			direc = "SW";
		} else if(theta >= 5 * npi / 8 && theta <= 3 * npi / 8) {
			direc = "S"; 
		} else {
			direc = "SE";
		}
		
		checkRep();
		return direc;
	}

	@Override
	public Iterator<CampusPoint> iterator() {
		return graph.iterator();
	}
	
	private void checkRep() {
		assert graph != null : "graph is null";
		assert buildings != null : "buildings is null";
	}
	
	/** Compares buildings specific to the campus path applications. */
	public static class BuildingComparator implements Comparator<CampusPoint> {

		/**
		 * Compares two points.
		 * 
		 * @param p1 First point to be compared
		 * @param p2 Second point to be compared
		 * @return a negative number if p1 < p2;
		 * 		   zero if p1 = p2;
		 * 		   a positive number if p1 > p2
		 * @throws IllegalArgumentException
		 * 				if p1 and/or p2 is null; or
		 * 				p1 and/or p2 are not campus buildings
		 */
		@Override
		public int compare(CampusPoint p1, CampusPoint p2) {
			if(p1 == null || p2 == null)
				throw new IllegalArgumentException("p1 and/or p2 is null");
				
			if(!p1.isBuilding() || !p2.isBuilding())
				throw new IllegalArgumentException
					("one or more of passed in points are not buildings");
			
			return (p1.getAbbr().compareTo(p2.getAbbr()));
		}
	}
}
