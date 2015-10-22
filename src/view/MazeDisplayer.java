package view;


import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	// just as a stub...
	int[][] mazeData={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,0,1,1,1,1,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,0,1},
			{1,0,1,1,1,0,0,1,1,1,1,0,0,0,1},
			{1,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
			{1,0,1,1,1,0,0,0,0,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,1,1,0,0,1,1,1,1,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,0,1},
			{1,0,0,0,1,0,0,1,0,0,1,0,0,0,1},
			{1,0,0,0,1,0,0,1,1,1,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};

	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

//	public void setMazeData(int[][] mazeData){
//		this.mazeData=mazeData;
//		redraw();
//	}
	
	public abstract void setCurrentMaze(Maze3d m);
	
	public abstract Maze3d getCurrentMaze();
	
	public abstract void setCharacterPosition(int dim,int wid,int len);
	
	public abstract Position getCharacter();
	
	public abstract void moveUp();

	public abstract void moveDown();
	
	public abstract void moveForward();
	
	public abstract void moveBackward();

	public abstract void moveLeft();

	public  abstract void moveRight();

}