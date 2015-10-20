package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

public class Maze3D extends MazeDisplayer {

	public int characterX=0;
	public int characterY=2;
	public int characterZ=0;
	public int exitX=0;
	public int exitY=0;
	public int exitZ=0;
	public int enterX=0;
	public int enterY=0;
	public int enterZ=0;

	
	
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
	public Maze3D(Composite parent, int style) {
		super(parent, style);
		
		final Color white=new Color(null, 255, 255, 255);
	//	final Color black=new Color(null, 150,150,150);
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
				          
				          if(i==characterZ && j==characterX){
							   e.gc.setBackground(new Color(null,200,0,0));
							   e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
							   e.gc.setBackground(new Color(null,255,0,0));
							   e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
							   e.gc.setBackground(new Color(null,0,0,0));				        	  
				          }
				      }
				   }
				
			}
		});
	}
	
	private void moveCharacter(int x,int z){
		if(x>=0 && x<mazeData[0].length && z>=0 && z<mazeData.length && mazeData[z][x]==0){
			characterX=x;
			characterZ=z;
			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});
		}
	}
	
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveUp()
	 */
	@Override
	public void moveUp() {
		int x=characterX;
		int z=characterZ;
		
		z=z-1;
		moveCharacter(x, z);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveDown()
	 */
	@Override
	public void moveDown() {
		int x=characterX;
		int z=characterZ;
		z=z+1;
		moveCharacter(x, z);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveLeft()
	 */
	@Override
	public void moveLeft() {
		int x=characterX;
		int z=characterZ;
		x=x-1;
		moveCharacter(x, z);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveRight()
	 */
	@Override
	public void moveRight() {
		int x=characterX;
		int z=characterZ;
		x=x+1;
		moveCharacter(x, z);
	}
	
	@Override
	public void setCharacterPosition(int dim, int wid,int len) {
		characterX=len;
		characterY=dim;
		characterZ=wid;
		moveCharacter(len,wid);
	}
	@Override
	public void setExit(int dim, int wid, int len) {
		this.exitY=dim;
		this.exitX=len;
		this.exitZ=wid;
	}
	public int getCharacterX() {
		return characterX;
	}
	public int getCharacterY() {
		return characterY;
	}
	public int getCharacterZ() {
		return characterZ;
	}
	
	public int getEnterX() {
		return enterX;
	}
	public int getEnterY() {
		return enterY;
	}
	public int getEnterZ() {
		return enterZ;
	}
	@Override
	public void setEnter(int dim, int wid, int len) {
		this.enterY=dim;
		this.enterX=len;
		this.enterZ=wid;		
	}

	
	
}