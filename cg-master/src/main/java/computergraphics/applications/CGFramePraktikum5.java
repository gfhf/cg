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
public class CGFramePraktikum5 extends AbstractCGFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4257130065274995543L;

    /**
     * Mapping an identifier to a Node.
     */
    private Map<String, Node> nodeRegistry = new HashMap<>();
    private HalfEdgeTriangleMesh mesh;
    private CurveNode _monomCurveNode;
    private CurveNode _bezierCurveNode;


    /**
     * Constructor.
     */
    public CGFramePraktikum5(int timerInverval) {
        super(timerInverval);

        setupSceneGraph();

    }

    /**
     * Sets up the scene graph.
     */
    private void setupSceneGraph() {
        Vector3[] set1 = new Vector3[3];
        set1[0] = new Vector3(0, 0, 0);
        set1[1] = new Vector3(0.5, 0.5, 0);
        set1[2] = new Vector3(0, 1, 0);

        Vector3[] set2 = new Vector3[3];
        set2[0] = new Vector3(0.5, 0, 0);
        set2[1] = new Vector3(0, 0.5, 0);
        set2[2] = new Vector3(0.5, 1, 0);

        AbstractCurve monomCurve = new MonomCurve(set1);
        AbstractCurve bezierCurve = new BezierCurve(set1);

        _monomCurveNode = new CurveNode(monomCurve);
        _bezierCurveNode = new CurveNode(bezierCurve);


        // TRIANGLE
        // Shader node does the lighting computation
        ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
        getRoot().addChild(shaderNode);

        Random rdm = new Random();

        ColorNode colorNode = new ColorNode(0, 0, 0);
        shaderNode.addChild(colorNode);
        colorNode.addChild(_monomCurveNode);

        ColorNode lineColorNode = new ColorNode(0, 0, 0);
        shaderNode.addChild(colorNode);


        Vector3 lineNode1 = null;

        for (int i = 0; i < set1.length; i++) {
            TranslationNode translationNodeControlPoint = new TranslationNode(monomCurve.getControlPoints()[i]);
            colorNode.addChild(translationNodeControlPoint);
            SphereNode controlPoint = new SphereNode(0.01, 10);
            translationNodeControlPoint.addChild(controlPoint);

            if(lineNode1 != null){
//                colorNode.addChild(new LineNode(lineNode1, monomCurve.getControlPoints()[i]));
            }
//            lineNode1 = monomCurve.getControlPoints()[i];
        }

        for (int i = 0; i < set1.length; i++) {
            TranslationNode translationNodeControlPoint = new TranslationNode(bezierCurve.getControlPoints()[i]);
            colorNode.addChild(translationNodeControlPoint);
            SphereNode controlPoint = new SphereNode(0.01, 10);
            translationNodeControlPoint.addChild(controlPoint);

            if(lineNode1 != null){
//                colorNode.addChild(new LineNode(lineNode1, bezierCurve.getControlPoints()[i]));
            }
//            lineNode1 = bezierCurve.getControlPoints()[i];
        }

        colorNode = new ColorNode(1, 0, 0);
        shaderNode.addChild(colorNode);
        colorNode.addChild(_bezierCurveNode);
    }

    /*
     * (nicht-Javadoc)
     *
     * @see computergrafik.framework.ComputergrafikFrame#timerTick()
     */
    @Override
    protected void timerTick() {
        System.out.println("Tick");
        if (_monomCurveNode.get_tangentPoint() < 1) {
            _monomCurveNode.set_tangentPoint(_monomCurveNode.get_tangentPoint() + 0.005);
            _bezierCurveNode.set_tangentPoint(_bezierCurveNode.get_tangentPoint() + 0.005);
        } else {
            _monomCurveNode.set_tangentPoint(0);
            _bezierCurveNode.set_tangentPoint(0);
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
            System.out.println("Key pressed: " + (char) keyCode);
            if (keyCode == (int) 'Q') {

                if (_monomCurveNode.get_tangentPoint() < 1) {
                    _monomCurveNode.set_tangentPoint(_monomCurveNode.get_tangentPoint() + 0.05);
                    _bezierCurveNode.set_tangentPoint(_bezierCurveNode.get_tangentPoint() + 0.05);
                } else {
                    _monomCurveNode.set_tangentPoint(0);
                    _bezierCurveNode.set_tangentPoint(0);
                }
            }
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        // The timer ticks every 1000 ms.
        new CGFramePraktikum5(100);
    }
}
