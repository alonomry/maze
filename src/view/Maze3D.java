package view;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * @author alon tal and omry dabush
 *<H2>Maze3D</H2>
 *kind of MazeDisplayer that him kind of canvas -> Maze3d kind of canvas
 */

public class Maze3D extends MazeDisplayer {

	
	Maze3d currentMaze;
	public Position character = new Position(0,3,8); //character default position
	Image myImage = new Image( getDisplay(), "lib/img/mushroom.jpg" ); //character image
	Timer timer;
	TimerTask task;
	
	/**
	 * painting single cube on the canvas
	 */
	private void paintCube(double[] p,double h,PaintEvent e){
		
        int[] f=new int[p.length];
        for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
        
        e.gc.drawPolygon(f);
        
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        

        int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
        e.gc.drawPolygon(b);
        int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
        e.gc.drawPolygon(fr);
        
        e.gc.fillPolygon(r);
		
	}
	/**
	 * <H2>Maze3D</H2>
	 * defining the printing area, backround color and calls in loop to paintcube
	 * @param parent
	 * @param style
	 */
	public Maze3D(Composite parent, int style) {
		super(parent, style);
		
		final Color white=new Color(null, 255, 255, 255);
		setBackground(white);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				   e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));

				   int width=getSize().x;
				   int height=getSize().y;
				   
				   int mx=width/2;

				   double w=(double)width/mazeData[0].length;
				   double h=(double)height/mazeData.length;
				   for(int i=0;i<mazeData.length;i++){
					   double w0=0.7*w +0.3*w*i/mazeData.length;
					   double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
					   double start=mx-w0*mazeData[i].length/2;
					   double start1=mx-w1*mazeData[i].length/2;
				      for(int j=0;j<mazeData[i].length;j++){
				          double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
				          double cheight=h/2;
				          if(mazeData[i][j]!=0)
				        	  paintCube(dpoints, cheight,e);
				          
				          //draw the character image when he moving
				          if(i==character.getWid() && j==character.getLen()){
				        	  e.gc.drawImage(myImage, 0, 0, 1024, 1024, (int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));			        	  
				          }
				      }
				   }
			}
		});
	}
	/**
	 * changing character parameters and calls to redraw
	 * @param dim the character dimension
	 * @param wid the character width
	 * @param len the character length
	 * @return true if success to move. else, false
	 */
	private boolean moveCharacter(int dim,int wid,int len){
		if((wid>=0 && wid< currentMaze.getWIDTH()) && (len>=0 && len<currentMaze.getLENGTH())){
			if  (currentMaze.maze3d[dim][wid][len] == 0){
				character.setDim(dim);
				character.setWid(wid);
				character.setLen(len);
				getDisplay().syncExec(new Runnable() {	
					@Override
					public void run() {
						redraw();
						
					}
					
				});
				return true;
			}	
		}
		return false;
	}

	
	@Override
	public boolean moveUp() {
		if ((character.getDim()+1) < currentMaze.getDIMENSION()){
			int y=character.getDim();
			int x=character.getLen();
			int z=character.getWid();
			if (currentMaze.maze3d[y+1][z][x] == 0){
				mazeData = currentMaze.getCrossSectionByY(y+1);
				character.setDim(y+1);
				redraw();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean moveDown() {
		if ((character.getDim()-1) >= 0){
			int y=character.getDim();
			int x=character.getLen();
			int z=character.getWid();
			if (currentMaze.maze3d[y-1][z][x] == 0){
				mazeData = currentMaze.getCrossSectionByY(y-1);
				character.setDim(y-1);
				redraw();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean moveForward() {
		int y=character.getDim();
		int z=character.getWid();
		int x=character.getLen();
		z=z-1;
		if (moveCharacter(y,z,x))
			return true;
		else return false;
	}

	@Override
	public boolean moveBackward() {
		int y=character.getDim();
		int z=character.getWid();
		int x=character.getLen();
		z=z+1;	
		if (moveCharacter(y,z,x))
			return true;
		else return false;
	}
	
	@Override
	public boolean moveLeft() {
		int y=character.getDim();
		int z=character.getWid();
		int x=character.getLen();
		x=x-1;
		if (moveCharacter(y,z,x))
			return true;
		else return false;
	}
	
	@Override
	public boolean moveRight() {
		int y=character.getDim();
		int z=character.getWid();
		int x=character.getLen();
		x=x+1;
		if (moveCharacter(y,z,x))
			return true;
		else return false;
	}

	@Override
	public void setCharacterPosition(int dim, int wid,int len) {//
		mazeData = currentMaze.getCrossSectionByY(dim);
		moveCharacter(dim, wid, len);
	}
	/**
	 * get character position
	 */
	public Position getCharacter() {
		return character;
	}
	
	@Override
	public void setCurrentMaze(Maze3d m) {
		currentMaze = m;
		mazeData = currentMaze.getCrossSectionByY(currentMaze.getEnter().getDim());
		setCharacterPosition(currentMaze.getEnter().getDim(), currentMaze.getEnter().getWid(), currentMaze.getEnter().getLen());
		redraw();
	}
	
	@Override
	public Maze3d getCurrentMaze() {
		return currentMaze;
	}
	
	@Override
	public void WalkToExit(Solution<Position> sol){
		timer = new Timer();
		task = new TimerTask() {
		int i = sol.getSolution().size()-1;
			@Override
			public void run() {//set the character position every step in the solution
				if(i>=0)
				{
					setCharacterPosition(sol.getSolution().get(i).getState().getDim(), 
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

	@Override
	public void WalkByHint(Solution<Position> sol){

		setCharacterPosition(sol.getSolution().get(sol.getSolution().size()-2).getState().getDim(), 
								  sol.getSolution().get(sol.getSolution().size()-2).getState().getWid(),
								  sol.getSolution().get(sol.getSolution().size()-2).getState().getLen());			
}
	
}
