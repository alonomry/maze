package view;

public class LongDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		System.out.println((long)obj);
	}

}
