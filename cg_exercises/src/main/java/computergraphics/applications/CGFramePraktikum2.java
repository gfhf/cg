/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import computergraphics.datastructures.HalfEdgeTriangleMesh;
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
public class CGFramePraktikum2 extends AbstractCGFrame {

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
    public CGFramePraktikum2(int timerInverval) {
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

        ColorNode colorNodeTriangle = new ColorNode(new Vector3(0.5, 0.2, 0.1));
        shaderNodeTriangle.addChild(colorNodeTriangle);
        nodeRegistry.put("colorNodeTriangle", colorNodeTriangle);

        HalfEdgeTriangleMesh mesh = new HalfEdgeTriangleMesh();
        mesh.addVertex(new Vertex(new Vector3(-0.5f, -0.5f, 0)));
        mesh.addVertex(new Vertex(new Vector3(0.5f, -0.5f, 0)));
        mesh.addVertex(new Vertex(new Vector3(0, 0.5f, 0)));
        mesh.addTriangle(0, 1, 2);
        mesh.addVertex(new Vertex(new Vector3(0.5f, -0.5f, 0)));
        mesh.addVertex(new Vertex(new Vector3(0.5f, 0.5f, 0)));
        
        mesh.addVertex(new Vertex(new Vector3(0, -0.5f, 0)));
        mesh.addTriangle(3, 4, 5);
        
        System.out.println("" + mesh.getNumberOfTriangles());
        mesh.computeTriangleNormals();

        TriangleMeshNode triMeshNode = new TriangleMeshNode(mesh);
        colorNodeTriangle.addChild(triMeshNode);
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

        } // if processing next key stroke
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        // The timer ticks every 1000 ms.
        new CGFramePraktikum2(1000);
    }
}