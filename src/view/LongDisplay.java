package view;
/**
 * 
 * <h2>LongDisplay</h1>
 * casting obj to long and print him
 *
 */
public class LongDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		System.out.println((long)obj);
	}

}
