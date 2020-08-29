package hw8;

import java.io.*;
import java.util.*;
import hw5.Graph;

/**
 * Parser utility to load data for campus paths and buildings.
 * 
 * @specfield point    : Point // a point on campus
 * @specfield building : Point // a point on campus that corresponds to a building
 * 								  (note: not all points are buildings)
 * 
 * @author Logan Ricord
 */
public class CampusParser {
	
	// Note: CampusParser does not represent an ADT
	
	/**
	 * A checked exception class for bad data files
	 */
	@SuppressWarnings("serial")
	public static class MalformedDataException extends Exception {
		public MalformedDataException() {
		}

		public MalformedDataException(String message) {
			super(message);
		}

		public MalformedDataException(Throwable cause) {
			super(cause);
		}

		public MalformedDataException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * Reads the campus paths dataset. Each non-tabbed line of the input file contains an
	 * x coordinate and y coordinate (separated by a comma) referring to a source point on 
	 * campus followed by a number of tabbed lines, each containing an x coordinate and y 
	 * coordinate (separated by a comma) referring to a destination point, followed by a 
	 * colon and a distance from the source point to the destination point.
	 * 
	 * @requires pathsFile is a valid file path
	 * @param pathsFile The file that will be read
	 * @param graph The graph to have campus data added to it
	 * @param buildings The list of buildings on campus
	 * @modifies graph
	 * @throws MalformedDataException 
	 * 			   if any non-tabbed line does not contain exactly two tokens, 
	 * 			   each separated by a comma, or else starting with a 
	 * 			   # symbol to indicate a comment line; or
	 * 			   if any tabbed line does not contain exactly three tokens,
	 * 			   separated by a comma then a colon, or else starting
	 * 			   with a # symbol to indicate a comment line
	 * @throws IllegalArgumentException
	 * 			   if pathsFile, graph, or buildings is null
	 */
	public static void parsePaths(String pathsFile, Graph<CampusPoint, Double> graph, 
			List<CampusPoint> buildings) throws MalformedDataException, 
			IllegalArgumentException {
		
		if(pathsFile == null)
			throw new IllegalArgumentException("pathsFile is null");
		
		if(buildings == null)
			throw new IllegalArgumentException("buildings is null");
		
		if(graph == null)
			throw new IllegalArgumentException("graph is null");
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pathsFile));
			String inputLine = reader.readLine();
			while (inputLine != null) {

				// Ignore comment lines.
				if (inputLine.startsWith("#")) {
					continue;
				}

				// Begin parsing the data, throwing an exception for malformed lines.
				String[] tokens = inputLine.split(",");
				if (tokens.length != 2) {
					throw new MalformedDataException(
							"Line should contain exactly one comma: "
									+ inputLine);
				}

				// Save parsed data into variables and create Point object
				double xCoord = Double.parseDouble(tokens[0]);
				double yCoord = Double.parseDouble(tokens[1]);
				CampusPoint src = new CampusPoint(xCoord, yCoord);
				
				// If point is a building, add building into graph; otherwise, add point
				if(buildings.contains(src)) {
					graph.addNode(buildings.get(buildings.indexOf(src)));
				} else {
					graph.addNode(src);
				}
				
				inputLine = reader.readLine();
				while(inputLine != null && inputLine.startsWith("\t")) {
					// Separate line into a Point and a distance, throwing an exception
					// for malformed lines
					tokens = inputLine.split("[:,]");
					if(tokens.length != 3) {
						throw new MalformedDataException(
								"Line should contain three tokens - first two separated by a comma," +
								" second and third separated by a colon: " +
								inputLine + "; tokens: " + tokens.length);
					}
					
					// Save parsed data into variables and create Point object
					double xCoordDest = Double.parseDouble(tokens[0]);
					double yCoordDest = Double.parseDouble(tokens[1]);
					CampusPoint dest = new CampusPoint(xCoordDest, yCoordDest);
					// Must remove preceding space for parsing the distance
					double dist = Double.parseDouble(tokens[2].replace(" ", ""));
					
					// Add edge made from distance and created Point into graph
					if(buildings.contains(dest)) {
						graph.addNode(buildings.get(buildings.indexOf(dest)));
						graph.addEdge(src, buildings.get(buildings.indexOf(dest)), dist);
					} else {
						graph.addNode(dest);
						graph.addEdge(src, dest, dist);
					}
					
					inputLine = reader.readLine();
				}
			}
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} catch (RuntimeException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.toString());
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	/**
	 * Reads the campus buildings dataset. Each line of the input file contains a
	 * campus building's abbreviated name, full name, x coordinate, and y coordinate,
	 * all separated by a tab value.
	 * 
	 * @requires buildingsFile is a valid file path
	 * @param buildingsFile The file that will be read
	 * @param buildings The list that will contain buildings from file
	 * @modifies buildings
	 * @throws MalformedDataException 
	 * 			   if any line does not contain exactly four tokens, 
	 * 			   each separated by a tab, or else starting with a 
	 * 			   # symbol to indicate a comment line
	 * @throws IllegalArgumentException
	 * 			   if buildingsFile or buildings is null
	 */
	public static void parseBuildings(String buildingsFile, List<CampusPoint> buildings) 
			throws MalformedDataException, IllegalArgumentException {
		
		if(buildingsFile == null) 
			throw new IllegalArgumentException("buildingsFile is null");
		
		if(buildings == null)
			throw new IllegalArgumentException("buildings is null");
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(buildingsFile));
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				// Ignore comment lines.
				if (inputLine.startsWith("#")) {
					continue;
				}

				// Parse the data into variables needed for Building object
				String[] tokens = inputLine.split("\t");
				if (tokens.length != 4) {
					throw new MalformedDataException(
							"Line should contain exactly three tabs: "
									+ inputLine);
				}

				// save parsed data into variables and create Point object
				String abbrName = tokens[0];
				String fullName = tokens[1];
				double xCoord = Double.parseDouble(tokens[2]);
				double yCoord = Double.parseDouble(tokens[3]);
				CampusPoint building = new CampusPoint(abbrName, fullName, xCoord, yCoord);
				
				// add created Building into returned set
				buildings.add(building);
			}
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} catch (RuntimeException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.toString());
					e.printStackTrace(System.err);
				}
			}
		}
	}
}