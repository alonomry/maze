package view;

import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import presenter.Command;

/**
 * @author alon tal and omry dabush<br>
 * <H2>MazeWindow</H2>
 * creates all buttons, labels and menu bar<br>
 * gets the HashMap for buttons action
 */
public class MazeWindow extends BasicWindow {
	String fileName;
	HashMap<String, Command> hashCommand;
	HashMap<String,Listener> buttons= new HashMap<>();
	MazeDisplayer maze;
	KeyListener CanvasKeyListener;
	Label upArrow;
	Label downArrow;
	Image up;
	Image down;
	Button GenerateButton;
	Button HintButton;
	Button SolveButton;
	boolean keyListenerActivator = false;

	public MazeWindow(String title, int width, int height) {//'c'tor
		super(title, width, height);
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
	    // the "X" button at the corner
	    shell.addListener(SWT.Close, buttons.get("exit")); 

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
	/**
	 * set the HashMap buttons actions
	 * @param buttons
	 */
	public void setButtons(HashMap<String, Listener> buttons) {
		this.buttons = buttons;
	}
	/**
	 * set the key listener
	 */
	public void setCanvasKeyListener(KeyListener canvasKeyListener) {
		CanvasKeyListener = canvasKeyListener;
	}
	/**
	 * listen to keys
	 */
	public void setKeyListenerOn(){
		keyListenerActivator = true;
	}
	/**
	 * do not listen to any key
	 */
	public void setKeyListenerOff(){
		keyListenerActivator = false;
	}
	/**
	 * disabling the buttons
	 */
	public void setButtonOff(){
		HintButton.setEnabled(false);
		SolveButton.setEnabled(false);
		
		upArrow.setVisible(false);
		downArrow.setVisible(false);	
	}
	/**
	 * enabling the buttons
	 */
	public void setButtonOn(){		
		
        HintButton.setEnabled(true);
        SolveButton.setEnabled(true);
		
		upArrow.setVisible(true);
		downArrow.setVisible(true);

		
	}
	
	/**
	 * dim or undim the label UP and label DOWN colors
	 */
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
