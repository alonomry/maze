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
							String theCommand = commandRecognizer(line);
							String[] stringCommand = line.split(" ");
							if (!(theCommand == null))
								hashCommand.get(theCommand).doCommand(stringCommand);
							else
								System.out.println("Worng Command");
					}
					hashCommand.get("exit").doCommand(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}	

	public String commandRecognizer (String s){
		String[] stringCommand = s.split(" ");
		switch (stringCommand[0]) {
		case "dir":
			return "dir";
		case "generate":
			if (s.contains("generate 3d maze"))
				return "generate 3d maze";
			break;
		case "save":
			if (s.contains("save maze"))
				return "save maze";
			break;
		case "load":
			if (s.contains("load maze"))
				return "load maze";
			break;
		case "maze":
			if (s.contains("maze size"))
				return "maze size";
			break;
		case "file":
			if (s.contains("file size"))
				return "file size";
			break;
		case "solve":
				return "solve";
		case "display":
			if (s.contains("display cross section by"))
				return "display cross section by";
			if (s.contains("display solution"))
				return "display solution";	
			return "display";
		default:
			break;
		}
		return null;	
	}
}

//---------------------------------------------------------
//----------------------- UNUSED CODE ---------------------
//---------------------------------------------------------

//public void start() {
//Thread t = new Thread(new Runnable() {
//	public void run() {
//		  String line;
//		  try {
//			while(!(line = in.readLine()).equals("exit")){
//					String[] stringCommand = line.split(" ");
//			//		if(hashCommand.containsKey(stringCommand[0])){
//						switch (stringCommand[0] & stringCommand.) {
//						case "dir":
//							hashCommand.get(stringCommand[0]).doCommand(stringCommand);
//							break;
//						case "generate":
//							hashCommand.get(stringCommand[0]+" "+stringCommand[1]+" "+stringCommand[2]).doCommand(stringCommand);
//							break;
//						case "display":
//							if (stringCommand[1].equals("cross")){
//								hashCommand.get(stringCommand[0]+" "+stringCommand[1]+" "+stringCommand[2]+" "+stringCommand[3]).doCommand(stringCommand);
//								break;
//							}
//							else {
//								if (stringCommand[1].equals("solution")){
//									hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
//									break;
//								}
//								else {
//									hashCommand.get(stringCommand[0]).doCommand(stringCommand);
//									break;
//								}
//									
//							}
//						case "save":
//							hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
//							break;
//						case "load":
//							hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
//							break;
//						case "maze":
//							try {
//								if
//								hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
//								throw new IOException("Wrong Cmmand");
//								break;
//							} catch (Exception e) {
//								
//								break;
//							}
//
//						case "file":
//							hashCommand.get(stringCommand[0]+" "+stringCommand[1]).doCommand(stringCommand);
//							break;
//						case "solve":
//							hashCommand.get(stringCommand[0]).doCommand(stringCommand);
//							break;
//						default:
//							System.out.println("Wrong Command");
//							break;
//						}
////					}
////					else
////						System.out.println("Wrong Command");
//							
//			}


