package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import controller.Command;

public class MyView implements View {
	
	CLI commandLine;
	InputStream inputFromClient;
	OutputStream outputFromClient;
	
	public MyView(InputStream inputFromClient, OutputStream outputFromClient) {
		this.inputFromClient = inputFromClient;
		this.outputFromClient = outputFromClient;
	}
	
	public CLI getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(HashMap<String,Command> newHashCommand) {
		this.commandLine = new CLI(inputFromClient, outputFromClient, newHashCommand);
	}

	@Override
	public void display (Object obj,DisplayType d) {
		d.display(obj);		
	}

	@Override
	public void start() {
		commandLine.start();
	}

}
