package view;

import algorithms.mazeGenerators.Maze3d;
/**
 * 
 * <h2>MazeDisplay</h1>
 * casting obj to Maze3d and print the maze
 *
 */
public class MazeDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		
		((Maze3d)obj).print();

	}

}
