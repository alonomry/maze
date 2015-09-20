package view;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class SolDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		
		((Solution<Position>)obj).print();
	}

}
