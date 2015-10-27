package view;

import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import CliDisplays.DisplayType;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;





/**
 * <H2>Gui</H2>
 * implements "View" interface<br>
 * all the buttons actions defined in this class<br>
 * @param buttons HashMap that connecting between String and Listeners.<br> the Shell buttons taking the actions from this Hash
 * @param CanvasKeyListener defining a key listener actions
 * @param mazewindow kind of Basic window
 * @param SolvingAlgorithm solving algorithm from properties file 
 */
public class Gui extends Observable implements View {
	
	/** The hash command for communicating with the presenter */
	HashMap<String, Command> hashCommand;
	
	/** HashMap that connecting between String and Listeners.<br> the Shell buttons taking the actions from this Hash */
	HashMap<String,Listener> buttons= new HashMap<>();
	
	/** defining a key listener actions */
	KeyListener CanvasKeyListener;
	
	/** kind of Basic window */
	MazeWindow mazewindow;
	
	/** solving algorithm from properties file and the file name from properties dialog */
	String fileName=null, SolvingAlgorithm;
	
	/** The shell. */
	Shell shell;
	
	/** The Last button pressed. */
	String LastButtonPressed;
	
	/** The Current maze name. */
	String CurrentMazeName;
	
	/** The key listener activator. */
	boolean keyListenerActivator = false;
	/** if the server is connected or not. */
	boolean serverConnected = true;
	
	/**
	 * Instantiates a new gui.
	 *
	 * @param title the screen title
	 * @param width the screen width
	 * @param height the screen height
	 */
	public Gui(String title, int width, int height) {
		this.mazewindow=new MazeWindow(title, width, height);
		this.shell=mazewindow.getShell();
		InitButtons();
		this.mazewindow.setButtons(this.buttons);
		this.mazewindow.setCanvasKeyListener(this.CanvasKeyListener);
	}
	
	
	/**
	 * Inits the buttons actions and put them on HashMap
	 */
	private void InitButtons() {	
		//generate button
		buttons.put("generate",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				//create new window,new shell
				Shell sh=new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
				sh.setLayout(new GridLayout(2,false));
				sh.setSize(300, 300);
				
				Label l=new Label(sh, SWT.NONE);
				l.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
				l.setText("maze name :");
				Text T=new Text(sh, SWT.None);
				T.setLayoutData(new GridData(SWT.NONE, SWT.TOP, true, true, 1, 1));
				
				Label dim=new Label(sh, SWT.NONE);
				dim.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
				dim.setText("dimenssion :");
				Text tdim=new Text(sh, SWT.None);
				tdim.setLayoutData(new GridData(SWT.NONE, SWT.TOP, true, true, 1, 1));
				
				Label wid=new Label(sh, SWT.NONE);
				wid.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
				wid.setText("width :");
				Text twid=new Text(sh, SWT.None);
				twid.setLayoutData(new GridData(SWT.NONE, SWT.TOP, true, true, 1, 1));
				
				Label len=new Label(sh, SWT.NONE);
				len.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
				len.setText("length :");
				Text tlen=new Text(sh, SWT.None);
				tlen.setLayoutData(new GridData(SWT.NONE, SWT.TOP, true, true, 1, 1));
				
				Button Ge=new Button(sh, SWT.PUSH);
				Ge.setText("Generate Maze");
				Ge.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 1, 1));
				Ge.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						CurrentMazeName=T.getText();
						String[] line={"generate", "3d", "maze", T.getText(), tdim.getText(), twid.getText(), tlen.getText()};
						Command command = hashCommand.get("generate 3d maze");
						command.setStringCommand(line);
						setChanged();
						notifyObservers(command);
						sh.close();
				
			}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				sh.open();
		}
		});
		
		//solve button
		buttons.put("solve",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				LastButtonPressed="solve";
				keyListenerActivator = false;
				serverConnected = true;
						if(CharAndEnterIsEqual()){
							String[] line={"solve",CurrentMazeName,SolvingAlgorithm};
							Command command = hashCommand.get("solve");
							command.setStringCommand(line);
							setChanged();
							notifyObservers(command);
							if (serverConnected){
								setChanged();
								notifyObservers("setButtonOff");
							}
						}
						else{
							String[] line={"solve",CurrentMazeName,SolvingAlgorithm,Integer.toString(mazewindow.maze.getCharacter().getDim()),	
																					Integer.toString(mazewindow.maze.getCharacter().getWid()),
																					Integer.toString(mazewindow.maze.getCharacter().getLen())};
							Command command = hashCommand.get("solve");
							command.setStringCommand(line);
							setChanged();
							notifyObservers(command);
							if (serverConnected){
								setChanged();
								notifyObservers("setButtonOff");
							}
						}
			}	
		});
		
		//hint button
		buttons.put("hint",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				LastButtonPressed="hint";
				if(CharAndEnterIsEqual()){
						String[] line={"solve",CurrentMazeName,SolvingAlgorithm};
						Command command = hashCommand.get("solve");
						command.setStringCommand(line);
						setChanged();
						notifyObservers(command);
				}
				else{
					String[] line={"solve",CurrentMazeName,SolvingAlgorithm,Integer.toString(mazewindow.maze.getCharacter().getDim()),	
																				Integer.toString(mazewindow.maze.getCharacter().getWid()),
																				Integer.toString(mazewindow.maze.getCharacter().getLen())};
						Command command = hashCommand.get("solve");
						command.setStringCommand(line);
						setChanged();
						notifyObservers(command);
				}
			}

		});
		
		
		//properties button
		buttons.put("properties",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				fileName= fd.open();
				if(fileName!=null){
					setChanged();
					notifyObservers(fileName);
				}
				
			}
		});
		
		//about button
		buttons.put("about",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Shell He=new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
				He.setLayout(new GridLayout(2,false));
				He.setSize(500, 300);
				
				Label l=new Label(He, SWT.NONE);
				l.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
				l.setText("All the rigths to Alon Tal and Omry Dabush");
				He.open();
				
			}
		});
		
		//exit button
		buttons.put("exit",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setChanged();
				notifyObservers(hashCommand.get("exit"));
				shell.dispose();
				System.exit(0);
			}
		});
		
		//key listener
		CanvasKeyListener=new KeyListener(){
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (keyListenerActivator){
					if (e.keyCode == SWT.ARROW_DOWN ) { //pressing Down button
							if (mazewindow.maze.moveBackward())
								playSound("lib/sound/beep.wav");
							setChanged();
							notifyObservers("checkDimention");
							if (CharAndExitIsEqual()){
								setButtonOff();
								doneMessageBox("Congratulation You Solved The Maze :)");
								playSound("lib/sound/iwon.wav");
								keyListenerActivator = false;
							}
				    } else if (e.keyCode == SWT.ARROW_UP ) { //pressing Up button
					    	if (mazewindow.maze.moveForward())
					    		playSound("lib/sound/beep.wav");
							setChanged();
							notifyObservers("checkDimention");
							if (CharAndExitIsEqual()){
								setButtonOff();
								doneMessageBox("Congratulation You Solved The Maze :)");
								playSound("lib/sound/iwon.wav");
								keyListenerActivator = false;
							}
				    } else if (e.keyCode == SWT.ARROW_LEFT ) { //pressing Left button
					    	if (mazewindow.maze.moveLeft())
					    		playSound("lib/sound/beep.wav");
							setChanged();
							notifyObservers("checkDimention");
							if (CharAndExitIsEqual()){
								setButtonOff();
								doneMessageBox("Congratulation You Solved The Maze :)");
								playSound("lib/sound/iwon.wav");
								keyListenerActivator = false;
							}
				    } else if (e.keyCode == SWT.ARROW_RIGHT ) { //pressing Right button
					    	if(mazewindow.maze.moveRight())
					    		playSound("lib/sound/beep.wav");
							setChanged();
							notifyObservers("checkDimention");
							if (CharAndExitIsEqual()){
								setButtonOff();
								doneMessageBox("Congratulation You Solved The Maze :)");
								playSound("lib/sound/iwon.wav");
								keyListenerActivator = false;
							}
					} else if (e.keyCode == SWT.SHIFT){ //pressing Shift button goes upper dimension
							if (mazewindow.maze.moveUp())
								playSound("lib/sound/beep.wav");
							setChanged();
							notifyObservers("checkDimention");
							
							if (CharAndExitIsEqual()){
								setButtonOff();
								doneMessageBox("Congratulation You Solved The Maze :)");
								playSound("lib/sound/iwon.wav");
								keyListenerActivator = false;
							}
					} else if (e.keyCode == 0x2f){// The key "/" to go lower dimension
							if (mazewindow.maze.moveDown())
								playSound("lib/sound/beep.wav");
							setChanged();
							notifyObservers("checkDimention");
							if (CharAndExitIsEqual()){
								setButtonOff();
								doneMessageBox("Congratulation You Solved The Maze :)");
								playSound("lib/sound/iwon.wav");
								keyListenerActivator = false;
							}
					}
				}
			}
			};	
		}

	/**
	 * <H2>display</H2>
	 * checks what object returned from Mymodel and than display something or making other algorithm
	 * @param obj object that returned
	 * @param d used in CLI interface, in GUI interface Irrelevant
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void display(Object obj, DisplayType d) {
		
		String type;
			type = obj.getClass().getCanonicalName();
			switch (type) {
				case "algorithms.mazeGenerators.Maze3d": //returned Maze3d Object
					mazewindow.maze.setCurrentMaze((Maze3d)obj);
					keyListenerActivator =true;
					setButtonOn();
					checkDimention();
					//moving the character to the enter position
					//this.mazewindow.maze.setCharacterPosition(((Maze3d)obj).getEnter().getDim(),((Maze3d)obj).getEnter().getWid(),((Maze3d)obj).getEnter().getLen());
					break;
				case "java.lang.String": //returned String Object
					if (((String)obj).contains("solution")) //if the String contains "solution", create new line and send to the presenter
					{
						String[] line={"display","solution",CurrentMazeName };
						Command command = hashCommand.get("display solution");
						command.setStringCommand(line);
						setChanged();
						notifyObservers(command);
						break;
					}
					
					//if the String equals to "maze ___ is ready", create new line and send to the presenter
					if (((String)obj).equals("maze "+CurrentMazeName+" is ready")) {
						String[] line1={"display",CurrentMazeName};
						Command command1 = hashCommand.get("display");
						command1.setStringCommand(line1);
						setChanged();
						notifyObservers(command1);	
						break;
					}
					
					if (((String)obj).contains("dont have solution for ")) {
						doneMessageBox((String)obj);
						break;
					}
					
					if (((String)obj).equals("Can't Connect To Server...")) {
						doneMessageBox((String)obj);
						serverConnected = false;
						break;
					}
					
					//if the string contains some error message
					if (((String)obj).contains("Valid")||((String)obj).contains("found")||((String)obj).contains("exist")){
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								MessageBox messageBox = new MessageBox(shell,  SWT.ICON_WARNING| SWT.OK);
								messageBox.setMessage(((String)obj));
								messageBox.setText("error");	
								messageBox.open();	
							}
						});
						break;
					}
					if (((String)obj).equals("checkDimention")){
						checkDimention();
						break;
					}
					if (((String)obj).equals("setButtonOff")){
						setButtonOff();
						break;
					}
					if (((String)obj).equals("setButtonOn")){
						setButtonOff();
						break;
					}
					
				break;	
			case "algorithms.search.Solution": //returned Solution Object
					if(LastButtonPressed.equals("solve")){//if the solution is to solve the whole maze
						mazewindow.maze.WalkToExit((Solution<Position>)obj);
						break;
					}
					else if(LastButtonPressed.equals("hint")){ //if the solution returned for hint
						mazewindow.maze.WalkByHint((Solution<Position>)obj);
						if (CharAndExitIsEqual()){
							setButtonOff();
							doneMessageBox("you reach the exit point");
						}
						break;
					}
								
				break;
			case "presenter.Properties": //returned Properties Object
				this.setSolvingAlgorithm(((Properties)obj).getSolvingAlgorithm());	
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						MessageBox messageBox = new MessageBox(shell,  SWT.ICON_INFORMATION| SWT.OK);
						messageBox.setMessage("Properties Sucsessfuly Loaded");
						messageBox.setText("load complete");	
						messageBox.open();	
					}
				});
				break;
			default:
				break;
				
			}
	}
	
	/**
	 * <H2>doneMessageBox</h2>
	 * Opening message box when the mazed solved
	 *
	 * @param s the s
	 */
	public void doneMessageBox(String s){
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageBox messageBox = new MessageBox(shell,  SWT.ICON_WARNING| SWT.OK);
				messageBox.setText("Maze Is Solved");
				messageBox.setMessage(s);	
				messageBox.open();	
			}
		});
	}
	
	/**
	 * Sets the command line hash map
	 *
	 * @param newhashCommand the HashMap
	 */
	public void setCommandLine(HashMap<String, Command> newhashCommand) {
		this.hashCommand = new HashMap<>(newhashCommand);
		
	}
	
	/**
	 * Sets the solving algorithm.
	 *
	 * @param solvingAlgorithm the new solving algorithm
	 */
	public void setSolvingAlgorithm(String solvingAlgorithm) {
		SolvingAlgorithm = solvingAlgorithm;
	}

	
	/**
	 * checks if the character position and the enter position is the same
	 *
	 * @return true, if successful
	 */
	public boolean CharAndEnterIsEqual(){
		if(mazewindow.maze.getCharacter().equals(mazewindow.maze.getCurrentMaze().getEnter())){
			return true;
		}
		return false;	
	}
	
	/**
	 * checks if the character position and the exit position is the same
	 *
	 * @return true, if successful
	 */
	public boolean CharAndExitIsEqual(){
		if(mazewindow.maze.getCharacter().equals(mazewindow.maze.getCurrentMaze().getExit())){
			return true;
		}
		return false;	
	}

	/**
	 * Check if can move upper dimension or lowe dimension
	 */
	public void checkDimention(){
		mazewindow.arrowDimention();
	}
	
	/**
	 * Sets the button off.
	 */
	public void setButtonOff(){
		mazewindow.setButtonOff();
	}
	
	/**
	 * Sets the button on.
	 */
	public void setButtonOn(){
		mazewindow.setButtonOn();
	}
	
	/**
	 * Play sound.
	 *
	 * @param musicfile the musicfile path
	 */
	public void playSound(String musicfile) {
		File audioFile = new File(musicfile);
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(audioFile));
			clip.start();
		}catch (Exception e) {
			setChanged();
			notifyObservers("Wrong File");
			
		}
	}
	
	/**
	 * calls to Run of mazewindow class
	 */
	@Override
	public void start() {
		mazewindow.run();
		
	}
}
		
		
