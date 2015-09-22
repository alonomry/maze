package view;

import java.util.Arrays;
/**
 * 
 *<h2>CrossDisplay</h2>
 *gets - int[][] <br>
 *printing 2d array, used for display cross section
 *
 */
public class CrossDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		for (int[] row : (int[][])obj)
		{
		    System.out.println(Arrays.toString(row));
		}
		System.out.println();

	}

}
