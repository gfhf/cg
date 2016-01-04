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
 * Scene graph node which represents a complex 3D model of a tree.
 */
public class ComplexTreeNode extends Node {

	private CuboidNode trunk;
	private SphereNode crown;

	public ComplexTreeNode(double scale) {

		ScaleNode scaleNode = new ScaleNode(new Vector3(scale, scale, scale));
		addChild(scaleNode);

		double widthTrunk = 0.07;
		double heigthTrunk = 0.07;
		double depthTrunk = 0.35;

		// TRUNK
		Vector3 brown = new Vector3(0.578125, 0.3671875, 0.2734375);
		ColorNode colorNodeTrunk = new ColorNode(brown);
		scaleNode.addChild(colorNodeTrunk);
		
		TranslationNode translationNodeTrunk = new TranslationNode(new Vector3(0,0,0.2));
		colorNodeTrunk.addChild(translationNodeTrunk);
		
		trunk = new CuboidNode(widthTrunk, heigthTrunk, depthTrunk);
		translationNodeTrunk.addChild(trunk);

		// CROWN
		Vector3 darkGreen = new Vector3(0.14453125, 0.5078125, 0.14453125);
		ColorNode colorNodeCrown = new ColorNode(darkGreen);
		scaleNode.addChild(colorNodeCrown);
		
		TranslationNode translatioNodeCrown = new TranslationNode(new Vector3(0,0,0.3));
		colorNodeCrown.addChild(translatioNodeCrown);
		
		double radius = 0.13;
		int resolution = 5;
		crown = new SphereNode(radius, resolution, new Vector3(1,1,0));
//		crown = new SphereNode(radius, resolution);
		translatioNodeCrown.addChild(crown);
	}

	@Override
	public void drawGl(GL2 gl) {
	    // Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}
	}

}
