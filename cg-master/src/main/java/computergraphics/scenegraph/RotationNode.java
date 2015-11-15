/**
 * WP Computer Graphik WS 15/16
 * Praktikumgruppe Nummer ${todo}
 * stephan.berngruber@haw-hamburg.de
 * rene.goetsch@haw-hamburg.de
 * Aufgabenblatt Nr. ${todo}
 * Verwendete Quellen: 
 * 	https://www.opengl.org/sdk/docs/man2/xhtml/glRotate.xml
 * 	http://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/
 * 
 */

package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

/**
 * Scene graph node which realizes a rotation around a given axis by a given
 * angle in degree. *
 */
public class RotationNode extends Node {

	/**
	 * A vector describing the axis to rotate around
	 */
	private final Vector3 rotationAxis = new Vector3(1, 1, 1);

	/**
	 * Getter.
	 */
	public Vector3 getRotationAxis() {
		return rotationAxis;
	}
	
	/**
	 * Setter.
	 */
	public void setRotationAxis(Vector3 rotationAxis) {
		this.rotationAxis.copy(rotationAxis);
	}

	/**
	 * The angle of the rotation in degree.
	 */
	private double angle;

	/**
	 * Getter.
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Setter.
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Constructor.
	 * @param angle In degree.
	 * @param rotationAxis not null.
	 */
	public RotationNode(Vector3 rotationAxis, double angle) {
		this.rotationAxis.copy(rotationAxis);
		this.angle = angle;
	}

	@Override
	public void drawGl(GL2 gl) {
		// Remember current state of the render system
		gl.glPushMatrix();

		// Apply scaling
		gl.glRotated(angle, rotationAxis.get(0), rotationAxis.get(1),rotationAxis.get(2));

		// Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}

		// Restore original state
		gl.glPopMatrix();
	}

}
