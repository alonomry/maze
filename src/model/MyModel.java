package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import algorithms.demo.Maze3dAdapter;
import algorithms.io.MyCompressorOutputStream;
import algorithms.io.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AirDistance;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.ManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import controller.Controller;


/**
 * @author Alon Tal, Omry Dabush
 * <h1>MyModel</h1>
 * this Class is responsible for all the calculation command and "Updating" the controller with every change<br>
 * the class uses a few Data Member to store results and communicating with the controller
 * @param Controller controller;
 * @param HashMap<String, Maze3d> AllMazes
 * @param HashMap<String, Solution<Position>> Allsolutions
 * @param ExecutorService threadpool;
 *
 */
public class MyModel implements Model{

	Controller controller;
	HashMap<String, Maze3d> AllMazes= new HashMap<>();
	HashMap<String, Solution<Position>> Allsolutions= new HashMap<>();
	ExecutorService threadpool;
	
	/**
	 * @author Alon Tal, Omry Dabush
	 * <h2>MyModel Constructor</h2>
	 * This constructor will initialize only the thread pool Data member
	 * @param threadpool
	 */
	public MyModel() {
		threadpool=Executors.newFixedThreadPool(10); //10 threads can run each time
	}
	
	/**
	 * <h2>setController</h2>
	 * This method will initialize the controller<br>
	 * @param controller
	 */
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * <h2>dirCommand</h2>
	 * This method will send to the Controller Class a list of files and folders</br>
	 * to be presented in the path the was specified 
	 * @param File f
	 * @param Controller controller
	 */
	@Override
	public void dirCommand(String[] param) {
		try {
			if(param.length==1){
				File f = new File(".");
				controller.update(f.list());
			}
			else if (param.length == 2){
				File f = new File(param[1]);
					if(f.exists())
						controller.update(f.list());	
					else throw new IOException("Not a Valid Path");
			}
			else throw new IOException("Not a Valid command");
		} catch (Exception e) {
			controller.update(e.getMessage());
		}			
	}
/**
 * <h2>generateCommand</h2>
 * This method will activate the MyMaze3dGenerator generate method,<br>
 * that will generate a new maze3d.
 * @param Thread generate
 */


	@Override
	public void generateCommand(String[] param) {
		Thread Generate=new Thread(new Runnable() {
			public void run() {
				threadpool.execute(new Runnable() {
					@Override
					public void run() {
		try {
			if (param.length == 7){
				if(AllMazes.get(param[3])!=null)
				{
					throw new IOException("maze with same name already exist");
				}
				Maze3dGenerator mg=new MyMaze3dGenerator(Integer.parseInt(param[4]), Integer.parseInt(param[5]), Integer.parseInt(param[6]));
				Maze3d maze=mg.generate(mg.getDIMENSION(), mg.getWIDTH(), mg.getLENGTH());
					AllMazes.put(param[3],maze);
					//	Thread.sleep(20000);	//for debugging only, 20 sec sleep
					controller.update("maze "+param[3]+" is ready");
			}
					else throw new IOException("Not a Valid Command");
			
		} catch (Exception e) {
			controller.update(e.getMessage());		}
					}
				});
		
	}
		});
		Generate.start();

	}

	/**
	 * <h2>displayCommand</h2>
	 * This method receives a maze name as a String<br>
	 * and see if the maze exist in a Hashmap of mazes<br>
	 * if the maze does exist it will send the maze to the display for displaying
	 * @param String mazeName
	 */
	@Override
	public void displayCommand(String[] param) {
		try {
			if(param.length==2){
				if(AllMazes.get(param[1])!=null)
				{
				Maze3d maze = AllMazes.get(param[1]);
				controller.update(maze);
				}
				else throw new IOException("Not a Valid Maze Name");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
			}
	}

	/**
	 * <h2>displayCrossSectionCommand</h2>
	 * This method gets from the user witch cross section does he want to present<br>
	 * by X,Y,Z calculate the cross section and send the result to the controller as int[][]
	 * @param Maze3d maze
	 * @param String X/Y/Z
	 * @param String index
	 * 
	 */
	@Override
	public void displayCrossSectionCommand(String[] param) {
		try {
			if(param.length==8){
				if(AllMazes.get(param[7])!=null)
				{
					Maze3d maze = AllMazes.get(param[7]);
					if(param[4].equals("x")){
						int[][] maze2dx=maze.getCrossSectionByX(Integer.parseInt(param[5]));
						controller.update(maze2dx);
					}
					else if(param[4].equals("y")){
						int[][] maze2dy=maze.getCrossSectionByY(Integer.parseInt(param[5]));
						controller.update(maze2dy);
					}
					else if(param[4].equals("z")){
						int[][] maze2dz=maze.getCrossSectionByZ(Integer.parseInt(param[5]));
						controller.update(maze2dz);
					}
					else throw new IOException("Wrong Axis");
				}
				else throw new IOException("Not a Valid Maze Name");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
			}
		
	}

	/**
	 * <h2>saveCommand</h2>
	 * This method creates a new File to see is the path provided is a valid one<br>
	 * if so the is being compressed and to the file name provided by the user
	 * @param File file
	 * @param Maze3d maze
	 * @param Outputstream co
	 * 
	 */
	@Override
	public void saveCommand(String[] param) {
		try {
			if(param.length==4){
				if(AllMazes.get(param[2])!=null)
				{
				Maze3d maze = AllMazes.get(param[2]);
				File f=new File(param[3]);
				f.createNewFile();//create the file
				if(f.exists()) // check if path is valid
				{
					OutputStream co=new MyCompressorOutputStream(new FileOutputStream(param[3])); //calling compressor
					co.write(maze.toByteArray());//save the last maze that created
					co.close();
					controller.update("Maze "+param[2]+" Saved to "+param[3]);
				}
				}
				else throw new IOException("Not a Valid path");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
			}		
	}

	
	/**
	 * <h2>loadCommand</h2>
	 * This method creates a new File to see is the path provided is a valid one<br>
	 * if so it get the compressed maze as byte[] and then performs a Decompression method and<br>
	 * creates a new maze in the name provided by the user 
	 * 
	 * @param File f
	 * @param Maze3d maze
	 */
	@Override
	public void loadCommand(String[] param) {
			try {
				if (param.length==4)
				{
					File f=new File(param[2]);
					if(f.exists()){
						MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(param[2]));
						byte[] b = in.decompress();
						Maze3d fromfile = new Maze3d(b);
						in.close();
						if(AllMazes.get(param[3])!=null)//get the maze for specific name
						{
							throw new IOException("maze with same name already exist");
						}
						AllMazes.put(param[3], fromfile);
						controller.update("Load completed");
						}
					else throw new IOException("Not a Valid Path");
				}
				else throw new IOException("Not a Valid Command");
			} catch (Exception e) {
				controller.update(e.getMessage());
			}
	}

	
	/**
	 * <h2>mazeSizeCommand</h2>
	 * the method calculation the maze size in Bytes and uses a inner method from Maze3d Class
	 * @param Maze3d maze
	 */
	@Override
	public void mazeSizeCommand(String[] param) {
		try {
			if(param.length==3){
				if(AllMazes.get(param[2])!=null)//get the array list for specific name
				{
				Maze3d maze=AllMazes.get(param[2]);
				controller.update(""+maze.GetMazeSizeInBytes()+" bytes");
				}
				
				else throw new IOException("maze not found");	
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
			}
		
	}

	/**
	 * <h2>fileSizeCommand</h2>
	 * This method calculates the size of a compressed maze in a File in Bytes
	 * and uses the built in Method to send the file size to the controller
	 * @param File file
	 * 
	 */
	@Override
	public void fileSizeCommand(String[] param) {
		try {
			if (param.length==3){
				File f=new File(param[2]);
				if(f.exists()){
					controller.update(""+f.length()+" bytes");
				}
				else throw new IOException("Not a Valid path");		
			}
			
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
		}		
	}
	/**
	 * <h2>SolveCommand</h2>
	 * This method solve the maze in one of three algorithms and send a suitable message to the controller<br>
	 * the user need to provide the  algorithm and the method will use the<br>
	 * BFS,Astar-air,Astar-manhattan algorithms<br>
	 * Ones finish the calculation it stores the result in a Hashmap of Solutions (Allsolution Data member)
	 * @param Maze3d maze
	 * @param Solution<Postion> sol
	 * @param Maze3dAdapter MA
	 * @param Searcher<Position> AstarsearcherManhattan
	 * @param Searcher<Position> AstarsearcherAir
	 * @param Searcher<Position> searcher
	 */
	@Override
	public void SolveCommand(String[] param) {
		Thread Solve=new Thread(new Runnable() {
			public void run() {
				threadpool.execute(new Runnable() {
					@Override
					public void run() {
		try {
			if(param.length==3)
			{
				if(AllMazes.get(param[1])!=null)//get the array list for specific name
				{
				Maze3d maze=AllMazes.get(param[1]);
				Solution<Position> sol=new Solution<>();
				Maze3dAdapter MA=new Maze3dAdapter(maze, 10);//cost 10
				switch (param[2]) {
				case "Astar-manhattan":
					Searcher<Position> AstarsearcherManhattan=new Astar<Position>(new ManhattanDistance());
					sol= AstarsearcherManhattan.search(MA);
					Allsolutions.put(param[1], sol);
					controller.update("solution for "+param[1]+" is ready");
					break;
				case "Astar-air":
					Searcher<Position> AstarsearcherAir=new Astar<Position>(new AirDistance());
					sol= AstarsearcherAir.search(MA);
					Allsolutions.put(param[1], sol);
					controller.update("solution for "+param[1]+" is ready");
					break;
				case "Bfs":
					Searcher<Position> searcher=new Bfs<>();
					sol= searcher.search(MA);
					Allsolutions.put(param[1], sol);
					controller.update("solution for "+param[1]+" is ready");
					break;

				default:
					throw new IOException("not a valid algorithm");
					
				}
				}
				else throw new IOException("maze not found");
			}
			else throw new IOException("Not a Valid command");
		} catch (Exception e) {
			controller.update(e.getMessage());
		}
					}
				});
			}
		});
				Solve.start();
	}

/**
 * <h2>dislplaySolutionCommand</h2>
 * This method retrieves the result of a specific maze and send it to the controller
 * @param Solution<Position> sol
 * 
 */
	
	@Override
	public void dislplaySolutionCommand(String[] param) {
		try {
			if(param.length==3){
				Solution<Position> sol=new Solution<>();
				if(Allsolutions.get(param[2])!=null)
				{
				sol=Allsolutions.get(param[2]);
				controller.update(sol);
				}
				else throw new IOException("dont have solution for "+param[2]);
			}
			else throw new IOException("Not a Valid command");
		} catch (Exception e) {
			controller.update(e.getMessage());
		}
	}
/**
 * <h2>exitCommand</h2>
 * This method performs a controlled exit from all opened Threads with the use of Threadpool.Shutdown command
 * Witch allows all open thread to finish and only then exit from the program.
 * 
 */
	@Override
	public void exitCommand() {
		controller.update("shutting down");
		threadpool.shutdown();
		//wait 10 seconds over and over again until all running jobs have finished
		@SuppressWarnings("unused")
		boolean allTasksCompleted=false;
		try {
			while(!(allTasksCompleted=threadpool.awaitTermination(10, TimeUnit.SECONDS))){
				controller.update("waiting for all threads to finish");
			}
			
		} catch (InterruptedException e) {
			controller.update(e.getMessage());
		}
		
		
		controller.update("all tasks have been completed, Good Bye");
		
	}
	


}
