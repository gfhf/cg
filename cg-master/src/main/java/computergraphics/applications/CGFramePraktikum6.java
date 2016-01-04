/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import java.util.HashMap;
import java.util.Map;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.*;
import computergraphics.scenegraph.ShaderNode.ShaderType;

/**
 * Application for the first exercise.
 *
 * @author Philipp Jenke
 */
public class CGFramePraktikum6 extends AbstractCGFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4257130065274995543L;

    /**
     * Constructor.
     */
    public CGFramePraktikum6(int timerInverval) {
        super(timerInverval);

        setupSceneGraph();
    }

    /**
     * Sets up the scene graph.
     */
    private void setupSceneGraph() {
        // TRIANGLE
        // Shader node does the lighting computation
        ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
        getRoot().addChild(shaderNode);

        ColorNode colorNode =  new ColorNode(0.75, 0.25, 0.25);
        shaderNode.addChild(colorNode);

        Vector3 support = new Vector3(0, 0, 0);
        Vector3 u = new Vector3(1, 0, support.getZ());
        Vector3 v = new Vector3(0, 1, support.getZ());
        PlainNode plainNode = new PlainNode(support, u, v, 1, 1);
        colorNode.addChild(plainNode);

//        // SPHERE
//        // Shader node does the lighting computation
//        ShaderNode shaderNodeSphere = new ShaderNode(ShaderType.PHONG);
//        getRoot().addChild(shaderNodeSphere);
//
//        ColorNode colorNodeSphere = new ColorNode(0.75, 0.25, 0.25);
//        shaderNodeSphere.addChild(colorNodeSphere);
//
//        SphereNode sphereNode = new SphereNode(0.25, 20, new Vector3(1,0,0));
////        CuboidNode cu = new CuboidNode(1,1,1);
//        colorNodeSphere.addChild(cu);
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
     * Called on key pressed. keyCode is holding the code for the pressed key.
     */
    public void keyPressed(int keyCode) {
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        // The timer ticks every 1000 ms.
        new CGFramePraktikum6(1000);
    }
}
