package hw8.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hw5.Graph;
import hw8.*;
import hw8.CampusParser.MalformedDataException;

/**
 * This class contains a number of tests that can be used to test the various
 * methods in the CampusParser class
 * 
 * @author Logan Ricord
 */

public class CampusParserTest {

	private static Graph<CampusPoint, Double> graph;
	private static Graph<CampusPoint, Double> graph2;
	private static List<CampusPoint> buildings;
	private static List<CampusPoint> buildings2;
	
	@Before
	public void setUp() throws Exception {
		graph = new Graph<CampusPoint, Double>();
		graph2 = new Graph<CampusPoint, Double>();
		buildings = new ArrayList<CampusPoint>();
		buildings2 = new ArrayList<CampusPoint>();
	}
	
	private void set() {
		graph.clear();
		graph2.clear();
		buildings.clear();
		buildings2.clear();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParsePathsNullFile() throws MalformedDataException {
		set();
		CampusParser.parsePaths(null, graph, buildings);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParsePathsNullGraph() throws MalformedDataException {
		set();
		CampusParser.parsePaths(".src/hw8/data/baseballPaths.dat", null, buildings);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParsePathsNullBuildings() throws MalformedDataException {
		set();
		CampusParser.parsePaths(".src/hw8/data/baseballPaths.dat", graph, null);
	}
	
	@Test
	public void testParsePathsEmpty() throws MalformedDataException {
		set();
		CampusParser.parsePaths("./src/hw8/data/emptyPathsFile.dat", graph, buildings);
		assertEquals("graph does not match empty graph after empty path parse",
				graph.getNodes(), graph2.getNodes());
	}
	
	@Test
	public void testParsePathsSixPaths() throws MalformedDataException {
		set();
		CampusParser.parsePaths("./src/hw8/data/sixPaths.dat", graph, buildings);
		CampusPoint p1 = new CampusPoint(5.0, 6.0);
		CampusPoint p2 = new CampusPoint(8.0, 10.0);
		CampusPoint p3 = new CampusPoint(12.0, 16.0);
		graph2.addNode(p1);
		graph2.addNode(p2);
		graph2.addNode(p3);
		graph2.addEdge(p1, p2, 5.0);
		graph2.addEdge(p2, p1, 5.0);
		graph2.addEdge(p1, p3, 12.3);
		graph2.addEdge(p3, p1, 12.3);
		graph2.addEdge(p2, p3, 7.2);
		graph2.addEdge(p3, p2, 7.2);
		assertEquals("graph does not match six-path graph after six-path parse",
				graph.getNodes(), graph2.getNodes());
	}
	
	@Test(expected=MalformedDataException.class)
	public void testParsePathsInvalid() throws MalformedDataException {
		set();
		CampusParser.parsePaths("./src/hw8/data/InvalidPaths.dat", graph, buildings);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseBuildingsNullFile() throws MalformedDataException {
		set();
		CampusParser.parseBuildings(null, buildings);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseBuildingsNullBuildings() throws MalformedDataException {
		set();
		CampusParser.parseBuildings("./src/hw8/data/EmptyBuildingsFile.dat", null);
	}
	
	@Test
	public void testParseBuildingsEmpty() throws MalformedDataException {
		set();
		CampusParser.parseBuildings("./src/hw8/data/EmptyBuildingsFile.dat", buildings);
		assertEquals("buildings list does not equal empty buildings list after empty buildings parse",
				buildings, buildings2);
	}
	
	@Test
	public void testParseBuildingsThreeBuildings() throws MalformedDataException {
		set();
		CampusParser.parseBuildings("./src/hw8/data/threeBuildings.dat", buildings);
		buildings2.add(new CampusPoint("SEA", "Seattle Mariners", 43.23, 59.923));
		buildings2.add(new CampusPoint("BOS", "Boston Red Sox", 59.321, 91.329));
		buildings2.add(new CampusPoint("NYY", "New York Yankees", 109.93, 43.129));
		assertEquals("buildings list does not equal three-building list after three-building parse",
				buildings, buildings2);
	}
	
	@Test(expected=MalformedDataException.class)
	public void testParseBuildingsInvalid() throws MalformedDataException {
		set();
		CampusParser.parseBuildings("./src/hw8/data/InvalidBuildings.dat", buildings);
	}

}
