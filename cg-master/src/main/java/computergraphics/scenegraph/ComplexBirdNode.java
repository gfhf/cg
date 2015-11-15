package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector3;

/**
 * Scene graph node which represents a complex 3D model of a bird.
 */
public class ComplexBirdNode extends Node {
	/**
	 * The body of the bird.
	 */
	private CuboidNode body;
	/**
	 * The wing of a bird
	 */
	private SingleTriangleNode wing;

	/**
	 * GroupNode that has both wings as children.
	 */
	private GroupNode wingsGroupNode;

	/*
	 * Axis around the wings are moving
	 */
	private Vector3 flappingAxis = new Vector3(0, 1, 0);

	/*
	 * Speed wings are moving.
	 */
	private float flappingSpeed = 20;

	/*
	 * Speed of the bird
	 */
	private double speed;

	/**
	 * Getter.
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Setter.
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public ComplexBirdNode(double scale, double speed) {
		this.speed = speed;
		ScaleNode scaleNode = new ScaleNode(new Vector3(scale, scale, scale));
		addChild(scaleNode);

		double widthTrunk = 0.025;
		double heigthTrunk = 0.05;
		double depthTrunk = 0.015;

		// Body
		Vector3 blackish = new Vector3(0.0123, 0.0123, 0.0123);
		ColorNode colorNodeBody = new ColorNode(blackish);
		scaleNode.addChild(colorNodeBody);

		TranslationNode translationNodeBody = new TranslationNode(new Vector3(0, 0, 0.2));
		colorNodeBody.addChild(translationNodeBody);

		body = new CuboidNode(widthTrunk, heigthTrunk, depthTrunk);
		translationNodeBody.addChild(body);

		// Wings
		ColorNode colorNodeWing = new ColorNode(blackish);
		scaleNode.addChild(colorNodeWing);

		wingsGroupNode = new GroupNode();
		colorNodeWing.addChild(wingsGroupNode);

		// left wing
		ScaleNode scaleNodeWingLeft = new ScaleNode(new Vector3(0.06, 0.04, 1));
		wingsGroupNode.addChild(scaleNodeWingLeft);

		TranslationNode translatioNodeWingLeft = new TranslationNode(new Vector3(0.4, 0, 0.2));
		scaleNodeWingLeft.addChild(translatioNodeWingLeft);

		RotationNode rotationNodeWingLeft = new RotationNode(new Vector3(0, 0, 1), 0);
		translatioNodeWingLeft.addChild(rotationNodeWingLeft);
		
		wing = new SingleTriangleNode();
		rotationNodeWingLeft.addChild(wing);

		// right wing
		ScaleNode scaleNodeWingRight = new ScaleNode(new Vector3(-0.06, 0.04, 1));
		wingsGroupNode.addChild(scaleNodeWingRight);

		TranslationNode translatioNodeWingRight = new TranslationNode(new Vector3(0.4, 0, 0.2));
		scaleNodeWingRight.addChild(translatioNodeWingRight);
		
		RotationNode rotationNodeWingRight = new RotationNode(new Vector3(0, 0, 1), 0);
		translatioNodeWingRight.addChild(rotationNodeWingRight);

		wing = new SingleTriangleNode();
		rotationNodeWingRight.addChild(wing);
	}

	@Override
	public void drawGl(GL2 gl) {
		// Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}
	}

	/**
	 * To be called on a logic update.
	 */
	public void update() {
		for (Node child : wingsGroupNode.getAllChildren()) {
			RotationNode rotationNode = (RotationNode) child.getChildNode(0).getChildNode(0);
			double currentAngle = rotationNode.getAngle();

			if (currentAngle >= 80) {
				double currentY = flappingAxis.get(1);

				// "flip" axis
				flappingAxis = new Vector3(0, currentY * -1, 0);
				rotationNode.setAngle(10);
				//rotationNode.setRotationAxis(flappingAxis);
			}
			rotationNode.setAngle(currentAngle + flappingSpeed);
		}
	}
}
