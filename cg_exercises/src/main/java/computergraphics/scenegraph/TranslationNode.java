package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

public class TranslationNode extends Node
{
	  /**
	   * Translates factors in x-, y- and z-direction.
	   */
	  private final Vector3 translation = new Vector3(1, 1, 1);
	  
	  
	  /**
	   * Constructor.
	   */
	  public TranslationNode(Vector3 translation) {
	    this.translation.copy(translation);
	  }
	  
	  @Override
	  public void drawGl(GL2 gl) {
	    // Remember current state of the render system
	    gl.glPushMatrix();

	    // Apply translation
	    gl.glTranslatef((float) translation.get(0), (float) translation.get(1),
	        (float) translation.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();

	  }
}
