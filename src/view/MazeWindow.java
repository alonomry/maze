package view;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;

public class MazeWindow extends BasicWindow {//implements View {

	
	String fileName;
	HashMap<String, Command> hashCommand;
	HashMap<String,Listener> buttons= new HashMap<>();
	MazeDisplayer maze;
	KeyListener CanvasKeyListener;
	Timer timer;
	TimerTask task;
	
	
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}

	public void WalkToExit(Solution<Position> sol){
	for(int i= sol.getSolution().size()-1;i>=0;i--)//the size of the solution
	{								
		maze.setCharacterPosition(sol.getSolution().get(i).getState().getDim(), sol.getSolution().get(i).getState().getWid(), sol.getSolution().get(i).getState().getLen());
		//set the character position every step in the solution
		try {
			Thread.sleep((long) (0.3 * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	}
	
	@Override
	void initWidgets() {
		
		
		  Menu menuBar, fileMenu, helpMenu;

		  MenuItem fileMenuHeader, helpMenuHeader;

		  MenuItem fileExitItem, filePropertiesItem, helpGetHelpItem;
		  
		
		  shell.setLayout(new GridLayout(2,false));
		
		//******Generate button*****
		
		Button GenerateButton=new Button(shell, SWT.PUSH);
		GenerateButton.setText("Generate");
		GenerateButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		GenerateButton.addListener(SWT.Selection, buttons.get("generate"));
		
		
		//********end of Generate button*******
		
			
		//******canvas*******
		//MazeDisplayer maze=new Maze2D(shell, SWT.BORDER);		
		maze=new Maze3D(shell, SWT.BORDER);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		
		//*******end of canvas*******
		
		
		//******Hint button*****
		
		Button HintButton=new Button(shell, SWT.PUSH);
		HintButton.setText("Hint");
		HintButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		HintButton.addListener(SWT.Selection, buttons.get("hint"));
		
		//*****end of Hint button******
		
		//******Solve button**********
		
		Button SolveButton=new Button(shell, SWT.PUSH);
		SolveButton.setText("Solve");
		SolveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		SolveButton.addListener(SWT.Selection, buttons.get("solve"));
		
		//*****end of Solve button******
		

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
				
	}

	
	
	public void setButtons(HashMap<String, Listener> buttons) {
		this.buttons = buttons;
	}

	public void setCanvasKeyListener(KeyListener canvasKeyListener) {
		CanvasKeyListener = canvasKeyListener;
	}


}
