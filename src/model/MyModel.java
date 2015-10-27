package model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import algorithms.io.MyCompressorOutputStream;
import algorithms.io.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Solution;
import presenter.Presenter;
import presenter.Properties;


/**
 * @author Alon Tal, Omry Dabush
 * <h1>MyModel</h1>
 * this Class is responsible for all the calculation command and "Updating" the controller with every change<br>
 * the class uses a few Data Member to store results and communicating with the controller
 * @param Presenter controller;
 * @param HashMap<String, Maze3d> AllMazes
 * @param HashMap<String, Solution<Position>> Allsolutions
 * @param ExecutorService threadpool;
 *
 */
public class MyModel extends Observable implements Model{

	Presenter presenter;
	HashMap<String, Maze3d> AllMazes= new HashMap<>();
//	HashMap<Maze3d, Solution<Position>> MazeToSolution;
	ExecutorService threadpool;
	Position OriginalEnter;
	Properties properties;
	
	/**
	 * @author Alon Tal, Omry Dabush
	 * <h2>MyModel Constructor</h2>
	 * This constructor will initialize only the thread pool Data member
	 * @param threadpool
	 */
	
	public MyModel() {
		loadProperties();
		threadpool=Executors.newFixedThreadPool(Integer.parseInt(this.properties.getMaxTreads()));
	}
	
	public void loadProperties(){
		this.properties=new Properties("lib/properties.xml");
	}
	
	
	/**
	 * <h2>setPresenter</h2>
	 * This method will initialize the controller<br>
	 * @param presenter
	 */
	
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	/**
	 * <h2>dirCommand</h2>
	 * This method will send to the Controller Class a list of files and folders</br>
	 * to be presented in the path the was specified 
	 * @param File f
	 * @param Presenter controller
	 */
	@Override
	public void dirCommand(String[] param) {
		try {
			if(param.length==1){
				File f = new File(".");
				setChanged();
				notifyObservers(f.list());
			}
			else if (param.length == 2){
				File f = new File(param[1]);
					if(f.exists()){
						setChanged();
						notifyObservers(f.list());
					}
					else throw new IOException("Not a Valid Path");
			}
			else throw new IOException("Not a Valid command");
		} catch (Exception e) {
			setChanged();
			notifyObservers(e.getMessage());
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
		Future<Maze3d> future =	threadpool.submit(new Callable<Maze3d>() {
			@Override
			public Maze3d call() throws Exception {
				try {
					if (param.length == 7){
						Maze3dGenerator mg = null;
						if(AllMazes.get(param[3])!=null)
						{
							throw new IOException("maze with same name already exist");
						}
						else if(properties.getGenerateAlgorithm().contains("mymaze")){
							mg = new MyMaze3dGenerator(Integer.parseInt(param[4]), Integer.parseInt(param[5]), Integer.parseInt(param[6]));
						}
						else if(properties.getGenerateAlgorithm().contains("simplemaze")){
							mg = new SimpleMaze3dGenerator(Integer.parseInt(param[4]), Integer.parseInt(param[5]), Integer.parseInt(param[6]));
						}
							Maze3d maze = mg.generate(mg.getDIMENSION(), mg.getWIDTH(), mg.getLENGTH());
							AllMazes.put(param[3],maze);
							//	Thread.sleep(20000);	//for debugging only, 20 sec sleep
							return maze;
					}
							else throw new IOException("Not a Valid Command");
					
				} catch (Exception e) {
					setChanged();
					notifyObservers(e.getMessage());
					return null;
					}

			}
		});
		try {
			if (future.get()!=null){
				AllMazes.put(param[3],future.get());
				setChanged();
				notifyObservers("maze "+param[3]+" is ready");				
			}

		} catch (InterruptedException e) {
				e.printStackTrace();
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
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
				setChanged();
				notifyObservers(maze);
				}
				else throw new IOException("Not a Valid Maze Name");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			setChanged();
			notifyObservers(e.getMessage());
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
						//presenter.update(maze2dx);------MVC------
						setChanged();
						notifyObservers(maze2dx);
					}
					else if(param[4].equals("y")){
						int[][] maze2dy=maze.getCrossSectionByY(Integer.parseInt(param[5]));
						//presenter.update(maze2dy);------MVC------
						setChanged();
						notifyObservers(maze2dy);
					}
					else if(param[4].equals("z")){
						int[][] maze2dz=maze.getCrossSectionByZ(Integer.parseInt(param[5]));
						//presenter.update(maze2dz);------MVC------
						setChanged();
						notifyObservers(maze2dz);
					}
					else throw new IOException("Wrong Axis");
				}
				else throw new IOException("Not a Valid Maze Name");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			//presenter.update(e.getMessage());------MVC------
			setChanged();
			notifyObservers(e.getMessage());
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
					//presenter.update("Maze "+param[2]+" Saved to "+param[3]);------MVC------
					setChanged();
					notifyObservers("Maze "+param[2]+" Saved to "+param[3]);
				}
				}
				else throw new IOException("Not a Valid path");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			//presenter.update(e.getMessage());------MVC------
			setChanged();
			notifyObservers(e.getMessage());
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
						//presenter.update("Load completed");------MVC------
						setChanged();
						notifyObservers("Load completed");
						}
					else throw new IOException("Not a Valid Path");
				}
				else throw new IOException("Not a Valid Command");
			} catch (Exception e) {
				//presenter.update(e.getMessage());------MVC------
				setChanged();
				notifyObservers(e.getMessage());
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
				//presenter.update(""+maze.GetMazeSizeInBytes()+" bytes");------MVC------
				setChanged();
				notifyObservers(""+maze.GetMazeSizeInBytes()+" bytes");
				}
				
				else throw new IOException("maze not found");	
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			//presenter.update(e.getMessage());------MVC------
			setChanged();
			notifyObservers(e.getMessage());
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
					//presenter.update(""+f.length()+" bytes");------MVC------
					setChanged();
					notifyObservers(""+f.length()+" bytes");
				}
				else throw new IOException("Not a Valid path");		
			}
			
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			//presenter.update(e.getMessage());------MVC------
			setChanged();
			notifyObservers(e.getMessage());
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
	public void SolveCommand(String[] param){
		try{
			if (param.length==3||param.length==6)
				if (AllMazes.get(param[1])!=null){
					try{
						//Creates a new Socket to server with the ip and port from the XML file
						Socket myServer = new Socket(properties.getServer_ip(),Integer.parseInt(properties.getSerever_port()));
						ObjectOutputStream outToServer = new ObjectOutputStream(myServer.getOutputStream());
						
						ArrayList<Object> dataToServer =new  ArrayList<>(); 
						dataToServer.add("solve");
						dataToServer.add(properties.getSolvingAlgorithm());
						if (param.length == 3){
							dataToServer.add(AllMazes.get(param[1]));
						}
						else{
							int y = Integer.parseInt(param[3]);
							int z = Integer.parseInt(param[4]);
							int x = Integer.parseInt(param[5]);
							Position newEnter = new Position(y,z,x);
							dataToServer.add(AllMazes.get(param[1]));
							dataToServer.add(newEnter);
							
						}
						
						outToServer.writeObject(dataToServer);
						outToServer.flush();
						
						
						ObjectInputStream inFromServer = new ObjectInputStream(myServer.getInputStream());
						@SuppressWarnings("unchecked")
						Solution<Position> dataFromServer = (Solution<Position>)inFromServer.readObject();
						
						if (dataFromServer == null){
							setChanged();
							notifyObservers("Wrong Solution");
						}
						else if (this.properties.get_interface().equals("CLI")){
								setChanged();	
								notifyObservers("Solution for "+param[1]+" is ready");
						}
							else{
								setChanged();	
								notifyObservers(dataFromServer);
							}
						if (dataFromServer != null){
							outToServer.close();
							inFromServer.close();
							myServer.close();
						}
								
							
					}catch (Exception e){
						setChanged();
						notifyObservers("Can't Connect To Server...");
						
					}
				}
				else{
					throw new IOException("Maze Not Found");
					
				}
			else throw new IOException("Not A Valid Command");
		} catch (IOException e1) {
			setChanged();
			notifyObservers(e1.getMessage());
		}
	}
	

/**
 * <h2>dislplaySolutionCommand</h2>
 * This method retrieves the result of a specific maze and send it to the controller
 * @param Solution<Position> sol
 * 
 */
	
	@Override
	public void dislplaySolutionCommand(String[] param) {
		
		try{
			if (param.length==3){
				if (AllMazes.get(param[2])!=null){
					try{
						Socket myServer = new Socket(properties.getServer_ip(),Integer.parseInt(properties.getSerever_port()));	
						ObjectOutputStream outToServer = new ObjectOutputStream(myServer.getOutputStream());
						
						ArrayList<Object> dataToServer =new  ArrayList<>(); 
						dataToServer.add("get solution");
						dataToServer.add(AllMazes.get(param[2]));
						
						outToServer.writeObject(dataToServer);
						outToServer.flush();
						
						ObjectInputStream inFromServer = new ObjectInputStream(myServer.getInputStream());
						@SuppressWarnings("unchecked")
						Solution<Position> dataFromServer = (Solution<Position>)inFromServer.readObject();
						
						if (dataFromServer == null){
							setChanged();
							notifyObservers("dont have solution for "+param[2]);
						}
						else{
							setChanged();	
							notifyObservers(dataFromServer);
							outToServer.close();
							inFromServer.close();
							myServer.close();
						}
					}catch  (Exception e){
						setChanged();
						notifyObservers("Can't Connect To Server...");
					}
				}
				else throw new IOException("The Maze "+param[2]+ " Does Not Exist");
			}
			else throw new IOException("Wrong Command");
		}catch (IOException e){
				setChanged();
				notifyObservers(e.getMessage());	
		}
	}
/**
 * <h2>exitCommand</h2>
 * This method performs a controlled exit from all opened Threads with the use of Threadpool.Shutdown command
 * Witch allows all open thread to finish and only then exit from the program.
 * @throws IOException 
 * 
 */
	@Override
	public void exitCommand() {
		setChanged();
		notifyObservers("Shutting Down");

		
		threadpool.shutdown();
		//wait 10 seconds over and over again until all running jobs have finished
		@SuppressWarnings("unused")
		boolean allTasksCompleted=false;
		try {
			while(!(allTasksCompleted=threadpool.awaitTermination(10, TimeUnit.SECONDS))){
				//presenter.update("waiting for all threads to finish");------MVC------
				setChanged();
				notifyObservers("waiting for all threads to finish");
			}
			
		} catch (InterruptedException e) {
			//presenter.update(e.getMessage());------MVC------
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		setChanged();
		notifyObservers("all tasks have been completed, Good Bye");
		
	}


public Properties getProperties() {
	return properties;
}

/**
 * 
 *<h2>setProperties</h2>
 *gets - properties file path <br>
 *reading from Xml file and
 *
 */
public void setProperties(String path) {
	this.properties = new Properties(path);
	setChanged();
	notifyObservers(this.properties);
}

	

}
