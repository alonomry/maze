package view;

import java.util.HashMap;
import java.util.Observable;
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




public class Gui extends Observable implements View {
	HashMap<String, Command> hashCommand;
	HashMap<String,Listener> buttons= new HashMap<>();
	KeyListener CanvasKeyListener;
	MazeWindow mazewindow;
	String fileName, SolvingAlgorithm;
	Shell shell;
	String LastButtonPressed;
	String CurrentMazeName;
	
	public Gui(String title, int width, int height) {
		this.mazewindow=new MazeWindow(title, width, height);
		this.shell=mazewindow.getShell();
		InitButtons();
		this.mazewindow.setButtons(this.buttons);
		this.mazewindow.setCanvasKeyListener(this.CanvasKeyListener);
	}
	
	//MazeDisplayer maze;
	
	private void InitButtons() {	
		
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
						
						String[] line1={"display",T.getText()};
						Command command1 = hashCommand.get("display");
						command1.setStringCommand(line1);
						setChanged();
						notifyObservers(command1);	
						sh.close();
						
						
						
						//setbuttonavalible //HintButton.setEnabled(false);
				
			}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				sh.open();
		}
		});
				
		
		
		
				
				
		buttons.put("solve",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				LastButtonPressed="solve";
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
		
		
		
		buttons.put("properties",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				fileName= fd.open();
				setChanged();
				notifyObservers(fileName);
				
			}
		});
		
		
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
		
		
		buttons.put("exit",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setChanged();
				notifyObservers(hashCommand.get("exit"));
				shell.close();
				
			}
		});
		
		
		CanvasKeyListener=new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN ) {
						mazewindow.maze.moveBackward();
						setChanged();
						notifyObservers("checkDimention");
			    } else if (e.keyCode == SWT.ARROW_UP ) {
				    	mazewindow.maze.moveForward();
						setChanged();
						notifyObservers("checkDimention");
			    } else if (e.keyCode == SWT.ARROW_LEFT ) {
				    	mazewindow.maze.moveLeft();
						setChanged();
						notifyObservers("checkDimention");
			    } else if (e.keyCode == SWT.ARROW_RIGHT ) {
				    	mazewindow.maze.moveRight();
						setChanged();
						notifyObservers("checkDimention");
				} else if (e.keyCode == SWT.SHIFT){
						mazewindow.maze.moveUp();
						setChanged();
						notifyObservers("checkDimention");
				} else if (e.keyCode == 0x2f){// The key "/" to go down
						mazewindow.maze.moveDown();
						setChanged();
						notifyObservers("checkDimention");
				}
			}
		};	
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void display(Object obj, DisplayType d) {
		String type;
			type = obj.getClass().getCanonicalName();
			switch (type) {
				case "algorithms.mazeGenerators.Maze3d":
					mazewindow.maze.setCurrentMaze((Maze3d)obj);
					checkDimention();
					this.mazewindow.maze.setCharacterPosition(((Maze3d)obj).getEnter().getDim(),((Maze3d)obj).getEnter().getWid(),((Maze3d)obj).getEnter().getLen());

				break;
				case "java.lang.String":
				{
					if (((String)obj).contains("solution"))
					{
						String[] name = ((String)obj).split(" ");
						String[] line={"display","solution",name[2] };
						Command command = hashCommand.get("display solution");
						command.setStringCommand(line);
						setChanged();
						notifyObservers(command);
					}
					if (((String)obj).contains("Valid")||((String)obj).contains("found"))
					{
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								MessageBox messageBox = new MessageBox(shell,  SWT.ICON_WARNING| SWT.OK);
								messageBox.setMessage(((String)obj));
								messageBox.setText("error");	
								messageBox.open();	
							}
						});
						
					}
					if (((String)obj).equals("checkDimention")){
						checkDimention();
					}
				break;	
			}
			case "algorithms.search.Solution":

								mazewindow.WalkToExit((Solution<Position>)obj);
								checkDimention();
				break;
			case "presenter.Properties":
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
	
	
	
	public void setCommandLine(HashMap<String, Command> newhashCommand) {
		this.hashCommand = new HashMap<>(newhashCommand);
		
	}
	
	public void setSolvingAlgorithm(String solvingAlgorithm) {
		SolvingAlgorithm = solvingAlgorithm;
	}

	
	public boolean CharAndEnterIsEqual(){
		if(mazewindow.maze.getCharacter().equals(mazewindow.maze.getCurrentMaze().getEnter())){
			return true;
		}
		return false;	
	}

	public void checkDimention(){
		mazewindow.arrowDimention();
	}
	
	@Override
	public void start() {
		mazewindow.run();
		
	}
}
		
		
