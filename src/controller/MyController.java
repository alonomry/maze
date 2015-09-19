package controller;

import java.util.HashMap;

import model.Model;
import model.MyModel;
import view.View;

public class MyController implements Controller{

	Model model;
	View view;
	HashMap<String, Command> hashCommand;
	
	public MyController() {
		InitCommands();
	}
	
	private void InitCommands() {
		hashCommand.put("dirs", ((MyModel)model).dirCommand());
//		hashCommand.put("generate 3d maze", )
		
	}

	@Override
	public void setModel(Model m) {
		this.model = m;
		
	}

	@Override
	public void setView(View v) {
		this.view = v;
		
	}

	@Override
	public void display(String[] s) {
		// TODO Auto-generated method stub
		
	}

}
