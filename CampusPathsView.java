package hw9;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import hw5.Edge;
import hw8.CampusPaths;
import hw8.CampusPoint;


/**
 * CampusPathsView is the view for the CampusPaths model, providing a GUI for the
 * user that allows the user to more easily view and interact with campus data.
 */
public class CampusPathsView {

	/** The model in the model-view-controller architectural pattern */
	private CampusPaths model;
	/** The frame holding all of the GUI components */
	private JFrame frame;
	
	/** The panel holding all of the controls for the GUI */
	private JPanel controls;
	/** The button providing reset functionality for the GUI */
	private JButton resetButton;
	/** The button providing find path functionality for the GUI */
	private JButton findButton;
	
	/** The list of buildings used as starting buildings for a path in the GUI */
	private JComboBox<String> startBuildings;
	/** The list of buildings used as destination buildings for a path in the GUI */
	private JComboBox<String> endBuildings;
	
	/** Displays the distance between two buildings */
	private JTextArea distance;
	/** Displays the estimated travel time (on foot) between buildings */
	private JTextArea walkTime;
	
	/** Displays the map of campus */
	private CampusMap map;
	
	/** Two colors used primarily in the GUI */
	private static final Color PRIMARY_COLOR = new Color(100, 0, 200);
	private static final Color SECONDARY_COLOR = new Color(255, 255, 220);
	
	/**
	 * Creates a GUI for finding campus paths.
	 * 
	 * @param model The model used in the MVC architecture
	 */
	public CampusPathsView(CampusPaths model) {
		try {
			this.model = model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Set up the frame
		frame = new JFrame("Campus Path Finder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1024, 768));
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	
		// Set up all of the components
		addMap();
		createControls();
		addButtons();
		addBoxes();
		addText();
		
		frame.add(controls);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * (helper) Creates and adds the map to the frame.
	 */
	private void addMap() {
		map = new CampusMap(model);
		map.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 3));
		frame.add(map);
		frame.add(Box.createRigidArea(new Dimension(0, 5)));
	}
	
	/**
	 * (helper) Creates the control panel.
	 */
	private void createControls() {
		controls = new JPanel();
		controls.setPreferredSize(new Dimension(975, 75));
		controls.setMaximumSize(new Dimension(975, 70));
		controls.setBackground(SECONDARY_COLOR);
		controls.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 3));
	}
	
	/**
	 * (helper) Adds buttons to the control panel.
	 */
	private void addButtons() {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		Border padding = BorderFactory.createEmptyBorder(5, 12, 5, 12);
		
		findButton = new JButton(" Find ");
		findButton.setBackground(new Color(225, 255, 225));
		findButton.setBorder(BorderFactory.createCompoundBorder(border, padding));
		controls.add(findButton);
		
		resetButton = new JButton("Reset");
		resetButton.setBackground(new Color(255, 225, 225));
		resetButton.setBorder(BorderFactory.createCompoundBorder(border, padding));
		controls.add(resetButton);
	}
	
	/**
	 * (helper) Adds boxes with starting/destination building lists to control panel.
	 */
	private void addBoxes() {
		try {
			startBuildings = new JComboBox<String>();
			startBuildings.addItem("Select a starting building ...");
			endBuildings = new JComboBox<String>();
			endBuildings.addItem("Select a destination building ...");
			
			for(CampusPoint p : model.getBuildings()) {
				String str = p.getAbbr() + " - " + p.getName();
				startBuildings.addItem(str);
				endBuildings.addItem(str);
			}
			controls.add(startBuildings);
			controls.add(endBuildings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * (helper) Adds text displays to control panel.
	 */
	private void addText() {
		distance = new JTextArea(" Distance (feet): - ");
		distance.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
		controls.add(distance);
		
		walkTime = new JTextArea(" Walk Time (min): - ");
		walkTime.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
		controls.add(walkTime);
	}
	
	/**
	 * Returns the box containing the starting buildings.
	 *
	 * @return the box containing the starting buildings
 	 */
	public JComboBox<String> getStartingBox() {
		return startBuildings;
	}
	
	/**
	 * Returns the box containing the destination buildings.
	 *
	 * @return the box containing the destination buildings
 	 */
	public JComboBox<String> getDestBox() {
		return endBuildings;
	}
	
	/**
	 * Returns the find button.
	 * 
	 * @return the find button
	 */
	public JButton getFindButton() {
		return findButton;
	}
	
	/** 
	 * Returns the reset button.
	 * 
	 * @return the reset button
	 */
	public JButton getResetButton() {
		return resetButton;
	}
	
	/**
	 * Returns the text area holding the distance text.
	 * 
	 * @return the text area holding the distance text
	 */
	public JTextArea getDistText() {
		return distance;
	}
	
	/**
	 * Returns the text area holding the walk time text.
	 * 
	 * @return the text area holding the walk time text
	 */
	public JTextArea getWalkTimeText() {
		return walkTime;
	}
	
	/**
	 * Sets the endpoints in the map to be start and end.
	 * 
	 * @param start The start of the path
	 * @param end The end of the path (or destination)
	 */
	public void setEndpoints(CampusPoint start, CampusPoint end) {
		map.startPoint = start;
		map.endPoint = end;
	}
	
	/**
	 * Sets the path in the map to the given path.
	 * 
	 * @param path The path to be set as the map's current path
	 */
	public void setPath(List<Edge<Double, CampusPoint>> path) {
		map.path = path;
		for(int i = 0; i < path.size(); i++) {
			map.distance = map.distance + (int)Math.round(path.get(i).getLabel());
		}
	}
	
	/**
	 * Clears the map's current path.
	 */
	public void clearPath() {
		distance.setText(" Distance (feet): - ");
		walkTime.setText(" Walk Time (min): - ");
		map.clearPath();
	}
	
	/**
	 * Returns the distance of the map's current path.
	 * 
	 * @return the distance of the map's current path
	 */
	public int getDistance() {
		return map.distance;
	}
	
	/**
	 * Returns the width of the map image.
	 * 
	 * @return the width of the map image.
	 */
	public int getImageWidth() {
		return map.imageWidth;
	}
	
	/**
	 * Returns the height of the map image.
	 * 
	 * @return the height of the map image.
	 */
	public int getImageHeight() {
		return map.imageHeight;
	}
	
	/**
	 * Returns the current displayed width of the map image. 
	 *
	 * @return the current displayed width of the map image
	 */
	public int getCurrWidth() {
		return map.currWidth;
	}
	
	/**
	 * Returns the current displayed height of the map image. 
	 *
	 * @return the current displayed height of the map image
	 */
	public int getCurrHeight() {
		return map.currHeight;
	}
	
	/**
	 * Returns the abbreviation of the currently selected starting building,
	 * or the string itself if the selected item is not a building.
	 * 
	 * @return the abbreviation of the currently selected starting building
	 */
	public String getSelectedStart() {
		String[] arr = ((String)startBuildings.getSelectedItem()).split("-");
		return arr[0].trim();
	}
	
	/**
	 * Returns the abbreviation of the currently selected destination building,
	 * or the string itself if the selected item is not a building.
	 * 
	 * @return the abbreviation of the currently selected destination building
	 */
	public String getSelectedDest() {
		String[] arr = ((String)endBuildings.getSelectedItem()).split("-");
		return arr[0].trim();	
	}
	
	/**
	 * CampusMap provides additional information and functionality for a campus map 
	 * image, including the ability to draw paths on the map, circle buildings on the 
	 * map and highlight selected buildings on the map.
	 */
	class CampusMap extends JPanel {
		
		private static final long serialVersionUID = 1L;

		/** The model in the model-view-controller architecture that contains this */
		private CampusPaths model;
		
		/** The campus map image */
		private BufferedImage image;
		/** The width of the campus map image */
		private int imageWidth;
		/** The height of the campus map image */
		private int imageHeight;
		
		/** The current displayed width of the campus map image */
		private int currWidth;
		/** The current displayed height of the campus map image */
		private int currHeight;
		
		/** The distance of the current path */
		private int distance;
		/** The current path */
		private List<Edge<Double, CampusPoint>> path;
		/** The starting building of the current path */
		private CampusPoint startPoint;
		/** The destination building of the current path */
		private CampusPoint endPoint;
		
		/** The file containing the campus map image */
		private static final String IMAGE_FILE = "./src/hw8/data/campus_map.jpg";
		
		/**
		 * Constructs the CampusMap for the given model.
		 * 
		 * @param model The model to be used for constructing this
		 */
		public CampusMap(CampusPaths model) {
			path = null;
			try {
				this.model = model;
				image = ImageIO.read(new File(IMAGE_FILE));
				imageWidth = image.getWidth();
				imageHeight = image.getHeight();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			currWidth = this.getWidth();
			currHeight = this.getHeight();
			distance = 0;
		}
		
		/**
		 * Paints the map with the campus buildings circled, selected buildings
		 * (if any) with filled-in circles, and the current path (if any) drawn.
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			currWidth = this.getWidth();
			currHeight = this.getHeight();
			g2.drawImage(image, 0, 0, currWidth, currHeight, null);
			
			g2.setColor(SECONDARY_COLOR);
			circleBuildings(g2);
			
			g2.setColor(PRIMARY_COLOR);
			fillStartingBuilding(g2);
			fillDestBuilding(g2);
			
			drawPath(g2);
			repaint();
		}
		
		/**
		 * Circles all of the buildings on the campus map.
		 */
		private void circleBuildings(Graphics2D g2) {
			for(CampusPoint p : model.getBuildings()) {
				if(p.isBuilding()) {
					int xCoord = (int) Math.round(p.getX() * currWidth / imageWidth);
					int yCoord = (int) Math.round(p.getY() * currHeight / imageHeight);
					g2.drawOval(xCoord - 7, yCoord - 7, 14, 14);
				}
			}
		}
		
		/**
		 * Fills in the circle of the selected started building (if any).
		 */
		private void fillStartingBuilding(Graphics2D g2) {
			if(!getSelectedStart().startsWith("Select")) {
				CampusPoint start = model.getBuilding(getSelectedStart());
				if(startPoint != null && !startPoint.equals(start))
					clearPath();
				if(start != null) {
					int xStart = (int) Math.round(start.getX() * currWidth / imageWidth);
					int yStart = (int) Math.round(start.getY() * currHeight / imageHeight);
					g2.fillOval(xStart - 6, yStart - 6, 12, 12);
				}
			}
		}
		
		/**
		 * Fills in the circle of the selected destination building (if any).
		 */
		private void fillDestBuilding(Graphics2D g2) {
			if(!getSelectedDest().startsWith("Select")) {
				CampusPoint dest = model.getBuilding(getSelectedDest());
				if(endPoint != null && !endPoint.equals(dest))
					clearPath();
				if(dest != null) {
					int xDest = (int) Math.round(dest.getX() * currWidth / imageWidth);
					int yDest = (int) Math.round(dest.getY() * currHeight / imageHeight);
					g2.fillOval(xDest - 6, yDest - 6, 12, 12);
				}
			}
		}
		
		/**
		 * Draws the current path onto the map (if any).
		 */
		private void drawPath(Graphics2D g2) {
			if(path != null) {
				currHeight = this.getHeight();
				currWidth = this.getWidth();
				g2.setColor(PRIMARY_COLOR);
				
				// traverse the entire path, drawing lines for each segment
				for(int i = 0; i < path.size() - 1; i++) {
					CampusPoint p1 = path.get(i).getDestination();
					CampusPoint p2 = path.get(i+1).getDestination();
					int p1x = (int) Math.round(p1.getX() * currWidth / imageWidth);
					int p1y = (int) Math.round(p1.getY() * currHeight / imageHeight);
					int p2x = (int) Math.round(p2.getX() * currWidth / imageWidth);
					int p2y = (int) Math.round(p2.getY() * currHeight / imageHeight);
					
					// draw line segment
					g2.drawLine(p1x, p1y, p2x, p2y);
				}
			}
		}
		
		/** 
		 * Clears the current path (if any).
		 */
		public void clearPath() {
			if(path != null)
				path.clear();
			distance = 0;
		}
	}
	
}
