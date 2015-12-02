/**
 * WP Computer Graphik WS 15/16
 * Praktikumgruppe Nummer ${todo}
 * stephan.berngruber@haw-hamburg.de
 * rene.goetsch@haw-hamburg.de
 * Aufgabenblatt Nr. ${todo}
 * Verwendete Quellen: 
 * 	https://en.wikipedia.org/wiki/RGB_color_model
 * 	http://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/
 * 
 */

package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

/**
 * Scene graph node which sets the color for all its child nodes. *
 */
public class ColorNode extends Node {

	/**
	 * Vector for saving the color values of this node.
	 */
	private Vector3 colorVector;

	/**
	 * Getter.
	 */
	public Vector3 getColor() {
		return colorVector;
	}

	/**
	 * Setter for the color represented by this node. Valid range for values is
	 * 0 to 1.0.
	 */
	public void setColor(Vector3 colorVector) {
		this.colorVector = colorVector;
	}

	/**
	 * Setter for the color represented by this node, excapting rgb values.
	 * Valid range for values is 0 to 1.0.
	 */
	public void setColor(double r, double g, double b) {
		setColor(new Vector3(r, g, b));
	}

	/**
	 * Initializes a ColorNode with the passed in vector interpreting its x
	 * component as red value, y component as green value and z component as
	 * blue value. Valid range for values is 0 to 1.0.
	 */
	public ColorNode(Vector3 color) {
		this();
		this.colorVector.copy(color);
	}

	/**
	 * Initializes a ColorNode with the passed in red, green, and blue values.
	 * Valid range for values is 0 to 1.0.
	 */
	public ColorNode(double r, double g, double b) {
		this(new Vector3(r, g, b));
	}

	/**
	 * Initializes a ColorNode with color set to (0,0,0) -> black.
	 */
	public ColorNode() {
		colorVector = new Vector3(0, 0, 0);
	}

	@Override
	public void drawGl(GL2 gl) {
		/// Remember current state of the render system
		gl.glPushMatrix();

		// get the values saved in the color vector
		double[] colorValues = colorVector.data();
		gl.glColor3d(colorValues[0], colorValues[1], colorValues[2]);

		// Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}

		// Restore original state
		gl.glPopMatrix();
	}

}
