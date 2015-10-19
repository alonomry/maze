
package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import CliDisplays.DisplayType;
import presenter.Command;

/**
 * <h2>MyView</h2>
 * implemnts View <br>
 * 
 */

public class MyView extends Observable implements View, Observer {
	
	/** The command line. */
	CLI commandLine;
	
	/** The input from client. */
	InputStream inputFromClient;
	
	/** The output from client. */
	OutputStream outputFromClient;
	
	
	/**
	 * Instantiates a new my view.
	 *
	 * @param inputFromClient the input from client
	 * @param outputFromClient the output from client
	 */
	public MyView(InputStream inputFromClient, OutputStream outputFromClient) {
		this.inputFromClient = inputFromClient;
		this.outputFromClient = outputFromClient;
	}
	
	/**
	 * Gets the command line.
	 *
	 * @return the command line
	 */
	public CLI getCommandLine() {
		return commandLine;
	}

	/**
	 * Sets the command line.
	 *
	 * @param newHashCommand the new hash command
	 */
	public void setCommandLine(HashMap<String,Command> newHashCommand) {
		this.commandLine = new CLI(inputFromClient, outputFromClient, newHashCommand);
		commandLine.addObserver(this);
	}

	/**
	 * implements display method of view interface <br>
	 * gets- object, DisplayType<br>
	 * depend on the object type, the DisplayType will be suitable
	 * 
	 */
	@Override
	public void display (Object obj,DisplayType d) {
		d.display(obj);		
	}


	/**
	 * start method calls to CLI start method
	 */
	@Override
	public void start() {
		commandLine.start();
//		setChanged();
//		notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == commandLine){
			setChanged();
			notifyObservers(arg);	
		}
		
	}

}
