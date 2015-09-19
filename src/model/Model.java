package model;

import controller.Controller;

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
