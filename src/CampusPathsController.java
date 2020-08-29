package hw9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hw8.CampusPaths;
import hw8.CampusPoint;

/** 
 * CampusPathsController is the controller for the CampusPaths model, listening
 * for user interaction with the GUI.
 */

public class CampusPathsController {

	/** The model in the model-view-controller architectural pattern */
	private CampusPaths model;
	/** The view in the model-view-controller architectural pattern */
	private CampusPathsView view;
	
	/**
	 * Adds button functions to the buttons in the GUI.
	 * 
	 * @param model The model used in the MVC architecture
	 * @param view The view used in the MVC architecture
	 */
	public CampusPathsController(CampusPaths model, CampusPathsView view) {
		this.model = model;
		this.view = view;
		view.getFindButton().addActionListener(new FindListener());
		view.getResetButton().addActionListener(new ResetListener());
	}
	
	/** Adds reset functionality to the reset button */
	private class ResetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(view.getResetButton())) {
				view.getStartingBox().setSelectedIndex(0);
				view.getDestBox().setSelectedIndex(0);
			}
			view.clearPath();
		}
		
	}
	
	/** Adds find path functionality to the find button */
	private class FindListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(view.getFindButton())) {
				view.clearPath();
				String[] startTokens = 
						((String)view.getStartingBox().getSelectedItem()).split("-");
				String[] endTokens = 
						((String)view.getDestBox().getSelectedItem()).split("-");
				
				// If both selected items are buildings
				if(startTokens.length == 2 && endTokens.length == 2) {
					String startAbbr = startTokens[0].trim();
					String endAbbr = endTokens[0].trim();
					CampusPoint start = null;
					CampusPoint end = null;
					for(CampusPoint p : model.getBuildings()) {
						if(p.getAbbr().equals(startAbbr))
							start = p;
						if(p.getAbbr().equals(endAbbr))
							end = p;
					}
					
					view.setEndpoints(start, end);
					view.setPath(model.findPath(start, end));
					
					// Calculate estimated walk time based on avg human walk speed = 4.6 ft/s
					int time = (int) Math.round((view.getDistance() / 4.6) / 60);
					view.getDistText().setText(" Distance (feet): " + view.getDistance() + " ");
					view.getWalkTimeText().setText(" Walk Time (min): " + time + " ");
				}
			}
		}
		
	}
	
}
