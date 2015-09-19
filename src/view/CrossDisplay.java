package view;

import java.util.Arrays;

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
