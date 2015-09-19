package view;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;

public class ArrayListDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		
		for(int i=0;i<((ArrayList<Maze3d>)obj).size();i++)
		{
			((ArrayList<Maze3d>)obj).get(i).print();
		}

	}

}
