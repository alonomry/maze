package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

import controller.Command;

public class CLI implements View{

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
							String[] stringCommand = line.split(" ");
							if(hashCommand.containsKey(stringCommand[0])){
								switch (stringCommand[0]) {
								case "dir":
									hashCommand.get(stringCommand[0]).doCommand(stringCommand);
									break;
								case "generate":
									hashCommand.get(stringCommand[0]+" "+stringCommand[1]+" "+stringCommand[2]).doCommand(stringCommand);
									break;
								case "display":
									if (stringCommand[1].equals("cross")){
										hashCommand.get(stringCommand[0]+" "+stringCommand[1]+" "+stringCommand[2]+" "+stringCommand[3]).doCommand(stringCommand);
										break;
									}
									else {
										if (stringCommand[1].equals("solution")){
											hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
											break;
										}
										else {
											hashCommand.get(stringCommand[0]).doCommand(stringCommand);
											break;
										}
											
									}
								case "save":
									hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
									break;
								case "load":
									hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
									break;
								case "maze":
									hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
									break;
								case "file":
									hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
									break;
								case "solve":
									hashCommand.get(stringCommand[0]).doCommand(stringCommand);
									break;
								default:
									break;
								}
							}
							else
								System.out.println("Wrong Command");
									
					}
					hashCommand.get("exit").doCommand(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}


	@Override
	public void display(Object obj) {
		// TODO Auto-generated method stub
		
	}
}
