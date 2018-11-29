package hw8.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hw5.*;
import hw8.*;
import hw8.CampusPaths.BuildingComparator;
import hw8.CampusParser.MalformedDataException;

/**
 * This class contains a number of tests that can be used to test the various
 * methods in the CampusPaths class
 * 
 * @author Logan Ricord
 */

public class CampusPathsTest {

	private static CampusPaths model;
	private static Graph<CampusPoint, Double> graph;
	private static Graph<CampusPoint, Double> graph2;
	private static List<Edge<Double, CampusPoint>> path;
	private static List<Edge<Double, CampusPoint>> path2;
	private static CampusPoint p1;
	private static CampusPoint p2;
	private static CampusPoint p3;
	private static BuildingComparator b;
	
	@Before
	public void setUp() throws Exception {
		model = new CampusPaths();
		graph = new Graph<CampusPoint, Double>();
		graph2 = new Graph<CampusPoint, Double>();
		path = new ArrayList<Edge<Double, CampusPoint>>();
		path2 = new ArrayList<Edge<Double, CampusPoint>>();
		p1 = new CampusPoint("SEA", "Seattle Mariners", 1.0, 4.0);
		p2 = new CampusPoint("BOS", "Boston Red Sox", 3.0, 7.0);
		p3 = new CampusPoint("NYY", "New York Yankees", 2.0, 3.0);
		b = new BuildingComparator();
	}
	
	private void set() {
		graph.clear();
		graph2.clear();
		path.clear();
		path2.clear();
	}

	@Test
	public void testBuildGraphBaseballFiles() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", 
				"./src/hw8/data/baseballBuildings.dat");
		graph2.addNode(p1);
		graph2.addNode(p2);
		graph2.addNode(p3);
		graph2.addNode(new CampusPoint("OAK", "Oakland Athletics", 10.0, 5.0));
		graph2.addNode(new CampusPoint("LAA", "Los Angeles Angels", 9.0, 8.0));
		graph2.addNode(new CampusPoint("TEX", "Texas Rangers", 3.0, 12.0));
		graph2.addNode(new CampusPoint("TOR", "Toronto Blue Jays", 5.0, 5.0));
		int remaining = graph2.nodeCount();
		for(CampusPoint p : model) {
			assertTrue("Incorrect node in built graph", graph2.contains(p));
			remaining--;
		}
		assertEquals("Incorrect number of nodes in built graph", 0, remaining);	
	}
	
	@Test(expected=MalformedDataException.class)
	public void testBuildGraphInvalidPathFile() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/InvalidPaths.dat", 
				"./src/hw8/data/baseballBuildings.dat");
	}
	
	@Test(expected=MalformedDataException.class)
	public void testBuildGraphInvalidBuildingFile() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", 
				"./src/hw8/data/InvalidBuildings.dat");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBuildGraphNullPathsFile() throws MalformedDataException {
		set();
		model.buildGraph(null, "./src/hw8/data/baseballBuildings.dat");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBuildGraphNullBuildingsFile() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", null);
	}
	
	@Test
	public void testFindPathBaseballFile() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", 
				"./src/hw8/data/baseballBuildings.dat");
		path = model.findPath(p1, p3);
		path2.add(new Edge<Double, CampusPoint>(5.0, p2));
		path2.add(new Edge<Double, CampusPoint>(2.0, p3));
		assertEquals("path from findPath does not equal expected path", path, path2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindPathNullStartPoint() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", 
				"./src/hw8/data/baseballBuildings.dat");
		model.findPath(null, p3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindPathNullEndPoint() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", 
				"./src/hw8/data/baseballBuildings.dat");
		model.findPath(p1, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindPathFirstPointNotInGraph() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", 
				"./src/hw8/data/baseballBuildings.dat");
		model.findPath(new CampusPoint(200.0, 200.0), p1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindPathSecondPointNotInGraph() throws MalformedDataException {
		set();
		model.buildGraph("./src/hw8/data/baseballPaths.dat", 
				"./src/hw8/data/baseballBuildings.dat");
		model.findPath(p1, new CampusPoint(200.0, 200.0));
	}
	
	@Test
	public void testGetDirection() {
		CampusPoint c1 = new CampusPoint(1.0, 2.0);
		CampusPoint c2 = new CampusPoint(4.0, 3.0);
		String direc = model.getDirection(c1, c2);
		assertEquals("getDirection on two points is not correct", "E", direc);
		
		String direc2 = model.getDirection(c2, c1);
		assertEquals("getDirection on two points is not correct", "W", direc2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetDirectionNullStart() {
		model.getDirection(null, p1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetDirectionNullEnd() {
		model.getDirection(p1, null);
	}
	
	@Test
	public void testCompareTwoBuildingsGreater() {
		assertTrue("comparison of two buildings did not return positive num as expected",
				b.compare(p1, p2) > 0);
	}
	
	@Test
	public void testCompareTwoBuildingsLess() {
		assertTrue("comparison of two buildings did not return negative num as expected",
				b.compare(p2, p1) < 0);
	}
	
	@Test
	public void testCompareTwoBuildingsSame() {
		assertTrue("comparison of two identical buildings did not return 0 as expected",
				b.compare(p1, p1) == 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCompareNonBuildings() {
		b.compare(new CampusPoint(1.0, 1.0), new CampusPoint(1.0, 2.0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCompareNullBuilding() {
		b.compare(null, new CampusPoint(1.0, 1.0));
	}
}
