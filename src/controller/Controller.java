package controller;

import model.Model;
import view.View;


/**
 * @author alon tal,omry dabush
 * <h2>Controller-Interface</h2>
 * control Model and View in MVC architecture
 */

public interface Controller {
	
	/**
	 * Sets the model.
	 *
	 * @param m the new model
	 */
	void setModel (Model m);
	
	/**
	 * Sets the view.
	 *
	 * @param v the new view
	 */
	void setView (View v);
	
	/**
	 * Display.
	 *
	 * @param s the string that need to by displayed
	 */
	void display (String[] s);
	
	/**
	 * Update.
	 *
	 * @param obj the obj that returns from the model and goes to the view 
	 */
	void update(Object obj);
}
