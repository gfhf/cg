package computergraphics.scenegraph;

import java.util.Timer;
import java.util.TimerTask;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

public class MovementNode extends Node
{
	  /**
	   * Translates factors in x-, y- and z-direction.
	   */
	  private double x;
	  private double y;
	  private double z;
	  private boolean zmax;
	  private Timer timer = new Timer();
	  
	  
	  /**
	   * Constructor.
	   */
	  public MovementNode(Vector3 translation, int time) {
	    this.x = translation.get(0);
	    this.y = translation.get(1);
	    this.z = translation.get(2);
	    
	    timer.schedule(new TimerTask() {
		      @Override
		      public void run() {
		    	  if(z <= 0) {
		    		  zmax = false;
		    	  }
		    	  if(z >= 10) {
		    		  zmax = true;
		    	  }
		    	  if (zmax){
		    		  z = (z-0.1);  
		    	  }
		    	  else{
		    		  z = (z+0.1);
		    	  }
		      }
		    }, time, time);
	  }
	  
	  @Override
	  public void drawGl(GL2 gl) {
	    // Remember current state of the render system
	    gl.glPushMatrix();

	    // Apply translation
	    gl.glTranslatef((float) x, (float) y,
	        (float) z);

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();

	  }
}
