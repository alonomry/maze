package model;

import controller.Controller;

public interface Model {
	public void setController(Controller controller);
	public void dirCommand(String param);
	public void generateCommand();
	public void displayCommand();
	public void displayCrossSectionCommand();
	public void saveCommand();
	public void loadCommand();
	public void mazeSizeCommand();
	public void fileSizeCommand();
	public void SolveCommand();
	public void dislplaySolutionCommand();
	public void exitCommand();
	
}
