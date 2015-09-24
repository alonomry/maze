package controller;

/**
 * 
 * <h2> Command-Interface</h2>
 * each command will extand this interface need to implement doCommand method
 *
 */
public interface Command {

	public void doCommand(String[] param) ;
}
