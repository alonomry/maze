package view;

public class StringArrayDisplay implements DisplayType {

	@Override
	public void display(Object obj) {
		for(int i=0;i<((String[])obj).length;i++)
		{
			System.out.println(((String[])obj)[i]);
		}
			}

	}


