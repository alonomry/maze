package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import controller.Command;

public class CLI {

	BufferedReader in = null;
	PrintWriter out = null;
	HashMap<String, Command> hashCommand;
	public CLI(InputStream inputFromClient, OutputStream outputFromClient, HashMap<String,Command> newHashCommand) {
		this.in = new BufferedReader (new InputStreamReader(inputFromClient));
		this.out = new PrintWriter(new OutputStreamWriter(outputFromClient));
		this.hashCommand = new HashMap<>(newHashCommand);
	}
	
	
	public void start() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				  String line;
				  try {
					while(!(line = in.readLine()).equals("exit")){
							if(hashCommand.containsKey(line))
								hashCommand.get(line).doCommand();
							else
								out.write("Wrong Command");		
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		}
	}
