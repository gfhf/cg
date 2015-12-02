/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import java.util.Random;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.ComplexBirdNode;
import computergraphics.scenegraph.ComplexTreeNode;
import computergraphics.scenegraph.CuboidNode;
import computergraphics.scenegraph.GroupNode;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.TranslationNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFramePraktikum1_1_Wood extends AbstractCGFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4257130065274995543L;

	/**
	 * Scale for the scene
	 */
	private final double SCALE = 1;

	/**
	 * For generating random numbers
	 */
	private Random random;

	/**
	 * Constructor.
	 */
	public CGFramePraktikum1_1_Wood(int timerInverval) {
		super(timerInverval);

		random = new Random();
		setupSceneGraph();
	}

	/**
	 * Sets up the scene graph.
	 */
	private void setupSceneGraph() {

		// shader
		ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
		getRoot().addChild(shaderNode);

		// color
		Vector3 lightGreen = new Vector3(0.14453125, 0.8671875, 0);
		ColorNode colorNode = new ColorNode(lightGreen);
		shaderNode.addChild(colorNode);

		// rotation
		Vector3 axis = new Vector3(1, 0, 0);
		double rotationAngle = 180;
		RotationNode rotationNode = new RotationNode(axis, rotationAngle);
		colorNode.addChild(rotationNode);

		ScaleNode scaleNodePlain = new ScaleNode(new Vector3(SCALE, SCALE, SCALE));
		rotationNode.addChild(scaleNodePlain);

		CuboidNode plainNode = new CuboidNode(3, 3, 0.001);
		scaleNodePlain.addChild(plainNode);

		createTrees(shaderNode);

		createBirds(shaderNode);
	}

	public void createTrees(ShaderNode shaderNode) {
		// TREEs
		GroupNode treesGroupNode = new GroupNode();
		shaderNode.addChild(treesGroupNode);

		int numberOfTrees = 70;
		for (int i = 0; i < numberOfTrees; i++) {
			int signMultiplier = getSign();
			double x = signMultiplier * random.nextDouble();
			signMultiplier = getSign();
			double y = signMultiplier * random.nextDouble();
			signMultiplier = getSign();
			double z = signMultiplier * random.nextDouble();
			// maximum
			z = z >= -0.022 ? -0.022 : z;
			// minimum
			z = z <= -0.1 ? -0.1 : z;

			TranslationNode translatioNodeTree = new TranslationNode(new Vector3(x, y, z));
			treesGroupNode.addChild(translatioNodeTree);

			RotationNode rotationNodeTree = new RotationNode(new Vector3(0,0,1), random.nextInt(360));
			translatioNodeTree.addChild(rotationNodeTree);
			
			ComplexTreeNode treeNode = new ComplexTreeNode(SCALE);
			rotationNodeTree.addChild(treeNode);
		}
	}

	public void createBirds(ShaderNode shaderNode) {
		// BIRD
		GroupNode birdsGroupNode = new GroupNode();
		shaderNode.addChild(birdsGroupNode);

		int numberOfBirds = 20;
		for (int i = 0; i < numberOfBirds; i++) {
			// get a positive or negative 1
			int signMultiplier = getSign();
			double x = signMultiplier * random.nextDouble();

			signMultiplier = getSign();
			double y = signMultiplier * random.nextDouble();

			signMultiplier = getSign();
			double z = signMultiplier * random.nextDouble();
			// set a maximum
			z = z >= 0.6 ? 0.6 : z;
			// set a minimum
			z = z <= 0.28 ? 0.28 : z;

			// initializing a random angle for circle animation
			RotationNode rotationNodeBird = new RotationNode(new Vector3(), random.nextInt(360));
			birdsGroupNode.addChild(rotationNodeBird);

			TranslationNode translatioNodeBird = new TranslationNode(new Vector3(x, y, z));
			rotationNodeBird.addChild(translatioNodeBird);

			double speed = signMultiplier * (random.nextDouble() * random.nextInt(4) + 1.1);
			
			ComplexBirdNode birdNode = new ComplexBirdNode(SCALE, speed);

			translatioNodeBird.addChild(birdNode);
		}
	}

	/**
	 * @return A positive or negative 1.
	 */
	private int getSign() {
		return random.nextBoolean() ? -1 : 1;
	}

	/*
	 * (nicht-Javadoc)
	 * 
	 * @see computergrafik.framework.ComputergrafikFrame#timerTick()
	 */
	@Override
	protected void timerTick() {
		// System.out.println(tickCounter);
		circleBirds();		
	}

	/**
	 * Circles birds around
	 */
	private void circleBirds() {
		GroupNode birdsGroupNode = (GroupNode) getRoot().getChildNode(0).getChildNode(2);
		for (Node child : birdsGroupNode.getAllChildren()) {
			RotationNode rotationBirdNode = (RotationNode) child;
			ComplexBirdNode birdNode = (ComplexBirdNode) rotationBirdNode.getChildNode(0).getChildNode(0);

			rotationBirdNode.setRotationAxis(new Vector3(0, 0, 1));
			double currentAngle = rotationBirdNode.getAngle();

			double speed = birdNode.getSpeed();
			rotationBirdNode.setAngle(currentAngle + speed);
			
			birdNode.update();
		}
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
		} // if processing next key stroke
	}

	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		// The timer ticks every 1000 ms.
		new CGFramePraktikum1_1_Wood(50);
	}
}
