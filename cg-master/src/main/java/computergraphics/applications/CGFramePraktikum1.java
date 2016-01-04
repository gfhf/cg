/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFramePraktikum1 extends AbstractCGFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4257130065274995543L;

	/**
	 * Mapping an identifier to a Node.
	 */
	private Map<String, Node> nodeRegistry = new HashMap<>();

	/**
	 * Constructor.
	 */
	public CGFramePraktikum1(int timerInverval) {
		super(timerInverval);

		setupSceneGraph();
	}

	/**
	 * Sets up the scene graph.
	 */
	private void setupSceneGraph() {
		// TRIANGLE
		// Shader node does the lighting computation
		ShaderNode shaderNodeTriangle = new ShaderNode(ShaderType.PHONG);
		getRoot().addChild(shaderNodeTriangle);
		shaderNodeTriangle.setParent(getRoot());

		ColorNode colorNodeTriangle = new ColorNode();
		shaderNodeTriangle.addChild(colorNodeTriangle);
		colorNodeTriangle.setParent(shaderNodeTriangle);
		nodeRegistry.put("colorNodeTriangle", colorNodeTriangle);

		TranslationNode translationNode = new TranslationNode(new Vector3());
		colorNodeTriangle.addChild(translationNode);
		translationNode.setParent(colorNodeTriangle);
		nodeRegistry.put("translationNodeTriangle", translationNode);

		ScaleNode scaleNodeTriangle = new ScaleNode(new Vector3(1,0,0));
		translationNode.addChild(scaleNodeTriangle);
		scaleNodeTriangle.setParent(translationNode);
		nodeRegistry.put("scaleNodeTriangle", scaleNodeTriangle);

		SingleTriangleNode triangleNode = new SingleTriangleNode();
		scaleNodeTriangle.addChild(triangleNode);
		triangleNode.setParent(scaleNodeTriangle);

		// SPHERE
		// Shader node does the lighting computation
		ShaderNode shaderNodeSphere = new ShaderNode(ShaderType.PHONG);
		getRoot().addChild(shaderNodeSphere);

		ColorNode colorNodeSphere = new ColorNode(0.75, 0.25, 0.25);
		shaderNodeSphere.addChild(colorNodeSphere);

		SphereNode sphereNode = new SphereNode(0.25, 20, new Vector3(1,0,0));
//		SphereNode sphereNode = new SphereNode(0.1, 20);
		colorNodeSphere.addChild(sphereNode);
	}

	/*
	 * (nicht-Javadoc)
	 * 
	 * @see computergrafik.framework.ComputergrafikFrame#timerTick()
	 */
	@Override
	protected void timerTick() {
		System.out.println("Tick");
	}

	/**
	 * The system time stamp of the last time a key has been pressed.
	 */
	private long lastKeyStroke = 0;

	/**
	 * Time in milliseconds the system is waiting after a key was pressed before
	 * processing another key stroke.
	 */
	private final long MS_BEFORE_PROCESSING_NEXT_KEY = 200;

	/**
	 * Called on key pressed. keyCode is holding the code for the pressed key.
	 */
	public void keyPressed(int keyCode) {

		long currentTime = System.currentTimeMillis();

		if (MS_BEFORE_PROCESSING_NEXT_KEY < currentTime - lastKeyStroke) {
			lastKeyStroke = currentTime;

			// System.out.println("Key pressed: " + (char) keyCode);
			// Change color of triangle randomly
			if (keyCode == (int) 'Q') {
				// System.out.println("Pressed key: " + keyCode);
				Random random = new Random();
				ColorNode colorNodeTriangle = (ColorNode) nodeRegistry.get("colorNodeTriangle");
				colorNodeTriangle.setColor(random.nextDouble(), random.nextDouble(), random.nextDouble());
			}

			// double scale of triangle replacing old scale node
			else if (keyCode == (int) 'E') {
				System.out.println("Scaling up");
				ScaleNode scaleNodeTriangle = (ScaleNode) nodeRegistry.get("scaleNodeTriangle");
				Node parentNode = scaleNodeTriangle.getParent();
				
				Vector3 currentScale = scaleNodeTriangle.getScale();
				Vector3 doubledScale = currentScale.multiply(2);

				ScaleNode doubledScaleNodeTriangle = new ScaleNode(doubledScale);
				parentNode.removeChild(scaleNodeTriangle);
				parentNode.addChild(doubledScaleNodeTriangle);
				doubledScaleNodeTriangle.setParent(parentNode);

				for (Node child : scaleNodeTriangle.getAllChildren()) {
					doubledScaleNodeTriangle.addChild(child);
					child.setParent(doubledScaleNodeTriangle);
				}
				nodeRegistry.put("scaleNodeTriangle", doubledScaleNodeTriangle);
			}

//			// double scale of triangle mounting the new node
//			else if (keyCode == (int) 'D') {
//				System.out.println("Scaling up");
//				// ScaleNode scaleNodeTriangle = (ScaleNode)
//				// nodeRegistry.get("scaleNodeTriangle");
//				Node parentNode = getRoot().getChildNode(0).getChildNode(0);
//				ScaleNode scaleNodeTriangle = (ScaleNode) parentNode.getChildNode(0);
//
//				Vector3 currentScale = scaleNodeTriangle.getScale();
//				Vector3 doubledScale = currentScale.multiply(2);
//
//				ScaleNode doubledScaleNodeTriangle = new ScaleNode(doubledScale);
//				parentNode.removeChild(scaleNodeTriangle);
//				parentNode.addChild(doubledScaleNodeTriangle);
//
//				doubledScaleNodeTriangle.addChild(scaleNodeTriangle);
//			}

			// half scale of triangle replacing old scale node
			else if (keyCode == (int) 'W') {
				System.out.println("Scaling down");
				ScaleNode scaleNodeTriangle = (ScaleNode) nodeRegistry.get("scaleNodeTriangle");
				Node parentNode = scaleNodeTriangle.getParent();
				
				Vector3 currentScale = scaleNodeTriangle.getScale();
				Vector3 halfedScale = currentScale.multiply(0.5);

				ScaleNode halfedScaleNodeTriangle = new ScaleNode(halfedScale);
				parentNode.removeChild(scaleNodeTriangle);
				parentNode.addChild(halfedScaleNodeTriangle);
				halfedScaleNodeTriangle.setParent(parentNode);

				for (Node child : scaleNodeTriangle.getAllChildren()) {
					halfedScaleNodeTriangle.addChild(child);
					child.setParent(halfedScaleNodeTriangle);
				}
				nodeRegistry.put("scaleNodeTriangle", halfedScaleNodeTriangle);
			}

//			// half scale of triangle mounting the new node
//			else if (keyCode == (int) 'S') {
//				System.out.println("Scaling down");
//				Node parentNode = getRoot().getChildNode(0).getChildNode(0);
//				ScaleNode scaleNodeTriangle = (ScaleNode) parentNode.getChildNode(0);
//
//				Vector3 currentScale = scaleNodeTriangle.getScale();
//				Vector3 halfedScale = currentScale.multiply(0.5);
//
//				ScaleNode halfedScaleNodeTriangle = new ScaleNode(halfedScale);
//				parentNode.removeChild(scaleNodeTriangle);
//				parentNode.addChild(halfedScaleNodeTriangle);
//
//				halfedScaleNodeTriangle.addChild(scaleNodeTriangle);
//			}

			// translate left by replacing node
			else if (keyCode == (int) 'R') {
				System.out.println("translating left");

				TranslationNode currentTranslationNode = (TranslationNode) nodeRegistry.get("translationNodeTriangle");
				Node parentNode = currentTranslationNode.getParent();
				parentNode.removeChild(currentTranslationNode);

				Vector3 translationLeft = currentTranslationNode.getTranslation().add(new Vector3(-1, 0, 0));
				
				TranslationNode replacementTranslationNode = new TranslationNode(translationLeft);

				parentNode.addChild(replacementTranslationNode);
				replacementTranslationNode.setParent(parentNode);

				for (Node child : currentTranslationNode.getAllChildren()) {
					replacementTranslationNode.addChild(child);
					child.setParent(replacementTranslationNode);					
				}
				nodeRegistry.put("translationNodeTriangle", replacementTranslationNode);
			}

			// translate right by replacing node
			else if (keyCode == (int) 'T') {
				System.out.println("translating left");

				TranslationNode currentTranslationNode = (TranslationNode) nodeRegistry.get("translationNodeTriangle");
				Node parentNode = currentTranslationNode.getParent();
				parentNode.removeChild(currentTranslationNode);

				Vector3 translationLeft = currentTranslationNode.getTranslation().add(new Vector3(1, 0, 0));
				
				TranslationNode replacementTranslationNode = new TranslationNode(translationLeft);

				parentNode.addChild(replacementTranslationNode);
				replacementTranslationNode.setParent(parentNode);

				for (Node child : currentTranslationNode.getAllChildren()) {
					replacementTranslationNode.addChild(child);
					child.setParent(replacementTranslationNode);					
				}
				nodeRegistry.put("translationNodeTriangle", replacementTranslationNode);
			}
		} // if processing next key stroke
	}

	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		// The timer ticks every 1000 ms.
		new CGFramePraktikum1(1000);
	}
}
