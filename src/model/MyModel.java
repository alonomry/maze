package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class MyModel implements Model{

	Controller controller;
	HashMap<String, ArrayList<Maze3d>> AllMazes= new HashMap<>();
	HashMap<String, ArrayList<Solution<Position>>> Allsolutions= new HashMap<>();
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void dirCommand(String[] param) {
		try {
			if (param.length == 2){
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


	@Override
	public void generateCommand(String[] param) {
		new Thread(new Runnable() {
			public void run() {
		try {
			if (param.length == 7){
				ArrayList<Maze3d> arraylist = new ArrayList<Maze3d>();
				if(AllMazes.get(param[3])!=null)
				{
				arraylist=AllMazes.get(param[3]);
				}
				Maze3dGenerator mg=new MyMaze3dGenerator(Integer.parseInt(param[4]), Integer.parseInt(param[5]), Integer.parseInt(param[6]));
				Maze3d maze=mg.generate(mg.getDIMENSION(), mg.getWIDTH(), mg.getLENGTH());
				arraylist.add(maze);
					AllMazes.put(param[3],arraylist);
						//Thread.sleep(10000);	//for checks only
					controller.update("maze "+param[3]+" is ready");
			}
					else throw new IOException("Not a Valid Command");
			
		} catch (Exception e) {
			controller.update(e.getMessage());		}
		
	}
		}).start();
	}

	@Override
	public void displayCommand(String[] param) {
		try {
			if(param.length==2){
				if(AllMazes.get(param[1])!=null)
				{
				ArrayList<Maze3d> arraylist = AllMazes.get(param[1]);
				controller.update(arraylist);
				}
				else throw new IOException("Not a Valid Maze Name");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
			}
	}

	@Override
	public void displayCrossSectionCommand(String[] param) {
		try {
			if(param.length==8){
				if(AllMazes.get(param[7])!=null)
				{
				ArrayList<Maze3d> arraylist = AllMazes.get(param[7]);
				if(param[4].equals("x")){
					int[][] maze2dx=arraylist.get(0).getCrossSectionByX(Integer.parseInt(param[5]));
					controller.update(maze2dx);
				}
				if(param[4].equals("y")){
					int[][] maze2dy=arraylist.get(0).getCrossSectionByy(Integer.parseInt(param[5]));
					controller.update(maze2dy);
				}
//				if(param[4].equals("z")){
//					int[][] maze2dz=arraylist.get(0).getCrossSectionByz(Integer.parseInt(param[5]));
//					controller.update(maze2dy);
//				}
				}
				else throw new IOException("Wrong Axis");
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
			}
		
	}

	@Override
	public void saveCommand(String[] param) {
		try {
			if(param.length==4){
				if(AllMazes.get(param[2])!=null)
				{
				ArrayList<Maze3d> arraylist = AllMazes.get(param[2]);
				File f=new File(param[3]);
				f.createNewFile();//create the file
				if(f.exists()) // check if path is valid
				{
				OutputStream co=new MyCompressorOutputStream(new FileOutputStream(param[3])); //calling compressor
				co.write(arraylist.get(arraylist.size()-1).toByteArray());//save the last maze that created
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

	@Override
	public void loadCommand(String[] param) {
			try {
				if (param.length==4)
				{
					File f=new File(param[2]);
					if(f.exists()){
						InputStream in = new MyDecompressorInputStream(new FileInputStream(param[2]));
						long length = f.length(); //get the file size
						// Before converting to an int type, we check
					      // to ensure that file is not larger than Integer.MAX_VALUE.
					      if (length > Integer.MAX_VALUE) {
					        throw new IOException("Could not completely read file " + f.getName() + " as it is too long (" + length + " bytes, max supported " + Integer.MAX_VALUE + ")");
					      }
						
						byte[] b=new byte[(int)length];
						in.read(b); 
						in.close(); //closing stream
						Maze3d fromfile = new Maze3d(b);
						
						ArrayList<Maze3d> arraylist = new ArrayList<Maze3d>();
						if(AllMazes.get(param[3])!=null)//get the array list for specific name
						{
						arraylist=AllMazes.get(param[3]);
						}
						arraylist.add(fromfile);//add the maze to arraylist
						AllMazes.put(param[3], arraylist);
						controller.update("Load completed");
						}
					else throw new IOException("Not a Valid Path");
				}
				else throw new IOException("Not a Valid Command");
			} catch (Exception e) {
				controller.update(e.getMessage());
			}
	}

	@Override
	public void mazeSizeCommand(String[] param) {
		try {
			if(param.length==3){
				if(AllMazes.get(param[2])!=null)//get the array list for specific name
				{
				ArrayList<Maze3d> arraylist = new ArrayList<Maze3d>();
				arraylist=AllMazes.get(param[2]);
				controller.update(ObjectSizeCalculator.getObjectSize(arraylist));
				}
				
				else throw new IOException("maze not found");	
			}
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
			}
		
	}

	@Override
	public void fileSizeCommand(String[] param) {
		try {
			if (param.length==3){
				File f=new File(param[2]);
				if(f.exists()){
					controller.update(f.length());
				}
				else throw new IOException("Not a Valid path");		
			}
			
			else throw new IOException("Not a Valid Command");
		} catch (Exception e) {
			controller.update(e.getMessage());
		}		
	}

	@Override
	public void SolveCommand(String[] param) {
		new Thread(new Runnable() {
			public void run() {
		try {
			if(param.length==3)
			{
				if(AllMazes.get(param[1])!=null)//get the array list for specific name
				{
				ArrayList<Maze3d> arraylist = new ArrayList<Maze3d>();
				arraylist=AllMazes.get(param[1]);
				Solution<Position> sol=new Solution<>();
				ArrayList<Solution<Position>> arrsol = new ArrayList<Solution<Position>>();
				Maze3dAdapter MA=new Maze3dAdapter(arraylist.get(arraylist.size()-1), 10);//cost 10
				switch (param[2]) {
				case "Astar-manhattan":
					Searcher<Position> AstarsearcherManhattan=new Astar<Position>(new ManhattanDistance());
					sol= AstarsearcherManhattan.search(MA);
					if(Allsolutions.get(param[1])!=null)
					{
					arrsol=Allsolutions.get(param[1]);
					}
					arrsol.add(sol);
					Allsolutions.put(param[1], arrsol);
					controller.update("solution for "+param[1]+" is ready");
					break;
				case "Astar-air":
					Searcher<Position> AstarsearcherAir=new Astar<Position>(new AirDistance());
					sol= AstarsearcherAir.search(MA);
					if(Allsolutions.get(param[1])!=null)
					{
					arrsol=Allsolutions.get(param[1]);
					}
					arrsol.add(sol);
					Allsolutions.put(param[1], arrsol);
					controller.update("solution for "+param[1]+" is ready");
					break;
				case "Bfs":
					Searcher<Position> searcher=new Bfs<>();
					sol= searcher.search(MA);
					if(Allsolutions.get(param[1])!=null)
					{
					arrsol=Allsolutions.get(param[1]);
					}
					arrsol.add(sol);
					Allsolutions.put(param[1], arrsol);
					controller.update("solution for "+param[1]+" is ready");
					break;

				default:
					break;
				}
				}
				else throw new IOException("maze not found");
			}
			else throw new IOException("Not a Valid command");
		} catch (Exception e) {
			controller.update(e.getMessage());
		}
			}
		}).start();
	}

	@Override
	public void dislplaySolutionCommand(String[] param) {
		try {
			if(param.length==3){
				ArrayList<Solution<Position>> arrsol = new ArrayList<Solution<Position>>();
				if(Allsolutions.get(param[2])!=null)
				{
				arrsol=Allsolutions.get(param[2]);
				controller.update(arrsol.get(arrsol.size()-1));
				}
				else throw new IOException("maze not found");
			}
			else throw new IOException("Not a Valid command");
		} catch (Exception e) {
			controller.update(e.getMessage());
		}
	}

	@Override
	public void exitCommand() {
		controller.update("Good Bye");
		
	}
	


}
