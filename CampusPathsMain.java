package hw9;

import hw8.CampusPaths;

/**
 * CampusPathsMain creates a GUI of campus, allowing the user to see the
 * shortest path between two buildings on campus along with the distances
 * between the two buildings and the estimated travel time (on foot).
 */

public class CampusPathsMain {
	
	/** The paths file to be read from */
	private static final String PATHS_FILE = "./src/hw8/data/campus_paths.dat";
	/** The buildings file to be read from */
	private static final String BUILDINGS_FILE = "./src/hw8/data/campus_buildings.dat";
	
	/**
	 * Runs the campus paths GUI.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CampusPaths model = new CampusPaths(PATHS_FILE, BUILDINGS_FILE);
			CampusPathsView view = new CampusPathsView(model);
			new CampusPathsController(model, view);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
