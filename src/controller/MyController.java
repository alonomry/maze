package controller;

import java.util.HashMap;
import model.Model;
import view.*;

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
		String temp=obj.getClass().getCanonicalName();
		switch (temp) {
		case "java.lang.String":
			view.display(obj,new StringDisplay());
			break;
		case "java.lang.String[]":
			view.display(obj,new StringArrayDisplay());
			break;
		case "java.util.ArrayList":
			view.display(obj,new ArrayListDisplay());
			break;
		case "int[][]":
			view.display(obj,new CrossDisplay());			
			break;
		case "java.lang.Long":
			view.display(obj,new LongDisplay());			
			break;
		case "algorithms.search.Solution":
			view.display(obj,new SolDisplay());			
			break;
			
			
			
			

		default:
			break;
		}
		
		
	}
	
	
	
	// ----------------------------------------
	// ------------commands classes------------
	// ----------------------------------------
	
	
	public class dirCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.dirCommand(param);			
		}
		
	}
	
	public class generateCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.generateCommand(param);
			
		}
		
	}
	
	public class displayCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.displayCommand(param);
			
		}
		
	}
	
	public class displayCrossSectionCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.displayCrossSectionCommand(param);			
		}
		
	}
	
	public class saveCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.saveCommand(param);
		}
		
	}
	
	public class loadCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.loadCommand(param);
		}
		
	}
	
	public class mazeSizeCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.mazeSizeCommand(param);
			
		}
		
	}
	
	public class fileSizeCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.fileSizeCommand(param);
		}
		
	}
	
	public class solveCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.SolveCommand(param);
			
		}
		
	}
	
	public class displaySolutionCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.dislplaySolutionCommand(param);
			
		}
		
	}
	
	public class exitCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.exitCommand();
			
		}
		
	}

}
