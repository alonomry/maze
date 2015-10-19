package boot;


import model.MyModel;
import presenter.MyPresenter;
import view.Gui;
import view.MazeWindow;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		
		/*MyView view = new MyView(System.in,System.out);
		MyModel model = new MyModel();
		MyPresenter presenter = new MyPresenter(model,view);
		model.setPresenter(presenter);
		view.setCommandLine(presenter.getHashCommand());
		model.addObserver(presenter);
		view.addObserver(presenter);
		view.start();*/
	
		
		
		
		Gui gui=new Gui("maze example", 700, 600);
		MyModel model = new MyModel();
		MyPresenter presenter = new MyPresenter(model,gui);
		model.setPresenter(presenter);
		gui.setCommandLine(presenter.getHashCommand());
		model.addObserver(presenter);
		gui.addObserver(presenter);
		gui.start();
	}
}
