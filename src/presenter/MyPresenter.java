package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import CliDisplays.CrossDisplay;
import CliDisplays.LongDisplay;
import CliDisplays.MazeDisplay;
import CliDisplays.SolDisplay;
import CliDisplays.StringArrayDisplay;
import CliDisplays.StringDisplay;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Model;
import model.MyModel;
import view.*;

// TODO: Auto-generated Javadoc
/**
 * The Class MyController.
 */
public class MyPresenter implements Presenter, Observer{

	/** The model. */
	Model model;
	
	/** The view. */
	View view;
	
	/**  hashmap between string to command. */
	HashMap<String, Command> hashCommand;
	HashMap<Maze3d,Solution<Position>> caching;
	
	/**
	 * Instantiates a new MyController
	 *
	 * @param m the model
	 * @param v the View
	 */
	public MyPresenter(Model m,View v) {
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
	@Override
	public void update(Observable o, Object arg) {
		String type;
		if (o == model){
			type = arg.getClass().getCanonicalName();
			switch (type) {
			case "java.lang.String":
				view.display(arg,new StringDisplay());
				break;
			case "java.lang.String[]":
				view.display(arg,new StringArrayDisplay());
				break;
			case "algorithms.mazeGenerators.Maze3d":
				view.display(arg,new MazeDisplay());
				break;
			case "int[][]":
				view.display(arg,new CrossDisplay());			
				break;
			case "java.lang.Long":
				view.display(arg,new LongDisplay());			
				break;
			case "algorithms.search.Solution":
				view.display(arg,new SolDisplay());			
				break;
			case "presenter.Properties":
				view.display(arg,new StringDisplay());			
				break;	
				
			}
		}
		if (o == view){
			type = arg.getClass().getInterfaces()[0].getSimpleName();
			switch (type) {
			case "Command":
				((Command)arg).doCommand();
				break;
			case "Serializable":
				if(arg.getClass().getCanonicalName()=="java.lang.String")
				model.setProperties((String)arg);
				break;
				
			default:
				break;
			}

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

		String[] param;
		
		public void setStringCommand(String[] s) {
			param = s;
		}
		@Override
		public void doCommand() {
			model.dirCommand(param);			
		}
		
	}
	
	/**
	 * The Class generateCommand.
	 * generate new maze
	 */
	public class generateCommand implements Command{
		
		String[] param;
		
		@Override
		public void doCommand() {
			model.generateCommand(param);
			
		}
		
		public void setStringCommand(String[] s) {
			param = s;
		}
		
	}
	
	/**
	 * The Class displayCommand.
	 * gets String- maze name
	 * display the maze 
	 */
	public class displayCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.displayCommand(param);
			
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}
	
	/**
	 * The Class displayCrossSectionCommand.
	 * display the cross section by given axis and maze name
	 */
	public class displayCrossSectionCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.displayCrossSectionCommand(param);			
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}
	
	/**
	 * The Class saveCommand.
	 * save the maze to file
	 */
	public class saveCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.saveCommand(param);
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}
	
	/**
	 * The Class loadCommand.
	 * load maze from file
	 */
	public class loadCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.loadCommand(param);
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}
	
	/**
	 * The Class mazeSizeCommand.
	 * size of the maze object in running time
	 */
	public class mazeSizeCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.mazeSizeCommand(param);
			
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}
	
	/**
	 * The Class fileSizeCommand.
	 * size of maze file
	 */
	public class fileSizeCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.fileSizeCommand(param);
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}
	
	/**
	 * The Class solveCommand.
	 * solving maze by given algorithm: Bfs, Astar-air, Astar-manhattan
	 */
	public class solveCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.SolveCommand(param);
			
		}

		@Override
		public void setStringCommand(String[] s) {
			param =s;
			
		}
		
	}
	
	/**
	 * The Class displaySolutionCommand.
	 * display the solution for spesific maze
	 */
	public class displaySolutionCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.dislplaySolutionCommand(param);
			
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}
	
	/**
	 * The Class exitCommand.
	 * stops all running threads and exit
	 */
	public class exitCommand implements Command{

		String[] param;
		
		@Override
		public void doCommand() {
			model.exitCommand();
			
		}

		@Override
		public void setStringCommand(String[] s) {
			param = s;
			
		}
		
	}



}
