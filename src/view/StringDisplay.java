package view;

public class StringDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		
		System.out.println((String)obj);

}
}
