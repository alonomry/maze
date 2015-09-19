package model;

import java.io.File;
import controller.Controller;

public class MyModel implements Model{

	Controller controller;

	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void dirCommand(String param) {
		File f = new File(param);	
		f.list();
	}

	@Override
	public void generateCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayCrossSectionCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mazeSizeCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fileSizeCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SolveCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dislplaySolutionCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitCommand() {
		System.out.println("Good Bye");
		
	}
	


}
