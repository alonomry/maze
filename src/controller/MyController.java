package controller;

import java.util.HashMap;
import model.Model;
import view.View;

public class MyController implements Controller{

	Model model;
	View view;
	HashMap<String, Command> hashCommand;
	
	
	public MyController(Model m,View v) {
		this.model = m;
		this.view = v;
		this.hashCommand = new HashMap<>();
		InitCommands();
	}
	
	private void InitCommands() {
		hashCommand.put("dir",new dirCommand());
		hashCommand.put("generate 3d maze",new generateCommand());
		hashCommand.put("display",new displayCommand());
		hashCommand.put("display cross section by",new displayCrossSectionCommand());
		hashCommand.put("save maze",new saveCommand());
		hashCommand.put("load maze",new loadCommand());
		hashCommand.put("maze size",new mazeSizeCommand());
		hashCommand.put("file size",new fileSizeCommand());
		hashCommand.put("solve",new solveCommand());
		hashCommand.put("display solution",new displaySolutionCommand());
		hashCommand.put("exit",new exitCommand());
		
	}
	
	

	@Override
	public void setModel(Model m) {
		this.model = m;
		
	}

	@Override
	public void setView(View v) {
		this.view = v;
		
	}

	public HashMap<String, Command> getHashCommand() {
		return hashCommand;
	}

	@Override
	public void display(String[] s) {
		// TODO Auto-generated method stub
		
	}
	public void update(Object obj) {
		view.display(obj);
		
	}
	
	
	
	// ----------------------------------------
	// ------------commands classes------------
	// ----------------------------------------
	
	
	public class dirCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class generateCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class displayCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class displayCrossSectionCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class saveCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class loadCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class mazeSizeCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class fileSizeCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class solveCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class displaySolutionCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class exitCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.exitCommand();
			
		}
		
	}

}
