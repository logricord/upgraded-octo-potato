package hw8.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hw8.CampusPoint;

/**
 * This class contains a number of tests that can be used to test the various
 * methods in the CampusPoint class.
 * 
 * @author Logan Ricord
 */

public class CampusPointTest {

	private static CampusPoint p;
	private static CampusPoint p2;
	private static CampusPoint p3;
	private static CampusPoint b;
	private static CampusPoint b2;
	private static CampusPoint b3;
	
	private static final Double X = 3.45;
	private static final Double Y = 12.53;
	private static final long X_ROUNDED = Math.round(X);
	private static final long Y_ROUNDED = Math.round(Y);
	private static final String ABBR = "SEA";
	private static final String FULL = "Seattle Mariners";
	
	@Before
	public void setUp() throws Exception {
		p = new CampusPoint(X, Y);
		p2 = new CampusPoint(X, Y);
		p3 = new CampusPoint(500.51, 31.325);
		b = new CampusPoint(ABBR, FULL, X, Y);
		b2 = new CampusPoint(ABBR, FULL, X, Y);
		b3 = new CampusPoint("SEA", "Seattle SuperSonics", 500.51, 31.325);
	}

	@Test
	public void testGetX() {
		assertEquals("getX does not return correct x value", X, p.getX());
	}
	
	@Test
	public void testGetY() {
		assertEquals("getY does not return correct y value", Y, p.getY());
	}
	
	@Test
	public void testGetAbbr() {
		assertEquals("getAbbr on building does not return correct abbreviation",
				ABBR, b.getAbbr());
	}
	
	@Test
	public void testGetNullAbbr() {
		assertEquals("getAbbr on non-building does not return null", null, p.getAbbr());
	}
	
	@Test
	public void testGetName() {
		assertEquals("getName on building does not return correct name", FULL, b.getName());
	}
	
	@Test
	public void testGetNullName() {
		assertEquals("getName on non-building does not return null", null, p.getName());
	}
	
	@Test
	public void testIsBuildingOnBuilding() {
		assertTrue("isBuilding test on building returned false", b.isBuilding());
	}
	
	@Test
	public void testIsBuildingOnNonBuilding() {
		assertFalse("isBuilding test on non-building returned true", p.isBuilding());
	}
	
	@Test
	public void testToStringBuilding() {
		assertEquals("toString on building does not match expected",
				"(" + X_ROUNDED + ", " + Y_ROUNDED + ")", b.toString());
	}
	
	@Test
	public void testToStringNonBuilding() {
		assertEquals("toString on non-building does not match expected",
				"(" + X_ROUNDED + ", " + Y_ROUNDED + ")", p.toString());
	}
	
	@Test
	public void testEqualsTwoUnequalBuildings() {
		assertFalse("equals on two unequal buildings returned true", b.equals(b3));
	}
	
	@Test
	public void testEqualsReflexiveTwoEqualBuildings() {
		assertTrue("reflexive equals test on two equal buildings returned false",
				b.equals(b2));
		assertTrue("reflexive equals test on two equal buildings returned false", 
				b2.equals(b));
	}
	
	@Test
	public void testEqualsSymmetricBuilding() {
		assertTrue("symmetric equals test on building returned false", b.equals(b));
	}
	
	@Test
	public void testEqualsTwoUnequalNonBuildings() {
		assertFalse("equals on two unequal points returned true", p.equals(p3));
	}
	
	@Test
	public void testEqualsReflexiveTwoEqualNonBuildings() {
		assertTrue("reflexive equals test on two equal points returned false", 
				p.equals(p2));
		assertTrue("reflexive equals test on two equal points returned false", 
				p2.equals(p));
	}
	
	@Test
	public void testEqualsSymmetricNonBuilding() {
		assertTrue("symmetric equals test on non-building returned false", p.equals(p));
	}
	
	@Test
	public void testEqualsOneBuildingOneNonBuildingEqual() {
		assertTrue("equals on equal point and building returned false", p.equals(b));
	}
	
	@Test
	public void testEqualsOneBuildingOneNonBuildingUnequal() {
		assertFalse("equals on unequal building and non-building returned true",
				p.equals(b3));
	}
}