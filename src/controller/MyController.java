package controller;

import java.util.HashMap;
import model.Model;
import view.*;

// TODO: Auto-generated Javadoc
/**
 * The Class MyController.
 */
public class MyController implements Controller{

	/** The model. */
	Model model;
	
	/** The view. */
	View view;
	
	/**  hashmap between string to command. */
	HashMap<String, Command> hashCommand;
	
	
	/**
	 * Instantiates a new MyController.
	 *
	 * @param m the model
	 * @param v the View
	 */
	public MyController(Model m,View v) {
		this.model = m;
		this.view = v;
		this.hashCommand = new HashMap<>();
		InitCommands();
	}
	
	/**
	 * Inits the hashmap commands.
	 */
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
	
	

	/**
	 * set the model
	 */
	@Override
	public void setModel(Model m) {
		this.model = m;
		
	}

	/**
	 * set the view
	 */
	@Override
	public void setView(View v) {
		this.view = v;
		
	}

	/**
	 * Gets the hash command.
	 *
	 * @return the hash command
	 */
	public HashMap<String, Command> getHashCommand() {
		return hashCommand;
	}

	/**
	 * gets string array from model and bring him to View
	 * 
	 */
	@Override
	public void display(String[] s) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * gets object from model, checking for type and inject to view the specific display for the object
	 */
	public void update(Object obj) {
		String temp=obj.getClass().getCanonicalName();
		switch (temp) {
		case "java.lang.String":
			view.display(obj,new StringDisplay());
			break;
		case "java.lang.String[]":
			view.display(obj,new StringArrayDisplay());
			break;
		case "algorithms.mazeGenerators.Maze3d":
			view.display(obj,new MazeDisplay());
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
	
	
	/**
	 * The Class dirCommand.
	 * display the current dir or specific dir
	 */
	public class dirCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.dirCommand(param);			
		}
		
	}
	
	/**
	 * The Class generateCommand.
	 * generate new maze
	 */
	public class generateCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.generateCommand(param);
			
		}
		
	}
	
	/**
	 * The Class displayCommand.
	 * gets String- maze name
	 * display the maze 
	 */
	public class displayCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.displayCommand(param);
			
		}
		
	}
	
	/**
	 * The Class displayCrossSectionCommand.
	 * display the cross section by given axis and maze name
	 */
	public class displayCrossSectionCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.displayCrossSectionCommand(param);			
		}
		
	}
	
	/**
	 * The Class saveCommand.
	 * save the maze to file
	 */
	public class saveCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.saveCommand(param);
		}
		
	}
	
	/**
	 * The Class loadCommand.
	 * load maze from file
	 */
	public class loadCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.loadCommand(param);
		}
		
	}
	
	/**
	 * The Class mazeSizeCommand.
	 * size of the maze object in running time
	 */
	public class mazeSizeCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.mazeSizeCommand(param);
			
		}
		
	}
	
	/**
	 * The Class fileSizeCommand.
	 * size of maze file
	 */
	public class fileSizeCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.fileSizeCommand(param);
		}
		
	}
	
	/**
	 * The Class solveCommand.
	 * solving maze by given algorithm: Bfs, Astar-air, Astar-manhattan
	 */
	public class solveCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.SolveCommand(param);
			
		}
		
	}
	
	/**
	 * The Class displaySolutionCommand.
	 * display the solution for spesific maze
	 */
	public class displaySolutionCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.dislplaySolutionCommand(param);
			
		}
		
	}
	
	/**
	 * The Class exitCommand.
	 * stops all running threads and exit
	 */
	public class exitCommand implements Command{

		@Override
		public void doCommand(String[] param) {
			model.exitCommand();
			
		}
		
	}

}
