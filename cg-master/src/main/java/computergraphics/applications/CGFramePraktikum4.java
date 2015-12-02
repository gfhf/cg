/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import computergraphics.datastructures.*;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.TriangulatedMeshNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Application for the first exercise.
 *
 * @author Philipp Jenke
 */
public class CGFramePraktikum4 extends AbstractCGFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4257130065274995543L;

    /**
     * Mapping an identifier to a Node.
     */
    private Map<String, Node> nodeRegistry = new HashMap<>();
    private HalfEdgeTriangleMesh mesh;

    /**
     * Constructor.
     */
    public CGFramePraktikum4(int timerInverval) {
        super(timerInverval);

        setupSceneGraph();

    }

    /**
     * Sets up the scene graph.
     */
    private void setupSceneGraph() {
    	ObjIO io = new ObjIO();

        IImpliciteFunction sphere = new ImpliciteSphere(1);

        MarchingCubes mq = new MarchingCubes(0.25, new Vector3(-2,-2,-2), new Vector3(2,2,2), 0, sphere);
    	mesh = mq.makeItSo();
        mesh.computeTriangleNormals();
        mesh.computeVertexNormals();
        mesh.calculateCurveColor();

    	// TRIANGLE
        // Shader node does the lighting computation
        ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
        ColorNode colorNode = new ColorNode(new Vector3(0.0, 1.0, 0.0));
        getRoot().addChild(shaderNode);
        shaderNode.addChild(colorNode);
        TriangulatedMeshNode triMeshNode = new TriangulatedMeshNode(mesh);
        colorNode.addChild(triMeshNode);
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

			 System.out.println("Key pressed: " + (char) keyCode);
			if (keyCode == (int) 'S') {
				// System.out.println("Pressed key: " + keyCode);
                ((TriangulatedMeshNode) getRoot().getChildNode(0).getChildNode(0).getChildNode(0)).laplace();
			}

			
		} // if processing next key stroke
	}

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        // The timer ticks every 1000 ms.
        new CGFramePraktikum4(1000);
    }
}
