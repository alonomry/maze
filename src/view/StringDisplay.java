package view;
/**
 * 
 * <h2>StringDisplay</h1>
 * casting obj to String and print him
 *
 */
public class StringDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		
		System.out.println((String)obj);

}
}
