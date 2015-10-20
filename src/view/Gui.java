package view;

import java.util.HashMap;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

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
import CliDisplays.SolDisplay;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;




public class Gui extends Observable implements View {
	HashMap<String, Command> hashCommand;
	HashMap<String,Listener> buttons= new HashMap<>();
	KeyListener CanvasKeyListener;
	MazeWindow mazewidow;
	String fileName;
	Shell shell;

	
	public Gui(String title, int width, int height) {
		this.mazewidow=new MazeWindow(title, width, height);
		this.shell=mazewidow.getShell();
		InitButtons();
		this.mazewidow.setButtons(this.buttons);
		this.mazewidow.setCanvasKeyListener(this.CanvasKeyListener);
	}
	
	//MazeDisplayer maze;
	
	private void InitButtons() {
		
		
		buttons.put("generate",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				//create new window,new shell
				Shell sh=new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
				sh.setLayout(new GridLayout(2,false));
				sh.setSize(500, 300);
				
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
				Ge.setText("Generate maze !");
				Ge.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 1, 1));
				Ge.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
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
				
				Shell sol=new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
				sol.setLayout(new GridLayout(2,false));
				sol.setSize(400, 200);
				
				Label l=new Label(sol, SWT.NONE);
				l.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
				l.setText("enter maze name to solve :");
				Text T=new Text(sol, SWT.None);
				T.setLayoutData(new GridData(SWT.NONE, SWT.TOP, true, true, 1, 1));
				
				Button Ge=new Button(sol, SWT.PUSH);
				Ge.setText("solve maze !");
				Ge.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 1, 1));
				Ge.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						if(CharAndEnterIsEqual()){
							String[] line={"solve",T.getText(),"Astar-air"};
							Command command = hashCommand.get("solve");
							command.setStringCommand(line);
							setChanged();
							notifyObservers(command);
							sol.close();
						}
						else{
						String[] line={"solve",T.getText(),"Astar-air",Integer.toString(((Maze3D)(mazewidow.maze)).getCharacterY()),
								Integer.toString(((Maze3D)(mazewidow.maze)).getCharacterZ()),
								Integer.toString(((Maze3D)(mazewidow.maze)).getCharacterX())};
						Command command = hashCommand.get("solve");
						command.setStringCommand(line);
						setChanged();
						notifyObservers(command);
						sol.close();
						}
						
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
					}
					
				});
			
				sol.open();

			}
				
				
			
		});
		
		
		buttons.put("hint",new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				// TODO Auto-generated method stub
				
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
				if (e.keyCode == SWT.ARROW_DOWN ) {//down
					mazewidow.maze.moveDown();
		    } else if (e.keyCode == SWT.ARROW_UP ) {//up
		    	mazewidow.maze.moveUp();
		    } else if (e.keyCode == SWT.ARROW_LEFT ) {//left
		    	mazewidow.maze.moveLeft();
		    } else if (e.keyCode == SWT.ARROW_RIGHT ) {//right
		    	mazewidow.maze.moveRight();
			} else if (e.keyCode == 16777221 ) {//page up
	            	
			}		
			}
		};
	
		
		
		
		
		
		
		
		
	}

	@Override
	public void display(Object obj, DisplayType d) {
		String type;
			type = obj.getClass().getCanonicalName();
			switch (type) {
			case "algorithms.mazeGenerators.Maze3d":
			this.mazewidow.maze.setExit(((Maze3d)obj).getExit().getDim(),((Maze3d)obj).getExit().getWid(),((Maze3d)obj).getExit().getLen());
			this.mazewidow.maze.setMazeData(((Maze3d)obj).getCrossSectionByY(((Maze3d)obj).getEnter().getDim()));
			this.mazewidow.maze.setCharacterPosition(((Maze3d)obj).getEnter().getDim(),((Maze3d)obj).getEnter().getWid(),((Maze3d)obj).getEnter().getLen());
			this.mazewidow.maze.setEnter(((Maze3d)obj).getEnter().getDim(),((Maze3d)obj).getEnter().getWid(),((Maze3d)obj).getEnter().getLen());
			
				break;
			case "java.lang.String":
			{
				if (((String)obj).contains("solution"))
				{
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageBox messageBox = new MessageBox(shell,  SWT.ICON_INFORMATION| SWT.OK);
							messageBox.setMessage(((String)obj));
							messageBox.setText("solve complete");	
							messageBox.open();	
						}
					});
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
				break;	

			}
			case "algorithms.search.Solution":

								mazewidow.WalkToExit((Solution<Position>)obj);
							
				break;
			default:
				break;
				
			}
			
		
	}
	
	
	
	public void setCommandLine(HashMap<String, Command> newhashCommand) {
		this.hashCommand = new HashMap<>(newhashCommand);
		
	}
	

	@Override
	public void start() {
		mazewidow.run();
		
	}
	
	public boolean CharAndEnterIsEqual(){
		if(((Maze3D)(mazewidow.maze)).getCharacterX()==((Maze3D)(mazewidow.maze)).getEnterX()&&
				((Maze3D)(mazewidow.maze)).getCharacterY()==((Maze3D)(mazewidow.maze)).getEnterY()&&
				((Maze3D)(mazewidow.maze)).getCharacterZ()==((Maze3D)(mazewidow.maze)).getEnterZ()){
			return true;
		}
		return false;	
	}
	
	
	
}
		
		
