package view;


import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import presenter.Command;

public class MazeWindow extends BasicWindow {//implements View {

	
	String fileName;
	HashMap<String, Command> hashCommand;
	HashMap<String,Listener> buttons= new HashMap<>();
	MazeDisplayer maze;
	KeyListener CanvasKeyListener;
	Timer timer;
	TimerTask task;
	Label upArrow;
	Label downArrow;
	Image up;
	Image down;
	Button GenerateButton;
	Button HintButton;
	Button SolveButton;
	boolean keyListenerActivator = false;

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}

	public void WalkToExit(Solution<Position> sol){
		timer = new Timer();
		task = new TimerTask() {
		int i = sol.getSolution().size()-1;
			@Override
			public void run() {//set the character position every step in the solution
				if(i>=0)
				{
					maze.setCharacterPosition(sol.getSolution().get(i).getState().getDim(), 
											  sol.getSolution().get(i).getState().getWid(),
											  sol.getSolution().get(i).getState().getLen());
					
					i--;
				}
				else{
					timer.cancel();
					timer.purge();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, (long) (0.3 * 1000));
		timer.purge();
		
	}
	
	public void WalkByHint(Solution<Position> sol){

					maze.setCharacterPosition(sol.getSolution().get(sol.getSolution().size()-2).getState().getDim(), 
											  sol.getSolution().get(sol.getSolution().size()-2).getState().getWid(),
											  sol.getSolution().get(sol.getSolution().size()-2).getState().getLen());			
	}
	
	
	@Override
	void initWidgets() {
		
		
		  Menu menuBar, fileMenu, helpMenu;

		  MenuItem fileMenuHeader, helpMenuHeader;

		  MenuItem fileExitItem, filePropertiesItem, helpGetHelpItem;
		  
		
		  shell.setLayout(new GridLayout(2,false));
		
		//******Generate button*****
		
		GenerateButton=new Button(shell, SWT.PUSH);
		GenerateButton.setText("Generate");
		GenerateButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		GenerateButton.addListener(SWT.Selection, buttons.get("generate"));
		
		//********end of Generate button*******
		
			
		//******canvas*******
		//MazeDisplayer maze=new Maze2D(shell, SWT.BORDER);		
		maze=new Maze3D(shell, SWT.BORDER);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,5));
		
		//*******end of canvas*******
		
		
		//******Hint button*****
		
		HintButton=new Button(shell, SWT.PUSH);
		HintButton.setText("Hint");
		HintButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		HintButton.addListener(SWT.Selection, buttons.get("hint"));
		HintButton.setEnabled(false);
		
		
		//*****end of Hint button******
		
		//******Solve button**********
		
		SolveButton=new Button(shell, SWT.PUSH);
		SolveButton.setText("Solve");
		SolveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		SolveButton.addListener(SWT.Selection, buttons.get("solve"));
		SolveButton.setEnabled(false);
		
		//*****end of Solve button****** 

		//*****Status Arrows*******
		//___UP ARROW_____
			upArrow = new Label(shell, SWT.BORDER_SOLID);
			up = new Image(display, "lib/img/up.png");
			upArrow.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
			upArrow.setImage(up);
			upArrow.setVisible(false);
			
			//___DOWN ARROW_____
			downArrow = new Label(shell, SWT.BORDER_SOLID);
			down = new Image(display, "lib/img/down.png");
			downArrow.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
			downArrow.setImage(down);
			downArrow.setVisible(false);

		//*****End of Status Arrow****
	

		//**********menu settings************
		//***********************************
		
		menuBar = new Menu(shell, SWT.BAR);
	    fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText("&File");
	    
	    fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);

	    filePropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
	    filePropertiesItem.setText("&Properties");
	    filePropertiesItem.addListener(SWT.Selection, buttons.get("properties"));

	    fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText("&Exit");
	    fileExitItem.addListener(SWT.Selection, buttons.get("exit"));

	    helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    helpMenuHeader.setText("&Help");

	    helpMenu = new Menu(shell, SWT.DROP_DOWN);
	    helpMenuHeader.setMenu(helpMenu);

	    helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
	    helpGetHelpItem.setText("&About");
	    helpGetHelpItem.addListener(SWT.Selection, buttons.get("about"));
	    
	
	//*********key listener***********	 
	    maze.addKeyListener(CanvasKeyListener);
	//*********and of key listener***********   
	
	    shell.setMenuBar(menuBar);
	    
	    shell.addListener(SWT.Close, buttons.get("exit"));
				
	}
	
	public void setButtons(HashMap<String, Listener> buttons) {
		this.buttons = buttons;
	}

	public void setCanvasKeyListener(KeyListener canvasKeyListener) {
		CanvasKeyListener = canvasKeyListener;
	}
	
	public void setKeyListenerOn(){
		keyListenerActivator = true;
	}
	
	public void setKeyListenerOff(){
		keyListenerActivator = false;
	}
	
	public void setButtonOff(){
		HintButton.setEnabled(false);
		SolveButton.setEnabled(false);
		
		upArrow.setVisible(false);
		downArrow.setVisible(false);	
	}
	
	public void setButtonOn(){		
		
        HintButton.setEnabled(true);
        SolveButton.setEnabled(true);
		
		upArrow.setVisible(true);
		downArrow.setVisible(true);

		
	}
	
	public void arrowDimention() {
		Image upGrey = new Image(display, up,SWT.IMAGE_GRAY);
		Image downGrey = new Image(display, down,SWT.IMAGE_GRAY);
		if (maze.getCharacter().getDim() + 1 < maze.getCurrentMaze().getDIMENSION()){
			if (maze.getCurrentMaze().maze3d[maze.getCharacter().getDim() + 1][maze.getCharacter().getWid()][maze.getCharacter().getLen()] == 0){
				upArrow.setVisible(true);
				upArrow.setImage(up);
			}
			else {
				upArrow.setVisible(true);
				upArrow.setImage(upGrey);
			}
		}
		else {
			upArrow.setVisible(true);
			upArrow.setImage(upGrey);
		}
		
		if (maze.getCharacter().getDim() - 1 >= 0){
			if (maze.getCurrentMaze().maze3d[maze.getCharacter().getDim() - 1][maze.getCharacter().getWid()][maze.getCharacter().getLen()] == 0){
				downArrow.setVisible(true);
				downArrow.setImage(down);
			}
			else{
				downArrow.setVisible(true);
				downArrow.setImage(downGrey); 
			}
		}
		else {
			downArrow.setVisible(true);
			downArrow.setImage(downGrey); 
		}
			
	}

}
