/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ObjIO;
import computergraphics.datastructures.Vertex;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.*;
import computergraphics.scenegraph.ShaderNode.ShaderType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Application for the first exercise.
 *
 * @author Philipp Jenke
 */
public class CGFramePraktikum3 extends AbstractCGFrame {

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
    public CGFramePraktikum3(int timerInverval) {
        super(timerInverval);

        setupSceneGraph();
    }

    /**
     * Sets up the scene graph.
     */
    private void setupSceneGraph() {
    	ObjIO io = new ObjIO();
    	mesh = new HalfEdgeTriangleMesh();
    	io.einlesen(".\\obj\\cow.obj", mesh);
        mesh.computeTriangleNormals();
        mesh.computeVertexNormals();
        mesh.calculateCurveColor();
    	// TRIANGLE
        // Shader node does the lighting computation
        ShaderNode shaderNodeTriangle = new ShaderNode(ShaderType.PHONG);
        getRoot().addChild(shaderNodeTriangle);
        shaderNodeTriangle.setParent(getRoot());

        ColorNode colorNodeTriangle = new ColorNode(new Vector3(0.0, 1.0, 0.0));
        //shaderNodeTriangle.addChild(colorNodeTriangle);
        //colorNodeTriangle.setParent(shaderNodeTriangle);
        nodeRegistry.put("colorNodeTriangle", colorNodeTriangle);

        
//        mesh.addVertex(new Vertex(new Vector3(-0.5f, -0.5f, 0)));
//        mesh.addVertex(new Vertex(new Vector3(0.5f, -0.5f, 0)));
//        mesh.addVertex(new Vertex(new Vector3(0, 0.5f, 0)));
//        mesh.addVertex(new Vertex(new Vector3(2, 2, 2f)));
//        mesh.addVertex(new Vertex(new Vector3(2f, 0, 0)));
//        mesh.addVertex(new Vertex(new Vector3(0, 2f, 4f)));
//
//
//        mesh.addTriangle(0, 1, 2);
//        mesh.addTriangle(2, 3, 4);
//        mesh.addTriangle(0, 2, 4);

        
        
        
        
        TriangulatedMeshNode triMeshNode = new TriangulatedMeshNode(mesh);
        shaderNodeTriangle.addChild(triMeshNode);
        triMeshNode.setParent(shaderNodeTriangle);
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
				getRoot().getChildNode(0).removeChild(0);//(new LaplaceNode(mesh));
				getRoot().getChildNode(0).addChild(new LaplaceNode(mesh));
			}

			
		} // if processing next key stroke
	}

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        // The timer ticks every 1000 ms.
        new CGFramePraktikum3(1000);
    }
}
