package view;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * 
 * <h2>SolDisplay</h1>
 * casting obj to Solution<position> and print the solution
 *
 */
public class SolDisplay implements DisplayType {

	@SuppressWarnings("unchecked")
	@Override
	public void display(Object obj) {
		
		((Solution<Position>)obj).print();
	}

}
