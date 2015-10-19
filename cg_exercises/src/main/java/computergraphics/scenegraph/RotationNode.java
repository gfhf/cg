package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

public class RotationNode extends Node
{
	/**
	   * Rotate factors in x-, y- and z-direction.
	   */
	  private final Vector3 rotation = new Vector3(1, 1, 1);
	  private final double angle;
	  /**
	   * Constructor.
	   */
	  public RotationNode(Vector3 rotation, float angle) {
	    this.rotation.copy(rotation);
	    this.angle = angle;
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
