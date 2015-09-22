package model;

import controller.Controller;
/**
 *<h2> Model Interface</h2>
 *This Interface dictates the behavior of our model
 *with the commands :<br>
 *@param setController
 *@param generateCommand
 *@param displayCommand
 *@param displayCrossSectionCommand
 *@param saveCommand
 *@param loadCommand
 *@param mazeSizeCommand
 *@param fileSizeCommand
 *@param SolveCommand
 *@param dislplaySolutionCommand
 *@param exitCommand
 *
 *
 * @author Alon Tal, Omry Dabush
 *
 */
public interface Model {
	public void setController(Controller controller);
	public void dirCommand(String[] param);
	public void generateCommand(String[] param);
	public void displayCommand(String[] param);
	public void displayCrossSectionCommand(String[] param);
	public void saveCommand(String[] param);
	public void loadCommand(String[] param);
	public void mazeSizeCommand(String[] param);
	public void fileSizeCommand(String[] param);
	public void SolveCommand(String[] param);
	public void dislplaySolutionCommand(String[] param);
	public void exitCommand();
	
}
