package boot;


import controller.MyController;
import model.MyModel;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		MyView view = new MyView(System.in,System.out);
		MyModel model = new MyModel();
		MyController controller = new MyController(model,view);
		model.setController(controller);
		view.setCommandLine(controller.getHashCommand());
		view.start();
	}
}
