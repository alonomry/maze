package view;

import algorithms.mazeGenerators.Maze3d;

public class MazeDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		
		((Maze3d)obj).print();

	}

}
