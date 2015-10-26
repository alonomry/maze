package view;


import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * 
 * @author alon tal and omry dabush
 * <H2>MazeDisplayer</H2>
 * abstract class, kind of canvas<br>
 *	defines abstract methods for managing maze canvas
 */
public abstract class MazeDisplayer extends Canvas{
	
	// default maze on start, shows 29.10 , the date of test!
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

	
	public MazeDisplayer(Composite parent, int style) {//c'tor
		super(parent, style);
	}
	
	
	/**
	 * sets the current maze
	 */
	public abstract void setCurrentMaze(Maze3d m);
	/**
	 * get the current maze
	 */
	public abstract Maze3d getCurrentMaze();
	/**
	 * sets the character position parameters
	 */
	public abstract void setCharacterPosition(int dim,int wid,int len);
	
	public abstract Position getCharacter();
	/**
	 * moving the character to upper dimension
	 */
	public abstract boolean moveUp();
	/**
	 * moving the character to lower dimension
	 */
	public abstract boolean moveDown();
	/**
	 * moving the character up
	 */
	public abstract boolean moveForward();
	/**
	 * moving the character down
	 */
	public abstract boolean moveBackward();
	/**
	 * moving the character left
	 */
	public abstract boolean moveLeft();
	/**
	 * moving the character right
	 */
	public abstract boolean moveRight();

	/**
	 * taking the character step by step to the exit by timer task
	 */
	public abstract void WalkToExit(Solution<Position> obj);
	/**
	 * moving the one step to help the client get to exit 
	 */
	public abstract void WalkByHint(Solution<Position> sol);

}