package hw8;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hw5.*;
import hw8.CampusParser.MalformedDataException;
import hw8.CampusPaths.BuildingComparator;

/**
 * CampusPathsVC represents the view and controller for CampusPaths, providing 
 * a user interface for CampusPaths. Users can use this class to view a list of 
 * campus buildings or to find the shortest route between campus buildings.
 * 
 * @specfield point    : Point // a point on campus
 * @specfield building : Point // a point on campus that corresponds to a building
 * 								  (note: not all points are buildings)
 * @specfield graph    : Graph<Point, Double> // stores points
 * 
 * @author Logan Ricord
 */

public class CampusPathsVC {
	
	private CampusPaths model;
	
	private static final String PATHS_FILE = "./src/hw8/data/campus_paths.dat";
	private static final String BUILDINGS_FILE = "./src/hw8/data/campus_buildings.dat";
	
	/**
	 * Allows user to find shortest path between buildings and list buildings.
	 * @throws MalformedDataException if either field is a file that is not well-formed
	 * @throws NullPointerException if either field is null
	 */
	public static void main(String[] args) 
			throws NullPointerException, MalformedDataException {
		new CampusPathsVC();
	}
	
	/**
	 * Allows user to find shortest path between buildings and list buildings.
	 * @throws MalformedDataException if either field is a file that is not well-formed
	 * @throws NullPointerException if either field is null
	 */
	public CampusPathsVC() throws NullPointerException, MalformedDataException {
		model = new CampusPaths(PATHS_FILE, BUILDINGS_FILE);		
		
		Scanner key = new Scanner(System.in);
		String menu = "Menu:\n\t" + "r to find a route\n\t" + 
				"b to see a list of all buildings\n\t" + "q to quit\n";
		String prompt = "Enter an option ('m' to see the menu): ";
		String opt = "";
		
		System.out.println(menu);
		System.out.print(prompt);
		opt = key.nextLine();
		while(!opt.equals("q")) {
			if(opt.equals("r")) {
				System.out.print("Abbreviated name of starting building: ");
				String start = key.nextLine();
				System.out.print("Abbreviated name of ending building: ");
				String end = key.nextLine();
				printPath(start, end);
			} else if(opt.equals("b")) {
				listBuildings();
			} else if(opt.equals("m")) {
				System.out.println(menu);
			} else if(opt.startsWith("#") || opt.length() == 0) {
				System.out.println(opt);
			} else { // unrecognized command
				System.out.println("Unknown option\n");
			}
			if(!opt.startsWith("#") && opt.length() != 0)
				System.out.print(prompt);
			opt = key.nextLine();
		}
		key.close();
	}
		
	/**
	 * Prints the shortest path between start and end in campus.
	 * 
	 * @param campus The graph to be searched in for a path.
	 * @param start The starting point of the path
	 * @param end The end point of the path
	 * @effects prints the shortest path between start and end in campus, including distances
	 * 			and direction of travel
	 * @throws IllegalArgumentException if campus, start, or end are null
	 */
	private void printPath(String start, String end) throws IllegalArgumentException {
		if(start == null)
			throw new IllegalArgumentException("abbreviated start name is null");
		
		if(end == null)
			throw new IllegalArgumentException("abbreviated end name is null");
		
		boolean startValid = false;
		boolean endValid = false;
		CampusPoint startBuild = null;
		CampusPoint endBuild = null;

		for(CampusPoint p : model) {
			if(p.isBuilding()) {
				if(p.getAbbr().equals(start)) {
					startBuild = p;
					startValid = true;
				}
				if(p.getAbbr().equals(end)) {
					endBuild = p;
					endValid = true;
				}
			}
		}
		
		if(!startValid)
			System.out.println("Unknown building: " + start);
		if(!endValid)
			System.out.println("Unknown building: " + end);
		
		if(startValid && endValid) {
			List<Edge<Double, CampusPoint>> path = model.findPath(startBuild, endBuild);
			System.out.println("Path from " + startBuild.getName() + " to " + 
					endBuild.getName() + ":");	
			
			CampusPoint curr = startBuild;
			double totalDist = 0.0;
			for(int i = 0; i < path.size(); i++) {
				Edge<Double, CampusPoint> currEdge = path.get(i);
				String direc = model.getDirection(curr, currEdge.getDestination());
				System.out.println("\tWalk " + Math.round(currEdge.getLabel()) + " feet " + 
						direc + " to " + currEdge.getDestination());
				totalDist = totalDist + currEdge.getLabel();
				curr = currEdge.getDestination();
			}
			System.out.println("Total distance: " + Math.round(totalDist) + " feet");
		}
		System.out.println();
	}
	
	/**
	 * Lists the buildings in the given campus.
	 * 
	 * @param campus The graph to be searched for for buildings.
	 * @effects prints out the buildings in campus, ordered lexicographically
	 */
	private void listBuildings() {
		System.out.println("Buildings:");
		
		Set<CampusPoint> buildings = new TreeSet<CampusPoint>(new BuildingComparator());
		for(CampusPoint p : model) {
			if(p.isBuilding()) {
				buildings.add(p);
			}
		}
		for(CampusPoint p : buildings) {
			System.out.println("\t" + p.getAbbr() + ": " + p.getName());
		}
		System.out.println();
	}
}
