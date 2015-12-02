/**
 * WP Computer Graphik WS 15/16
 * Praktikumgruppe Nummer ${todo}
 * stephan.berngruber@haw-hamburg.de
 * rene.goetsch@haw-hamburg.de
 * Aufgabenblatt Nr. ${todo}
 * Verwendete Quellen: 
 * 	http://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

/**
 * Scene graph node which realizes a translation.
 */
public class TranslationNode extends Node
{
	/**
	   * Scaling factors in x-, y- and z-direction.
	   */
	  private final Vector3 translation = new Vector3(0, 0, 0);
	  
	  /**
	   * Getter.
	   */
	  public Vector3 getTranslation(){
		  return translation;
	  }

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

	    // Apply scaling
	    gl.glTranslated(translation.get(0), translation.get(1), translation.get(2));

	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();
	  }
}
