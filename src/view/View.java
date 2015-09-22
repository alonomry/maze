package view;
/**
 * 
 * <h2>View-Interface</h1>
 * each view option will extand this interface and implements:<br>
 * 1)display method<br>
 * 2)start method
 *
 */
public interface View {
	public void display (Object obj,DisplayType d);
	public void start();
	
}
