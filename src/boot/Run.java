package boot;

import model.MyModel;
import presenter.MyPresenter;
import presenter.Properties;
import view.Gui;
import view.MyView;
/**
 * 
 * @author alon tal and omry dabush
 * <h2> Run</h2>
 * project main class - getting properties and than decide open Gui or Cli
 *
 */
public class Run {

	public static void main(String[] args) {
		
		MyModel model = new MyModel();
		Properties properties=model.getProperties();
		if(properties.get_interface().contains("GUI")){
			Gui gui=new Gui("maze example", 700, 600);
			MyPresenter presenter = new MyPresenter(model,gui);
			model.setPresenter(presenter);
			gui.setCommandLine(presenter.getHashCommand());
			gui.setSolvingAlgorithm(properties.getSolvingAlgorithm());
			model.addObserver(presenter);
			gui.addObserver(presenter);
			gui.start();
		}
		else if(properties.get_interface().contains("CLI")){
			MyView view = new MyView(System.in,System.out);
			MyPresenter presenter = new MyPresenter(model,view);
			model.setPresenter(presenter);
			view.setCommandLine(presenter.getHashCommand());
			model.addObserver(presenter);
			view.addObserver(presenter);
			view.start();
		}
	}
}
