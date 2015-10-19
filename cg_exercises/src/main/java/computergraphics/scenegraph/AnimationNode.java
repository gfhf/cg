package computergraphics.scenegraph;

import java.util.Timer;
import java.util.TimerTask;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

public class AnimationNode extends Node
{
	
	/**
	   * Timer object to create a game loop.
	   */
	  private Timer timer = new Timer();
	  private Vector3 rotation = new Vector3(1, 1, 1);
	  private double angle;
	  
	public AnimationNode(Vector3 rotation, double a, int time){
		this.rotation.copy(rotation);
	    this.angle = a;
		
		timer.schedule(new TimerTask() {
		      @Override
		      public void run() {
		    	  angle +=1;
		      }
		    }, time, time);
		
	}

	@Override
	  public void drawGl(GL2 gl) {
	    // Remember current state of the render system
	    gl.glPushMatrix();

	    // Apply rotate
	    gl.glRotatef((float) angle, (float) rotation.get(0), (float) rotation.get(1),
	        (float) rotation.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();

	  }
}
